package br.org.fundatec.instavelhoapp;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by tecnico on 05/10/2017.
 */

public class FotosAdapter extends ArrayAdapter<FotosActivity> {


    public FotosAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    public FotosAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull FotosActivity[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.content_fotos, null);
        }

        FotosActivity f = getItem(position);

        return v;
    }
}

