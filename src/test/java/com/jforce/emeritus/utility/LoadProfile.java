package com.jforce.emeritus.utility;

public class LoadProfile {
  private String description;
  private int users;

  // No-arg constructor (important for SnakeYAML)
  public LoadProfile() {}

  // Getters and Setters
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public int getUsers() {
    return users;
  }

  public void setUsers(int users) {
    this.users = users;
  }

  @Override
  public String toString() {
    return "LoadProfile{description='" + description + "', users=" + users + "}";
  }
}
