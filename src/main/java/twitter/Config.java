package twitter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration data for a Tweetbot.
 */
public class Config {

  private String consumerKey;
  private String consumerSecret;
  private String accessToken;
  private String accessTokenSecret;
  private String user;

  public Config() {
    Properties properties = loadProperties();
    this.consumerKey = properties.getProperty("oauth.consumerKey");
    this.consumerSecret = properties.getProperty("oauth.consumerSecret");
    this.accessToken = properties.getProperty("oauth.accessToken");
    this.accessTokenSecret = properties.getProperty("oauth.accessTokenSecret");
    this.user = properties.getProperty("bot.user");
  }

  private Properties loadProperties() {
    Properties properties = new Properties();
    try {
      InputStream in = ClassLoader.getSystemClassLoader().getResourceAsStream("twitter4j.properties");
      properties.load(in);
      in.close();
    } catch (IOException e) {
      System.err.print("Unable to load Configuration file.");
    }
      return properties;
  }

  public String getConsumerKey() {
    return consumerKey;
  }

  public String getConsumerSecret() {
    return consumerSecret;
  }

  public String getAccessToken() {
    return accessToken;
  }

  public String getAccessTokenSecret() {
    return accessTokenSecret;
  }

  public String getHandle() {
    return user;
  }
}
