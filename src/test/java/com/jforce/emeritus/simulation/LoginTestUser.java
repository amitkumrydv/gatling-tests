package com.jforce.emeritus.simulation;

import java.time.Duration;
import java.util.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import io.gatling.javaapi.jdbc.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.jdbc.JdbcDsl.*;

public class LoginTestUser extends Simulation {

  private HttpProtocolBuilder httpProtocol = http
    .baseUrl("https://studentzone-ncdoe.nmims.edu")
    .disableFollowRedirect()
    .disableAutoReferer()
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:142.0) Gecko/20100101 Firefox/142.0");
  
  private Map<CharSequence, String> headers_0 = Map.ofEntries(
    Map.entry("Priority", "u=0, i"),
    Map.entry("Sec-Fetch-Dest", "document"),
    Map.entry("Sec-Fetch-Mode", "navigate"),
    Map.entry("Sec-Fetch-Site", "none"),
    Map.entry("Sec-Fetch-User", "?1"),
    Map.entry("Upgrade-Insecure-Requests", "1")
  );
  
  private Map<CharSequence, String> headers_1 = Map.ofEntries(
    Map.entry("Cache-Control", "no-cache"),
    Map.entry("Pragma", "no-cache"),
    Map.entry("Priority", "u=0, i"),
    Map.entry("Sec-Fetch-Dest", "document"),
    Map.entry("Sec-Fetch-Mode", "navigate"),
    Map.entry("Sec-Fetch-Site", "cross-site"),
    Map.entry("Upgrade-Insecure-Requests", "1")
  );
  
  private Map<CharSequence, String> headers_2 = Map.ofEntries(
    Map.entry("Accept", "*/*"),
    Map.entry("Priority", "u=4"),
    Map.entry("content-type", "application/json")
  );
  
  private Map<CharSequence, String> headers_4 = Map.ofEntries(
    Map.entry("Origin", "https://studentzone-ncdoe.nmims.edu"),
    Map.entry("Priority", "u=0, i"),
    Map.entry("Referer", "https://studentzone-ncdoe.nmims.edu/"),
    Map.entry("Sec-Fetch-Dest", "document"),
    Map.entry("Sec-Fetch-Mode", "navigate"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("Sec-Fetch-User", "?1"),
    Map.entry("Upgrade-Insecure-Requests", "1")
  );
  
  private Map<CharSequence, String> headers_5 = Map.ofEntries(
    Map.entry("Priority", "u=0, i"),
    Map.entry("Referer", "https://studentzone-ncdoe.nmims.edu/"),
    Map.entry("Sec-Fetch-Dest", "document"),
    Map.entry("Sec-Fetch-Mode", "navigate"),
    Map.entry("Sec-Fetch-Site", "same-origin"),
    Map.entry("Sec-Fetch-User", "?1"),
    Map.entry("Upgrade-Insecure-Requests", "1")
  );
  
  private Map<CharSequence, String> headers_6 = Map.ofEntries(
    Map.entry("Accept", "*/*"),
    Map.entry("Content-Type", "application/json"),
    Map.entry("Origin", "https://studentzone-ncdoe.nmims.edu"),
    Map.entry("Priority", "u=4"),
    Map.entry("Referer", "https://studentzone-ncdoe.nmims.edu/"),
    Map.entry("Sec-Fetch-Dest", "empty"),
    Map.entry("Sec-Fetch-Mode", "cors"),
    Map.entry("Sec-Fetch-Site", "same-origin")
  );
  
  private Map<CharSequence, String> headers_7 = Map.ofEntries(
    Map.entry("Accept", "image/avif,image/webp,image/png,image/svg+xml,image/*;q=0.8,*/*;q=0.5"),
    Map.entry("Priority", "u=6"),
    Map.entry("Referer", "https://studentzone-ncdoe.nmims.edu/"),
    Map.entry("Sec-Fetch-Dest", "image"),
    Map.entry("Sec-Fetch-Mode", "no-cors"),
    Map.entry("Sec-Fetch-Site", "same-origin")
  );
  

  private ScenarioBuilder scn = scenario("LoginTestUser")
    .exec(
      http("Login_Page")
        .post("/")
        .headers(headers_4)
        .formParam("username", "77777777173")
        .formParam("password", "admin@ncdoe2024")
        .check(status().is(302))
//      http("quickJoinTestList")
//        .get("/quickJoinTestList")
//        .headers(headers_5),
//      pause(Duration.ofMillis(204)),
//      http("getAllPGIAForSapid")
//        .post("/internal-assessment/m/pg/getAllPGIAForSapid")
//        .headers(headers_6)
//        .body(RawFileBody("0006_request.json")),
//      http("request_7")
//        .get("/")
//        .headers(headers_7)
    );

  {
	  setUp(scn.injectOpen(atOnceUsers(1000))).protocols(httpProtocol);
  }
}
