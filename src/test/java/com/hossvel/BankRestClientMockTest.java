package com.hossvel;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@QuarkusTest
public class BankRestClientMockTest {

    @InjectMock
    @RestClient
    FraudClient fraudClient;

    @Test
    public void testFraudulentTransfer() {
        TransferRequest request = new TransferRequest();
        request.fromAccount = "123";
        request.toAccount = "456";
        request.amount = 10000.0;


        Mockito.when(fraudClient.checkFraud(Mockito.any())).thenReturn(true);

        var response = given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/bank/transfer");

        assertEquals(403, response.statusCode());
        assertTrue(response.body().asString().contains("fraudulent"));
    }

    @Test
    public void testValidTransfer() {
        TransferRequest request = new TransferRequest();
        request.fromAccount = "123";
        request.toAccount = "456";
        request.amount = 100.0;

        Mockito.when(fraudClient.checkFraud(Mockito.any())).thenReturn(false);

        var response = given()
                .contentType("application/json")
                .body(request)
                .when()
                .post("/bank/transfer");

        assertEquals(200, response.statusCode());
        assertTrue(response.body().asString().contains("successful"));
    }


}
