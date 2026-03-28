package com.jforce.emeritus.simulation;


import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

import java.util.List;
import java.util.stream.Collectors;

import com.jforce.emeritus.assertions.AssertionFactory;
import com.jforce.emeritus.config.HttpProtocolConfig;
import com.jforce.emeritus.config.UserInjection;
import com.jforce.emeritus.utility.UserFeeder;
import static io.gatling.javaapi.core.CoreDsl.csv;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CourseDeletingSimulation extends Simulation{

//	
//	  // CSV feeder with course IDs
//	  FeederBuilder<String> courseFeeder = csv("data/courses.csv").queue();
//
//	  ScenarioBuilder deleteCoursesScenario = scenario("Delete Multiple Courses")
//	    .feed(courseFeeder)
//	    .exec(
//	      http("Delete Course")
//	        .delete("/api/v1/courses/#{courseId}?event=delete")
//	        .check(status().is(200))
//	    );
//
//	  {
//	    setUp(
//	      deleteCoursesScenario.injectOpen(atOnceUsers(690))
//	    )  .assertions(AssertionFactory.globalAssertions())
//        .protocols(HttpProtocolConfig.httpProtocolWithAuthentication);
//	  }
//	  
//	  
	  
	  
	  
	  // Load all 600 course IDs from the CSV into memory first
	  List<Object> courseIdList = csv("data/courses.csv").readRecords().stream()
	    .map(record -> record.get("courseId"))
	    .collect(Collectors.toList());

	  ScenarioBuilder deleteCoursesScenario = scenario("Delete Multiple Courses")
			  .exec(session -> session.set("courseIdList", courseIdList)) // store list in session
			    .foreach("#{courseIdList}", "courseId") // loop over each courseId
			    .on(
			      exec(session -> {
			        String courseId = session.getString("courseId"); // ✅ this is from foreach()
			        String url = "/api/v1/courses/" + courseId + "?event=delete";
			        System.out.println("🗑️ Deleting courseId: " + courseId + " | URL: " + url);
			        return session;
			      })
			      .exec(
			        http("Delete Course #{courseId}")
			          .delete("/api/v1/courses/#{courseId}?event=delete")
			          .check(status().in(200, 204))
			      )
			    );

	  {
	    setUp(
	      deleteCoursesScenario.injectOpen(atOnceUsers(1))
	    )  .assertions(AssertionFactory.globalAssertions())
      .protocols(HttpProtocolConfig.httpProtocolWithAuthentication);
	  }
	
	
	
}
