package util;

import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tyler on 1/18/16.
 */
public class BowlingScoreUtilsTest {

  @Test
  public void testMatchesBowlingFormat() {
    String tweet = "@Test John 6 2 7 1 10 9 0 8 2 10 10 3 5 7 2 5 5 8";
    assertTrue(BowlingScoreUtils.matchesBowlingScoreFormat(tweet));
  }

  @Test
  public void testMatchesBowlingFormatPerfect() {
    String tweet = "@Test John 10 10 10 10 10 10 10 10 10 10 10 10";
    assertTrue(BowlingScoreUtils.matchesBowlingScoreFormat(tweet));
  }

  @Test
  public void testMatchesBowlingFormatZeros() {
    String tweet = "@Test John 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";
    assertTrue(BowlingScoreUtils.matchesBowlingScoreFormat(tweet));
  }

  @Test
  public void testMatchesBowlingFormatZerosAndStrikes() {
    String tweet = "@Test John 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 10 10 10";
    assertTrue(BowlingScoreUtils.matchesBowlingScoreFormat(tweet));
  }

  @Test
  public void testDeosntMatchBowlingFormat() {
    String tweet = "@Test John 0 0";
    assertFalse(BowlingScoreUtils.matchesBowlingScoreFormat(tweet));
  }

  @Test
  public void testGetName() {
    String tweet = "@Test John 10 10 10 10 10 10 10 10 10 10 10 10";
    assertEquals("John", BowlingScoreUtils.getPlayerName(tweet));
  }

  @Test
  public void testGetScores() {
    String tweet = "@Test John 10 10 10 10 10 10 10 10 10 10 10 10";
    Integer[] rolls = {10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10};
    List<Integer> scores = Arrays.asList(rolls);
    assertEquals(scores, BowlingScoreUtils.getScores(tweet));
  }

  @Test
  public void testNoName() {
    String tweet = "@Test 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 10 10 10";
    assertFalse(BowlingScoreUtils.matchesBowlingScoreFormat(tweet));
  }

}