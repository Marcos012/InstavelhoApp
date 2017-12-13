package br.org.fundatec.instavelhoapp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by tecnico on 05/10/2017.
 */

public class FotosAdapter extends ArrayAdapter<String> {


    public FotosAdapter(@NonNull Context context, @LayoutRes int resource, ArrayList<String> fotos) {
        super(context, resource, fotos);
    }



    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.item_imagem, null);
        }

        String urlDaFoto = getItem(position);

        ImageView image= (ImageView) v.findViewById(R.id.imageView);




        if (image != null){
            Picasso.with(getContext()).load(urlDaFoto + "").into(image);
        }

        return v;
    }

}

