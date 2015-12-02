package sample;

import javafx.scene.paint.Color;

import static org.junit.Assert.*;

/**
 * Created by Sara on 11/10/2015.
 */
public class SellEnergyTest {

    @org.junit.Before
    public void setUp() throws Exception {

    }

    @org.junit.After
    public void tearDown() throws Exception {

    }

    @org.junit.Test
    public void testSellEnergy() throws Exception {
        Player player1 = new Player("Sara", Main.Race.LEGGITE, Color.RED);
        Store store = new Store();
        Controller.players.add(player1);
        int mon = player1.getMoneyStash();
        store.sellEnergy(3);
        assertTrue(player1.getMoneyStash() == mon + 75);
    }

    @org.junit.Test
    public void testSellTooMuchEnergy() throws Exception {
        Player player1 = new Player("Sara", Main.Race.LEGGITE, Color.RED);
        Store store = new Store();
        Controller.players.add(player1);
        int mon = player1.getMoneyStash();
        store.sellEnergy(10);
        assertTrue(player1.getMoneyStash() == mon);
    }
}