package data;

import ai.Brain;
import ai.Player;
import card.StackCard;

public class DataModel {
    Player[] players;
    StackCard stackCard;
    Brain brain;

    {
        players = new Player[4];
        stackCard = new StackCard();
        brain = new Brain();
        for (int i = players.length - 1; i >= 0; i--)
            players[i] = new Player(stackCard, brain);
        for (int i = players.length; i > 0; i--)
            players[i - 1].setNext(players[i % players.length]);
        for (Player player : players)
            player.offerCard(13);
    }

}
