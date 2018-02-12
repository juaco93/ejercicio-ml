package com.jadevelopment.ejercicioml.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jadevelopment.ejercicioml.R;
import com.jadevelopment.ejercicioml.data.Config;
import com.jadevelopment.ejercicioml.data.adapter.AdapterMetodoPago;
import com.jadevelopment.ejercicioml.data.api.MercadoLibreAPI;
import com.jadevelopment.ejercicioml.data.model.payment_methods;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PaymentMethodSelection extends AppCompatActivity {
    private Retrofit mRestAdapter;
    private MercadoLibreAPI mMercadoLibreApi;

    private Spinner spMetodoPago;
    private ProgressBar pbPaymentMethodSelection;

    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method_selection);

        spMetodoPago = (Spinner) findViewById(R.id.spMetodoPago);
        pbPaymentMethodSelection = (ProgressBar) findViewById(R.id.pbPaymentSelection);
        pbPaymentMethodSelection.setVisibility(View.INVISIBLE);

        //// RETROFIT
        // Interceptor para log del Request
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // Inicializar GSON
        Gson gson =
                new GsonBuilder()
                        .create();

        // Crear conexión al servicio REST
        mRestAdapter = new Retrofit.Builder()
                .baseUrl(MercadoLibreAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        // Crear conexión a la API de MercadoLibre
        mMercadoLibreApi = mRestAdapter.create(MercadoLibreAPI.class);

        amount = getIntent().getStringExtra("amount");

        getPayment_methods();
    }

    private void getPayment_methods(){
        Log.d("methods", "Recuperando payment_methods desde el servidor");

        pbPaymentMethodSelection.setVisibility(View.VISIBLE);

        // Realizar petición HTTP
        Call<List<payment_methods>> call = mMercadoLibreApi.getPaymentMethods(Config.API_KEY);
        call.enqueue(new Callback<List<payment_methods>>() {
            @Override
            public void onResponse(Call<List<payment_methods>> call,
                                   Response<List<payment_methods>> response) {
                if (!response.isSuccessful()) {
                    // Procesar error de API
                    String error = "Ha ocurrido un error. Contacte al administrador";
                    if (response.errorBody()
                            .contentType()
                            .subtype()
                            .equals("json")) {
                        Log.d("methods", response.errorBody().toString());
                    } else {
                        Log.d("methods", response.errorBody().toString());
                    }

                    pbPaymentMethodSelection.setVisibility(View.INVISIBLE);
                    Log.d("methods", response.message());
                    Log.d("methods", response.raw().toString());
                    return;
                }

                // Respuesta correcta del servidor
                pbPaymentMethodSelection.setVisibility(View.INVISIBLE);
                cargarSpinnerMetodosPago(response.body());
            }

            @Override
            public void onFailure(Call<List<payment_methods>> call, Throwable t) {
                Log.d("methods", "Petición rechazada:" + t.getMessage());
                pbPaymentMethodSelection.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void cargarSpinnerMetodosPago(List<payment_methods> metodosDePago) {
        AdapterMetodoPago adapter;
        adapter = new AdapterMetodoPago(this, metodosDePago);
        spMetodoPago.setAdapter(adapter);

    }

    public void irASeleccionBanco(View v){
        Intent intent= new Intent(this,BankSelection.class);
        payment_methods metodo = (payment_methods) spMetodoPago.getSelectedItem();
        intent.putExtra("payment_method",(new Gson()).toJson(metodo));
        intent.putExtra("amount",amount);
        startActivity(intent);
    }
}
