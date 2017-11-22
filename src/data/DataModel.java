package data;

import ai.*;
import card.StackCard;

import java.util.Scanner;

public class DataModel {
    Player[] players;
    StackCard stackCard;
    Brain brain;
    Controller[] controllers;
    {
        players = new Player[4];
        stackCard = new StackCard();
        brain = new Brain();
        controllers = new Controller[4];
        for (int i = players.length - 1; i >= 0; i--)
            players[i] = new Player(stackCard, brain);
        for (int i = players.length; i > 0; i--)
            players[i - 1].setNext(players[i % players.length]);
        for (int i = 0, playersLength = players.length; i < playersLength; i++)
            players[i].setController(controllers[i] = new Controller(players[i]));
//        players[0].setController(new ConsoleController(players[0], new Scanner(System.in)));
        players[0].setController(controllers[0] = new MouseController(players[0]));
    }

    public void reset() {
        for (Player player : players)
            player.reset();
        stackCard.reset();

        for (Controller controller : controllers) {
            controller.reset();
        }
    }

    public void wash() {
        stackCard.wash();
    }

    public Controller getController(int index) {
        return controllers[index];
    }
}
