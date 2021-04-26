package src;

import src.game.*;

public class RunFast {
    public static void main(String[] args) throws Exception {
        Game game = Game.getGame();
        game.startGame();
    }
}