package poker.service;

import java.util.Collection;
import poker.model.Card;
import poker.model.CardSet;
import poker.model.Level;
import poker.model.Result;
import poker.model.Suit;
import poker.model.Table;

public class Dealer {

  static ResultCalculator rc = ResultCalculator.getInstance();
  public final static int DECK_SIZE = Suit.values().length * Level.values().length;
  public final static int BANK_SIZE = 5;
  Table table;
  boolean showLog;

  public Dealer(Table table) {
    this.table = table;
    dealCards();
  }

  public Dealer(Table table, boolean showLog) {
    this(table);
    this.showLog = showLog;
  }

  public boolean amIaWinner() {
    showLog("Table : " + table);
    Result myResult = rc.calculateResult(new CardSet(table.myCards, table.bank));
    showLog("Your result : " + myResult);
    Result opponentResult;
    for (Collection<Card> c : table.opponentsCards) {
      opponentResult = rc.calculateResult(new CardSet(c, table.bank));
      if (opponentResult.compareTo(myResult) > 0) {
        showLog("This result is better : " + opponentResult);
        return false;
      }
    }
    showLog("You are a winner!!!");
    return true;
  }

  public final void dealCards() {
    for (Collection<Card> c : table.opponentsCards) {
      for (int i = 1; i <= Table.PLAYER_CARDS_COUNT; i++) {
        c.add(nextCard());
      }
    }
    for (int i = 1; i <= BANK_SIZE; i++) {
      table.bank.add(nextCard());
    }

  }

  private Card nextCard() {
    Card result = new Card((int) (Math.random() * DECK_SIZE));
    if (table.hasCard(result)) {
      return nextCard();
    } else {
      return result;
    }
  }

  private void showLog(Object log) {
    if (showLog) {
      System.out.println(log);
    }
  }
}
