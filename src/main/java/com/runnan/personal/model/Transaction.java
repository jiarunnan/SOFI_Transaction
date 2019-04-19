package com.runnan.personal.model;

import com.google.gson.annotations.SerializedName;
import java.util.Objects;


public class Transaction {

  @SerializedName("user-id")
  private
  String userId;
  @SerializedName("merchant")
  private String merchantName;
  @SerializedName("price")
  private
  String price;
  @SerializedName("purchase-date")
  private String purchaseDate;
  @SerializedName("tx-id")
  private String transactionId;

  private Transaction(String userId, String merchantName, String price, String purchaseDate,
      String transactionId) {
    this.userId = userId;
    this.merchantName = merchantName;
    this.price = price;
    this.purchaseDate = purchaseDate;
    this.transactionId = transactionId;
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

  public String getMerchantName() {
    return merchantName;
  }

  public void setMerchantName(String merchantName) {
    this.merchantName = merchantName;
  }

  public String getPrice() {
    return price;
  }

  public void setPrice(String price) {
    this.price = price;
  }

  public String getPurchaseDate() {
    return purchaseDate;
  }

  public void setPurchaseDate(String purchaseDate) {
    this.purchaseDate = purchaseDate;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }

  @Override
  public String toString() {
    return "Transaction{" +
        "userId='" + userId + '\'' +
        ", merchantName='" + merchantName + '\'' +
        ", price='" + price + '\'' +
        ", purchaseDate='" + purchaseDate + '\'' +
        ", transactionId='" + transactionId + '\'' +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof Transaction)) {
      return false;
    }
    Transaction that = (Transaction) o;
    return Objects.equals(getUserId(), that.getUserId()) &&
        Objects.equals(getMerchantName(), that.getMerchantName()) &&
        Objects.equals(getPrice(), that.getPrice()) &&
        Objects.equals(getPurchaseDate(), that.getPurchaseDate()) &&
        Objects.equals(getTransactionId(), that.getTransactionId());
  }

  @Override
  public int hashCode() {

    return Objects
        .hash(getUserId(), getMerchantName(), getPrice(), getPurchaseDate(), getTransactionId());
  }

  public static class Builder {

    private String userId;
    private String merchantName;
    private String price;
    private String purchaseDate;
    private String txId;

    public Builder withUserId(String userId) {
      this.userId = userId;
      return this;
    }

    public Builder withMerchantName(String merchantName) {
      this.merchantName = merchantName;
      return this;
    }

    public Builder withPrice(String price) {
      this.price = price;
      return this;
    }

    public Builder withPurchaseDate(String purchaseDate) {
      this.purchaseDate = purchaseDate;
      return this;
    }

    public Builder withTxID(String txId) {
      this.txId = txId;
      return this;
    }

    public Transaction build() {
      return new Transaction(userId, merchantName, price, purchaseDate, txId);
    }
  }
}
