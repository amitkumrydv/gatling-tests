// package com.jforce.emeritus.utility;
//
// import java.io.InputStream;
// import org.yaml.snakeyaml.Yaml;
// import org.yaml.snakeyaml.constructor.Constructor;
//
// public class ConfigLoader {
//
//  private static final String CONFIG_FILE_PATH = "gatling_load_config.yml";
//  private GatlingConfig gatlingConfig;
//
//  public ConfigLoader() {
//    loadConfiguration();
//  }
//
//  private void loadConfiguration() {
//    Yaml yaml = new Yaml(new Constructor(GatlingConfig.class)); // Tell SnakeYAML the root class
//    InputStream inputStream =
//        this.getClass().getClassLoader().getResourceAsStream(CONFIG_FILE_PATH);
//
//    if (inputStream == null) {
//      throw new RuntimeException("Cannot find the configuration file: " + CONFIG_FILE_PATH);
//    }
//    this.gatlingConfig = yaml.load(inputStream);
//  }
//
//  public GatlingConfig getGatlingConfig() {
//    return gatlingConfig;
//  }
//
//  public int getUsersForProfile(String profileName) {
//    if (gatlingConfig != null && gatlingConfig.getVirtualUsers() != null) {
//      LoadProfile profile = gatlingConfig.getVirtualUsers().get(profileName);
//      if (profile != null) {
//        return profile.getUsers();
//      }
//    }
//    throw new IllegalArgumentException("Profile not found: " + profileName);
//  }
//
//  // --- Main method for testing ---
//  public static void main(String[] args) {
//    ConfigLoader loader = new ConfigLoader();
//    GatlingConfig config = loader.getGatlingConfig();
//
//    System.out.println("Full configuration loaded:");
//    System.out.println(config);
//
//    System.out.println("\n--- Accessing specific values ---");
//    try {
//      System.out.println("Peak users: " + loader.getUsersForProfile("peak"));
//      System.out.println("Moderate users: " + loader.getUsersForProfile("moderate"));
//      // Example of how you'd use it in Gatling (conceptual)
//      // int peakUsers = loader.getUsersForProfile("peak");
//      // setUp(myScenario.injectOpen(atOnceUsers(peakUsers))).protocols(httpProtocol);
//
//    } catch (IllegalArgumentException e) {
//      System.err.println(e.getMessage());
//    }
//  }
// }



