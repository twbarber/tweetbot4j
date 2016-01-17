package personality.example;

import personality.Personality;
import twitter.Response;
import twitter.Tweet;

/**
 * tweetbot4j example Personality for Unit conversion.
 */
public class UnitConverterPersonality implements Personality {

  public Response onMention(Tweet tweet) {
    return new Response();
  }

}
