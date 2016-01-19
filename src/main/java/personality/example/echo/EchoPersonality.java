package personality.example.echo;

import personality.Personality;
import twitter.Config;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Echos what the tweeting user said, and retweets it to the original tweeter.
 */
public class EchoPersonality implements Personality {

  private Configuration configuration;

  public EchoPersonality(Config config) {
    this.configuration = new ConfigurationBuilder()
        .setOAuthConsumerKey(config.getConsumerKey())
        .setOAuthConsumerSecret(config.getConsumerSecret())
        .setOAuthAccessToken(config.getAccessToken())
        .setOAuthAccessTokenSecret(config.getAccessTokenSecret())
        .setUser(config.getHandle())
        .build();
  }

  @Override public void onMention(Status status) {
    Twitter twitter = new TwitterFactory(this.configuration).getInstance();
    String content = status.getText().replace("@" + this.configuration.getUser(), "");
    System.out.print(content);
    String response = "@" + status.getUser().getScreenName() + content;
    StatusUpdate stat = new StatusUpdate(response);
    stat.setInReplyToStatusId(status.getId());
    System.out.print("Sending the echo.");
    try {
      twitter.updateStatus(stat);
      System.out.print("Sent the echo.");
    } catch (TwitterException e) {
      System.err.print("Unable to echo.");
      e.printStackTrace();
    }
  }
}
