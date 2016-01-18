package twitter;

import personality.Personality;
import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Main interface between Personalities and Twitter API.
 */
public class Tweetbot {

  private Configuration config;
  private Personality personality;

  public Tweetbot(Config config, Personality personality) {
    this.config = new ConfigurationBuilder()
        .setOAuthConsumerKey(config.getConsumerKey())
        .setOAuthConsumerSecret(config.getConsumerSecret())
        .setOAuthAccessToken(config.getAccessToken())
        .setOAuthAccessTokenSecret(config.getAccessTokenSecret())
        .setUser(config.getHandle())
        .build();
    this.personality = personality;
  }

  public void run() {
    TwitterStream twitterStream = getStreamWithListener();
    twitterStream.onStatus(status -> {
      this.personality.onMention(status);
    });
  }

  private TwitterStream getStreamWithListener() {
    TwitterStream twitterStream = new TwitterStreamFactory(this.config).getInstance();
    twitterStream.addListener(listener);
    FilterQuery tweetFilterQuery = new FilterQuery();
    tweetFilterQuery.track("@" + this.config.getUser());
    twitterStream.filter(tweetFilterQuery);
    return twitterStream;
  }

  public final UserStreamListener listener = new UserStreamListener() {

    public void onException(Exception e) {

    }

    public void onStatus(Status status) {
      System.out.print("tweet gotten");
    }

    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    public void onTrackLimitationNotice(int i) {

    }

    public void onScrubGeo(long l, long l1) {

    }

    public void onStallWarning(StallWarning stallWarning) {

    }

    public void onDeletionNotice(long l, long l1) {

    }

    public void onFriendList(long[] longs) {

    }

    public void onFavorite(User user, User user1, Status status) {

    }

    public void onUnfavorite(User user, User user1, Status status) {

    }

    public void onFollow(User user, User user1) {

    }

    public void onUnfollow(User user, User user1) {

    }

    public void onDirectMessage(DirectMessage directMessage) {

    }

    public void onUserListMemberAddition(User user, User user1, UserList userList) {

    }

    public void onUserListMemberDeletion(User user, User user1, UserList userList) {

    }

    public void onUserListSubscription(User user, User user1, UserList userList) {

    }

    public void onUserListUnsubscription(User user, User user1, UserList userList) {

    }

    public void onUserListCreation(User user, UserList userList) {

    }

    public void onUserListUpdate(User user, UserList userList) {

    }

    public void onUserListDeletion(User user, UserList userList) {

    }

    public void onUserProfileUpdate(User user) {

    }

    public void onUserSuspension(long l) {

    }

    public void onUserDeletion(long l) {

    }

    public void onBlock(User user, User user1) {

    }

    public void onUnblock(User user, User user1) {

    }

    public void onRetweetedRetweet(User user, User user1, Status status) {

    }

    public void onFavoritedRetweet(User user, User user1, Status status) {

    }

    public void onQuotedTweet(User user, User user1, Status status) {

    }

  };

}
