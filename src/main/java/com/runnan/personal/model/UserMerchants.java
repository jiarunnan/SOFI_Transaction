package com.runnan.personal.model;

import com.google.gson.annotations.SerializedName;
import java.util.List;
import java.util.Objects;

public class UserMerchants {

  @SerializedName("user-id")
  private String userId;
  @SerializedName("most-frequently-visited-merchants")
  private List<String> mostFrequentlyMerchants;


  private UserMerchants(String userId,
      List<String> mostFrequentlyMerchants) {
    this.userId = userId;
    this.mostFrequentlyMerchants = mostFrequentlyMerchants;
  }

  public static Builder builder() {
    return new Builder();
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public List<String> getMostFrequentlyMerchants() {
    return mostFrequentlyMerchants;
  }

  public void setMostFrequentlyMerchants(List<String> mostFrequentlyMerchants) {
    this.mostFrequentlyMerchants = mostFrequentlyMerchants;
  }

  @Override
  public String toString() {
    return "UserMerchants{" +
        "userId='" + userId + '\'' +
        ", mostFrequentlyMerchants=" + mostFrequentlyMerchants +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserMerchants)) {
      return false;
    }
    UserMerchants that = (UserMerchants) o;
    return Objects.equals(getUserId(), that.getUserId()) &&
        Objects.equals(getMostFrequentlyMerchants(), that.getMostFrequentlyMerchants());
  }

  @Override
  public int hashCode() {

    return Objects.hash(getUserId(), getMostFrequentlyMerchants());
  }

  public static class Builder {

    private String userId;
    private List<String> mostFrequentlyMerchants;

    public Builder withUserId(String userId) {
      this.userId = userId;
      return this;
    }

    public Builder withMostFrequentlyMerchant(List<String> mostFrequentlyMerchant) {
      this.mostFrequentlyMerchants = mostFrequentlyMerchant;
      return this;
    }

    public UserMerchants build() {
      return new UserMerchants(userId, mostFrequentlyMerchants);
    }
  }

}
