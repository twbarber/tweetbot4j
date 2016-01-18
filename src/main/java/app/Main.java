package app;

import twitter.Config;
import twitter.Tweetbot;

/**
 * Main class for running Jar.
 */
public class Main {

  public static void main(String[] args) {
    Config config = new Config();
    Tweetbot tweetbot = new Tweetbot(config);
    tweetbot.run();
  }

}
