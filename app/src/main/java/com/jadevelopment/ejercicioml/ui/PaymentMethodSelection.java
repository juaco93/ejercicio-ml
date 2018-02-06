package com.jadevelopment.ejercicioml.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jadevelopment.ejercicioml.R;
import com.jadevelopment.ejercicioml.data.Config;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_method_selection);

        spMetodoPago = (Spinner) findViewById(R.id.spMetodoPago);

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

        getPayment_methods();
    }

    private void getPayment_methods(){
        Log.d("methods", "Recuperando payment_methods desde el servidor");

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
                        //ApiError apiError = ApiError.fromResponseBody(response.errorBody());

                        //error = apiError.getMessage();
                        //Log.d(TAG, apiError.getDeveloperMessage());
                    } else {
                        Log.d("methods", response.errorBody().toString());
                        /*try {
                            // Reportar causas de error no relacionado con la API
                            Log.d(TAG, response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }*/
                    }
                    //showLoadingIndicator(false);
                    //showErrorMessage(error);
                    Log.d("methods", response.message());
                    Log.d("methods", response.raw().toString());
                    return;
                }


/*
                serverDirecciones = response.body().getDatos();
                Log.d("direcciones", "bien, recibido: " + response.body().getDatos().toString());
                if (serverDirecciones.size() > 0) {
                    // Mostrar lista de ordenes
                    mostrarDirecciones(serverDirecciones);
                    Log.d("direcciones","obtuvimos nueva direccion del fragment, pasamos a habilitar el boton");
                    chequearDireccion();
                } else {
                    // Mostrar empty state
                    mostrarDireccionesEmpty();
                }
                */
            }

            @Override
            public void onFailure(Call<List<payment_methods>> call, Throwable t) {
                //showLoadingIndicator(false);
                Log.d("methods", "Petición rechazada:" + t.getMessage());
                //showErrorMessage("Error de comunicación");
            }
        });
    }

    private void cargarSpinnerMetodosPago(List<payment_methods> metodosDePago) {

        // String[] items = new String[direccionesServer.size()];
        String[] items = new String[metodosDePago.size()+1];

        //Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(this, R.layout.spinner_list_item_metodo_pago, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //setting adapter to spinner
        spMetodoPago.setAdapter(adapter);

    }
}
