package poker.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Result implements Comparable<Result> {

  public final Hand hand;
  public final List<Card> cards = new ArrayList<>();

  public Result(Hand hand, Collection<Card> set) {
    if (hand == null || set == null || set.isEmpty()) {
      throw new IllegalArgumentException();
    }
    this.hand = hand;
    cards.addAll(set);
  }

  @Override
  public int compareTo(Result o) {
    if (hand == o.hand) {
      if (cards.size() != o.cards.size()) {
        throw new IllegalArgumentException();
      }
      int temp;
      for (int i = 0; i < cards.size(); i++) {
        temp = cards.get(i).level.compareTo(o.cards.get(i).level);
        if (temp != 0) {
          return temp;
        }
      }
      return 0;
    } else {
      return hand.compareTo(o.hand);
    }
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 97 * hash + (this.hand != null ? this.hand.hashCode() : 0);
    hash = 97 * hash + Objects.hashCode(this.cards);
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Result other = (Result) obj;
    return compareTo(other) == 0;
  }

  @Override
  public String toString() {
    return "Result{" + "hand=" + hand + ", cards=" + cards + '}';
  }
}
