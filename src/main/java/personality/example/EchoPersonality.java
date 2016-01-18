package personality.example;

import personality.Personality;
import twitter.Config;
import twitter.Response;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by tyler on 1/17/16.
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
    System.out.print("Sending the heartbeat, chief.");
    try {
      twitter.updateStatus(stat);
      System.out.print("Sent the heartbeat, chief.");
    } catch (TwitterException e) {
      System.err.print("Couldn't confirm. Am not alive.");
      e.printStackTrace();
    }
  }
}
