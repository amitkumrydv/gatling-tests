package com.jforce.emeritus.assertions;

import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.Assertion;
import java.util.List;

public class AssertionFactory {
	
	
	// @formatter:off

       
	  
         /** @param The maximum response time of any request should be less than 2000 milliseconds (2 seconds).
          *  @param The average (mean) response time of all requests must be less than 1000 ms (1 second).
          *  @param 95% of the requests should take less than 1500 ms.
          *  Throughput.
          *  Success rate.  
          */
          public static List<Assertion> globalAssertions() {

                              return List.of( global().responseTime().max().lt(2000),
                                              global().responseTime().mean().lt(1000),
                                              global().responseTime().percentile(95).lt(1500),
                                              global().requestsPerSec().gt(100.0),
                                              global().successfulRequests().percent().gt(99.0));
                                   }
  
  
         /**
          * @param The maximum response time of any scenario should be less than 2500 ms (2.5 seconds).
          * @param The average response time of this specific scenario should be less than 1000 ms (1 second).
          * @param The number of failed requests in this scenario should be less than 10.
          * @param  The success rate in this scenario should be more than 98%.
          */
          public static List<Assertion> perScenarioAssertions(String scenarioName) {
	  
                                       return List.of( forAll().responseTime().max().lt(2500),
                                                       details(scenarioName).responseTime().mean().lt(1000),
                                                       details(scenarioName).failedRequests().count().lt(10L),
                                                       details(scenarioName).successfulRequests().percent().gt(98.0));
                                                }
          

       /** Strict SLA assertions  */
         public static List<Assertion> strictSLAAssertions() {
                                                  return List.of(global().failedRequests().percent().lte(1.0),
                                                                 global().responseTime().percentile(99).lt(1800),
                                                                 global().allRequests().count().gt(10000L));
                                                          }


          /** Combined example usage  */
         public static List<Assertion> fullSet(String scenarioName) {
                                        return List.copyOf(globalAssertions());
                                    }
         
         

 	    /** @param The maximum response time of any request should be less than 2000 milliseconds (2 seconds).
 	     *  @param The average (mean) response time of all requests must be less than 1000 ms (1 second).
 	     *  @param 95% of the requests should take less than 1500 ms.
 	     *  Throughput.
 	     *  Success rate.
 	     *  
 	     */
         public static List<Assertion> basicAssertions() {
                                             return List.of(global().responseTime().max().lt(3000),
                                            	            global().responseTime().mean().lt(2000),
                                            	            global().responseTime().percentile(95).lt(2500),
                                            	            global().requestsPerSec().gt(1.0),
                                            	            global().successfulRequests().percent().gt(99.0));
                                                      }
         
}
