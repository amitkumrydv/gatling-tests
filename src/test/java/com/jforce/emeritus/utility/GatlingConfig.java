package com.jforce.emeritus.utility;

import java.util.Map;

public class GatlingConfig {
  // The field name "virtualUsers" will map from "virtual_users" in YAML
  // SnakeYAML automatically handles snake_case to camelCase conversion.
  private Map<String, LoadProfile> virtualUsers;

  // No-arg constructor
  public GatlingConfig() {}

  // Getters and Setters
  public Map<String, LoadProfile> getVirtualUsers() {
    return virtualUsers;
  }

  public void setVirtualUsers(Map<String, LoadProfile> virtualUsers) {
    this.virtualUsers = virtualUsers;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder("GatlingConfig{\n  virtualUsers=\n");
    if (virtualUsers != null) {
      virtualUsers.forEach(
          (key, value) -> sb.append("    ").append(key).append(": ").append(value).append("\n"));
    }
    sb.append("}");
    return sb.toString();
  }
}
