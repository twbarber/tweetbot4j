package personality.example;

import personality.Personality;
import twitter.Response;

/**
 * tweetbot4j example Personality for Bowling Score calculation.
 */
public class BowlingScorePersonality implements Personality {

  public Response onMention() {
    return new Response();
  }

}
