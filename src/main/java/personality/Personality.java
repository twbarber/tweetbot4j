package personality;

import twitter.Response;

/**
 * Interface for designing personalities for Tweetbots.
 */
public interface Personality {

  Response onMention();

}
