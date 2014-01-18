package poker.model;

import java.util.ArrayList;
import java.util.Collection;

public class Table implements Cloneable {

  public final static int PLAYER_CARDS_COUNT = 2;
  public final static int MIN_PLAYERS = 2;
  public Collection<Card> myCards;
  public Collection<Card> bank = new ArrayList<>();
  public Collection<Collection<Card>> opponentsCards = new ArrayList<>();

  public Table(int size, Collection<Card> myCards) {
    if (size < MIN_PLAYERS || myCards.size() != PLAYER_CARDS_COUNT) {
      throw new IllegalArgumentException();
    }
    this.myCards = myCards;
    for (int i = 2; i <= size; i++) {
      opponentsCards.add(new ArrayList<Card>());
    }
  }

  public boolean hasCard(Card card) {
    if (bank.contains(card) || myCards.contains(card)) {
      return true;
    }
    for (Collection<Card> c : opponentsCards) {
      if (c.contains(card)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return "Table{" + "myCards=" + myCards
            + ", \nbank=" + bank
            + ", \nopponentsCards=" + opponentsCards + '}';
  }

  @Override
  public Table clone() {
    return new Table(opponentsCards.size() + 1, myCards);
  }
}
