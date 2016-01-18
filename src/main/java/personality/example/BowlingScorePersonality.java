package personality.example;

import java.util.List;
import personality.Personality;
import twitter.Response;
import twitter4j.Status;

/**
 * tweetbot4j example Personality for Bowling Score calculation.
 */
public class BowlingScorePersonality implements Personality {

  public void onMention(Status status) {

  }

  class Game {

    private String player;
    private List<Frame> score;

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
