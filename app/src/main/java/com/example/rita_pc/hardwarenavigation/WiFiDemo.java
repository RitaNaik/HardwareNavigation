package com.example.rita_pc.hardwarenavigation;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

public class WiFiDemo extends Fragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v=inflater.inflate(R.layout.activity_wi_fi_demo,container,false);

        Switch toggle = (Switch) v.findViewById(R.id.wifi_switch);
        toggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                // TODO Auto-generated method stub
                if (isChecked)
                {
                    toggleWiFi(true);
                    Toast.makeText(getContext(),
                            "Wi-Fi Enabled!", Toast.LENGTH_LONG).show();
                }
                else
                {
                    toggleWiFi(false);
                    Toast.makeText(getContext(),
                            "Wi-Fi Disabled!", Toast.LENGTH_LONG).show();
                }

            }
        });



        return v;
    }

    public void toggleWiFi(boolean status)
    {
        WifiManager wifiManager = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);

        if (status == true && !wifiManager.isWifiEnabled())
        {
            wifiManager.setWifiEnabled(true);

        }
        else if (status == false && wifiManager.isWifiEnabled())
        {
            wifiManager.setWifiEnabled(false);
        }
    }

}
