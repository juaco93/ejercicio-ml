package com.jadevelopment.ejercicioml.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jadevelopment.ejercicioml.R;

import java.math.BigDecimal;

public class MontoActivity extends AppCompatActivity {

    private EditText txtMonto;
    private Button btnContinuar;
    String monto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monto);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarMonto);
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

    }

    public void goToPaymentMethodSelection(View V){
        Intent intent = new Intent(this, PaymentMethodSelection.class);
        intent.putExtra("monto",monto);
        startActivity(intent);
    }
}
