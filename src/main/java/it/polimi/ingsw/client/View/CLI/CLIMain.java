package it.polimi.ingsw.client.View.CLI;

import it.polimi.ingsw.client.ClientState;
import it.polimi.ingsw.client.Networker;
import it.polimi.ingsw.client.View.View;
import it.polimi.ingsw.utils.Messages.Message;

public class CLIMain implements View {

    private final Object lock; //su cosa lockare - comune con ClientState
    private final ClientState clientState; //da dove leggere cambiamenti view
    private final Networker net; //a chi mandare messaggi

    private static CLIPrint cliPrint;
    private static ReadShell readShell;
    private boolean IHaveToRequestTheUsername=true;

    public CLIMain(Object lock, ClientState clientState, Networker net) {
        this.lock = lock;
        this.clientState = clientState;
        this.net = net;
        //net.setUserInterface(this);
    }

    public Object getLock() {
        return lock;
    }

    public ClientState getClientState() {
        return clientState;
    }

    public Networker getNet() {
        return net;
    }

    public CLIPrint getCliPrint() {
        return cliPrint;
    }

    public ReadShell getReadShell() {
        return readShell;
    }

    public boolean isIHaveToRequestTheUsername() {
        return IHaveToRequestTheUsername;
    }

    public void setIHaveToRequestTheUsername(boolean IHaveToRequestTheUsername) {
        this.IHaveToRequestTheUsername = IHaveToRequestTheUsername;
    }

    @Override
    public void receivedMessage(Message message){
        switch (message.getType()){
            case NEW_LOBBY -> readShell.askNumberOfPlayerMessage();
            case WAITING_FOR_PLAYERS -> clientState.setWaiting(true);
            case ERROR -> {
                cliPrint.printError(message.getUsername());
                if (message.getUsername().equals("Username already taken")){
                    readShell.askUsername();
                }
            }
            case OK -> {
                if (message.getUsername().equals("Move successful remove tiles")){
                    cliPrint.printOrderTiles(clientState.getSelectedTiles());
                }
                else if (message.getUsername().equals("Move successful swap order")) {
                    cliPrint.printOrderTiles(clientState.getSelectedTiles());
                }
            }
            default -> {
                break;
            }
        }
    }



    public void runUI() {
        cliPrint=new CLIPrint(this);
        readShell=new ReadShell(this);

        //richiesta username
        readShell.askUsername();



        while (!clientState.gameHasStarted()){
            if (clientState.isWaiting()){
                cliPrint.printWaiting();
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        clientState.setChair(clientState.getCurrentPlayer());
        Thread th1=new Thread(readShell);
        th1.start();
        //inizia la partita
        cliPrint.clearSheel();
        cliPrint.gameHasStarted();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        cliPrint.printChair();
        cliPrint.printCommonObjective(clientState.getGameCommonObjective());
        cliPrint.playerTurn();
        //ho già stampato il primo turno di gioco
        String curr=clientState.getNextPlayer();

        while (!clientState.isGameIsEnded()){
            //stampa nuovo turno se il current è il next di prima
           if (clientState.getCurrentPlayer().equals(curr)){
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   throw new RuntimeException(e);
               }
               cliPrint.clearSheel();
               cliPrint.playerTurn();
               curr=clientState.getNextPlayer();
          }

        }

        //è finita la partita
        cliPrint.clearSheel();
        cliPrint.printEndGame();
        //eliminiamo il thread
        th1.interrupt();


        /*
            richieste iniziali

            waiting_for_players
                ciclo gameHasStarted!=true

            inizia partita
                ciclo gameIsEnded!=true

            fine partita
         */

    }





}
