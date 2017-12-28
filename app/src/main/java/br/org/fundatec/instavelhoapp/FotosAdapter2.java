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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tecnico on 05/10/2017.
 */

public class FotosAdapter2 extends ArrayAdapter<String> {


    public FotosAdapter2(@NonNull Context context, @LayoutRes int resource, ArrayList<String> fotos2) {
        super(context, resource, fotos2);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_imagem2, null);
        }

        String urlDaFoto = getItem(position);

        ImageView image= (ImageView) v.findViewById(R.id.imageView);

        ImageView image2 = (ImageView) v.findViewById(R.id.imageView2);


        if (image != null){
            Picasso.with(getContext()).load(urlDaFoto + "").into(image);
        }

        if (image2 != null){
            Picasso.with(getContext()).load("https://cdn.pixabay.com/photo/2014/04/03/10/08/old-man-309921_960_720.png").into(image2);
        }

        return v;
    }

}

