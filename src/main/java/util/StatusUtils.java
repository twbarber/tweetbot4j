package util;

import java.util.regex.Pattern;

/**
 * Utilities class for dealing with Strings.
 */
public class StatusUtils {

  public static String removeMentions(String tweet) {
    String mentionsRegex = "(@\\w+)+";
    return tweet.replaceAll(mentionsRegex, "");
  }

  public static String getMentions(String tweet) {
    String mentionsRegex = "(@\\w+)+";
    return tweet.replaceAll(mentionsRegex, "");
  }

  public static boolean matchesBowlingScoreFormat(String tweet) {
    String bowlingRegex = "(@\\w+)+ \\w+ (\\d{1,2} ){11,20}(\\d{1,2})";
    return Pattern.matches(bowlingRegex, tweet);
  }
}
