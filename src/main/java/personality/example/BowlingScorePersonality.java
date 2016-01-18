package personality.example;

import java.util.LinkedList;
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
import util.BowlingScoreUtils;

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
    response += getCritique(bowlingGame.getScore());
    respondToTweet(status, response);
  }

  private String getCritique(int score) {
    return score == 300 ? " A perfect game!" :
        score >= 200 ? " Way to go!" :
        score > 100 ? " Not to bad." : " There's always next time.";
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
    System.out.print("Attempting to send the reply.");
    try {
      twitter.updateStatus(statusToPost);
      System.out.print("Sent the reply.");
    } catch (TwitterException e) {
      System.err.print("Unable to reply.");
      e.printStackTrace();
    }
  }

  public class Game {

    private String player;
    private List<Frame> scoreboard;

    public Game(String player, List<Integer> rolls) {
      this.player = player;
      this.scoreboard = populateScoreboard(rolls);
    }

    private List<Frame> populateScoreboard(List<Integer> rolls) {
      List<Frame> scoreboard = new LinkedList<>();
      int frameIndex = 1;
      for(int i = 0; i < rolls.size(); i++) {
        if (frameIndex == 10) {
          if (rolls.size() - i == 3) {
            scoreboard.add(new Frame(frameIndex, rolls.get(i), rolls.get(i + 1), rolls.get(i + 2)));
            i += 2;
          } else if (rolls.size() - i == 2) {
            scoreboard.add(new Frame(frameIndex, rolls.get(i), rolls.get(i + 1)));
            i++;
          } else {
            throw new IllegalArgumentException("Bad score format.");
          }
        } else {
          if (rolls.get(i) < 10) {
            scoreboard.add(new Frame(frameIndex, rolls.get(i), rolls.get(i + 1)));
            frameIndex++;
            i++;
          } else if (rolls.get(i) == 10) {
            scoreboard.add(new Frame(frameIndex, 10, 0));
            frameIndex++;
          } else if (rolls.get(i) > 10 || rolls.get(i) < 0) {
            throw new IllegalArgumentException("Bad score format.");
          }
        }
      }
      for (int i = 0; i < scoreboard.size() - 1; i++) {
        scoreboard.get(i).setNextFrame(scoreboard.get(i+1));
      }
      return scoreboard;
    }

    public int getScore() {
      if (scoreboard.isEmpty()) {
        throw new IllegalArgumentException("Game is incomplete.");
      }
      return calculateScore();
    }

    private int calculateScore() {
      int score = 0;
      for (Frame frame : scoreboard) {
        if (frame.isStrike()) {
          score += 10 + strikeBonus(frame);
        } else if (frame.isSpare()) {
          score += 10 + spareBonus(frame);
        } else {
          score += frame.total();
        }
      }
      return score;
    }

    private int spareBonus(Frame frame) {
      if (frame.frameNumber < 10) {
        return frame.nextFrame.firstBall;
      } else {
        return frame.extraBall;
      }
    }

    private int strikeBonus(Frame frame) {
      if (frame.frameNumber <= 9) {
        if (frame.nextFrame.isStrike() && frame.frameNumber != 9) {
          return frame.nextFrame.firstBall + frame.nextFrame.nextFrame.firstBall;
        } else {
          return frame.nextFrame.firstBall + frame.nextFrame.secondBall;
        }
      } else {
        if (frame.hasSecondStrike() && !frame.hasThirdStrike()) {
          return frame.extraBall;
        } else {
          return frame.secondBall + frame.extraBall;
        }
      }
    }

    public class Frame {

      private int frameNumber;
      private int firstBall;
      private int secondBall;
      private int extraBall;
      private Frame nextFrame;

      public Frame(int frameNumber, int firstBall, int secondBall) {
        this.frameNumber = frameNumber;
        this.firstBall = firstBall;
        this.secondBall = secondBall;
      }

      public Frame(int frameNumber, int firstBall, int secondBall, int extraBall) {
        this.frameNumber = frameNumber;
        this.firstBall = firstBall;
        this.secondBall = secondBall;
        this.extraBall = extraBall;
      }

      public void setNextFrame(Frame nextFrame) {
        this.nextFrame = nextFrame;
      }

      public boolean isSpare() {
        return firstBall + secondBall == 10;
      }

      public boolean isStrike() {
        return firstBall == 10;
      }

      public boolean hasSecondStrike() {
        return secondBall == 10;
      }

      public boolean hasThirdStrike() {
        return extraBall == 10;
      }

      public int total() {
        return firstBall + secondBall;
      }
    }
  }
}
