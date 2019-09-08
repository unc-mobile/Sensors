package com.example.scroggo.sensorpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout mLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ALL);

        mLayout = findViewById(R.id.linear);
        for (Sensor sensor : sensors) {
            TextView name = addField(sensor.getName());
            name.setOnClickListener(this);

            float range = sensor.getMaximumRange();
            addField("\tMaximum range: " + range);
            addField("\tResolution: " + sensor.getResolution());
            addField("\tMin Delay: " + sensor.getMinDelay());
            addField("\tPower: " + sensor.getPower());
            addField("\tVendor: " + sensor.getVendor());
            addField("\tVersion: " + sensor.getVersion());
            addField("\tIsWakeUp: " + sensor.isWakeUpSensor());
        }
    }

    private TextView addField(String text) {
        TextView textView = new TextView(this);
        textView.setText(text);
        mLayout.addView(textView);
        return textView;
    }

    @Override
    public void onClick(View view) {
        TextView textView = (TextView) view;
        String name = textView.getText().toString();
        Intent intent = new Intent(this, SensorStats.class);
        intent.putExtra(Intent.EXTRA_TEXT, name);
        startActivity(intent);
    }
}
