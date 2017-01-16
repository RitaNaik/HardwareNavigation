package com.example.rita_pc.hardwarenavigation;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TelephonyManagerDemo extends Fragment
{
    TextView textView1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.activity_telephony_manager_demo,container,false);


        textView1=(TextView)v.findViewById(R.id.txtDetails);


        //Get the instance of TelephonyManager

        TelephonyManager tm=(TelephonyManager)
                getActivity().getSystemService(Context.TELEPHONY_SERVICE);


        /**
         *
         * Runtime Persission
         * for version 6
         *
         *
         */

        if(ActivityCompat.checkSelfPermission(getContext(),
                android.Manifest.permission.READ_PHONE_STATE)!=
                PackageManager.PERMISSION_GRANTED
                )
        {

            ActivityCompat.requestPermissions
                    (getActivity(),new String[]
                            {android.Manifest.permission.READ_PHONE_STATE},1);
        }





        //Calling the methods of TelephonyManager the returns the information

        String IMEINumber=tm.getDeviceId();

        String subscriberID=tm.getDeviceId();

        String SIMSerialNumber=tm.getSimSerialNumber();

        String networkCountryISO=tm.getNetworkCountryIso();

        String SIMCountryISO=tm.getSimCountryIso();

        String softwareVersion=tm.getDeviceSoftwareVersion();

        String voiceMailNumber=tm.getVoiceMailNumber();

        //Get the phone type
        String strphoneType="";

        int phoneType=tm.getPhoneType();

        switch (phoneType)
        {
            case (TelephonyManager.PHONE_TYPE_CDMA):
                strphoneType="CDMA";
                break;
            case (TelephonyManager.PHONE_TYPE_GSM):
                strphoneType="GSM";
                break;
            case (TelephonyManager.PHONE_TYPE_NONE):
                strphoneType="NONE";
                break;
        }


        //Get the SIM state/Details


        String strSIMState="";

        int SIMState=tm.getSimState();

        switch(SIMState)
        {
            case TelephonyManager.SIM_STATE_ABSENT :
                strSIMState = "Absent";
                break;

            case TelephonyManager.SIM_STATE_NETWORK_LOCKED :
                strSIMState = "Lock";
                break;

            case TelephonyManager.SIM_STATE_PIN_REQUIRED :
                strSIMState = "Pin Require";
                break;

            case TelephonyManager.SIM_STATE_PUK_REQUIRED :
                strSIMState = "PUk Require";
                break;

            case TelephonyManager.SIM_STATE_READY :
                strSIMState = "State Ready";
                break;

            case TelephonyManager.SIM_STATE_UNKNOWN :
                strSIMState = "Sate Unknown";
                break;

        }

        //getting information if phone is in roaming
        boolean isRoaming=tm.isNetworkRoaming();

        String info="Phone Details:\n";
        info+="\n IMEI Number:"+IMEINumber;
        info+="\n SubscriberID:"+subscriberID;
        info+="\n Sim Serial Number:"+SIMSerialNumber;
        info+="\n Network Country ISO:"+networkCountryISO;
        info+="\n SIM Country ISO:"+SIMCountryISO;
        info+="\n Software Version:"+softwareVersion;
        info+="\n Voice Mail Number:"+voiceMailNumber;
        info+="\n Phone Network Type:"+strphoneType;
        info+="\n In Roaming? :"+isRoaming;
        info +="\n Sim State ? :" +strSIMState;


        textView1.setText(info);//displaying the information in the textView


    return v;

    }
}
