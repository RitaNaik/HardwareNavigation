package com.example.rita_pc.hardwarenavigation;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.gsm.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SendSMS extends Fragment
{
    EditText number,msg;
    private static final int PERMS_REQUEST_CODE=110;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View v=inflater.inflate(R.layout.activity_send_sms,container,false);

        Button btn = (Button) v.findViewById(R.id.button);
        number = (EditText) v.findViewById(R.id.editTextNumber);
        msg = (EditText) v.findViewById(R.id.editTextMessage);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if (hasPermissions())
                {
                    sendSms();
                }
                else
                {
                    requestPerms();
                }

            }

        });

        return v;
    }

    private boolean hasPermissions()
    {
        int res = 0;
        String[] permissions = new String[]{Manifest.permission.SEND_SMS};
        for (String per : permissions)
        {
            res = ActivityCompat.checkSelfPermission(getContext(),per);
            if (res == PackageManager.PERMISSION_GRANTED)
            {
                return true;
            }
        }
        return false;
    }

    private void requestPerms()
    {
        String[] permissions = new String[]{Manifest.permission.SEND_SMS};
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M)
        {
            requestPermissions(permissions,PERMS_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        boolean allowed=true;

        switch (requestCode)
        {
            case PERMS_REQUEST_CODE:
                for (int res:grantResults)
                {
                    //if user granted all permissions
                    allowed=true;
                }
                break;
            default:
                //if user not granted all permissions
                allowed=false;
                break;
        }
        if (allowed)
        {
            sendSms();
        }
        else
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            {
                if(shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS))
                {
                    Toast.makeText(getContext(),"Permission Denied", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void sendSms()
    {
        String mynumber = number.getText().toString();
        String mymsg = msg.getText().toString();

        try
        {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(mynumber, null, mymsg, null, null);
            Toast.makeText(getContext(), "SMS Sent!", Toast.LENGTH_LONG).show();
        }
        catch (Exception e)
        {
            Toast.makeText(getContext(),
                    "SMS faild, please try again later!",Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

}
