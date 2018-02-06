package com.jadevelopment.ejercicioml.data.api;

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
}
