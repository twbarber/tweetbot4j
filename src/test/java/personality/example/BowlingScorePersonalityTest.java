package personality.example;

import org.junit.Before;
import org.junit.Test;
import twitter.Config;

import static org.junit.Assert.*;

/**
 * Test Class for edge cases associated with bowling scoring.
 */
public class BowlingScorePersonalityTest {

  private BowlingScorePersonality personalityUnderTest;

  @Before
  public void setUp() throws Exception {
    this.personalityUnderTest = new BowlingScorePersonality(new Config());
  }

  @Test
  public void testZeroGame() {
    String tweet = "@Test John 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0";
    BowlingScorePersonality.Game game = personalityUnderTest.parseBowlingGame(tweet);
    assertEquals(0, game.getScore());
  }

  @Test
  public void testPerfectGame() {
    String tweet = "@Test John 10 10 10 10 10 10 10 10 10 10 10 10";
    BowlingScorePersonality.Game game = personalityUnderTest.parseBowlingGame(tweet);
    assertEquals(300, game.getScore());
  }

  @Test
  public void testEndStrikeSpare() {
    String tweet = "@Test John 2 8 10 10 8 2 10 10 9 1 10 10 10 9 1";
    BowlingScorePersonality.Game game = personalityUnderTest.parseBowlingGame(tweet);
    assertEquals(236, game.getScore());
  }

  @Test
  public void testEndStrikeNine() {
    String tweet = "@Test John 2 8 10 10 8 2 10 10 9 1 10 10 10 9 0";
    BowlingScorePersonality.Game game = personalityUnderTest.parseBowlingGame(tweet);
    assertEquals(235, game.getScore());
  }

  @Test
  public void testEndSpareStrike() {
    String tweet = "@Test John 2 8 10 10 8 2 10 10 9 1 10 10 9 1 10";
    BowlingScorePersonality.Game game = personalityUnderTest.parseBowlingGame(tweet);
    assertEquals(226, game.getScore());
  }

  @Test
  public void testEndNine() {
    String tweet = "@Test John 2 8 10 10 8 2 10 10 9 1 10 10 9 0";
    BowlingScorePersonality.Game game = personalityUnderTest.parseBowlingGame(tweet);
    assertEquals(214, game.getScore());
  }

  @Test
  public void testStrikeOut() {
    String tweet = "@Test John 8 2 9 1 10 10 9 1 9 1 10 10 8 2 10 10 10";
    BowlingScorePersonality.Game game = personalityUnderTest.parseBowlingGame(tweet);
    assertEquals(225, game.getScore());
  }

  @Test
  public void testEndTurkey() {
    String tweet = "@Test John 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 10 10 10";
    BowlingScorePersonality.Game game = personalityUnderTest.parseBowlingGame(tweet);
    assertEquals(30, game.getScore());
  }

}