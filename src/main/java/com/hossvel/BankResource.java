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
    @RestClient
    FraudClient fraudClient;

    @POST
    @Path("/transfer")
    public Response transfer(TransferRequest request) {
        boolean isFraud = fraudClient.checkFraud(request);
        if (isFraud) {
            return Response.status(403).entity("Transfer flagged as fraudulent").build();
        }
        // lógica de transferencia aquí
        return Response.ok("Transfer successful").build();
    }
}