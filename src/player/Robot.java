package src.player;

import src.cards.Poker;

import java.util.*;

public class Robot extends Player {
    public static void main(String[] args) {
        Robot robot = new Robot(new LinkedList<Poker>(Arrays.asList(Poker.values())), "Robot");
        robot.readCard(false);
    }

    public Robot(LinkedList<Poker> handCards, String playerName) {
        super(handCards, playerName);
    }

    @Override
    public void sortCards() {

    }

    @Override
    public void playCards() {

    }
}