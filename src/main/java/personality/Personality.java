package personality;

import twitter.Response;
import twitter4j.Status;

/**
 * Interface for designing personalities for Tweetbots.
 */
public interface Personality {

  void onMention(Status status);

}
