package com.example.tugassensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Trace;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    // Menampilkan list sensor
    private SensorManager sensorManager;

    // Menampilkan value setiap sensor
    private Sensor sensorLight, sensorProximity, sensorAmbientTemperature, sensorPressure,
            sensorRelativeHumidity;

    private TextView textSensorLight, textSensorProximity, textSensorAmbientTemperature,
            textSensorPressure, textSensorRelativeHumidity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Menampilkan list sensor
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorText = new StringBuilder();

        for (Sensor currentSensor : sensorList){
            sensorText.append(currentSensor.getName()).append(System.getProperty("line.separator"));
        }

        TextView sensorTextView = findViewById(R.id.sensor_list);
        sensorTextView.setText(sensorText);

        // Menampilkan value setiap sensor
        textSensorLight = findViewById(R.id.label_light);
        textSensorProximity = findViewById(R.id.label_proximity);
        textSensorAmbientTemperature = findViewById(R.id.label_temperature);
        textSensorPressure = findViewById(R.id.label_pressure);
        textSensorRelativeHumidity = findViewById(R.id.label_humidity);

        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorAmbientTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sensorRelativeHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);

        String sensor_error = "No sensor";
        if (sensorLight == null) {
            textSensorLight.setText(sensor_error);
        }
        if (sensorProximity == null) {
            textSensorProximity.setText(sensor_error);
        }
        if (sensorAmbientTemperature == null) {
            textSensorAmbientTemperature.setText(sensor_error);
        }
        if (sensorPressure == null) {
            textSensorPressure.setText(sensor_error);
        }
        if (sensorRelativeHumidity == null) {
            textSensorRelativeHumidity.setText(sensor_error);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sensorLight != null) {
            sensorManager.registerListener(this, sensorLight, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorProximity != null) {
            sensorManager.registerListener(this, sensorProximity, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorAmbientTemperature != null) {
            sensorManager.registerListener(this, sensorAmbientTemperature, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorPressure != null) {
            sensorManager.registerListener(this, sensorPressure, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorRelativeHumidity != null) {
            sensorManager.registerListener(this, sensorRelativeHumidity, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int sensorType = event.sensor.getType();
        float currentValue = event.values[0];
        switch (sensorType) {
            case Sensor.TYPE_LIGHT:
                textSensorLight.setText(String.format("%1$.2f", currentValue));
                changeBackgroundColor(currentValue);
                break;
            case Sensor.TYPE_PROXIMITY:
                textSensorProximity.setText(String.format("%1$.2f", currentValue));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                textSensorAmbientTemperature.setText(String.format("%1$.2f", currentValue));
                break;
            case Sensor.TYPE_PRESSURE:
                textSensorPressure.setText(String.format("%1$.2f", currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                textSensorRelativeHumidity.setText(String.format("%1$.2f", currentValue));
                break;
            default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private void changeBackgroundColor(float currentValue) {
        LinearLayout layout = findViewById(R.id.main);
        TextView topText = findViewById(R.id.judul);

        if (currentValue >= 2000 && currentValue <= 40000) {
            layout.setBackgroundColor(Color.WHITE);
            topText.setTextColor(Color.BLACK);
        } else if (currentValue >= 0 && currentValue < 2000) {
            layout.setBackgroundColor(Color.BLACK);
            topText.setTextColor(Color.WHITE);
        }
    }
}