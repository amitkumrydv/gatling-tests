package com.jforce.emeritus.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import com.jforce.emeritus.assertions.AssertionFactory;
import com.jforce.emeritus.config.HttpProtocolConfig;
import com.jforce.emeritus.config.UserInjection;
import com.jforce.emeritus.utility.UserFeeder;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * This simulation tests user login and access to a protected API endpoint.
 * It performs authentication using a feeder, checks HTTP status, logs information,
 * and throws exceptions if required session data is missing.
 *
 * <p><b>Note:</b> Logging sensitive information like passwords should be avoided in production.</p>
 *
 * @see UserFeeder#getRealisticUser()
 * @see HttpProtocolConfig#httpProtocolLogin
 * @see AssertionFactory#globalAssertions()
 */
public class LoginSimultion extends Simulation {
	
	// @formatter:off

    /**
     * Logger instance for debug and info messages.
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginSimultion.class);

    /**
     * Scenario: Logs in users using a feeder and performs validation and logging.
     *
     * @throws RuntimeException if session data is missing or malformed
     */
    ScenarioBuilder loginScenario =
        scenario("Login and Access Protected API")
            .feed(UserFeeder.getRealisticUser())
            .exec(
                http("Login Request")
                    .post("/auth/login")
                    .formParam("username", "#{username}")
                    .formParam("password", "#{password}")
                    .check(status().is(200))
            )
            .exec(session -> {
                try {
                    String username = session.getString("username");
                    String password = session.getString("password");
                    // Check for missing data and throw exception
                    if (username == null || password == null) {
                        throw new RuntimeException("Username or password not found in session.");
                    }

                    // Logging
                    logger.info("Username: {}", username);
                    logger.debug("Password: {}", password); // Don't log in production

                    return session;

                } catch (Exception e) {
                    logger.error("Session processing failed: {}", e.getMessage());
                    throw e; // Let Gatling fail this user's execution
                }
            });

    /**
     * Injects users and applies protocol configuration and global assertions.
     */
    {
        setUp(
            loginScenario.injectOpen(UserInjection.realisticLoad)
        )
        .assertions(AssertionFactory.globalAssertions())
        .protocols(HttpProtocolConfig.httpProtocolLogin);
    }
}
