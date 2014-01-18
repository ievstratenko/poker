package poker.model;

public enum Level {

  N2('2'),
  N3('3'),
  N4('4'),
  N5('5'),
  N6('6'),
  N7('7'),
  N8('8'),
  N9('9'),
  N10('0'),
  JACK('J'),
  QUEEN('Q'),
  KING('K'),
  ACE('A');
  public char code;

  private Level(char code) {
    this.code = code;
  }

  @Override
  public String toString() {
    return Character.toString(code);
  }

  public static Level getByCode(char code) {
    for (Level l : Level.values()) {
      if (l.code == code) {
        return l;
      }
    }
    return null;
  }
}
