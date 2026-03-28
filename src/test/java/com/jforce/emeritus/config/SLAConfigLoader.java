package com.jforce.emeritus.config;

public class SLAConfigLoader {

  //  public static class SLAConfig {
  //    public SLAProfile defaultProfile;
  //    public Map<String, SLAProfile> scenarios = new HashMap<>();
  //  }
  //
  //  private static final SLAConfig config;
  //
  //  static {
  //    try (InputStream is = SLAConfigLoader.class.getResourceAsStream("/sla-config.yaml")) {
  //      ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
  //      config = mapper.readValue(is, SLAConfig.class);
  //    } catch (Exception e) {
  //      throw new RuntimeException("Failed to load SLA config", e);
  //    }
  //  }
  //
  //  public static SLAProfile getDefaultProfile() {
  //    return config.defaultProfile;
  //  }
  //
  //  public static SLAProfile getScenarioProfile(String scenario) {
  //    return config.scenarios.getOrDefault(scenario, config.defaultProfile);
  //  }
}
