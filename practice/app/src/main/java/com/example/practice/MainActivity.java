package com.example.practice;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.database.Cursor;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private DBManager dbManager;
    private BluetoothAdapter bAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbManager = new DBManager(this);
        bAdapter = BluetoothAdapter.getDefaultAdapter();
        if(bAdapter == null)
        {
            Toast.makeText(this, "No Bluetooth", Toast.LENGTH_LONG).show();
        }
        else
        {
            if(!bAdapter.isEnabled())
            {
                startActivityForResult(new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE), 1);
            }
        }

    }
    // Start the service
    public void startService(View view) {
        startService(new Intent(this, SampleService.class));
    }
    // Stop the service
    public void stopService(View view) {
        stopService(new Intent(this, SampleService.class));
    }
    public void clearDb(View view) {
        dbManager.open();
        dbManager.clear();
    }
    public void showCount(View view) {

        dbManager.open();
        int ct = dbManager.fetch();
        TextView tv = (TextView)findViewById(R.id.count);
        tv.setText(ct+" Entries");
    }
    public void dispPaired(View view) {
        if(bAdapter==null){
            Toast.makeText(getApplicationContext(),"Bluetooth Not Supported",Toast.LENGTH_SHORT).show();
        }
        else {
            Set<BluetoothDevice> pairedDevices = bAdapter.getBondedDevices();
            ArrayList list = new ArrayList();
            if (((Set) pairedDevices).size() > 0) {
                for (BluetoothDevice device : pairedDevices) {
                    String devicename = device.getName();
                    String macAddress = device.getAddress();
                    list.add("Name: " + devicename + "MAC Address: " + macAddress);
                }
                ListView lstvw = (ListView) findViewById(R.id.listDevice);
                ArrayAdapter aAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, list);
                lstvw.setAdapter(aAdapter);
            }
        }
    }
}