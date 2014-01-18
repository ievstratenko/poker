package poker.model;

import java.util.Collection;
import java.util.Objects;
import java.util.TreeSet;

public class CardSet implements Comparable<CardSet> {

  public Collection<Card> cards = new TreeSet<>();
  public Result result;

  @SafeVarargs
public CardSet(Collection<Card>... sets) {
    for (Collection<Card> s : sets) {
      cards.addAll(s);
    }
  }

  @Override
  public int compareTo(CardSet o) {
    if (result == null || o == null || o.result == null) {
      throw new IllegalArgumentException();
    }
    return result.compareTo(o.result);
  }

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 53 * hash + Objects.hashCode(this.cards);
    hash = 53 * hash + Objects.hashCode(this.result);
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
    final CardSet other = (CardSet) obj;
    if (!Objects.deepEquals(this.cards, other.cards)) {
      return false;
    }
    if (!Objects.equals(this.result, other.result)) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "CardSet{" + "set=" + cards + ", \nresult=" + result + '}';
  }
}
