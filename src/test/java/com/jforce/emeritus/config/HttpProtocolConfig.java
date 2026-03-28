package com.jforce.emeritus.config;

import static com.jforce.emeritus.utility.Config.*;
import static com.jforce.emeritus.utility.Keys.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;
// import static io.gatling.javaapi.jdbc.JdbcDsl.*;

import io.gatling.javaapi.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HttpProtocolConfig {
	
	       // @formatter:off

           public static final String ACCESS_TOKEN = System.getenv("AUTH_TOKEN");
           private static final Logger logger = LoggerFactory.getLogger(HttpProtocolConfig.class);

            // Common for the all API
           public static HttpProtocolBuilder httpProtocol =
                                                      http.baseUrl(baseUrl)
                                                          .inferHtmlResources(
                                                                        AllowList(),
                                                                        DenyList(".*\\.js",
                                                                                 ".*\\.css",
                                                                                 ".*\\.gif",
                                                                                 ".*\\.jpeg",
                                                                                 ".*\\.jpg",
                                                                                 ".*\\.ico",
                                                                                 ".*\\.woff",
                                                                                 ".*\\.woff2",
                                                                                 ".*\\.(t|o)tf",
                                                                                 ".*\\.png",
                                                                                 ".*\\.svg",
                                                                                 ".*detectportal\\.firefox\\.com.*"))
                                                          .acceptHeader("*/*")
                                                          .acceptEncodingHeader("gzip, deflate, br")
                                                          .acceptLanguageHeader("en-US,en;q=0.5")
                                                          .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:137.0) Gecko/20100101 Firefox/137.0")
                                                          .authorizationHeader("Bearer " + ACCESS_TOKEN);
           
           

           public static HttpProtocolBuilder httpProtocolWithAuthentication =
                                                        http.baseUrl(baseUrl)
                                                            .acceptHeader("application/json")
                                                            .contentTypeHeader("application/json")
                                                            .authorizationHeader("Bearer " + ACCESS_TOKEN);
         
         

           public static HttpProtocolBuilder httpProtocolLogin =
                                                   http.baseUrl(baseUrl)
                                                       .acceptHeader("application/json")
                                                       .contentTypeHeader("application/json");
           
           

           public static HttpProtocolBuilder createHttpProtocolWithAuthentication() {
        	   
                                                 if (ACCESS_TOKEN == null || ACCESS_TOKEN.isEmpty()) {
                                                          logger.error("ACCESS_TOKEN is not set.");
                                                          throw new IllegalStateException("ACCESS_TOKEN is not set.");
                                                       }

                                                logger.info("Using Access Token: {}", ACCESS_TOKEN); // Debug log

                                                return http.baseUrl(baseUrl)
                                                          .acceptHeader("application/json")
                                                          .contentTypeHeader("application/json")
                                                          .authorizationHeader("Bearer " + ACCESS_TOKEN);
  }
}
