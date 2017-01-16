package com.example.rita_pc.hardwarenavigation;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class CameraDemo extends Fragment
{
    private static final int TAKE_PICTURE = 0;
    private static final int TAKE_Thumbnail = 1;
    private ImageView imageView;
    private static String TAG = "PermissionDemo";
    private static final int REQUEST_WRITE_STORAGE = 112;

    Context myContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {

        View v=inflater.inflate(R.layout.activity_camera_demo,container,false);

        Button btn1 = (Button) v.findViewById(R.id.button);
        Button btn2 = (Button) v.findViewById(R.id.button2);
        imageView = (ImageView) v.findViewById(R.id.imageView);



        int currentapiVersion = android.os.Build.VERSION.SDK_INT;

        if (currentapiVersion >= Build.VERSION_CODES.M)
        {
            // Do something for lollipop and above versions

            int permission = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (permission != PackageManager.PERMISSION_GRANTED)
            {
                Log.i(TAG, "Permission to record denied");

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(myContext);
                    builder.setMessage("Permission to access the SD-CARD")
                            .setTitle("Permission required");

                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int id) {
                            Log.i(TAG, "Clicked");
                            makeRequest();
                        }
                    });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }
                else
                {

                    makeRequest();

                    boolean rerult =  hasPermissionInManifest(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE);
                }

            }

        }
        else
        {
            // do something for phones running an SDK before lollipop
            // filecode();

        }

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                        TAKE_Thumbnail);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivityForResult
                        (new Intent(MediaStore.ACTION_IMAGE_CAPTURE),
                                TAKE_PICTURE);
            }
        });



        return v;
    }
    protected void makeRequest()
    {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_WRITE_STORAGE);


    }

    private void filecode(Intent data)
    {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");

        imageView.setImageBitmap(thumbnail);


        try
        {
            FileOutputStream fos = new FileOutputStream
                    (new File(Environment.getExternalStorageDirectory(), "image.jpg"));

            thumbnail.compress(Bitmap.CompressFormat.JPEG, 75, fos);

            fos.flush();

            fos.close();

            data=null;
            thumbnail=null;

        }
        catch (FileNotFoundException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();

        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


    }

    public boolean hasPermissionInManifest(Context context, String permissionName) {
        final String packageName = context.getPackageName();
        try {
            final PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName, PackageManager.GET_PERMISSIONS);
            final String[] declaredPermisisons = packageInfo.requestedPermissions;
            if (declaredPermisisons != null && declaredPermisisons.length > 0) {
                for (String p : declaredPermisisons) {
                    if (p.equals(permissionName)) {
                        return true;
                    }
                }
            }
        } catch (PackageManager.NameNotFoundException e) {

        }
        return false;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==TAKE_PICTURE)
        {

            if (data != null)
            {

                filecode(data);

            }



        }
        else if(requestCode==TAKE_Thumbnail)
        {

            if (data != null)
            {
                if (data.hasExtra("data"))
                {
                    Bitmap thumbnail1 = data.getParcelableExtra("data");
                    imageView.setImageBitmap(thumbnail1);

                    data = null;
                    thumbnail1=null;

                }
            }

        }


        super.onActivityResult(requestCode, resultCode, data);
    }
}
