package it.polimi.ingsw.client;


import it.polimi.ingsw.client.View.CLI.CLIMain;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.*;
import it.polimi.ingsw.utils.JsonReader;
import org.json.simple.parser.ParseException;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.net.Socket;


public class NetworkerTcp implements Networker, PropertyChangeListener {
    private static int port;
    private static String host;
    Socket socket ;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private View view;
    private final ClientState clientState;

    public NetworkerTcp(ClientState clientState,String host) {
        JsonReader config;
        try {
            InputStream is=this.getClass().getClassLoader().getResourceAsStream("ConnectionPorts.json");
            config=new JsonReader(is);
//            config = new JsonReader("src/main/resources/NetworkerTcp.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        this.clientState = clientState;
        this.host=host;
        port=config.getInt("tcpPort");
    }

    public void initializeConnection() {
        Reader reader;
        try {
            socket = new Socket(host, port);
            oos = new ObjectOutputStream(socket.getOutputStream());
            reader=new Reader(socket,oos,this, clientState);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        reader.start();
    }


    public void firstConnection (Message username){
        try {
            oos.writeObject(username);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void numberOfPlayersSelection(Message numberOfPlayers){
        try {
            oos.writeObject(numberOfPlayers);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void removeTilesFromBoard(Message tiles){
        try {
            oos.writeObject(tiles);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void switchTilesOrder(Message ints){
        try {
            oos.writeObject(ints);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addTilesToBookshelf (Message column){
        try {
            oos.writeObject(column);
            oos.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        view.receivedMessage((Message) evt.getNewValue());
    }
    @Override
    public void setView(View view) {
        this.view=view;
    }

}
