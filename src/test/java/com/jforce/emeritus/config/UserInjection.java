package com.jforce.emeritus.config;

import static com.jforce.emeritus.utility.Keys.*;
import static io.gatling.javaapi.core.CoreDsl.*;

import io.gatling.javaapi.core.OpenInjectionStep;
import java.util.List;

public class UserInjection {

	// @formatter:off

	
          static int virtualUsers = USERS;
          static int virtualspikeLoad = SPIKE_LOAD_USERS;
          static int virtualMinimalUserCount= MINIMAL_USER_COUNT;

          // Moderate Load Scenario (General Load Model)
          public static List<OpenInjectionStep> userLoadModel =List.of( rampUsers(200).during(60),
                                                                        constantUsersPerSec(100).during(300),
                                                                        rampUsersPerSec(100).to(400).during(120));
          

          // Reflects expected daily usage with gradual increase, steady phase, and spike.
          public static List<OpenInjectionStep> realisticLoad =List.of(nothingFor(5),
                                                                       rampUsers(virtualUsers).during(60),
                                                                       constantUsersPerSec(virtualUsers).during(120),
                                                                       rampUsersPerSec(25).to(virtualUsers).during(60),
                                                                       stressPeakUsers(virtualspikeLoad).during(5));

          // Light Load
          public static List<OpenInjectionStep> lightLoad =List.of(nothingFor(2),
        		                                                   rampUsers(virtualMinimalUserCount).during(30),
        		                                                   constantUsersPerSec(5).during(60));
         

          // Simulate a sudden massive surge
          public static List<OpenInjectionStep> spikeLoad = List.of(nothingFor(5),
        		                                                    atOnceUsers(virtualspikeLoad),
        		                                                    constantUsersPerSec(virtualspikeLoad).during(30));
        

          // Identify memory leaks or degradation over time
          public static List<OpenInjectionStep> soakTest = List.of(rampUsers(300).during(300),
		                                                           constantUsersPerSec(300).during(7200));
  

          // Stress Test: Push system to breaking point
          public static List<OpenInjectionStep> stressTest =List.of(rampUsersPerSec(100).to(2000).during(300));
          
          

          // Recovery Test: Behavior under failure/overload
          public static List<OpenInjectionStep> recoveryTest =List.of(rampUsers(500).during(60),
                                                                      constantUsersPerSec(1000).during(60),
                                                                      nothingFor(30),
                                                                      constantUsersPerSec(200).during(120));
          
          

  // Ramp Down: Scale down test
          public static List<OpenInjectionStep> rampDown =List.of(constantUsersPerSec(500).during(120),
        		                                                  rampUsersPerSec(500).to(0).during(60));
          
          
          

          public static List<OpenInjectionStep> realisticUsers(int users) {
        	  
                                                   if (users <= 0) {
                                                        throw new IllegalArgumentException("Number of users must be greater than 0.");
                                                        }

                                                   return List.of(nothingFor(5),
                                                                  rampUsers(users).during(60),
                                                                  constantUsersPerSec(users).during(120),
                                                                  rampUsersPerSec(25).to(users).during(60),
                                                                  stressPeakUsers(users).during(30));
                                                         }
            
}
