package com.jadevelopment.ejercicioml.data.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.jadevelopment.ejercicioml.R;
import com.jadevelopment.ejercicioml.data.model.payment_methods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Joaquin on 4/2/2018.
 */

/***** Adapter class extends with ArrayAdapter ******/
public class AdapterMetodopago extends ArrayAdapter<String>{

    public AdapterMetodopago(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public AdapterMetodopago(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId) {
        super(context, resource, textViewResourceId);
    }

    public AdapterMetodopago(@NonNull Context context, @LayoutRes int resource, @NonNull String[] objects) {
        super(context, resource, objects);
    }

    public AdapterMetodopago(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull String[] objects) {
        super(context, resource, textViewResourceId, objects);
    }

    public AdapterMetodopago(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    public AdapterMetodopago(@NonNull Context context, @LayoutRes int resource, @IdRes int textViewResourceId, @NonNull List<String> objects) {
        super(context, resource, textViewResourceId, objects);
    }
}