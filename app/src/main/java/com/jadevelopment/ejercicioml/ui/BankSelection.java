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
import com.jadevelopment.ejercicioml.data.adapter.AdapterBanco;
import com.jadevelopment.ejercicioml.data.adapter.AdapterMetodoPago;
import com.jadevelopment.ejercicioml.data.api.MercadoLibreAPI;
import com.jadevelopment.ejercicioml.data.model.CardIssuer;
import com.jadevelopment.ejercicioml.data.model.payment_methods;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BankSelection extends AppCompatActivity {
    private Retrofit mRestAdapter;
    private MercadoLibreAPI mMercadoLibreApi;

    private Spinner spSeleccionarBanco;
    private ProgressBar pbCardIssuers;

    private payment_methods mPayment_method;
    private String amount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_issuers);

        spSeleccionarBanco = (Spinner) findViewById(R.id.spBanco);
        pbCardIssuers = (ProgressBar) findViewById(R.id.pbCardIssuers);
        pbCardIssuers.setVisibility(View.INVISIBLE);

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
        if(getIntent().getStringExtra("payment_method")!=null)
        mPayment_method = (new Gson()).fromJson((getIntent().getStringExtra("payment_method")),payment_methods.class);

        getCardIssuers();
    }

    private void getCardIssuers(){
        Log.d("methods", "Recuperando card_issuers desde el servidor");
        pbCardIssuers.setVisibility(View.VISIBLE);

        // Realizar petición HTTP
        Call<List<CardIssuer>> call = mMercadoLibreApi.getCardIssuers(Config.API_KEY, mPayment_method.getId());
        call.enqueue(new Callback<List<CardIssuer>>() {
            @Override
            public void onResponse(Call<List<CardIssuer>> call,
                                   Response<List<CardIssuer>> response) {
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
                    pbCardIssuers.setVisibility(View.INVISIBLE);
                    Log.d("methods", response.message());
                    Log.d("methods", response.raw().toString());
                    return;
                }

                // Correcto, ocultamos progressbar
                pbCardIssuers.setVisibility(View.INVISIBLE);
                cargarSpinnerBanco(response.body());
            }

            @Override
            public void onFailure(Call<List<CardIssuer>> call, Throwable t) {
                Log.d("methods", "Petición rechazada:" + t.getMessage());
                pbCardIssuers.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void cargarSpinnerBanco(List<CardIssuer> bancos) {
        AdapterBanco adapter;
        adapter = new AdapterBanco(this, bancos);

        //setting adapter to spinner
        spSeleccionarBanco.setAdapter(adapter);
    }

    public void irASeleccionCuotas(View v){
        Intent intent= new Intent(this,CuotasSelection.class);
        CardIssuer banco = (CardIssuer) spSeleccionarBanco.getSelectedItem();
        intent.putExtra("CardIssuer",(new Gson()).toJson(banco));
        intent.putExtra("payment_method",(new Gson()).toJson(mPayment_method));
        intent.putExtra("amount",amount);

        startActivity(intent);
    }
}
