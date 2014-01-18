package poker.service;

import poker.model.Table;

public class MonteCarloCalculator {

  final Table original;
  final int repetition;
  boolean showLog;

  public MonteCarloCalculator(Table original, int repetition) {
    this.original = original;
    this.repetition = repetition;
  }

  public MonteCarloCalculator(Table original, int repetition, boolean showLog) {
    this(original, repetition);
    this.showLog = showLog;
  }

  public double getProbability() {
    int success = 0;
    for (int i = 1; i <= repetition; i++) {
      if (new Dealer(original.clone(), showLog).amIaWinner()) {
        success++;
      }
    }
    double result = (double) success / repetition;
    System.out.format("Probability of your win = %.3f \n", result * 100);
    System.out.format("Rate = %.3f \n", result * (original.opponentsCards.size() + 1));
    return result;
  }
}
