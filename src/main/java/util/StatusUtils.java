package util;

import java.util.regex.Pattern;

/**
 * Utilities class for dealing with Strings.
 */
public class StatusUtils {

  public static String removeMentions(String tweet) {
    String mentionsRegex = "(@\\w+)+";
    return tweet.replaceAll(mentionsRegex, "").trim();
  }

  public static String getMentions(String tweet) {
    String mentionsRegex = "(@\\w+)+";
    return tweet.replaceAll(mentionsRegex, "");
  }

}
