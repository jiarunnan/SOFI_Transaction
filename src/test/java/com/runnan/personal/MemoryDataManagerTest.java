package com.runnan.personal;

import com.runnan.personal.dataManager.MemoryDataManager;
import com.runnan.personal.model.Transaction;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;
import org.junit.Before;
import org.junit.Test;

public class MemoryDataManagerTest {

  private final String userA = "100";
  private final String merchantA = "merchantA";
  private final String merchantB = "merchantB";
  private final String merchantC = "merchantC";
  private final String merchantD = "merchantD";
  private final String price = "0.0";
  private final String txId = "0";
  private final String purchaseDate = "00000";
  Transaction transactionUser100MerchantA;
  Transaction transactionUser100MerchantB;
  Transaction transactionUser100MerchantC;
  Transaction transactionUser100MerchantD;


  @Before
  public void setUp() {
    transactionUser100MerchantA = Transaction.builder()
        .withMerchantName(merchantA).withUserId(userA).withPrice(price)
        .withPurchaseDate(purchaseDate).withTxID(txId).build();
    transactionUser100MerchantB = Transaction.builder()
        .withMerchantName(merchantB).withUserId(userA).withPrice(price)
        .withPurchaseDate(purchaseDate).withTxID(txId).build();
    transactionUser100MerchantC = Transaction.builder()
        .withMerchantName(merchantC).withUserId(userA).withPrice(price)
        .withPurchaseDate(purchaseDate).withTxID(txId).build();
    transactionUser100MerchantD = Transaction.builder()
        .withMerchantName(merchantD).withUserId(userA).withPrice(price)
        .withPurchaseDate(purchaseDate).withTxID(txId).build();
  }

  @Test
  public void When_CallSaveTransactionFiveTimesWithSameInput_Then_CallGetMostVisited_Expected_ListWithOneElement() {
    MemoryDataManager memoryDataManager = new MemoryDataManager();
    for (int i = 0; i < 5; i++) {
      memoryDataManager.saveTransaction(transactionUser100MerchantA);
    }
    List<String> res = memoryDataManager.getMostFrequentMerchant(userA);
    assert res.equals(Arrays.asList(merchantA));
  }

  @Test(expected = InternalException.class)
  public void When_CallSaveTransactionFourTimesWithSameInput_Then_CallGetMostVisited_Expected_InternalException() {
    MemoryDataManager memoryDataManager = new MemoryDataManager();
    for (int i = 0; i < 4; i++) {
      memoryDataManager.saveTransaction(transactionUser100MerchantA);
    }
    List<String> res = memoryDataManager.getMostFrequentMerchant(userA);
  }

  @Test
  public void When_CallSaveTransactionThreeTimesWithMerchantAOneTimeMerchantBOneTimeMerchantC_Then_CallGetMostVisited_Expected_ListOfMerchantABC() {
    MemoryDataManager memoryDataManager = new MemoryDataManager();

    for (int i = 0; i < 3; i++) {
      memoryDataManager.saveTransaction(transactionUser100MerchantA);
    }
    memoryDataManager.saveTransaction(transactionUser100MerchantB);
    memoryDataManager.saveTransaction(transactionUser100MerchantC);
    List<String> res = memoryDataManager.getMostFrequentMerchant(userA);
    Set<String> resSet = new HashSet<String>(res);
    assert resSet.equals(new HashSet<String>(Arrays.asList(merchantA, merchantB, merchantC)));
  }

  @Test
  public void When_CallSaveTransactionTwoTimesWithMerchantATwoTimeMerchantBTwoTimeMerchantCOneTimeMerchantD_Then_CallGetMostVisited_Expected_ListOfMerchantABC() {
    MemoryDataManager memoryDataManager = new MemoryDataManager();

    for (int i = 0; i < 2; i++) {
      memoryDataManager.saveTransaction(transactionUser100MerchantA);
      memoryDataManager.saveTransaction(transactionUser100MerchantB);
      memoryDataManager.saveTransaction(transactionUser100MerchantC);
    }
    memoryDataManager.saveTransaction(transactionUser100MerchantD);
    List<String> res = memoryDataManager.getMostFrequentMerchant(userA);
    Set<String> resSet = new HashSet<String>(res);
    assert resSet.equals(new HashSet<String>(Arrays.asList(merchantA, merchantB, merchantC)));
  }
}
