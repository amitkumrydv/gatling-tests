package com.jforce.emeritus.simulation;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import com.jforce.emeritus.assertions.AssertionFactory;
import com.jforce.emeritus.config.HttpProtocolConfig;
import com.jforce.emeritus.config.UserInjection;
import com.jforce.emeritus.endpoints.UserEndpoints;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.HttpProtocolBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProgressbarSimulation extends Simulation {

    private static final Logger logger = LoggerFactory.getLogger(ProgressbarSimulation.class);

    HttpProtocolBuilder httpProtocol = HttpProtocolConfig.createHttpProtocolWithAuthentication();

    ScenarioBuilder progressBarScenario = scenario("Progress Bar API Flow")
        // Initialize session
        .exec(session -> session
            .set("progressComplete", false)
            .set("attempts", 0)
            .set("progressValue", "0")
        )
        // Repeat polling up to maxAttempts
        .repeat(20, "loopCounter").on(
            exec(
                http("Student Listing progress bar --> /getReportByName/student-dashboard-list-view?includeReportData=false")
                    .get(UserEndpoints.GET_ALL_STUDENT_DASHBOARD_REPORT_API_PROGRESS_FALSE)
                    .check(status().is(200))
                    .check(jsonPath("$.reportGenrationPercentage").saveAs("progressValue"))
                    .check(jsonPath("$.reportGenerated").saveAs("reportGenerated"))
            )
            .exec(session -> {
                logger.info("reportGenrationPercentage: {}", session.getString("progressValue"));
                logger.info("reportGenerated: {}", session.getString("reportGenerated"));
                return session;
            })
            .exec(session -> {
                int attempts = session.getInt("attempts") + 1;
                String progressStr = session.getString("progressValue");
                double progress = Double.parseDouble(progressStr);
                boolean complete = "true".equals(session.getString("reportGenerated"));

                session = session
                    .set("progressComplete", complete)
                    .set("attempts", attempts);

                if (complete) {
                    session = session.set("loopCounter", 20); // exit loop early
                }

                logger.info("Attempt #{} , progress={}", attempts, progress);
                return session;
            })
        )
        // only call final report API if complete
        .doIf(session -> session.getBoolean("progressComplete")).then(
            exec(
                http("Fetch Final Report --> /getReportByName/student-dashboard-list-view?includeReportData=true")
                    .get(UserEndpoints.GET_ALL_STUDENT_DASHBOARD_REPORT_API_PROGRESS_TRUE)
                    .check(status().is(200))
                    .check(bodyString().saveAs("finalResponse")) // capture final response
            )
            .exec(session -> {
                logger.info("---- Final Report Response ----\n{}", session.getString("finalResponse"));
                return session;
            })
        );

    {
        setUp(progressBarScenario.injectOpen(UserInjection.lightLoad))
            .protocols(HttpProtocolConfig.createHttpProtocolWithAuthentication())
            .assertions(AssertionFactory.basicAssertions());
    }
}
