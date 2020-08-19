package com.example.practice;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.widget.Toast;
import android.content.Intent;
import android.app.Activity;
import androidx.appcompat.app.AppCompatActivity;

public class SampleService extends Service{
    private MediaPlayer player;
    int count;
    private DBManager dbManager;
    boolean running;
    @Override
    public IBinder onBind(Intent intent) {
        return  null;
    }
    @Override
    public void onCreate() {
        Toast.makeText(this, "Service was Created", Toast.LENGTH_LONG).show();
        dbManager = new DBManager(this);
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player = MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
        // This will play the ringtone continuously until we stop the service.
        player.setLooping(true);
        // It will start the player
        player.start();
        count=0;
        running=true;
        new Thread(new Runnable(){
            public void run() {
                // TODO Auto-generated method stub
                dbManager.open();
                while(running)
                {

                    if(count%1000==0)
                        dbManager.insert(1);
                    count+=1;
                }
            }
        }).start();
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        // Stopping the player when service is destroyed
        player.stop();
        running = false;
        Toast.makeText(this, "Service Stopped"+count, Toast.LENGTH_LONG).show();
    }
}
