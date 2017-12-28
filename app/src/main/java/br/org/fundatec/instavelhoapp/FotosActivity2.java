package br.org.fundatec.instavelhoapp;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;


public class FotosActivity2 extends AppCompatActivity {

    private static final String TAG = "FOTOS_ACTIVITY2";
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView mImageView;

    private ArrayList<String> fotos2 = new ArrayList<>();
    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos2);
        listView = (ListView) findViewById(R.id.ListFotos2);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //Mostrar o botão
        getSupportActionBar().setHomeButtonEnabled(true);      //Ativar o botão
        getSupportActionBar().setTitle("Fotos");     //Titulo para ser exibido na sua Action Bar em frente à seta

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FotosActivity2PermissionsDispatcher.dispatchTakePictureIntentWithCheck(FotosActivity2.this);
            }
        });
        mImageView = (ImageView)findViewById(R.id.image);
        lerBD();
    }

    //Ativa a camera
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
        FotosActivity2PermissionsDispatcher.onRequestPermissionsResult(FotosActivity2.this, requestCode, grantResults);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");

            //mImageView.setImageBitmap(imageBitmap);


            // Create a root reference
            StorageReference storageRef = FirebaseStorage.getInstance().getReference();

            // Create a reference to 'images/mountains.jpg'
            StorageReference novafoto = storageRef.child("images/" + UUID.randomUUID().toString() +  ".jpg");

            //mImageView.setDrawingCacheEnabled(true);
            //mImageView.buildDrawingCache();
            //
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] data2 = baos.toByteArray();

            UploadTask uploadTask = novafoto.putBytes(data2);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Toast.makeText(FotosActivity2.this, "Foto Adicionada!" , Toast.LENGTH_LONG).show();
                    String urlFoto = downloadUrl.toString();
                    salvarBD( urlFoto );
                    //fotos.add(urlFoto);

                    FotosAdapter2 arrayAdapter = new FotosAdapter2(FotosActivity2.this, android.R.layout.simple_list_item_1, fotos2 );
                    listView.setAdapter(arrayAdapter);
                }
            });
        }
        lerBD();
    }


    private void lerBD() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("quarto/102");
        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
//                String value = dataSnapshot.getValue(String.class);
//                Log.d(TAG, "Value is: " + value);
//                setTitle(value);
                fotos2.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()){
                    Log.e("LISTA2", postSnapshot.getKey());
                    fotos2.add(postSnapshot.getValue(String.class));
                }
                FotosAdapter2 arrayAdapter = new FotosAdapter2(FotosActivity2.this, android.R.layout.simple_list_item_1, fotos2 );
                listView.setAdapter(arrayAdapter);

                //tentando colocar array na listView
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private String salvarBD(String urlFoto){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String nQuarto = "102";
        DatabaseReference myRef = database.getReference("quarto/" + nQuarto);
        //criar string que quando clica muda o quarto.
        DatabaseReference temp = myRef.push();
        temp.setValue(urlFoto);
        return temp.getKey();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //Botão adicional na ToolBar
        switch (item.getItemId()) {
            case android.R.id.home:  //ID do seu botão (gerado automaticamente pelo android, usando como está, deve funcionar
                startActivity(new Intent(this, MainActivity.class));  //O efeito ao ser pressionado do botão (no caso abre a activity)
                finishAffinity();  //Método para matar a activity e não deixa-lá indexada na pilhagem
                break;
            default:break;
        }
        return true;
    }
}
