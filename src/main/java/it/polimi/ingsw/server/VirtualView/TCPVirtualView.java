package it.polimi.ingsw.server.VirtualView;

import it.polimi.ingsw.utils.Messages.*;
import it.polimi.ingsw.utils.Messages.MessageTypes;
import it.polimi.ingsw.utils.Messages.ViewMessage;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TCPVirtualView extends VirtualView{

    private final ObjectOutputStream oos;

    public TCPVirtualView(String username,ObjectOutputStream os) {
        this.oos = os;
    public TCPVirtualView(String username,Socket socket, ObjectOutputStream oos) throws IOException {
        this.socket = socket;
        this.oos = oos;
        this.setUsername(username);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        //objectName           Object

        //board                Matrix
        //bookshelf w/ name    Matrix
        //currPlayer           String
        //playerNames          List<String>
        //commonObj            List<Integer>
        //publicPoints w/ name List<Integer>
        //selectedTiles        List<Tiles>
        //personalObj          HashMap
        //privatePoints        int
        //Chat                 Message??

        //w/name vuol dire che nel campo username del messaggio scrivo
        //lo username del proprietario


        ViewMessage viewMsg = new ViewMessage();
        viewMsg.setType(MessageTypes.VIEW);
        viewMsg.setContent(evt.getSource());
        viewMsg.setObjectName((String)evt.getNewValue());
        viewMsg.setUsername((String) evt.getOldValue());


        try {
            oos.writeObject(viewMsg);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
