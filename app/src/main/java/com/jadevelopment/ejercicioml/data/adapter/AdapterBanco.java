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
import com.jadevelopment.ejercicioml.data.model.payment_methods;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Joaquin on 11/2/2018.
 */

public class AdapterBanco extends ArrayAdapter<CardIssuer>
{
    private Context context;

    List<CardIssuer> datos = null;

    public AdapterBanco(Context context, List<CardIssuer> datos)
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
        ImageView imagen;

        if (convertView == null)
        {
            convertView = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.spinner_list_item_metodo_pago,null);
        }
        ((TextView) convertView.findViewById(R.id.texto)).setText(datos.get(position).getName());

        imagen = convertView.findViewById(R.id.icono);
        //Carga de los logos de las empresas con Picasso
        Picasso
                .with(context)
                .load(datos.get(position).getThumbnail())
                .resize(62, 20)
                .centerInside()
                .into(imagen);

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
            BancoHolder holder = new BancoHolder();
            holder.setIcono((ImageView) row.findViewById(R.id.icono));
            holder.setTextView((TextView) row.findViewById(R.id.texto));
            row.setTag(holder);
        }

        //rellenamos el layout con los datos de la fila que se está procesando
        CardIssuer banco = datos.get(position);
        ((BancoHolder) row.getTag()).getTextView().setText(banco.getName());
        //((MetodoPagoHolder) row.getTag()).getIcono().setImageResource(metodos.getThumbnail());

        Picasso
                .with(context)
                .load(datos.get(position).getThumbnail())
                .resize(62, 20)
                .centerInside()
                .into(((BancoHolder) row.getTag()).getIcono());

        return row;
    }

    private static class BancoHolder
    {

        private ImageView icono;
        private TextView textView;

        public ImageView getIcono()
        {
            return icono;
        }

        public void setIcono(ImageView icono)
        {
            this.icono = icono;
        }

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
