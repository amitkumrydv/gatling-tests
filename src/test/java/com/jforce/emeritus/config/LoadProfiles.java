package com.jforce.emeritus.config;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

public class LoadProfiles {

  public static class LoadProfile {
    public final int users;
    public final String description;

    public LoadProfile(int users, String description) {
      this.users = users;
      this.description = description;
    }

    @Override
    public String toString() {
      return "LoadProfile{users=" + users + ", description='" + description + "'}";
    }
  }

  /**
   * Loads all load profiles from the YAML file.
   *
   * @return Map of scenario name to LoadProfile (users + description)
   */
  public static Map<String, LoadProfile> loadAllProfiles() {
    Yaml yaml = new Yaml(new SafeConstructor(null));
    InputStream input =
        LoadProfiles.class.getClassLoader().getResourceAsStream("user-load-profiles.yaml");

    if (input == null) {
      throw new RuntimeException("YAML file 'user-load-profiles.yaml' not found in resources!");
    }

    // Raw YAML data as Map<String, Map<String, Object>>
    Map<String, Map<String, Object>> rawData = yaml.load(input);

    Map<String, LoadProfile> profiles = new LinkedHashMap<>();

    for (Map.Entry<String, Map<String, Object>> entry : rawData.entrySet()) {
      String scenario = entry.getKey();
      Map<String, Object> details = entry.getValue();

      Object usersObj = details.get("users");
      if (usersObj == null) {
        throw new RuntimeException("Missing 'users' field for scenario: " + scenario);
      }

      int users = Integer.parseInt(usersObj.toString());

      Object descriptionObj = details.get("description");
      String description =
          (descriptionObj != null) ? descriptionObj.toString() : "(No description)";

      profiles.put(scenario, new LoadProfile(users, description));
    }

    return profiles;
  }

  // Optional: For quick console output
  public static void printAllProfiles() {
    Map<String, LoadProfile> profiles = loadAllProfiles();
    profiles.forEach(
        (scenario, profile) -> {
          System.out.println("Scenario: " + scenario);
          System.out.println("  Users: " + profile.users);
          System.out.println("  Description: " + profile.description);
          System.out.println("-----------------------------");
        });
  }
}
