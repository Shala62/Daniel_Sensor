package com.example.daniel_sensor;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.EventListener;

public class MainActivity extends AppCompatActivity {

    private EditText et1;
    private SensorManager sm;
    private Sensor sa;
    private SensorEventListener SEL;
    int latigo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        et1 = findViewById(R.id.etVacio);
        sm  = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sa = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        if (sa == null){
            Toast.makeText(this, "No hay Sensor", Toast.LENGTH_SHORT).show();
        }else {
            SEL = new SensorEventListener() {
                @Override
                public void onSensorChanged(SensorEvent sensorEvent) {
                    float x = sensorEvent.values[0];
                    et1.setText(Float.toString(x));

                    if (x >- 5 && latigo == 0){
                        latigo++;
                        getWindow().getDecorView().setBackgroundColor(Color.BLUE);
                    } else if (x > 5 && latigo == 1) {
                        latigo++;
                        getWindow().getDecorView().setBackgroundColor(Color.RED);
                    }
                    if (latigo == 2){
                        sonido();
                        latigo = 0;
                    }
                }

                @Override
                public void onAccuracyChanged(Sensor sensor, int accuracy) {

                }
            };
        }
    }

    private void sonido(){
        MediaPlayer mp = MediaPlayer.create(this, R.raw.samsung);
        mp.start();
    }

    public void iniciar() {
        sm.registerListener(SEL, sa, SensorManager.SENSOR_DELAY_NORMAL);
    }

    public void onResume(){
        iniciar();
        super.onResume();
    }

    public void detener(){
        sm.unregisterListener(SEL);
    }

    public void onStop(){
        detener();
        super.onStop();
    }

    
}