package poker.service;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;

import poker.model.Card;
import poker.model.CardSet;
import static poker.model.Hand.*;
import poker.model.Level;
import poker.model.Result;
import poker.model.Suit;

public class ResultCalculator {

	public final static int CARD_COUNT = 7;
	public static final int RESULT_CARD_COUNT = 5;
	private static ResultCalculator rc;
	CardSet set;
	public int[] levelProjection;
	public int[] suitProjection;

	private ResultCalculator() {
	}

	public static ResultCalculator getInstance() {
		return rc == null ? new ResultCalculator() : rc;
	}

	void checkValidity() {
		if (set.cards.size() != CARD_COUNT) {
			throw new IllegalArgumentException();
		}
	}

	void fillProjections() {
		levelProjection = new int[Level.values().length];
		suitProjection = new int[Suit.values().length];
		for (Card card : set.cards) {
			levelProjection[card.level.ordinal()]++;
			suitProjection[card.suit.ordinal()]++;
		}
	}

	@SafeVarargs
	final Collection<Card> completeResultSet(LinkedHashSet<Card>... sets) {
		LinkedHashSet<Card> result = new LinkedHashSet<>();
		for (LinkedHashSet<Card> s : sets) {
			for (Card c : s) {
				result.add(c);
				if (result.size() == RESULT_CARD_COUNT) {
					return result;
				}
			}
		}
		for (Card c : set.cards) {
			result.add(c);
			if (result.size() == RESULT_CARD_COUNT) {
				return result;
			}
		}
		return result;
	}

	public synchronized Result calculateResult(CardSet set) {
		this.set = set;
		checkValidity();
		fillProjections();
		set.result = getBestResult();
		return set.result;
	}

	Result getBestResult() {
		LinkedHashSet<Card> flushSet = getFlushCards();
		LinkedHashSet<Card> straightSet;
		if (flushSet != null) {
			Suit flushSuit = ((Card) flushSet.iterator().next()).suit;
			straightSet = getStraightCards(flushSuit);
			if (straightSet != null && straightSet.size() >= RESULT_CARD_COUNT) {
				return new Result(STRAIGHT_FLUSH,
						completeResultSet(straightSet));
			}
		}
		LinkedHashSet<Card> fourSet = getGroupSameLevel(4);
		if (fourSet != null) {
			return new Result(FOUR, completeResultSet(fourSet));
		}
		LinkedHashSet<Card> threeSet = getGroupSameLevel(3);
		LinkedHashSet<Card> pairSet = getGroupSameLevel(2);
		if (threeSet != null) {
			if (pairSet != null) {
				return new Result(FULL_HOUSE, completeResultSet(threeSet,
						pairSet));
			}
			Level treeLevel = ((Card) threeSet.iterator().next()).level;
			LinkedHashSet<Card> secondTreeSet = getGroupSameLevel(3,
					treeLevel.ordinal() - 1);
			if (secondTreeSet != null) {
				return new Result(FULL_HOUSE, completeResultSet(threeSet,
						secondTreeSet));
			}

		}
		if (flushSet != null) {
			return new Result(FLUSH, completeResultSet(flushSet));
		}
		straightSet = getStraightCards(null);
		if (straightSet != null) {
			return new Result(STRAIGHT, completeResultSet(straightSet));
		}
		if (threeSet != null) {
			return new Result(THREE, completeResultSet(threeSet));
		}
		if (pairSet != null) {
			Level pairLevel = ((Card) pairSet.iterator().next()).level;
			LinkedHashSet<Card> secondPairSet = getGroupSameLevel(2,
					pairLevel.ordinal() - 1);
			if (secondPairSet != null) {
				return new Result(TWO_PAIRS, completeResultSet(pairSet,
						secondPairSet));
			}
			return new Result(PAIR, completeResultSet(pairSet));
		}
		return new Result(HIGH_CARD, completeResultSet());
	}

	LinkedHashSet<Card> getGroupSameLevel(int size) {
		return getGroupSameLevel(size, Level.ACE.ordinal());
	}

	LinkedHashSet<Card> getGroupSameLevel(int size, int downFromLevel) {
		Level level = null;
		for (int i = downFromLevel; i >= 0; i--) {
			if (size == levelProjection[i]) {
				level = Level.values()[i];
				break;
			}
		}
		if (level != null) {
			LinkedHashSet<Card> result = new LinkedHashSet<>();
			for (Card c : set.cards) {
				if (c.level == level) {
					result.add(c);
				}
			}
			return result;
		}
		return null;
	}

	LinkedHashSet<Card> getFlushCards() {
		for (int i = 0; i < suitProjection.length; i++) {
			if (suitProjection[i] >= RESULT_CARD_COUNT) {
				LinkedHashSet<Card> result = new LinkedHashSet<>();
				for (Card c : set.cards) {
					if (c.suit == Suit.values()[i]) {
						result.add(c);
					}
				}
				return result;
			}
		}
		return null;
	}

	LinkedHashSet<Card> getStraightCards(Suit suit) {
		int localLevelProjection[];
		if (suit != null) {
			localLevelProjection = new int[Level.values().length];
			for (Card card : set.cards) {
				if (card.suit == suit) {
					localLevelProjection[card.level.ordinal()]++;
				}
			}
		} else {
			localLevelProjection = levelProjection;
		}
		int length = 0;
		int aceIndex = localLevelProjection.length - 1;
		for (int i = aceIndex; i >= -1; i--) {
			if (i == -1 || localLevelProjection[i] == 0) {
				boolean cycle = i == -1 && localLevelProjection[aceIndex] > 0;
				if (length >= RESULT_CARD_COUNT
						|| (length >= RESULT_CARD_COUNT - 1 && cycle)) {
					LinkedHashSet<Card> result = new LinkedHashSet<>();
					for (Card c : set.cards) {
						if (suit != null && suit != c.suit) {
							continue;
						}
						if (c.level.ordinal() > i
								&& c.level.ordinal() <= i + length) {
							result.add(c);
							length--;
						}
					}
					if (cycle) {
						for (Card c : set.cards) {
							if (suit != null && suit != c.suit) {
								continue;
							}
							result.add(c);
							break;
						}
					}
					return result;
				}
				length = 0;
			} else {
				length++;
			}
		}
		return null;
	}

	@Override
	public String toString() {
		return "ResultCalculator{" + "set=" + set + ", \nlevelProjection="
				+ Arrays.toString(levelProjection) + ", \nsuitProjection="
				+ Arrays.toString(suitProjection) + '}';
	}
}
