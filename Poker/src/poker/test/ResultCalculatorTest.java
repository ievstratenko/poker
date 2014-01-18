package poker.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import poker.model.Card;
import poker.model.CardSet;
import poker.model.Hand;
import poker.model.Result;
import poker.service.ResultCalculator;
import static poker.model.Hand.*;

public class ResultCalculatorTest {

  private static ResultCalculatorTest rct;

  private ResultCalculatorTest() {
  }

  public static ResultCalculatorTest getInstance() {
    return rct == null ? new ResultCalculatorTest() : rct;
  }
  static ResultCalculator rc = ResultCalculator.getInstance();
  String[] codes;

  public void test() {
    try {
      Result r11 = assertHand(HIGH_CARD, "SA", "DK", "S3", "S4", "S5", "H0", "H6");
      Result r12 = assertHand(HIGH_CARD, "HJ", "CK", "S3", "S7", "D8", "S2", "H6");
      Result r13 = assertHand(HIGH_CARD, "H0", "CK", "S3", "S7", "D8", "S2", "H6");
      Result r14 = assertHand(HIGH_CARD, "H9", "CK", "S3", "S7", "D8", "S2", "H6");
      Result r15 = assertHand(HIGH_CARD, "H9", "CK", "S3", "S7", "D8", "S2", "H5");
      Result r15a = assertHand(HIGH_CARD, "C9", "CK", "S3", "H7", "C8", "S2", "H5");
      Result r15b = assertHand(HIGH_CARD, "D9", "CK", "S3", "C7", "S8", "S2", "H5");
      assertOrder(r11, r12, r13, r14, r15);
      assertEquals(r15, r15a, r15b);

      Result r21 = assertHand(PAIR, "SK", "DQ", "SQ", "S4", "S2", "H5", "H6");
      Result r22 = assertHand(PAIR, "SA", "DJ", "SJ", "S4", "S2", "H5", "H6");
      Result r23 = assertHand(PAIR, "SJ", "D0", "S0", "S4", "S2", "H5", "H6");
      Result r24 = assertHand(PAIR, "S9", "D0", "S0", "S8", "S2", "H5", "H6");
      Result r25 = assertHand(PAIR, "S9", "D0", "S0", "S8", "S2", "H5", "H4");
      Result r25a = assertHand(PAIR, "S9", "H0", "D0", "S8", "S2", "H5", "H4");
      Result r25b = assertHand(PAIR, "D9", "H0", "S0", "C8", "S2", "H5", "H4");
      assertOrder(r21, r22, r23, r24, r25);
      assertEquals(r25, r25a, r25b);

      Result r221 = assertHand(TWO_PAIRS, "SK", "DQ", "SQ", "S4", "S5", "H5", "H6");
      Result r222 = assertHand(TWO_PAIRS, "SA", "DJ", "SJ", "S4", "S5", "H5", "H6");
      Result r223 = assertHand(TWO_PAIRS, "SA", "D0", "S0", "S4", "S7", "H7", "H6");
      Result r224 = assertHand(TWO_PAIRS, "SA", "D0", "S0", "S4", "S5", "H5", "H6");
      Result r225 = assertHand(TWO_PAIRS, "SK", "D0", "S0", "S4", "S5", "H5", "H6");
      Result r225a = assertHand(TWO_PAIRS, "SK", "H0", "C0", "S4", "C5", "H5", "H6");
      Result r225b = assertHand(TWO_PAIRS, "SK", "D0", "H0", "S4", "S5", "D5", "H6");
      assertOrder(r221, r222, r223, r224, r225);
      assertEquals(r225, r225a, r225b);

      Result r31 = assertHand(THREE, "SK", "DQ", "SQ", "S4", "CQ", "H5", "H6");
      Result r32 = assertHand(THREE, "SA", "DJ", "CJ", "SK", "SJ", "HQ", "H2");
      Result r33 = assertHand(THREE, "SA", "D0", "D7", "S4", "S7", "H7", "H6");
      Result r34 = assertHand(THREE, "S9", "D0", "C7", "S4", "S7", "H7", "H6");
      Result r35 = assertHand(THREE, "S2", "D0", "H7", "S4", "S7", "C7", "H6");
      Result r35a = assertHand(THREE, "S2", "S0", "S7", "S4", "D7", "C7", "C6");
      Result r35b = assertHand(THREE, "S2", "C0", "C7", "S4", "S7", "H7", "S6");
      assertOrder(r31, r32, r33, r34, r35);
      assertEquals(r35, r35a, r35b);

      Result rs1 = assertHand(STRAIGHT, "SK", "CA", "CJ", "S0", "DQ", "S2", "H6");
      Result rs2 = assertHand(STRAIGHT, "SK", "CQ", "SJ", "S0", "D5", "S2", "H9");
      Result rs3 = assertHand(STRAIGHT, "SK", "C8", "S7", "S6", "D5", "S2", "H4");
      Result rs4 = assertHand(STRAIGHT, "C6", "C2", "S3", "S4", "D5", "S6", "H6");
      Result rs5 = assertHand(STRAIGHT, "SA", "CA", "HA", "S5", "D4", "S2", "D3");
      Result rs5a = assertHand(STRAIGHT, "CA", "HA", "SA", "C5", "S4", "H2", "H3");
      Result rs5b = assertHand(STRAIGHT, "HA", "DA", "SA", "H5", "D4", "C2", "H3");
      assertOrder(rs1, rs2, rs3, rs4, rs5);
      assertEquals(rs5, rs5a, rs5b);

      Result rf1 = assertHand(FLUSH, "SA", "SK", "S3", "S4", "S5", "H5", "H6");
      Result rf2 = assertHand(FLUSH, "SA", "S7", "S3", "S4", "S5", "H5", "H6");
      Result rf3 = assertHand(FLUSH, "DA", "D7", "D3", "D2", "D5", "S5", "C5");
      Result rf4 = assertHand(FLUSH, "SK", "DA", "SQ", "SJ", "S0", "S5", "H6");
      Result rf5 = assertHand(FLUSH, "CK", "C7", "C2", "C4", "D3", "C5", "H6");
      Result rf5a = assertHand(FLUSH, "SK", "S7", "S2", "S4", "D3", "S5", "H6");
      Result rf5b = assertHand(FLUSH, "HK", "H7", "H2", "H4", "D3", "H5", "D6");
      assertOrder(rf1, rf2, rf3, rf4, rf5);
      assertEquals(rf5, rf5a, rf5b);

      Result rfh1 = assertHand(FULL_HOUSE, "CK", "HK", "SK", "HA", "CA", "DA", "H6");
      Result rfh2 = assertHand(FULL_HOUSE, "CQ", "HQ", "SQ", "HA", "CA", "DA", "H6");
      Result rfh3 = assertHand(FULL_HOUSE, "CK", "HK", "SK", "H0", "C0", "DJ", "HJ");
      Result rfh4 = assertHand(FULL_HOUSE, "CK", "HK", "SK", "H0", "C0", "D9", "H9");
      Result rfh5 = assertHand(FULL_HOUSE, "CK", "HK", "D0", "H0", "C0", "DA", "CA");
      Result rfh5a = assertHand(FULL_HOUSE, "CK", "HK", "S0", "D0", "C0", "DA", "CA");
      Result rfh5b = assertHand(FULL_HOUSE, "CK", "HK", "S0", "H0", "D0", "SA", "HA");
      assertOrder(rfh1, rfh2, rfh3, rfh4, rfh5);
      assertEquals(rfh5, rfh5a, rfh5b);

      Result r41 = assertHand(FOUR, "CK", "HK", "SA", "H0", "CA", "DA", "HA");
      Result r42 = assertHand(FOUR, "CQ", "DQ", "SA", "HA", "CA", "DA", "HQ");
      Result r43 = assertHand(FOUR, "CK", "HK", "S0", "H0", "C0", "D0", "HA");
      Result r44 = assertHand(FOUR, "CK", "SK", "S0", "H0", "C0", "D0", "HK");
      Result r45 = assertHand(FOUR, "C6", "H2", "S0", "H0", "C0", "D0", "H6");
      Result r45a = assertHand(FOUR, "C6", "H2", "S0", "H0", "C0", "D0", "S6");
      Result r45b = assertHand(FOUR, "S6", "H2", "S0", "H0", "C0", "D0", "D6");
      assertOrder(r41, r42, r43, r44, r45);
      assertEquals(r45, r45a, r45b);

      Result rsf1 = assertHand(STRAIGHT_FLUSH, "SA", "SK", "SJ", "SQ", "S0", "S2", "H6");
      Result rsf2 = assertHand(STRAIGHT_FLUSH, "SA", "SQ", "SJ", "S9", "S0", "S8", "H6");
      Result rsf3 = assertHand(STRAIGHT_FLUSH, "SA", "S7", "S3", "S4", "S5", "S8", "S6");
      Result rsf4 = assertHand(STRAIGHT_FLUSH, "SA", "S7", "S3", "S4", "S5", "S2", "S6");
      Result rsf5 = assertHand(STRAIGHT_FLUSH, "SA", "S7", "S3", "S4", "S5", "S2", "H6");
      Result rsf5a = assertHand(STRAIGHT_FLUSH, "HA", "H7", "H3", "H4", "H5", "H2", "S6");
      Result rsf5b = assertHand(STRAIGHT_FLUSH, "CA", "C7", "C3", "C4", "C5", "C2", "S6");
      assertOrder(rsf1, rsf2, rsf3, rsf4, rsf5);
      assertEquals(rsf5, rsf5a, rsf5b);

    } catch (Throwable e) {
      System.out.println("Test failed for set : " + Arrays.toString(codes));
      e.printStackTrace();
      System.out.println(rc);
    }
  }

  Result assertHand(Hand hand, String... codes) {
    Result r = getResult(codes);
    assert r.hand == hand;
    return r;
  }

  void assertOrder(Result... results) {
    for (int i = 1; i < results.length; i++) {
      for (int j = i + 1; j < results.length; j++) {
        assert results[i].compareTo(results[j]) > 0;
      }
    }
  }

  void assertEquals(Result... results) {
    for (int i = 1; i < results.length; i++) {
      for (int j = i; j < results.length; j++) {
        assert results[i].equals(results[j]);
      }
    }
  }

  CardSet getCardSet(String... codes) {
    assert codes.length == ResultCalculator.CARD_COUNT;
    List<Card> cards = new ArrayList<>();
    for (String code : codes) {
      cards.add(new Card(code));
    }
    return new CardSet(cards);
  }

  Result getResult(String... codes) {
    this.codes = codes;
    CardSet set = getCardSet(codes);
    rc.calculateResult(set);
    return set.result;
  }
}
