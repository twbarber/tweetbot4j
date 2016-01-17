package personality;

import twitter.Response;
import twitter.Tweet;

/**
 * Interface for designing personalities for Tweetbots.
 */
public interface Personality {

  Response onMention(Tweet tweet);

}
