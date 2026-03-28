package com.jforce.emeritus.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jforce.emeritus.assertions.AssertionFactory;
import com.jforce.emeritus.config.HttpProtocolConfig;
import com.jforce.emeritus.config.UserInjection;
import com.jforce.emeritus.endpoints.UserEndpoints;
import com.jforce.emeritus.utility.ProgramFeeder;

import io.gatling.javaapi.core.*;

/**
 * This simulation tests the "Save Program" API under load using unique program data.
 * It logs each programCode before sending requests and ensures all responses return HTTP 200.
 */
public class CreateProgramSimulation extends Simulation {

    private static final Logger logger = LoggerFactory.getLogger(CreateProgramSimulation.class);

    ScenarioBuilder createProgramScenario =
        scenario("Create Program - Load Test")
            .feed(ProgramFeeder.getProgramData())
            .exec(session -> {
                String programCode = session.getString("programCode");
                String programName = session.getString("programName");

                if (programCode == null || programCode.isBlank()) {
                    throw new RuntimeException("Missing programCode in session");
                }

                logger.info("Creating program with code: {} and name: {}", programCode, programName);
                return session;
            })
            .exec(
                http("Save Program Request")
                    .post(UserEndpoints.PROGRAM_CREATION)
                    .body(StringBody(
                        "{ " +
                            "\"programCode\": \"#{programCode}\"," +
                            "\"programName\": \"#{programName}\"," +
                            "\"modeOfLearning\": \"#{modeOfLearning}\"," +
                            "\"specializationType\": \"#{specializationType}\"," +
                            "\"userId\": \"#{userId}\"" +
                        "}"
                    )).asJson()
                    .check(status().is(200))
            );

    {
        setUp(
            createProgramScenario.injectOpen(atOnceUsers(2))
        )
        .protocols(HttpProtocolConfig.createHttpProtocolWithAuthentication())
        .assertions(AssertionFactory.basicAssertions());
    }
}
