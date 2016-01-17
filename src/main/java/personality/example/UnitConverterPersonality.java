package personality.example;

import personality.Personality;
import twitter.Response;

/**
 * tweetbot4j example Personality for Unit conversion.
 */
public class UnitConverterPersonality implements Personality {

  public Response onMention() {
    return new Response();
  }

}
