package com.example.scroggo.sensorpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    static final String SENSOR_NAME = "sensor";
    static final String SENSOR_TYPE = "sensor_type";
    static final String SENSOR_STRING_TYPE = "sensor_string_type";

    private LinearLayout mLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView list = findViewById(R.id.list);
        list.setAdapter(new SensorAdapter());
        list.setOnItemClickListener(this);
    }

    private class SensorAdapter extends BaseAdapter {
        List<Sensor> mSensors;
        public SensorAdapter() {
            SensorManager sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
            mSensors = sensorManager.getSensorList(Sensor.TYPE_ALL);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Object getItem(int i) {
            return mSensors.get(i);
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.sensor_item, viewGroup, false);
            }
            Sensor sensor = mSensors.get(i);
            TextView textView = view.findViewById(R.id.name);
            textView.setText(sensor.getName());

            textView = view.findViewById(R.id.type);
            textView.setText("\tTYPE: " + sensor.getStringType() + " (" + sensor.getType() + ")");

            textView = view.findViewById(R.id.range);
            float range = sensor.getMaximumRange();
            textView.setText("\tMaximum range: " + range);

            textView = view.findViewById(R.id.resolution);
            textView.setText("\tResolution: " + sensor.getResolution());

            textView = view.findViewById(R.id.delay);
            textView.setText("\tMin Delay: " + sensor.getMinDelay());

            textView = view.findViewById(R.id.power);
            textView.setText("\tPower: " + sensor.getPower());

            textView = view.findViewById(R.id.vendor);
            textView.setText("\tVendor: " + sensor.getVendor());

            textView = view.findViewById(R.id.version);
            textView.setText("\tVersion: " + sensor.getVersion());

            textView = view.findViewById(R.id.is_wake_up);
            textView.setText("\tIsWakeUp: " + sensor.isWakeUpSensor());

            return view;
        }

        @Override
        public int getCount() {
            return mSensors.size();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Sensor sensor = (Sensor) adapterView.getAdapter().getItem(i);

        Intent intent = new Intent(this, SensorStats.class);
        intent.putExtra(SENSOR_NAME, sensor.getName());
        intent.putExtra(SENSOR_TYPE, sensor.getType());
        intent.putExtra(SENSOR_STRING_TYPE, sensor.getStringType());

        startActivity(intent);
    }
}
