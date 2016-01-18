package util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by tyler on 1/18/16.
 */
public class StatusUtilsTest {

  @Test
  public void testMentionStrip() {
    String user = "@Test";
    String content = " this is a test.";
    String tweet = user + content;
    assertEquals(content.trim(), StatusUtils.removeMentions(user + tweet));
  }

}