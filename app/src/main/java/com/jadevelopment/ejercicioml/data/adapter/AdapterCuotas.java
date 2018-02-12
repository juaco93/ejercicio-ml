package com.jadevelopment.ejercicioml.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jadevelopment.ejercicioml.R;
import com.jadevelopment.ejercicioml.data.model.CardIssuer;
import com.jadevelopment.ejercicioml.data.model.Installment;
import com.jadevelopment.ejercicioml.data.model.PayerCost;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Joaquin on 11/2/2018.
 */

public class AdapterCuotas extends ArrayAdapter<PayerCost>
{
    private Context context;

    List<PayerCost> datos = null;

    public AdapterCuotas(Context context, List<PayerCost> datos)
    {
        //se debe indicar el layout para el item que seleccionado (el que se muestra sobre el botón del botón)
        super(context, R.layout.spinner_list_item_metodo_pago, datos);
        this.context = context;
        this.datos = datos;
    }

    //este método establece el elemento seleccionado sobre el botón del spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (convertView == null)
        {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.spinner_list_item_metodo_pago,null);
        }
        ((TextView) convertView.findViewById(R.id.texto)).setText(datos.get(position).getRecommendedMessage());

        return convertView;
    }

    //gestiona la lista usando el View Holder Pattern. Equivale a la típica implementación del getView
    //de un Adapter de un ListView ordinario
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent)
    {
        View row = convertView;
        if (row == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.spinner_list_item_metodo_pago, parent, false);
        }

        if (row.getTag() == null)
        {
            InstallmentHolder holder = new InstallmentHolder();
            holder.setTextView((TextView) row.findViewById(R.id.texto));
            row.setTag(holder);
        }

        //rellenamos el layout con los datos de la fila que se está procesando
        PayerCost banco = datos.get(position);

        ((InstallmentHolder) row.getTag()).getTextView().setText(banco.getRecommendedMessage());


        return row;
    }

    private static class InstallmentHolder
    {

        private TextView textView;

        public TextView getTextView()
        {
            return textView;
        }
        public void setTextView(TextView textView)
        {
            this.textView = textView;
        }

    }
}