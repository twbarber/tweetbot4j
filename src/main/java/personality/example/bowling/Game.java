package personality.example.bowling;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by Tyler on 1/19/16.
 */
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