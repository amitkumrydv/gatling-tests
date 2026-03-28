package com.jforce.emeritus.simulation;

import static com.jforce.emeritus.config.HttpProtocolConfig.httpProtocolWithAuthentication;
import static com.jforce.emeritus.config.UserInjection.*;
import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import com.jforce.emeritus.assertions.AssertionFactory;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;


public class CourseCreationSimulation extends Simulation {

    ScenarioBuilder CourseCreation =
        scenario("Course Creation")
            .exec(session -> {
                System.out.println("▶ Setting accountId to session...");
                return session.set("accountId", 1);
            })
            .exec(
                http("Create courses")
                    .post("/canvas-api/accounts/#{accountId}/courses")
                    .body(RawFileBody("JsonData/CourseCreation.json"))
                    .asJson()
                    .check(status().is(200))
                    // log request and response
                    .check(bodyString().saveAs("responseBody"))
            )
            .exec(session -> {
                System.out.println("▶ Response body:");
                System.out.println(session.getString("responseBody")); // Log response
                return session;
            });

    {
        setUp(CourseCreation.injectOpen(atOnceUsers(1))) // Use lightLoad or sample value like atOnceUsers(1)
            .assertions(AssertionFactory.perScenarioAssertions("Course creation"))
            .protocols(httpProtocolWithAuthentication);
    }
}

