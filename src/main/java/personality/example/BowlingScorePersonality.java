package personality.example;

import java.util.List;
import personality.Personality;
import twitter.Config;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;
import util.StatusUtils;

/**
 * tweetbot4j example Personality for Bowling Score calculation.
 */
public class BowlingScorePersonality implements Personality {

  private Configuration configuration;

  public BowlingScorePersonality(Config config) {
    this.configuration = new ConfigurationBuilder()
        .setOAuthConsumerKey(config.getConsumerKey())
        .setOAuthConsumerSecret(config.getConsumerSecret())
        .setOAuthAccessToken(config.getAccessToken())
        .setOAuthAccessTokenSecret(config.getAccessTokenSecret())
        .setUser(config.getHandle())
        .build();
  }

  public void onMention(Status status) {
    String tweet = status.getText();
    if (StatusUtils.matchesBowlingScoreFormat(tweet)) {
      Game bowlingGame = parseBowlingGame(tweet);
      respondWithFinalScore(bowlingGame, status);
    } else {
      respondBadFormatMessage(status);
    }
  }

  private Game parseBowlingGame(String tweet) {
    return new Game;
  }

  private void respondWithFinalScore(Game bowlingGame, Status statusToRespondTo) {
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

  private void respondBadFormatMessage(Status status) {
    final
  }

  class Game {

    private String player;
    private List<Frame> scoreboard;

    private void addFrame(Frame frameToAdd) {
      this.scoreboard.add(frameToAdd);
    }

  }

  class Frame {

    private int frameNumber;
    private int firstBall;
    private int secondBall;
    private int extraBall;

    public Frame(){

    }

  }

}
