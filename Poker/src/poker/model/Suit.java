package poker.model;

public enum Suit {

  SPADES('S'),
  CLUBS('C'),
  HEARTS('H'),
  DIAMONDS('D');
  public char code;

  private Suit(char code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return Character.toString(code);
  }

  public static Suit getByCode(char code) {
    for (Suit l : Suit.values()) {
      if (l.code == code) {
        return l;
      }
    }
    return null;
  }
}
