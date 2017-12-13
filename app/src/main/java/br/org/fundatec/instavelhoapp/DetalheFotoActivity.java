package br.org.fundatec.instavelhoapp;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class DetalheFotoActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_foto);
        DetalheFotoActivityPermissionsDispatcher.dispatchTakePictureIntentWithCheck(DetalheFotoActivity.this);
    }
    @NeedsPermission(Manifest.permission.CAMERA)
    public void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // NOTE: delegate the permission handling to generated method
        DetalheFotoActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            mImageView.setImageBitmap(imageBitmap);
        }
    }

   /* public void load(){


        ArrayList<String> fotos = new ArrayList<>();

        Uri downloadUrl = taskSnapshot.getDownloadUrl();
        Toast.makeText(FotosActivity.this, "Deu certo!!!" + downloadUrl.toString() , Toast.LENGTH_LONG).show();
        String urlFoto = downloadUrl.toString();

        fotos.add(urlFoto);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(FotosActivity.this, android.R.layout.simple_list_item_1, fotos );
        listView.setAdapter(arrayAdapter);
    }*/
}
