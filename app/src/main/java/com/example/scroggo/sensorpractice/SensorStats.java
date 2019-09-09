package com.example.scroggo.sensorpractice;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.Arrays;

public class SensorStats extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mSensor;

    private static final int SAMSUNG_MOTION = 65559;
    private static final int SAMSUNG_GRIP_WIFI = 65575;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MainActivity.SENSOR_NAME);
        setTitle(name);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        final int invalidSensor = Sensor.TYPE_ALL;
        int type = intent.getIntExtra(MainActivity.SENSOR_TYPE, invalidSensor);
        if (type == invalidSensor) {
            // Hmm, is it safe to call finish from onCreate?
            errorOut("Calling is missing SENSOR_TYPE!");
            return;
        }

        String stringType = intent.getStringExtra(MainActivity.SENSOR_STRING_TYPE);
        mSensor = mSensorManager.getDefaultSensor(type);
        if (mSensor == null) {
            errorOut("Could not find default sensor for " + stringType);
            return;
        }

        Toast.makeText(this, "Listening for " + stringType, Toast.LENGTH_SHORT).show();
    }

    private void errorOut(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        finish();
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        switch (sensorEvent.sensor.getType()) {
            case Sensor.TYPE_ACCELEROMETER: {
                String text = "x: " + sensorEvent.values[0]
                            + "y: " + sensorEvent.values[1]
                            + "z: " + sensorEvent.values[2];
                Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
                break;
            }
            case SAMSUNG_MOTION: {
                Toast.makeText(this, "Motion: " + Arrays.toString(sensorEvent.values), Toast.LENGTH_LONG).show();
                log("Saw some motion: " + Arrays.toString(sensorEvent.values));
                break;
            }
            case SAMSUNG_GRIP_WIFI: {
                Toast.makeText(this, "Grip: " + Arrays.toString(sensorEvent.values), Toast.LENGTH_LONG).show();
                break;
            }
            default:
                Toast.makeText(this,
                        "Other sensor " + sensorEvent.sensor.getStringType(),
                        Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void log(String msg) {
        Log.d("SCROGGO", msg);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        Toast.makeText(this, "onAccuracyChanged: " + i, Toast.LENGTH_SHORT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSensor != null) {
            mSensorManager.registerListener(this, mSensor, 1000000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensor != null) {
            mSensorManager.unregisterListener(this, mSensor);
        }
    }
}
