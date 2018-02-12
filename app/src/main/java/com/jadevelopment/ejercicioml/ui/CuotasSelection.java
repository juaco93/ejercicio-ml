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
import com.jadevelopment.ejercicioml.data.adapter.AdapterCuotas;
import com.jadevelopment.ejercicioml.data.api.MercadoLibreAPI;
import com.jadevelopment.ejercicioml.data.model.CardIssuer;
import com.jadevelopment.ejercicioml.data.model.Installment;
import com.jadevelopment.ejercicioml.data.model.PayerCost;
import com.jadevelopment.ejercicioml.data.model.payment_methods;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CuotasSelection extends AppCompatActivity {
    private Retrofit mRestAdapter;
    private MercadoLibreAPI mMercadoLibreApi;

    private Spinner spCuotasSelection;
    private ProgressBar pbCuotasSelection;


    private payment_methods mPayment_method;
    private String amount;
    private CardIssuer mCardIssuer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cuotas_selection);

        spCuotasSelection = (Spinner) findViewById(R.id.spCuotas);
        pbCuotasSelection = (ProgressBar) findViewById(R.id.pbCuotasSelection);
        pbCuotasSelection.setVisibility(View.INVISIBLE);

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


        if(getIntent().getStringExtra("amount")!=null)
            amount = getIntent().getStringExtra("amount");
        if(getIntent().getStringExtra("payment_method")!=null)
            mPayment_method = (new Gson()).fromJson((getIntent().getStringExtra("payment_method")),payment_methods.class);
        if(getIntent().getStringExtra("CardIssuer")!=null)
            mCardIssuer = (new Gson()).fromJson((getIntent().getStringExtra("CardIssuer")),CardIssuer.class);

        getInstallments();
    }

    private void getInstallments(){
        Log.d("methods", "Recuperando card_issuers desde el servidor");
        pbCuotasSelection.setVisibility(View.VISIBLE);

        // Realizar petición HTTP
        Call<List<Installment>> call = mMercadoLibreApi.getInstallments(Config.API_KEY,amount,mPayment_method.getId(),mCardIssuer.getId());
        call.enqueue(new Callback<List<Installment>>() {
            @Override
            public void onResponse(Call<List<Installment>> call,
                                   Response<List<Installment>> response) {
                if (!response.isSuccessful()) {
                    // Procesar error de API
                    String error = "Ha ocurrido un error. Contacte al administrador";

                    pbCuotasSelection.setVisibility(View.INVISIBLE);

                    Log.d("methods", response.message());
                    Log.d("methods", response.raw().toString());
                    return;
                }

                if(response.body().size()>0){
                    pbCuotasSelection.setVisibility(View.INVISIBLE);
                    cargarSpinnerCuotas(response.body().get(0).getPayerCosts());
                }
            }

            @Override
            public void onFailure(Call<List<Installment>> call, Throwable t) {
                Log.d("methods", "Petición rechazada:" + t.getMessage());
                pbCuotasSelection.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void cargarSpinnerCuotas(List<PayerCost> cuotas) {
        AdapterCuotas adapter;
        adapter = new AdapterCuotas(this, cuotas);
        spCuotasSelection.setAdapter(adapter);
    }

    public void irAInicio(View v){
        Intent intent= new Intent(this,MontoActivity.class);
        PayerCost cuota = (PayerCost) spCuotasSelection.getSelectedItem();
        intent.putExtra("PayerCost",(new Gson()).toJson(cuota));
        intent.putExtra("CardIssuer",(new Gson()).toJson(mCardIssuer));
        intent.putExtra("payment_method",(new Gson()).toJson(mPayment_method));
        intent.putExtra("amount",amount);

        startActivity(intent);
    }
}
