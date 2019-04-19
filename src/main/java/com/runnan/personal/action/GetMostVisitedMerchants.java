package com.runnan.personal.action;

import com.runnan.personal.dataManager.DataManager;
import com.runnan.personal.model.UserMerchants;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Singleton
@Path("/")
public class GetMostVisitedMerchants {


  private final DataManager dataManager;

  @Inject
  public GetMostVisitedMerchants(DataManager dataManager) {
    this.dataManager = dataManager;
  }

  @Path("/user/{user-id}")
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public UserMerchants getUserMostVisitedMerchant(@PathParam("user-id") String userId) {
    System.out.println(dataManager == null);
    List<String> merchants = dataManager.getMostFrequentMerchant(userId);
    return UserMerchants
        .builder()
        .withMostFrequentlyMerchant(merchants)
        .withUserId(userId)
        .build();
  }

}
