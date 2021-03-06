package com.jadevelopment.ejercicioml.data.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.jadevelopment.ejercicioml.R;
import com.jadevelopment.ejercicioml.data.model.payment_methods;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joaquin on 4/2/2018.
 */

public class AdapterMetodoPago extends ArrayAdapter<payment_methods>
{
    private Context context;

    List<payment_methods> datos = null;

    public AdapterMetodoPago(Context context, List<payment_methods> datos)
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
            MetodoPagoHolder holder = new MetodoPagoHolder();
            holder.setIcono((ImageView) row.findViewById(R.id.icono));
            holder.setTextView((TextView) row.findViewById(R.id.texto));
            row.setTag(holder);
        }

        //rellenamos el layout con los datos de la fila que se está procesando
        payment_methods metodos = datos.get(position);
        ((MetodoPagoHolder) row.getTag()).getTextView().setText(metodos.getName());
        //((MetodoPagoHolder) row.getTag()).getIcono().setImageResource(metodos.getThumbnail());

        Picasso
                .with(context)
                .load(datos.get(position).getThumbnail())
                .resize(62, 20)
                .centerInside()
                .into(((MetodoPagoHolder) row.getTag()).getIcono());

        return row;
    }

    private static class MetodoPagoHolder
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