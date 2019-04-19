package com.runnan.personal.dataManager;

import com.runnan.personal.model.Transaction;
import java.util.List;

public interface DataManager {

  void saveTransaction(Transaction transaction);

  List<String> getMostFrequentMerchant(String userId);

}
