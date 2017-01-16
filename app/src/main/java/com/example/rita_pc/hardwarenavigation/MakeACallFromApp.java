package com.example.rita_pc.hardwarenavigation;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MakeACallFromApp extends Fragment
{
    private static final int PERMS_REQUEST_CODE=112;

    Button CallButton;
    View v;
    EditText EnterNo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

         v=inflater.inflate(R.layout.activity_make_acall_from_app,container,false);

        CallButton = (Button) v.findViewById(R.id.button1);

        CallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hasPermissions())
                {
                    makeCall();
                }
                else
                {
                    requestPerms();


                }
            }
        });

        return v;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        boolean allowed = true;

        switch (requestCode)
        {
            case PERMS_REQUEST_CODE:
                for (int res : grantResults)
                {
                    //if user granted all permissions
                    allowed = true;
                    res = PackageManager.PERMISSION_GRANTED;
                }
                break;
            default:
                //if user not granted all permissions
                allowed = false;
                break;

        }
        if (allowed)
        {
            //if user granted all permissions then we will perform our task
            makeCall();
        }
        else
        {
            //if user not granted permissions we will give warning msg..

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if (shouldShowRequestPermissionRationale(Manifest.permission.CALL_PHONE))
                {
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private boolean hasPermissions()
    {
        int res=0;
        String []permissions=new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE};

        for (String per:permissions)
        {
            res=ActivityCompat.checkSelfPermission(getContext(),per);
            if ((res==PackageManager.PERMISSION_GRANTED))
            {
                return true;
            }
        }
        return false;
    }

    private void requestPerms()
    {
        String []permissions=new String[]{Manifest.permission.CALL_PHONE,Manifest.permission.READ_PHONE_STATE};
        if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M)
        {
            requestPermissions(permissions,PERMS_REQUEST_CODE);
        }
    }

    private void makeCall()
    {
        EnterNo = (EditText) v.findViewById(R.id.editText1);

        Intent makecall = new Intent(Intent.ACTION_CALL);

        makecall.setData(Uri.parse("tel:" + EnterNo.getText().toString()));

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(makecall);


    }
}
