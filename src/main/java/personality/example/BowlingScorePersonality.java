package personality.example;

import personality.Personality;
import twitter.Response;
import twitter.Tweet;

/**
 * tweetbot4j example Personality for Bowling Score calculation.
 */
public class BowlingScorePersonality implements Personality {

  public Response onMention(Tweet tweet) {
    return new Response();
  }

}
