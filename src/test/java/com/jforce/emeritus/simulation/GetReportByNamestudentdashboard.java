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

public class GetReportByNamestudentdashboard extends Simulation {
	
	
	
	
	
//Logger for debugging and info logs.
private static final Logger logger = LoggerFactory.getLogger(GetReportByNamestudentdashboard.class);

/**
 * Scenario: Fetches user profiles using user IDs from feeder.
 *
 * @throws RuntimeException if userId is missing from session
 */
ScenarioBuilder getReportByNameStudentDashboardListViewIncludeReportData =
    scenario("Get all student list data ")
        .exec(
            http("Get all student list data")
                .get(UserEndpoints.GET_ALL_STUDENT_DASHBOARD_REPORT_API_PROGRESS_FALSE)
                .check(status().is(200))
        );

/**
 * Injects 10 users instantly, sets authentication, and applies global assertions.
 */
{
    setUp(
    		getReportByNameStudentDashboardListViewIncludeReportData.injectOpen(UserInjection.spikeLoad)
    )
    .protocols(HttpProtocolConfig.createHttpProtocolWithAuthentication())
    .assertions(AssertionFactory.basicAssertions());
}
}


