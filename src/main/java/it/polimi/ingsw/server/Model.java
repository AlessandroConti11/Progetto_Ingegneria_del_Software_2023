package it.polimi.ingsw.server;

import it.polimi.ingsw.server.CommonObjective.CommonObjective;
import it.polimi.ingsw.server.Exceptions.*;
import it.polimi.ingsw.server.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.server.View.View;
import org.javatuples.Pair;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;


public class Model  {

    private GameState state = GameState.STARTING;
    private Board board;
    private ArrayList<Player> players;
    private ArrayList<View> virtualViews;
    private ArrayList<CommonObjective> commonobj = new ArrayList<>();

    private Player currPlayer;
    private Player nextPlayer;
    private Player winner;

    private ArrayList<Tiles> selectedTiles = new ArrayList<>();

    private boolean isFinished = false;

    private final PropertyChangeSupport notifier = new PropertyChangeSupport(this);


    //Probably temporary, just used for notification
    private ArrayList<Integer> privatePoints = new ArrayList<>();

    private ArrayList<Integer> publicPoints = new ArrayList<>();



    //Constructors

    public Model(ArrayList<Player> players) {
        this.players = players;
    }

    public Model(ArrayList<Player> players, ArrayList<View> views) {
        this.players = players;
        this.virtualViews = views;
        this.currPlayer = players.get(0);
        this.nextPlayer = players.get(1);
    }






    //PUBLIC METHODS

    /**
     * Initializes the board and the objectives:
     * Create and initialize the board,initialize common and personal objectives,
     * add the views as change listeners,initialize the arrays of points.
     */
    public void initialization()  {

        //Create and initialize the board
        board = new Board(players.size(), new Sachet());
        board.BoardInitialization();

        //Initialize common and personal objectives
        commonobjInit();
        personalobjInit();


        //Initializes the arrays of points
        for(Player p : players){
            privatePoints.add(p.getPrivatePoint());
            publicPoints.add(p.getPublicPoint());
        }


        //Add the views as change listeners
        for (View v : virtualViews){
            notifier.addPropertyChangeListener(v);
        }


        //Change game state
        state = GameState.CHOOSING_TILES;

    }


    /**
     * Removes an array of tiles from the board if the move is legitimate.
     * Also notifies the views of changes
     * @param points  The position of the tiles
     * @throws MoveNotPossible if the game is not in the right state
     * @throws NotCurrentPlayer if the player is not the current player
     * @throws IllegalArgumentException if the array points is null or the array is too long
     * @throws TilesNotAdjacent if the tiles are not adjacent
     * @throws OutOfDomain if at least one of the points is outside the board
     * @throws TilesCannotBeSelected if at least one of the selected tiles is either Empty or Not Allowed
     */
    public void removeTileArray(Player player,ArrayList<Point> points) throws MoveNotPossible{

        //Checks move legitimacy
        checkRemoveLegit(points,player);

        //Change game state
        state = GameState.CHOOSING_ORDER;

        //Notify the views and add the removed tiles to the selectedTiles array
        for (Point point : points) {

            //Adding the removed tiles to selectedTiles array
            selectedTiles.add(board.getGamesBoard().getTile(point));

            //notification
            Pair<Tiles, Point> p1 = new Pair<>(board.getGamesBoard().getTile(point), point);
            Pair<Tiles, Point> p2 = new Pair<>(Tiles.EMPTY, point);
            notifier.firePropertyChange(new PropertyChangeEvent(board, "board", p1, p2));

        }

        //Remove the selected tiles
        board.remove(points);


    }


    /**
     * Adds an ordered array of tiles in the player's bookshelf.
     * Since adding tiles to your bookshelf is the last action you can do on your turn,
     * it also calls the nextTurn function
     * @param player  The player who owns the Bookshelf
     * @param tiles   The color of the tiles to add
     * @param column   The column where you want to add the tiles
     * @throws OutOfDomain if requested column does not exists
     * @throws ColumnIsFull if the requested column is full
     * @throws MoveNotPossible if game is not in the right state
     * @throws NotCurrentPlayer if the player requesting the move is not the current player
     */
    public void addToBookShelf(Player player, ArrayList<Tiles> tiles, int column) throws MoveNotPossible{


        //Check for move legitimacy
        checkAddLegit(player,column,tiles.size());

        //Change game state
        state = GameState.CHOOSING_TILES;

        //Notifying changes
        ArrayList<Tiles> temp1 = new ArrayList<>(player.getBookshelf().getTiles().getColumn(column));
        Pair<ArrayList<Tiles>, Integer> p1 = new Pair<>(temp1, column);

        //Add to bookshelf
        player.getBookshelf().addTile(tiles, column);

        //Notifying changes pt.2
        ArrayList<Tiles> temp2 = new ArrayList<>(player.getBookshelf().getTiles().getColumn(column));
        Pair<ArrayList<Tiles>, Integer> p2 = new Pair<>(temp2, column);

        notifier.firePropertyChange(new PropertyChangeEvent(player, "bookshelf", p1, p2));


        //Checks if player filled his bookshelf
        if(!isFinished && player.getBookshelf().checkEndGame()){
            isFinished=true;
            player.setWinnerPoint(1);
            PropertyChangeEvent evt1 = new PropertyChangeEvent(player, "points",
                    publicPoints.get(players.indexOf(player)),player.getPublicPoint());

            notifier.firePropertyChange(evt1);

        }




        //Advances turn
        nextTurn();


    }


    /**
     *  Swap the order of the array of selected tiles to the order describes in the array ints.
     *       ex. oldSelectedTiles[G,B,Y], ints[2,1,3] --> newSelectedTiles[B,G,Y]
     * @param ints  The new order of the array
     * @param player  The player requesting the move
     * @throws NotCurrentPlayer if the player is not the current player
     * @throws MoveNotPossible if the game is not in the right state
     */
    public void swapOrder(ArrayList<Integer> ints,Player player) throws MoveNotPossible {

        if(state.equals(GameState.CHOOSING_ORDER)){

            if(player.equals(currPlayer)) {
                ArrayList<Tiles> array = new ArrayList<>();
                array.addAll(selectedTiles);
                for (int i = 0; i < ints.size(); i++) {
                    selectedTiles.set(i, array.get(ints.get(i) - 1));
                }
            }else throw new NotCurrentPlayer();

            //Change game state
            state = GameState.CHOOSING_COLUMN;
        }else throw new MoveNotPossible();
    }







    //Checks to see the legitimacy of moves


    //REMOVE CHECKS

    /**
     * If possible, removes the selected tiles from the board.
     * @param points The array of points you want to remove
     * @param player The player who wants to remove the tiles
     * @throws MoveNotPossible if the game is not in the right state
     * @throws NotCurrentPlayer if the player is not the current player
     * @throws IllegalArgumentException if the array points is null or the array is too long
     * @throws TilesNotAdjacent if the tiles are not adjacent
     * @throws OutOfDomain if at least one of the points is outside the board
     * @throws TilesCannotBeSelected if at least one of the selected tiles is either Empty or Not Allowed
     */
    private void checkRemoveLegit(ArrayList<Point> points, Player player) throws MoveNotPossible,IllegalArgumentException {

        if(state.equals(GameState.CHOOSING_TILES)){
        //Check if the player requesting the move is the current player
        if(!player.equals(currPlayer)) throw new NotCurrentPlayer();
        //check if the selected tiles can actually be selected
        else checkPointArrayDomain(points);

        }else throw new MoveNotPossible();

    }


    /**
     * Checks if the selected tiles can actually be removed from the board
     * @param points Array of coordinates of the tiles
     * @throws IllegalArgumentException if the array points is null or the array is too long
     * @throws TilesNotAdjacent if the tiles are not adjacent
     * @throws OutOfDomain if at least one of the points is outside the board
     * @throws TilesCannotBeSelected if at least one of the selected tiles is either Empty or Not Allowed
     */
    private void checkPointArrayDomain(ArrayList<Point> points) throws MoveNotPossible, IllegalArgumentException {
        //check if the array is not null
        if(points!=null ){
            //check the length of the array
            if(points.size()>3) throw new IllegalArgumentException();
            else {  //check if the tiles are adjacent
                if(!Board.checkAdjacentTiles(points)) throw new TilesNotAdjacent();
            }

            //Check if the selected tiles are allowed and not empty
            for(Point p : points){
                checkBoardDomain(p);
            }
        }
        else throw new IllegalArgumentException();

    }


    /**
     * Checks if the tiles of coordinates p can be selected (it's in the board, is allowed and not empty)
     * @param p the coordinates of the tiles
     * @throws OutOfDomain if at least one of the points is outside the board
     * @throws TilesCannotBeSelected if at least one of the selected tiles is either Empty or Not Allowed
     */
    private void checkBoardDomain(Point p) throws MoveNotPossible {
        //Check if the point is inside the board
        checkPointBoardDomain(p);
        //Check if there is a tile there
        if(board.getGamesBoard().getTile(p).equals(Tiles.NOTALLOWED) ||
                board.getGamesBoard().getTile(p).equals(Tiles.EMPTY)){
            throw new TilesCannotBeSelected();}
    }



    /**
     * Check if the point is inside the board
     * @param p Coordinates of the point
     * @throws OutOfDomain If the point is outside the board
     */
    private void checkPointBoardDomain(Point p) throws OutOfDomain {
        //Check if the point is inside the board
        if(p.getX()<0 || p.getX()>8) throw new OutOfDomain();
        else if(p.getY()<0 || p.getY()>8) throw new OutOfDomain();
    }


    //ADD CHECKS


    /**
     *  Check if the player can add tiles to his bookshelf
     * @param player The player trying to add tiles to his bookshelf
     * @param col    The column number where to add the tiles
     * @param size   The number of tiles you want to add
     * @throws OutOfDomain if requested column does not exists
     * @throws ColumnIsFull if the requested column is full
     * @throws MoveNotPossible if game is not in the right state
     * @throws NotCurrentPlayer if the player requesting the move is not the current player
     */
    private void checkAddLegit(Player player,int col,int size) throws MoveNotPossible {

        //checks the state of the game
        if(state.equals(GameState.CHOOSING_ORDER)||state.equals(GameState.CHOOSING_COLUMN)){
            //Check if the player requesting the move is the current player
            if(!player.equals(currPlayer)) throw new NotCurrentPlayer();

            //Check if the selected column exists and if there is enough empty space
            checkColumn(col,size);

        }else throw new MoveNotPossible();
    }


    /**
     * Check if the selected column exists and if there is enough empty space
     * @param col The column where you want to put the tiles
     * @param size The number of tiles you want to add
     * @throws OutOfDomain if requested column does not exists
     * @throws ColumnIsFull if the requested column is full
     */
    private void checkColumn(int col,int size) throws OutOfDomain, ColumnIsFull {
        if(col<0||col>5) throw new OutOfDomain();
        else if(!currPlayer.getBookshelf().checkColumns(size,col)) throw new ColumnIsFull();
    }









    //PRIVATE METHODS : UTILITY


    /**
     * Initializes common objectives
     */
    private void commonobjInit() {
       commonobj = CommonObjective.randomSubclass(2);
    }

    /**
     * Initializes private objectives
     */
    private void personalobjInit() {
        ArrayList<PersonalObjective> temp = PersonalObjective.randomSubclass(players.size());
        for(int i = 0;i<players.size();i++){
            players.get(i).setPersonalObjective(temp.get(i));
        }
    }


    /**
     * Updates and sets the current player's :
     * -Vicinity points
     * -Common objective points
     * -Personal objective points
     */
    private void updatePoints(){

        //Updates vicinity, common objective and personal objective points
        currPlayer.setVicinityPoint( currPlayer.getBookshelf().checkVicinityPoints());
        currPlayer.getPersonalObjective().personalObjectivePoint(currPlayer);
        for(CommonObjective o : commonobj){
             o.commonObjPointsCalculator(currPlayer,players.size());
        }


        //Notify the changes to the views
        PropertyChangeEvent evt = new PropertyChangeEvent(currPlayer, "points",
                publicPoints.get(players.indexOf(currPlayer)),currPlayer.getPublicPoint());

        notifier.firePropertyChange(evt);

    }


    /**
     * Update the points of the current player,
     * select the next player and calls the endGame function if the last turn has been played.
     * Also resets the board when needed
     */
    private void nextTurn(){


        //Update the points
        updatePoints();

        //changes current player
        currPlayer=nextPlayer;
        if(currPlayer==players.get(players.size()-1)){ nextPlayer = players.get(0);}
        else nextPlayer = players.get(players.indexOf(currPlayer)+1);


        //checks if the board needs to reset
        if(board.checkBoardReset()) board.boardResetENG();

        //checks if someone completed all their bookshelf
        if(isFinished && currPlayer.equals(players.get(0))) endGame();

    }





    //TO BE FINISHED
    /**
     * Select the winner of the game and ends it
     */
    private void endGame(){
        state = GameState.ENDING;
        selectWInner();
    }






    /**
     * Select the player who won the game
     */
    private void selectWInner(){
        int winnerpos=0;
        for( int i = 0; i< players.size(); i++){
            if(players.get(i).getPrivatePoint()>players.get(winnerpos).getPrivatePoint()) winnerpos=i;
        }

        winner = players.get(winnerpos);

    }









    //GETTERS AND SETTERS

    /**
     * Return the array of all players
     * @return  Array of all players
     */
    public ArrayList<Player> getPlayers() {
        return players;
    }



    /**
     * Return the board
     * @return the board
     */
    public Board getBoard() {
        return board;
    }


    /**
     *  Returns the current state of the game
     * @return The current state of the game
     */
    public GameState getState() {
        return state;
    }



    /**
     * Sets the state of the game
     * @param state The state you want to set the game to
     */
    public void setState(GameState state) {
        this.state = state;
    }



    /**
     * @return Array of all the common objectives
     */
    public ArrayList<CommonObjective> getCommonobj() {
        return commonobj;
    }




    /**
     * Set the current player (Just for Testing purposes)
     * @param currPlayer The player to set as current player
     */
    public void setCurrPlayer(Player currPlayer) {
        this.currPlayer = currPlayer;
    }

    /**
     * Return true if the game is finished, false otherwise
     * @return isFinished
     */
    public boolean getIsFinished() {
        return isFinished;
    }


    /**
     * @return Array of all the personal objectives of all players in the game
     */
   /* public ArrayList<PersonalObjective> getPersobj(){
        ArrayList<PersonalObjective> persobj = new ArrayList<>();
        for( Player p : players){
            persobj.add(p.getPersonalObjective());
        }
        return persobj;
    }*/

}
