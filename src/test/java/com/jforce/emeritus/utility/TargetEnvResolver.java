package com.jforce.emeritus.utility;

public class TargetEnvResolver {

  public record EnvInfo(String pageUrl, String baseUrl, String usersFeederFile) {}

  public static EnvInfo resolveEnvironmentInfo(String targetEnv) {
    return switch (targetEnv.toUpperCase()) {
      case "DEV" ->
          new EnvInfo(
             // "https://degree-courses-staging.emerituss.org",
             // "https://degree-courses-staging.emerituss.org",
        		  "http://localhost/",
        		  "http://localhost/",
              "data/users_dev.json");

      case "QA" ->
          new EnvInfo(
              "https://lms-staging-bennettonline.emerituss.org/",
              "https://lms-staging-bennettonline.emerituss.org/",
              "data/users_dev.json");

      case "PROD" ->
          new EnvInfo(
              "https://degree-courses-staging.emerituss.org",
              "https://degree-courses-staging.emerituss.org/",
              "data/users_dev.json");
      default -> throw new IllegalArgumentException("Unknown environment: " + targetEnv);
    };
  }
}
