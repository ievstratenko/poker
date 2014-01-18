package poker.model;

public class Card implements Comparable<Card> {

  public final Suit suit;
  public final Level level;

  public Card(Suit suit, Level level) {
    if (suit == null || level == null) {
      throw new IllegalArgumentException();
    }
    this.suit = suit;
    this.level = level;
  }

  public Card(String code) {
    if (code == null || code.length() < 2 || code.length() > 3) {
      throw new IllegalArgumentException();
    }
    this.suit = Suit.getByCode(code.charAt(0));
    this.level = Level.getByCode(code.charAt(1));
  }

  public Card(int index) {
    if (index < 0 || index >= Level.values().length * Suit.values().length) {
      throw new IllegalArgumentException();
    }
    this.suit = Suit.values()[index / Level.values().length];
    this.level = Level.values()[index % Level.values().length];
  }

  public boolean hasSameSuit(Card o) {
    return this.suit == o.suit;
  }

  @Override
  public int compareTo(Card o) {
    if (level == o.level) {
      return suit.compareTo(o.suit);
    } else {
      return -level.compareTo(o.level);
    }
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 29 * hash + (this.suit != null ? this.suit.hashCode() : 0);
    hash = 29 * hash + (this.level != null ? this.level.hashCode() : 0);
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
    final Card other = (Card) obj;
    if (this.suit != other.suit) {
      return false;
    }
    if (this.level != other.level) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return suit + "/" + level;
  }
}
