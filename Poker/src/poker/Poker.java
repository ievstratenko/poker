package poker;

import java.util.Collection;
import java.util.HashSet;

import poker.model.Card;
import poker.model.Table;
import poker.service.MonteCarloCalculator;
import poker.test.ResultCalculatorTest;

public class Poker {

  private static final ResultCalculatorTest rct = ResultCalculatorTest.getInstance();
  static final int REPETITIONS = 300000;
  static final int PLAYERS_COUNT = 3;
  static final String[] myCardsCodes = {"D7", "D8"};
  static final Collection<Card> myCards = new HashSet<>();

  static {
    rct.test();
    for (String code : myCardsCodes) {
      myCards.add(new Card(code));
    }
  }

  public static void main(String[] args) {
    new MonteCarloCalculator(new Table(PLAYERS_COUNT, myCards), REPETITIONS).getProbability();
  }
}
