package it.polimi.ingsw;

import it.polimi.ingsw.CommonObjective.CommonObjective;
import it.polimi.ingsw.PersonalObjective.PersonalObjective;
import it.polimi.ingsw.View.View;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;


public class Model {

    private  Board board;
    private ArrayList<Player> players;
    private ArrayList<View> virtualViews;
    private ArrayList<CommonObjective> commonobj;

    private Player currPlayer;
    private Player nextPlayer;

    private Player winner;
    private boolean isFinished = false;

    public Model(){};

    public Model( ArrayList<Player> players,  ArrayList<View> views) {
        this.players = players;
        this.virtualViews = views;
        this.currPlayer = players.get(0);
        this.nextPlayer = players.get(1);
    }


    /**
     * Initializes the board and the objectives
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    public void initialization() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {

        board = new Board(players.size(), new Sachet());
        commonobjInit();
        personalobjInit();


    }

    /**
     * Initializes common objectives
     *
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    public void commonobjInit() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
       commonobj = CommonObjective.randomSubclass(2);
    }

    /**
     * Initializes private objectives
     *
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     */
    public void personalobjInit() throws InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        for(Player p : players){
            p.setPersonalObjective(PersonalObjective.randomSubclass());
        }
    }

    /**
     * Removes an array of tiles from the board

     * @param points  The position of the tiles
     */
    public void removeTileArray( ArrayList<Point> points){

        for(int i = 0; i < points.size(); i++){
            board.remove(points);
        }


    }

    /**
     * Adds an ordered array of tiles in the player's bookshelf.
     * Since adding tiles to your bookshelf is the last action you can do on your turn,
     * it also calls the nextTurn function
     * @param player  Player which owns the Bookshelf
     * @param tiles   The color of the tiles to add
     * @param column   The column where you want to add the tiles
     */
    public void addToBookShelf(Player player, ArrayList<Tiles> tiles, int column){
        player.getBookshelf().addTile(tiles, column);

        if(!isFinished && player.getBookshelf().checkEndGame()){
            isFinished=true;
            player.setWinnerPoint(1);
        }

        nextTurn();

    }

    /**
     * Select the next player and calls the endGame function if the last turn has been played.
     * Also resets the board when needed
     * @return Return the player who will play the next turn
     */
    private void nextTurn(){

        currPlayer=nextPlayer;
        if(currPlayer==players.get(players.size())){ nextPlayer = players.get(0);}
        else nextPlayer = players.get(players.indexOf(currPlayer)+1);

        if(board.checkBoardReset()) board.boardReset();

        if(isFinished && currPlayer.equals(players.get(0))) endGame();


    }

    /**
     * Calls select winner
     */
    private void endGame(){
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

    public int getPlayerPublicPoints(Player player){
        return player.getPublicPoint();
    }

    public int getPlayerPrivatePoints(Player player){
        return player.getPrivatePoint();
    }

    public Board getBoard() {
        return board;
    }

    /**
     * @return Array of all the common objectives
     */
    public ArrayList<CommonObjective> getCommonobj() {
        return commonobj;
    }

    /**
     * @return Array of all the personal objectives of all players in the game
     */
    public ArrayList<PersonalObjective> getPersobj(){
        ArrayList<PersonalObjective> persobj = new ArrayList<>();
        for( Player p : players){
            persobj.add(p.getPersonalObjective());
        }
        return persobj;
    }

    public boolean isFinished() {
        return isFinished;
    }
}