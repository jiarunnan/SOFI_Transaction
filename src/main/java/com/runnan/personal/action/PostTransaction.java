package com.runnan.personal.action;

import com.runnan.personal.dataManager.DataManager;
import com.runnan.personal.model.Transaction;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// The Java class will be hosted at the URI path "/transaction"
@Path("/transactions")
@Singleton
public class PostTransaction {

    private final DataManager dataManager;

    @Inject
    public PostTransaction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Path("/{tx-id}/post")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClichedMessage(Transaction transaction) {
        try {
            dataManager.saveTransaction(transaction);
            return Response.status(201).entity(transaction).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }
}
