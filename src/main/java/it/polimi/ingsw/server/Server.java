package it.polimi.ingsw.server;

import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.utils.JsonReader;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server extends UnicastRemoteObject {
    private JsonReader config;
    private static Integer port;     // Da sistemare
    private final static ArrayList <ServerClientHandler> clientList = new ArrayList<>();
    private final Lobby lobby = new Lobby();
    private final Controller controller= new Controller(lobby);
    private final RMIHandlerInterface rmiHandler = new RMIHandler(controller);

    protected Server() throws RemoteException, IOException, ParseException{
        super();
        InputStream is=this.getClass().getClassLoader().getResourceAsStream("Server.json");
        config=new JsonReader(is);
//        config = new JsonReader("src/main/resources/Server.json");
        port=config.getInt("port");
    }

    public static void main( String[] args ) throws RemoteException {
        Server EchoServer = null;
        try {
            EchoServer = new Server();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        try {
            EchoServer.startServer();
        }
        catch (IOException e){
            System.err.println(e.getMessage());
        }


    }

    public void startServer() throws IOException{
        ExecutorService executor = Executors.newCachedThreadPool();
        ServerSocket serverSocket;
        lobby.setController(controller);

        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            System.err.println(e.getMessage()); // Porta non disponibile
            return;
        }

        // Preparing for the RMI connections
        RMIHandlerInterface stub = null;
        try {
            stub = (RMIHandlerInterface) UnicastRemoteObject.exportObject(rmiHandler, 0);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        // Bind the remote object's stub in the registry
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(1099);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            registry.bind("RMIHandler", stub);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }

        System.out.println("Server pronto");
        while (true) {
            try {
                Socket socket = serverSocket.accept();
                clientList.add(new ServerClientHandler(socket, controller));
                executor.submit(clientList.get(clientList.size()-1));
            } catch (IOException e) {
                break;
            }
        }
        executor.shutdown();
    }

    public static ArrayList<ServerClientHandler> getClientList() {
        return clientList;
    }
    public void removeClient(ServerClientHandler client){
        clientList.remove(client);
}

}
