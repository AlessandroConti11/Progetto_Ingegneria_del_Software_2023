package it.polimi.ingsw;

import java.util.ArrayList;

import static it.polimi.ingsw.Tiles.EMPTY;

public class Bookshelf {
        Matrix tiles;
        int num_of_tiles;

        public Bookshelf(){
            tiles=new Matrix(6,5);
            num_of_tiles=0;
        }

        public Matrix getTiles(){
            return tiles;
        }
        public boolean checkColumns(int choice, int column){
            if(tiles.getTile(0+choice-1,column).equals(EMPTY))
                return true;
            else return false;
        }
        public boolean checkEndGame(){
            for(int i=0;i<5;i++){
                if(tiles.getTile(0,i).equals(EMPTY)){
                    return false;
                }
            }
            return true;
        }
        public void addTile(ArrayList<Tiles> tiles, int column){
            int pos=firstFree(column);
            if (tiles.size() == 1) this.tiles.setTile(tiles.get(0), pos, column);
            else if (tiles.size()==2) {
                this.tiles.setTile(tiles.get(0), pos, column);
                this.tiles.setTile(tiles.get(1), pos+1, column);
            }
            else {
                this.tiles.setTile(tiles.get(0), pos, column);
                this.tiles.setTile(tiles.get(1), pos+1, column);
                this.tiles.setTile(tiles.get(2), pos+2, column);
            }
        }

        private int firstFree (int column){
            for (int i=0;i<6;i++){
                if(tiles.getTile(i,column).equals(EMPTY)){
                    return i;
                }
            }
            return -1;
        }
        public int checkVicinityPoints(){
            return 0;
        }
}