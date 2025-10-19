package com.hossvel;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;
@ApplicationScoped
public class BankService {

    @Inject
    @RestClient
    FraudClient fraudClient;

    TransferResponse transfer(TransferRequest request){

        boolean isFraud = fraudClient.checkFraud(request);

        TransferResponse transferResponse = new TransferResponse();
        transferResponse.isFraud = isFraud;
        transferResponse.message = "Transfer successful";
        if (isFraud) {
            transferResponse.message = "Transfer flagged as fraudulent";
        }
        // lógica de transferencia aquí
       return  transferResponse;


    }

    public boolean meetsMinimumAmount(double amount) {
        return amount >= 5000;
    }
}
