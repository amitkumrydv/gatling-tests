package com.jforce.emeritus.common;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;

public class RequestChains {
	
	
	  
       FeederBuilder.Batchable<String> users = csv("data/user.csv").circular();

  public static ChainBuilder login() {



    return exec(
        http("Login Request")
            .post("/auth/login")
            .formParam("username", "#{username}")
            .formParam("password", "#{password}")
            .check(status().is(200))
            .check(jsonPath("$.token").saveAs("authToken")));
  }

  public static ChainBuilder callProtectedEndpoint() {
    return exec(
        http("Call Protected API")
            .get("/protected-endpoint")
            .header("Authorization", "Bearer #{authToken}")
            .check(status().is(200)));
  }
}
