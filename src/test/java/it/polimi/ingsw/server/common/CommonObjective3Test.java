package it.polimi.ingsw.server.common;

import it.polimi.ingsw.server.CommonObjective.CommonObjective3;
import it.polimi.ingsw.server.Model.Player;
import it.polimi.ingsw.utils.Tiles;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test Class for CommonObjective 3
 */
class CommonObjective3Test {

    /**
     * Testing for success the case of 4 different
     * groups created manually that meet the criteria
     * in a bookshelf full of different tiles
     */
    @Test
    void checkConditionSuccess1() {
        Player player = new Player( "Jhon");

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                Tiles[] values = Tiles.values();
                Random random = new Random();

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                Tiles randomValue = values[index];
                player.getBookshelf().getTiles().setTile(randomValue, i, j);
            }
        }

        // Programming the specific boxes to manually get 4 groups of 4 same colored tiles
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 2);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 3);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 4);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 1, 4);

        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 2);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);

        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 2);

        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 2, 4);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 3, 4);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 4);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 4);

        // Creation of an instance for CommonObjective3
        CommonObjective3 obj = new CommonObjective3();

        // Checking that the checkCondition method returns true
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing for success the particular case of a big group
     * of four adjacent tiles of the same color that should be
     * considered as two groups by the method
     */
    @Test
    void checkConditionSuccess2() {
        Player player = new Player( "Jhon");

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                Tiles[] values = Tiles.values();
                Random random = new Random();

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                Tiles randomValue = values[index];
                player.getBookshelf().getTiles().setTile(randomValue, i, j);
            }
        }

        // Programming the specific boxes to manually get 4 groups of 4 same colored tiles
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 2);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 3);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 4);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 1, 4);

        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 2);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);

        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 2);

        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 0);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 4, 1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 5, 0);

        // Creation of an instance for CommonObjective3
        CommonObjective3 obj = new CommonObjective3();

        // Checking that the checkCondition method returns true
        assertTrue(obj.checkCondition(player));
    }

    /**
     * Testing first if statement for failure:
     * bookshelf programmed to only have 3 groups
     * that meet the commonObjective criteria.
     * Also testing the code by adding a group of
     * empty tiles that should not be considered
     * usable for the condition
     */
    @Test
    void checkConditionFailure1() {
        Player player = new Player("Jhon");
        Tiles[] values = Tiles.values();
        int x = 0;

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                player.getBookshelf().getTiles().setTile(values[x], i, j);
                x++;
                if (x == 6) x = 0;
            }
        }

        // Programming the specific boxes to manually get 4 groups of 4 same colored tiles
        player.getBookshelf().getTiles().setTile(Tiles.EMPTY, 0, 0);
        player.getBookshelf().getTiles().setTile(Tiles.EMPTY, 0, 1);
        player.getBookshelf().getTiles().setTile(Tiles.EMPTY, 1, 0);
        player.getBookshelf().getTiles().setTile(Tiles.EMPTY, 1, 1);

        player.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 2);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 3);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 4);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 1, 4);

        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 2);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);

        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 2);

        // Creation of an instance for CommonObjective3
        CommonObjective3 obj = new CommonObjective3();

        // Checking that the checkCondition method returns true
        assertFalse(obj.checkCondition(player));
    }

    /**
     * Testing the method in the case of 2 players game
     * when points need to be added
     */
    @Test
    void commonObjPointsCalculatorTwoPlayers() {
        Player player1 = new Player( "Jhon");
        Player player2 = new Player( "Obi");

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                Tiles[] values = Tiles.values();
                Random random = new Random();

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                Tiles randomValue = values[index];
                player1.getBookshelf().getTiles().setTile(randomValue, i, j);
            }
        }

        // Programming the specific boxes to manually get 4 groups of 4 same colored tiles for player 1
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 2);
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 4);
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 1, 4);

        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 2);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);

        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 2);

        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 2, 4);
        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 3, 4);
        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 4);
        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 4);

        // Programming the specific boxes to manually get 4 groups of 4 same colored tiles for player 2
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 2);
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 4);
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 1, 4);

        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 2);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);

        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 2);

        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 2, 4);
        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 3, 4);
        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 4);
        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 4);

        // Creation of an instance for CommonObjective3
        CommonObjective3 obj = new CommonObjective3();

        // Testing method for player1
        obj.commonObjPointsCalculator(player1, 2);
        assertEquals(8, player1.getCommonObjectivePoint());
        assertEquals(4, obj.getPoints());

        // Testing method for player2
        obj.commonObjPointsCalculator(player2, 2);
        assertEquals(4, player2.getCommonObjectivePoint());
        assertEquals(0, obj.getPoints());
    }

    /**
     * Testing the method in the case of 4 players game
     * when points need to be added
     */
    @Test
    void commonObjPointsCalculatorFourPlayers(){
        Player player1 = new Player( "Jhon");
        Player player2 = new Player( "Obi");
        Player player3 = new Player( "Pablo");
        Player player4 = new Player( "Felipe");

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                Tiles[] values = Tiles.values();
                Random random = new Random();

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                Tiles randomValue = values[index];
                player1.getBookshelf().getTiles().setTile(randomValue, i, j);
            }
        }

        // Programming the specific boxes to manually get 4 groups of 4 same colored tiles for player 1
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 2);
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 4);
        player1.getBookshelf().getTiles().setTile(Tiles.PINK, 1, 4);

        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 2);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);
        player1.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);

        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player1.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 2);

        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 2, 4);
        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 3, 4);
        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 4);
        player1.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 4);

        // Programming the specific boxes to manually get 4 groups of 4 same colored tiles for player 2
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 2);
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 4);
        player2.getBookshelf().getTiles().setTile(Tiles.PINK, 1, 4);

        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 2);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);
        player2.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);

        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player2.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 2);

        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 2, 4);
        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 3, 4);
        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 4);
        player2.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 4);

        // Programming the specific boxes to manually get 4 groups of 4 same colored tiles for player 3
        player3.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 2);
        player3.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 3);
        player3.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 4);
        player3.getBookshelf().getTiles().setTile(Tiles.PINK, 1, 4);

        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 2);
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);
        player3.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);

        player3.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 3);
        player3.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 3);
        player3.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player3.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 2);

        player3.getBookshelf().getTiles().setTile(Tiles.YELLOW, 2, 4);
        player3.getBookshelf().getTiles().setTile(Tiles.YELLOW, 3, 4);
        player3.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 4);
        player3.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 4);

        // Programming the specific boxes to manually get 4 groups of 4 same colored tiles for player 4
        player4.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 2);
        player4.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 3);
        player4.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 4);
        player4.getBookshelf().getTiles().setTile(Tiles.PINK, 1, 4);

        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 2);
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);
        player4.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);

        player4.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 3);
        player4.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 3);
        player4.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player4.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 2);

        player4.getBookshelf().getTiles().setTile(Tiles.YELLOW, 2, 4);
        player4.getBookshelf().getTiles().setTile(Tiles.YELLOW, 3, 4);
        player4.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 4);
        player4.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 4);

        // Creation of an instance for CommonObjective3
        CommonObjective3 obj = new CommonObjective3();

        // Testing method for player1
        obj.commonObjPointsCalculator(player1, 4);
        assertEquals(8, player1.getCommonObjectivePoint());
        assertEquals(6, obj.getPoints());

        // Testing method for player2
        obj.commonObjPointsCalculator(player2, 4);
        assertEquals(6, player2.getCommonObjectivePoint());
        assertEquals(4, obj.getPoints());

        // Testing method for player3
        obj.commonObjPointsCalculator(player3, 4);
        assertEquals(4, player3.getCommonObjectivePoint());
        assertEquals(2, obj.getPoints());

        // Testing method for player4
        obj.commonObjPointsCalculator(player4, 4);
        assertEquals(2, player4.getCommonObjectivePoint());
        assertEquals(0, obj.getPoints());
    }

    /**
     * Testing first if statement for failure in case player's
     * bookshelf does not meet the obj3 condition criteria
     */
    @Test
    void commonObjPointsCalculatorFailure1(){
        Player player = new Player( "Jhon");
        Tiles tiles = Tiles.EMPTY;

        // Initializing the bookshelf
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 5; j++) {
                player.getBookshelf().getTiles().setTile(tiles, i, j);
            }
        }

        // Creation of an instance for CommonObjective3
        CommonObjective3 obj = new CommonObjective3();

        // Testing method for player
        obj.commonObjPointsCalculator(player, 2);
        assertEquals(0, player.getCommonObjectivePoint());
        assertEquals(8, obj.getPoints());
    }

    /**
     * Testing the first if statement for failure in case the player
     * already received the commonObjectivePoints
     */
    @Test
    void commonObjPointsCalculatorFailure2(){
        Player player = new Player( "Jhon");

        // Initializing the bookshelf
        for (int i=0; i<6; i++){
            for (int j=0; j<5; j++){
                Tiles[] values = Tiles.values();
                Random random = new Random();

                // Random number generator from 0 to 5 to avoid EMPTY and NOT ALLOWED tiles [0, 5)
                int index = random.nextInt(6);
                Tiles randomValue = values[index];
                player.getBookshelf().getTiles().setTile(randomValue, i, j);
            }
        }

        // Programming the specific boxes to manually get 4 groups of 4 same colored tiles
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 2);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 3);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 0, 4);
        player.getBookshelf().getTiles().setTile(Tiles.PINK, 1, 4);

        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 2, 2);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 1);
        player.getBookshelf().getTiles().setTile(Tiles.BLUE, 3, 0);

        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 2, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 3, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 3);
        player.getBookshelf().getTiles().setTile(Tiles.WHITE, 4, 2);

        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 2, 4);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 3, 4);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 4, 4);
        player.getBookshelf().getTiles().setTile(Tiles.YELLOW, 5, 4);

        // Creation of an instance for CommonObjective3
        CommonObjective3 obj = new CommonObjective3();

        // Testing method for player first time
        obj.commonObjPointsCalculator(player, 3);
        assertEquals(8, player.getCommonObjectivePoint());
        assertEquals(6, obj.getPoints());

        // Testing method for player second time
        obj.commonObjPointsCalculator(player, 3);
        assertEquals(8, player.getCommonObjectivePoint());
        assertEquals(6, obj.getPoints());
    }
}