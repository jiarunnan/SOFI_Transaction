package com.runnan.personal.dataManager;

import com.runnan.personal.model.Transaction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

  /** This method is for save the transaction information to memoryDataManager
   * save transaction to three map.
   *   - ``Map<String, TreeMap<Integer, Set<String>>> userToFerquencyToMerchantSet;``
   *      - save User's frequent to merchant map (Tree Map)
   *   - ``Map<String, HashMap<String, Integer>> userToMerchantToFrequency;``
   *      - save User's merchant to frequent map (HashMap)
   *   - ``Map<String, Integer> userTransactionNumbers;``
   *      - save User's transaction number
   * - In saveTransaction method, I update this three map, and keep the frequent merchantmap size as 3.
   * - So the TimeComplexity of  save step is O(lg(3)) -> O(1)
   * @param transaction transaction instance, auto translated by gson
   */
  public void saveTransaction(Transaction transaction) {
    Objects.requireNonNull(transaction, "Transaction can not be null");
    String user = transaction.getUserId();
    String merchant = transaction.getMerchantName();
    TreeMap<Integer, Set<String>> frequentToMerchantSet = userToFerquencyToMerchantSet
        .getOrDefault(user, new TreeMap<Integer, Set<String>>(Collections
            .reverseOrder()));
    HashMap<String, Integer> merchantToFrequency = userToMerchantToFrequency
        .getOrDefault(user, new HashMap<String, Integer>());
    int frequent = merchantToFrequency.getOrDefault(merchant, 0);
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
    userTransactionNumbers.put(user, userTransactionNumbers.getOrDefault(user, 0) + 1);
  }

  /**
   * this method will return a list of Merchant (size of 3), which the user mostly visited.
   * If user transaction number is smaller than 5, raise an exception.
   * TimeComplexity: O(1)
   * @param userId userID
   * @return List of merchant name
   */
  public List<String> getMostFrequentMerchant(String userId) {
    Objects.requireNonNull(userId, "User-Id can not be null");
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
