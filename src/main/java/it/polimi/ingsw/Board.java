package it.polimi.ingsw;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Board {
//attributi
    private static Matrix gamesBoard=new Matrix(9, 9);
    private int numberOfPlayer;
    private Sachet boardSachet;

    //creazione gamesBoard vera e propria -->
    // --> vera board (celle che non fanno parte tabellone settati a NOTALLOWED e altre a EMPTY)
    // --> board in base al numero di giocatori creata nel costruttore  Board(int numberOfPlayer)
    static {
        //tutta matrice EMPTY
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                gamesBoard.setEmpty(i, j);
            }
        }
        //posizioni matrice che NON possono essere inserite tessere
        //riga 0
        for (int i = 0; i <= 2; i++) {
            gamesBoard.setNotAllowed(0, i);
        }
        for (int i = 5; i <= 8; i++) {
            gamesBoard.setNotAllowed(0, i);
        }
        //riga 1
        for (int i = 0; i <= 2; i++) {
            gamesBoard.setNotAllowed(1, i);
        }
        for (int i = 6; i <= 8; i++) {
            gamesBoard.setNotAllowed(0, i);
        }
        //riga 2
        for (int i = 0; i <= 1; i++) {
            gamesBoard.setNotAllowed(2, i);
        }
        for (int i = 7; i <= 8; i++) {
            gamesBoard.setNotAllowed(2, i);
        }
        //riga 3
        gamesBoard.setNotAllowed(3, 0);
        //riga 4
        //riga 5
        gamesBoard.setNotAllowed(5, 8);
        //riga 6
        for (int i = 0; i <= 1; i++) {
            gamesBoard.setNotAllowed(6, i);
        }
        for (int i = 7; i <= 8; i++) {
            gamesBoard.setNotAllowed(6, i);
        }
        //riga 7
        for (int i = 0; i <= 2; i++) {
            gamesBoard.setNotAllowed(7, i);
        }
        for (int i = 6; i <= 8; i++) {
            gamesBoard.setNotAllowed(7, i);
        }
        //riga 8
        for (int i = 0; i <= 3; i++) {
            gamesBoard.setNotAllowed(8, i);
        }
        for (int i = 6; i <= 8; i++) {
            gamesBoard.setNotAllowed(8, i);
        }
    }

//-------------------------------------------------------------------------------------------------------\\

//metodi
    //costruttore --> assegna numberOfPlayer
    //            --> crea la Board in base al numero di giocatori
        // RICHIEDE CHE numberOfPlayer SIA UN NUMERO TRA 2, 3, 4
    public Board(int numberOfPlayer) {
        this.numberOfPlayer = numberOfPlayer;
        switch (numberOfPlayer){
            case 2:
                gamesBoard.setNotAllowed(0, 3);
                gamesBoard.setNotAllowed(0, 4);
                gamesBoard.setNotAllowed(1, 5);
                gamesBoard.setNotAllowed(2, 2);
                gamesBoard.setNotAllowed(2, 6);
                gamesBoard.setNotAllowed(3, 1);
                gamesBoard.setNotAllowed(3, 8);
                gamesBoard.setNotAllowed(4, 0);
                gamesBoard.setNotAllowed(4, 8);
                gamesBoard.setNotAllowed(5, 0);
                gamesBoard.setNotAllowed(5, 7);
                gamesBoard.setNotAllowed(6, 2);
                gamesBoard.setNotAllowed(6, 6);
                gamesBoard.setNotAllowed(7, 3);
                gamesBoard.setNotAllowed(8, 4);
                gamesBoard.setNotAllowed(8, 5);
                break;
            case 3:
                gamesBoard.setNotAllowed(0, 4);
                gamesBoard.setNotAllowed(1, 5);
                gamesBoard.setNotAllowed(3, 1);
                gamesBoard.setNotAllowed(4, 0);
                gamesBoard.setNotAllowed(4, 8);
                gamesBoard.setNotAllowed(5, 7);
                gamesBoard.setNotAllowed(7, 3);
                gamesBoard.setNotAllowed(8, 4);
                break;
        }
    }
    //costruttore --> assegna boardSachet
    public Board(Sachet boardSachet) {
        this.boardSachet = boardSachet;
    }
    //costruttore --> assegna numberOfPlayer e boardSachet
    //            --> crea la Board in base al numero di giocatori
        // RICHIEDE CHE numberOfPlayer SIA UN NUMERO TRA 2, 3, 4
    public Board(int numberOfPlayer, Sachet boardSachet) {
        this.numberOfPlayer = numberOfPlayer;
        switch (numberOfPlayer){
            case 2:
                gamesBoard.setNotAllowed(0, 3);
                gamesBoard.setNotAllowed(0, 4);
                gamesBoard.setNotAllowed(1, 5);
                gamesBoard.setNotAllowed(2, 2);
                gamesBoard.setNotAllowed(2, 6);
                gamesBoard.setNotAllowed(3, 1);
                gamesBoard.setNotAllowed(3, 8);
                gamesBoard.setNotAllowed(4, 0);
                gamesBoard.setNotAllowed(4, 8);
                gamesBoard.setNotAllowed(5, 0);
                gamesBoard.setNotAllowed(5, 7);
                gamesBoard.setNotAllowed(6, 2);
                gamesBoard.setNotAllowed(6, 6);
                gamesBoard.setNotAllowed(7, 3);
                gamesBoard.setNotAllowed(8, 4);
                gamesBoard.setNotAllowed(8, 5);
                break;
            case 3:
                gamesBoard.setNotAllowed(0, 4);
                gamesBoard.setNotAllowed(1, 5);
                gamesBoard.setNotAllowed(3, 1);
                gamesBoard.setNotAllowed(4, 0);
                gamesBoard.setNotAllowed(4, 8);
                gamesBoard.setNotAllowed(5, 7);
                gamesBoard.setNotAllowed(7, 3);
                gamesBoard.setNotAllowed(8, 4);
                break;
        }
        this.boardSachet = boardSachet;
    }

    //getter gamesBoard --> ritorna la Matrix
    public static Matrix getGamesBoard() {
        return gamesBoard;
    }
    //getter numberOfPlayer --> ritorna il numero di giocatori
    public int getNumberOfPlayer() {
        return numberOfPlayer;
    }



    //riempimento matrice iniziale - OK
    public void BoardInitialization(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //se tiles==EMPTY riempe randomicamente
                if (gamesBoard.getTile(i, j).equals(Tiles.EMPTY)){
                    //in posizione i,j scegli randomicamente le tiles --> sachet.draw()
                    PlaceTiles(boardSachet.draw(), i, j);
                }
            }
        }
    }

    //ritorna true se la board deve essere resettata - OK
    public boolean checkBoardReset(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                //per tutte le tiles!=NOTALLOWED && !=EMPTY
                if (!gamesBoard.getTile(i, j).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(i, j).equals(Tiles.EMPTY)){
                    //se adiacente c'è almeno 1 tiles ==> return false
                    //se NON siamo sul bordo della board
                    if ((i!=0 && i!=8) ||
                        (j!=0 && j!=8)){
                        //adiacenza:
                        //sx
                        if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //dx
                        if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //alto
                        if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                            return false;
                        }
                        //basso
                        if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                            !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                            return false;
                        }
                    }
                    //se siamo sul bordo alto della board
                    else if (i==0){
                        switch (j){
                            case 0: //angolo in alto a sx
                                //adiacenza solo:
                                //dx
                                if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                                    !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                //basso
                                if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                break;
                            case 8: //angolo in alto a dx
                                //adiacenza solo:
                                //sx
                                if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                                    !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                //basso
                                if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                                    !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                break;
                            default: //riga alta meno angoli alti
                                //adiacenza solo:
                                // sx
                                if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                                    !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // dx
                                if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                                    !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // basso
                                if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                                    !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                break;
                        }

                    }
                    //se siamo sul bordo basso della board
                    else if (i==8){
                        switch (j){
                            case 0: //siamo nell'angolo in basso a sx
                                //adicenza solo:
                                // sx
                                if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // alto
                                if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                break;
                            case 8: //siamo nell'angolo in basso a dx
                                //adicenza solo:
                                // dx
                                if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // alto
                                if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                break;
                            default: //riga bassa meno angoli bassi
                                //adicenza solo:
                                // sx
                                if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // dx
                                if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // alto
                                if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                break;
                        }
                    }
                    //se siamo sul bordo sx della board
                    else if (j==0){
                        switch (i){
                            case 0: //siamo nell'angolo sx alto
                                //adiacenza solo:
                                // basso
                                if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // dx
                                if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                break;
                            case 8: //siamo nell'angolo sx basso
                                //adiacenza solo:
                                // alto
                                if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // dx
                                if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                break;
                            default: //colonna sx meno angolo sx alto e basso
                                //adiacenza solo:
                                // alto
                                if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // basso
                                if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // dx
                                if (!gamesBoard.getTile(i+1, j).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                break;
                        }
                    }
                    //se siamo sul bordo dx della board
                    else if (j == 8) {
                        switch (i){
                            case 0: //siamo nell'angolo dx in alto
                                //adiacenza solo:
                                // basso
                                if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // sx
                                if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                break;
                            case 8: //siamo nell'angolo dx in basso
                                //adiacenza solo:
                                // alto
                                if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // sx
                                if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                break;
                            default: //colonna dx meno angolo dx alto e basso
                                //adiacenza solo:
                                // alto
                                if (!gamesBoard.getTile(i, j-1).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // basso
                                if (!gamesBoard.getTile(i, j+1).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                // sx
                                if (!gamesBoard.getTile(i-1, j).equals(Tiles.NOTALLOWED) &&
                                        !gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                                    return false;
                                }
                                break;
                        }
                    }
                }
            }
        }
        //se supera i 2 for ==> non ci sono tiles adiacenti con altre tiles
        return true;
    }

    //resetta la board - versione ENG - OK
    //1. rimuovere tiles rimanenti e rimette in sachet
    //2. riempe board con tiles rimantenti nel sachet
    public void boardResetENG(){
        //ricerca tiles!=EMPTY && tiles!=NOTALLOWED
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (!gamesBoard.getTile(i, j).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(i, j).equals(Tiles.EMPTY)) {
                    //chiama addTiles --> aggiunge colore al Sachet
                    boardSachet.addTiles(gamesBoard.getTile(i, j));
                    //chiama remove   --> rimuovi effettivamente tiles da board
                    gamesBoard.remove(i, j);
                }
            }
        }
        //riempe board con tiles presenti nel sachet --> chiama BoardInitialization()
        BoardInitialization();
    }

    //resetta la board - versione ITA - OK
    //0. lascia le tiles rimaste sulla board
    //1. riempe board con tiles rimanenti nel sachet
    public void boardResetITA(){
        //riempe board con tiles presenti nel sachet --> chiama BoardInitialization()
        BoardInitialization();
    }


    //aggiunge tiles nella board - OK
    public void PlaceTiles(Tiles tile, int row, int col){
        gamesBoard.setTile(tile, row, col);
    }

    //rimuove i tiles nelle posizioni indicate - OK
        //RICHIEDE CHE List.size() SIA COMPRESA TRA 1, 2, 3
    public void remove(List<Point> position){
        //per ogni elemento nella position rimuovi il tiles da gamesBoard
        for (int i = 0; i < position.size(); i++) {
            gamesBoard.remove(position.get(i).x, position.get(i).y);
        }
    }

    //ritorna TRUE se tiles è libera --> ha almeno un lato libero - OK
        //RICHIEDE CHE position APPARTIENE ALLA BOARD VERA --> croce storta
    public boolean checkFreeTiles(Point position){
        int i=position.x;
        int j=position.y;
        //se NON siamo sul bordo
        if ((position.x!=0 && position.x!=8)||
            (position.y!=0 && position.y!=8)){
            //sx
            if (gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                return true;
            }
            //dx
            if (gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                return true;
            }
            //alto
            if (gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                return true;
            }
            //basso
            if (gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                return true;
            }
        }
        //se position è riga alto
        if (position.x==0){
            switch (position.y){
                case 0: //angolo in alto a sx
                    //sx
                    if (gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //basso
                    if (gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    break;
                case 8: //angolo in alto a dx
                    //dx
                    if (gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //basso
                    if (gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    break;
                default:
                    //sx
                    if (gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //dx
                    if (gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //basso
                    if (gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    break;
            }
        }
        //se position è riga basso
        else if (position.x==8){
            switch (position.y){
                case 0: //angolo in basso a sx
                    //sx
                    if (gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //alto
                    if (gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    break;
                case 8: //angolo in basso a dx
                    //dx
                    if (gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //alto
                    if (gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    break;
                default:
                    //sx
                    if (gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //dx
                    if (gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //alto
                    if (gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    break;
            }
        }
        //se position è colonna sx
        else if (position.y==0){
            switch (position.x){
                case 0: //angolo a sx in alto
                    //sx
                    if (gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //basso
                    if (gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    break;
                case 8: //angolo a dx in basso
                    //sx
                    if (gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //alto
                    if (gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    break;
                default:
                    //dx
                    if (gamesBoard.getTile(i+1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //alto
                    if (gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //basso
                    if (gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    break;
            }
        }
        //se position è colonna dx
        else if (position.y==8){
            switch (position.x){
                case 0: //angolo a dx in alto
                    //sx
                    if (gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //basso
                    if (gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    break;
                case 8: //angolo a dx in basso
                    //sx
                    if (gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //alto
                    if (gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    break;
                default:
                    //sx
                    if (gamesBoard.getTile(i-1, j).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //alto
                    if (gamesBoard.getTile(i, j-1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    //basso
                    if (gamesBoard.getTile(i, j+1).equals(Tiles.EMPTY)){
                        return true;
                    }
                    break;
            }
        }
        //se siamo arrivati qui vuol dire che tile NON è libera
        return false;
    }

    //ritorna la posizione delle tiles libere - OK
    public ArrayList<Point> freeTiles(){
        ArrayList<Point> result=new ArrayList<Point>();
        Point position=new Point();
        //giriamo tutta la board
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                position.setLocation(i, j);
                //ricerca delle tiles!=NOTALLOWED e !=EMPTY
                //              tiles libere
                if (!gamesBoard.getTile(i, j).equals(Tiles.NOTALLOWED) &&
                    !gamesBoard.getTile(i, j).equals(Tiles.EMPTY) &&
                    checkFreeTiles(position)){
                    //aggiungi position all'ArrayList delle tiles libere
                    result.add(position);
                }
            }
        }
        //ritorna il result
        return result;
    }

    //ritorna TRUE se nella List i Point sono adiacenti --> hanno un lato in comune
    //adiacenza su board --> sono sulla stessa riga/colonna
        //RICHIEDE CHE NON CI SIANO PIÙ Point UGUALI --> STESSA X E STESSA Y
    public boolean checkAdjacentTiles(List<Point> position){
        return false;

    }

    //ritorna la posizione delle tiles adiacenti a quella in ingresso
    public ArrayList<Point> adjacentTiles(Point tile){
        ArrayList<Point> result=new ArrayList<>();


        return result;
    }





}


//checkAdiacenze