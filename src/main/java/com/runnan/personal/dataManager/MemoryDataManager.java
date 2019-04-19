package com.runnan.personal.dataManager;

import com.runnan.personal.model.Transaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.inject.Singleton;
import jdk.nashorn.internal.runtime.regexp.joni.exception.InternalException;

@Singleton
public class MemoryDataManager implements DataManager {

  Map<String, TreeMap<Integer, Set<String>>> userToFerquencyToMerchantSet;
  Map<String, HashMap<String, Integer>> userToMerchantToFrequency;
  Map<String, Integer> userTransactionNumbers;


  public MemoryDataManager() {
    this.userToFerquencyToMerchantSet = new HashMap<String, TreeMap<Integer, Set<String>>>();
    this.userToMerchantToFrequency = new HashMap<String, HashMap<String, Integer>>();
    this.userTransactionNumbers = new HashMap<String, Integer>();
  }

  /**
   *
   * @param transaction
   */
  public void saveTransaction(Transaction transaction) {

    String user = transaction.getUserId();
    String merchant = transaction.getMerchantName();
    System.out.println(userToFerquencyToMerchantSet.size());
    TreeMap<Integer, Set<String>> frequentToMerchantSet = userToFerquencyToMerchantSet
        .getOrDefault(user, new TreeMap<Integer, Set<String>>(Collections
            .reverseOrder()));
    HashMap<String, Integer> merchantToFrequency = userToMerchantToFrequency
        .getOrDefault(user, new HashMap<String, Integer>());
    int frequent = merchantToFrequency.getOrDefault(merchant, 0);
    System.out.println(frequentToMerchantSet.toString());
    //update frequentToMerchant map, keep the size of 3. So TimeComplexity is O(log(3))
    if (frequentToMerchantSet.get(frequent) != null) {
      Set<String> set = frequentToMerchantSet.get(frequent);
      set.remove(merchant);
      if (set.size() == 0) {
        frequentToMerchantSet.remove(frequent);
      } else {
        frequentToMerchantSet.put(frequent, set);
      }
      set = frequentToMerchantSet.getOrDefault(frequent + 1, new HashSet<String>());
      set.add(merchant);
      frequentToMerchantSet.put(frequent + 1, set);
    } else {
      Set<String> set = frequentToMerchantSet.getOrDefault(frequent + 1, new HashSet<String>());
      set.add(merchant);
      frequentToMerchantSet.put(frequent + 1, set);
      if (frequentToMerchantSet.size() > 3) {
        frequentToMerchantSet.pollLastEntry();
      }
    }
    merchantToFrequency.put(merchant, frequent + 1);

    userToFerquencyToMerchantSet.put(user, frequentToMerchantSet);
    userToMerchantToFrequency.put(user, merchantToFrequency);
    userTransactionNumbers.put(user, userTransactionNumbers.getOrDefault(user, -1) + 1);
    System.out.println(userToFerquencyToMerchantSet.size());
  }

  /**
   *
   * @param userId
   * @return
   */
  public List<String> getMostFrequentMerchant(String userId) {
    int num = userTransactionNumbers.getOrDefault(userId, 0);
    if (num < 5) {
      String errorMsg = String.format("The user only has %s transactions", num);
      throw new InternalException(errorMsg);
    }
    List<String> res = new ArrayList<>();
    for (Set<String> set : userToFerquencyToMerchantSet.get(userId).values()) {
      res.addAll(set);
      if (res.size() > 3) {
        break;
      }
    }
    if (res.size() > 3) {
      res = res.subList(0, 3);
    }
    return res;
  }
}
