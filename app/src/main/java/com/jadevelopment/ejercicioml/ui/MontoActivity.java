package com.jadevelopment.ejercicioml.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jadevelopment.ejercicioml.R;
import com.jadevelopment.ejercicioml.data.model.CardIssuer;
import com.jadevelopment.ejercicioml.data.model.PayerCost;
import com.jadevelopment.ejercicioml.data.model.payment_methods;

import java.math.BigDecimal;

public class MontoActivity extends AppCompatActivity {

    private EditText txtMonto;
    private Button btnContinuar;
    private String monto;

    // Objetos para recibir el fin del flujo
    private payment_methods mPayment_method;
    private CardIssuer mCardIssuer;
    private PayerCost mPayerCost;
    private String mAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monto);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMonto);
        //setSupportActionBar(toolbar);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txtMonto = (EditText) findViewById(R.id.txtMonto);
        btnContinuar = (Button) findViewById(R.id.btnContinuar);

        txtMonto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            private String current = "";
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().equals(current)){
                    txtMonto.removeTextChangedListener(this);
                    String cleanString = s.toString().replaceAll("[$,.]", "");
                    BigDecimal parsed = new BigDecimal(cleanString).setScale(2,BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR);
                    String formato = "$"+String.valueOf(parsed);
                    current = formato;
                    txtMonto.setText(formato);
                    txtMonto.setSelection(formato.length());
                    txtMonto.addTextChangedListener(this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Tratamiento del editText
                String cleanString = txtMonto.getText().toString().replaceAll("[$,.]", "");
                BigDecimal parsed = new BigDecimal(cleanString).setScale(2,BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100),BigDecimal.ROUND_FLOOR);
                monto = String.valueOf(parsed);
            }
        });

        // Monto a pagar
        if(getIntent().getStringExtra("amount")!=null)
            mAmount = getIntent().getStringExtra("amount");
        // Metodo de pago
        if(getIntent().getStringExtra("payment_method")!=null)
            mPayment_method = (new Gson()).fromJson((getIntent().getStringExtra("payment_method")),payment_methods.class);
        // Banco
        if(getIntent().getStringExtra("CardIssuer")!=null)
            mCardIssuer = (new Gson()).fromJson((getIntent().getStringExtra("CardIssuer")),CardIssuer.class);
        // Cuotas
        if(getIntent().getStringExtra("PayerCost")!=null)
            mPayerCost = (new Gson()).fromJson((getIntent().getStringExtra("PayerCost")),PayerCost.class);

        // Muestra de alerta final
        if(mAmount!=null && mPayment_method!=null && mCardIssuer!=null && mPayerCost!=null)
            mostrarMensajeResumen(mAmount,mPayment_method.getName(),mCardIssuer.getName(),mPayerCost.getRecommendedMessage());

    }

    public void goToPaymentMethodSelection(View V){
        if(chequearMonto()) {
            Intent intent = new Intent(this, PaymentMethodSelection.class);
            intent.putExtra("amount", monto);
            startActivity(intent);
        }else{
            Toast.makeText(this,"Ingresá un monto válido!",Toast.LENGTH_LONG).show();
        }
    }

    private boolean chequearMonto(){
        if(!txtMonto.getText().toString().isEmpty()){
            return true;
        }else{
            return false;
        }
    }

    public void mostrarMensajeResumen(String monto, String metodo_pago, String banco, String cuotas){
        AlertDialog.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder = new android.support.v7.app.AlertDialog.Builder(this);
        } else {
            builder = new AlertDialog.Builder(getBaseContext());
        }
        builder.setTitle("¡Has finalizado tu pago!")
                .setMessage("Resumen\n" +
                        "Pagaste: $"+monto+"\n"+
                        "Metodo de pago: "+metodo_pago +"\n"+
                        "Banco: "+banco+"\n"+
                        "Cuotas: "+cuotas+"\n\n"+
                        "¡Gracias por tu pago!"
                )
                .setPositiveButton("¡Listo, gracias!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setCancelable(false)
                .show();
    }
}
