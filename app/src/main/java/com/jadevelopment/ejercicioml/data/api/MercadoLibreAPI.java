package com.jadevelopment.ejercicioml.data.api;

import com.jadevelopment.ejercicioml.data.model.CardIssuer;
import com.jadevelopment.ejercicioml.data.model.Installment;
import com.jadevelopment.ejercicioml.data.model.PayerCost;
import com.jadevelopment.ejercicioml.data.model.payment_methods;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Joaquin on 4/2/2018.
 */

public interface MercadoLibreAPI {
    public static final String BASE_URL = "https://api.mercadopago.com/v1/";

    @GET("payment_methods")
    Call<List<payment_methods>> getPaymentMethods(
            @Query(value = "public_key", encoded = true) String public_key
    );

    @GET("payment_methods/card_issuers")
    Call<List<CardIssuer>> getCardIssuers(
            @Query(value = "public_key", encoded = true) String public_key,
            @Query(value = "payment_method_id", encoded = true) String payment_method_id
    );

    @GET("payment_methods/installments")
    Call<List<Installment>> getInstallments(
            @Query(value = "public_key", encoded = true) String public_key,
            @Query(value = "amount", encoded = true) String amount,
            @Query(value = "payment_method_id", encoded = true) String payment_method_id,
            @Query(value = "issuer.id", encoded = true) String issuer_id
    );
}
