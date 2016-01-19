package personality.example.bowling;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * tweetbot4j example Personality for Bowling Score calculation.
 */
public class BowlingScorePersonality implements Personality {

  private Logger logger = LoggerFactory.getLogger(BowlingScorePersonality.class);
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
    this.logger.info("Mentioned in status: " + status.getId());
    String tweet = status.getText();
    if (BowlingScoreUtils.matchesBowlingScoreFormat(tweet)) {
      try {
        Game bowlingGame = parseBowlingGame(status.getText());
        respondWithFinalScore(status, bowlingGame);
      } catch (Exception e) {
        respondBadFormatMessage(status);
      }
    } else {
      respondBadFormatMessage(status);
    }
  }

  public Game parseBowlingGame(String tweet) {
    String player = BowlingScoreUtils.getPlayerName(tweet);
    List<Integer> rolls = BowlingScoreUtils.getScores(tweet);
    return new Game(player, rolls);
  }

  private void respondWithFinalScore(Status status, Game bowlingGame) {
    String response = "You scored: " + String.valueOf(bowlingGame.getScore() + ".");
    this.logger.info("Responding to: " + status.getUser());
    response += getCritique(bowlingGame.getScore());
    respondToTweet(status, response);
  }

  private String getCritique(int score) {
    return score == 300 ? " A perfect game!" :
        score >= 200 ? " Way to go!" :
        score > 150 ? " Not too bad." : " There's always next time.";
  }

  private void respondBadFormatMessage(Status status) {
    String response = "Unable to generate score with that input, sorry.";
    respondToTweet(status, response);
  }

  private void respondToTweet(Status tweet, String response) {
    Twitter twitter = new TwitterFactory(this.configuration).getInstance();
    String status = "@" + tweet.getUser().getScreenName() + " " +response;
    StatusUpdate statusToPost = new StatusUpdate(status);
    statusToPost.setInReplyToStatusId(tweet.getId());
    this.logger.info("Attempting to send the reply.");
    try {
      twitter.updateStatus(statusToPost);
      this.logger.info("Sent the reply.");
    } catch (TwitterException e) {
      System.err.print("Unable to reply.");
      e.printStackTrace();
    }
  }

}
