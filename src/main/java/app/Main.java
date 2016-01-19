package app;

import personality.Personality;
import personality.example.bowling.BowlingScorePersonality;
import personality.example.echo.EchoPersonality;
import twitter.Config;
import twitter.Tweetbot;

/**
 * Main class for running Jar.
 */
public class Main {

  public static void main(String[] args) {
    Config config = new Config();
    Personality personality;
    if ((System.getProperty("bot.personality") != null
        && System.getProperty("bot.personality").equals("bowling"))
        || (args.length != 0 && args[0].equals("bowling"))) {
      personality = new BowlingScorePersonality(config);
    } else {
      personality = new EchoPersonality(config);
    }
    Tweetbot tweetbot = new Tweetbot(config, personality);
    tweetbot.run();
  }

}
