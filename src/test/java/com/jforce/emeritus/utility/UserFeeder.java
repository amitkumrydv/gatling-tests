package com.jforce.emeritus.utility;

import static io.gatling.javaapi.core.CoreDsl.csv;

import io.gatling.javaapi.core.FeederBuilder;

public class UserFeeder {

  public static FeederBuilder<String> getCanvasUserID() {
    return csv("data/Canvas_user_ids.csv").circular();
  }
  
  
  
  
  public static FeederBuilder<String> getRealisticUser() {
	    return csv("data/users.csv").circular();
	  }
  
  

  // Optionally: Random inline feeder
  /*
  public static FeederBuilder<Object> getRandomInlineFeeder() {
      return java.util.Arrays.asList(
          java.util.Collections.singletonMap("userId", 14),
          java.util.Collections.singletonMap("userId", 22),
          java.util.Collections.singletonMap("userId", 35)
      ).iterator();
  }
  */
}
