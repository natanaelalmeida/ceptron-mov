package com.almeida.natanaels.identity_physical_movements.Activity;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.almeida.natanaels.identity_physical_movements.Entities.MovementsLabels;
import com.almeida.natanaels.identity_physical_movements.Interfaces.Monitoring;
import com.almeida.natanaels.identity_physical_movements.R;
import com.almeida.natanaels.identity_physical_movements.Sensors.MonitoringService;

public class AcquisitionActivity extends AppCompatActivity implements SensorEventListener, Monitoring {

    private Spinner spinner;
    private Button btn_executar;
    private LinearLayout progress;

    private MonitoringService mMonitoringService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acquisition);

        btn_executar = findViewById(R.id.btn_run);
        btn_executar.setOnClickListener(btn_executarOnClick);

        spinner = findViewById(R.id.sp_label);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.label_pt_br_array, android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        progress = findViewById(R.id.progress);
        mMonitoringService = new MonitoringService(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private View.OnClickListener btn_executarOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            boolean status = mMonitoringService.execute();
            spinner.setEnabled(!status);
        }
    };

    @Override
    public void onSensorChanged(SensorEvent event) {
        mMonitoringService.SensorChanged(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void start() {
        btn_executar.setText(R.string.parar);
        progress.setVisibility(View.VISIBLE);
    }

    @Override
    public void stop() {
        btn_executar.setText(R.string.executar);
        progress.setVisibility(View.GONE);
        mMonitoringService.save(getLabel());
    }

    @Override
    public String getLabel() {
        String value = (String)spinner.getSelectedItem();
        return MovementsLabels.getLabelForDesc(value);
    }
}
