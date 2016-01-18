package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utilities for parsing tweets for the BowlingScorePersonality.
 */
public class BowlingScoreUtils {

  /**
   * Tests if tweet matches bowling game format.
   * @param tweet Tweet to parse.
   * @return True if format matches, false otherwise.
   */
  public static boolean matchesBowlingScoreFormat(String tweet) {
    String bowlingRegex = "(@\\w+)+ \\w+ (\\d{1,2} ){10,20}(\\d{1,2})";
    return Pattern.matches(bowlingRegex, tweet);
  }

  /**
   * Gets player name from tweet.
   * @param tweet Tweet to parse.
   * @return Name of player.
   */
  public static String getPlayerName(String tweet) {
    String text = StatusUtils.removeMentions(tweet).trim();
    String[] textElements = text.split(" ");
    return textElements[0];
  }

  /**
   * Strips mentions and player name, returns a list of scores for each ball roll.
   * @param tweet Tweet to parse.
   * @return List of scores for each roll.
   */
  public static List<Integer> getScores(String tweet) {
    String[] rawScores = StatusUtils.removeMentions(tweet).trim().split(" ");
    List<Integer> scores = new ArrayList<>();
    for (int i = 1; i < rawScores.length; i++) {
      scores.add(Integer.valueOf(rawScores[i]));
    }
    return scores;
  }
}
