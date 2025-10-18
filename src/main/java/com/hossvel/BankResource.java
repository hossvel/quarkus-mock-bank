package com.hossvel;

import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/bank")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BankResource {

    @Inject
    BankService bankService;

    @POST
    @Path("/transfer")
    public Response transfer(TransferRequest request) {
        TransferResponse transferResponse = bankService.transfer(request);
        if (transferResponse.isFraud) {
            return Response.status(403).entity(transferResponse).build();
        }
        return Response.ok(transferResponse).build();
    }
}