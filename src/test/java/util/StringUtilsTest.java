package util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tyler on 1/18/16.
 */
public class StringUtilsTest {

  @Test
  public void testMentionStrip() {
    String user = "@Test";
    String content = " this is a test.";
    String tweet = user + content;
    assertEquals(content, StringUtils.removeMentions(user + tweet));
  }

  @Test
  public void testMatchesBowlingFormat() {
    String tweet = "@Test John 6 2 7 1 10 9 0 8 2 10 10 3 5 7 2 5 5 8";
    assertTrue(StringUtils.matchesBowlingScoreFormat(tweet));
  }

  @Test
  public void testMatchesBowlingFormatPerfect() {
    String tweet = "@Test John 10 10 10 10 10 10 10 10 10 10 10 10";
    assertTrue(StringUtils.matchesBowlingScoreFormat(tweet));
  }

  @Test
  public void testMatchesBowlingFormatZeros() {
    String tweet = "@Test John 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";
    assertTrue(StringUtils.matchesBowlingScoreFormat(tweet));
  }

  @Test
  public void testMatchesBowlingFormatZerosAndStrikes() {
    String tweet = "@Test John 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 10 10 10";
    assertTrue(StringUtils.matchesBowlingScoreFormat(tweet));
  }

  @Test
  public void testDeosntMatchBowlingFormat() {
    String tweet = "@Test John 0 0";
    assertFalse(StringUtils.matchesBowlingScoreFormat(tweet));
  }

}