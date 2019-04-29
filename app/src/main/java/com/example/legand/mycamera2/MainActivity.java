package com.example.legand.mycamera2;

import android.app.Activity;
import android.arch.lifecycle.ViewModelProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.frosquivel.magicalcamera.MagicalCamera;
import com.frosquivel.magicalcamera.MagicalPermissions;

import java.util.jar.Manifest;

public class MainActivity extends AppCompatActivity {

    private final static int RESIZE_PHOTO_PIXELS_PORCENTAGE=50;
    private MagicalPermissions magicalPermissions;
    private ImageView ImageViewFoto;
    private MagicalCamera magicalCamera;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonFoto).setOnClickListener(this);
        ImageViewFoto=findViewById(R.id.imageViewFoto);


        String[] permissions = new String[] {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
        };
        magicalPermissions= new MagicalPermissions(this, permissions);
        magicalCamera=new MagicalCamera(this,RESIZE_PHOTO_PIXELS_PORCENTAGE,magicalPermissions);
    }

    public void onClick(View v) {magicalCamera.takePhoto();}
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        Map<String, Boolean> map = magicalPermissions.permissionResult(requestCode, permissions, grantResults);
        for (String permission : map.keySet()) {
            Log.d("PERMISSIONS", permission + " was: " + map.get(permission));
        }
        //Following the example you could also
        //locationPermissions(requestCode, permissions, grantResults);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //CALL THIS METHOD EVER
        if (resultCode==RESULT_OK){
        magicalCamera.resultPhoto(requestCode, resultCode, data);

        //this is for rotate picture in this method
        //magicalCamera.resultPhoto(requestCode, resultCode, data, MagicalCamera.ORIENTATION_ROTATE_180);

        //with this form you obtain the bitmap (in this example set this bitmap in image view)
        ImageViewFoto.setImageBitmap(magicalCamera.getPhoto());

        //if you need save your bitmap in device use this method and return the path if you need this
        //You need to send, the bitmap picture, the photo name, the directory name, the picture type, and autoincrement photo name if           //you need this send true, else you have the posibility or realize your standard name for your pictures.
        String path = magicalCamera.savePhotoInMemoryDevice(magicalCamera.getPhoto(), "myPhotoName", "myDirectoryName", MagicalCamera.JPEG, true);
    }
        if(path != null){
            Toast.makeText(MainActivity.this, "The photo is save in device, please check this path: " + path, Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(MainActivity.this, "Sorry your photo dont write in devide, please contact with fabian7593@gmail and say this error", Toast.LENGTH_SHORT).show();
        }
    }
}
