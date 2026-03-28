package com.jforce.emeritus.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jforce.emeritus.assertions.AssertionFactory;
import com.jforce.emeritus.config.HttpProtocolConfig;
import com.jforce.emeritus.config.UserInjection;
import com.jforce.emeritus.endpoints.UserEndpoints;
import com.jforce.emeritus.utility.UserFeeder;
import io.gatling.javaapi.core.*;

/**
 * This simulation tests the "Get User Profile" API using a realistic user load.
 * It retrieves user IDs from a feeder, logs userId, validates the HTTP 200 status,
 * and throws an exception if userId is missing from the session.
 *
 * <p><b>Note:</b> Authentication is handled by {@code createHttpProtocolWithAuthentication()}.</p>
 * 
 * @see UserFeeder#getCanvasUserID()
 * @see HttpProtocolConfig#createHttpProtocolWithAuthentication()
 * @see AssertionFactory#globalAssertions()
 */
public class GetUserProfileSimulation extends Simulation {

	
	
	//Logger for debugging and info logs.
    private static final Logger logger = LoggerFactory.getLogger(GetUserProfileSimulation.class);

    /**
     * Scenario: Fetches user profiles using user IDs from feeder.
     *
     * @throws RuntimeException if userId is missing from session
     */
    ScenarioBuilder getUserProfileSimulation =
        scenario("Get user profile - Realistic Load")
            .feed(UserFeeder.getCanvasUserID())
            .exec(session -> {
                String userId = session.getString("userId");

                if (userId == null || userId.isBlank()) {
                    throw new RuntimeException("Missing userId in session");
                }

                logger.info("Fetching profile for userId: {}", userId);
                return session;
            })
            .exec(
                http("Get User - Realistic Load")
                    .get(UserEndpoints.GET_USER_PROFILE_API)
                    .check(status().is(200))
            );

    /**
     * Injects 10 users instantly, sets authentication, and applies global assertions.
     */
    {
        setUp(
        	getUserProfileSimulation.injectOpen(UserInjection.lightLoad)
        )
        .protocols(HttpProtocolConfig.createHttpProtocolWithAuthentication())
        .assertions(AssertionFactory.basicAssertions());
    }
}