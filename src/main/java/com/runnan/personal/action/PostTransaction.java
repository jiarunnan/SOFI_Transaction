package com.runnan.personal.action;
import com.google.inject.servlet.RequestScoped;
import com.runnan.personal.dataManager.DataManager;
import com.runnan.personal.model.Transaction;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.POST;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

// The Java class will be hosted at the URI path "/helloworld"
@Path("/transactions")
@Singleton
public class PostTransaction {

    private final DataManager dataManager;

    @Inject
    public PostTransaction(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Path("/{tx-id}/post")
    @POST // 声明这个接口必须GET访问
    @Produces(MediaType.APPLICATION_JSON) // 声明这个接口将以json格式返回
    public Response getClichedMessage(Transaction transaction) {
        try {
            dataManager.saveTransaction(transaction);
            return Response.status(201).entity(transaction).build();
        } catch (Exception e) {
            return Response.status(400).entity(e.getMessage()).build();
        }
    }
}
