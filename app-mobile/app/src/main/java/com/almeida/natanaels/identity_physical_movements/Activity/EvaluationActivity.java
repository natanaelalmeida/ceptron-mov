package com.almeida.natanaels.identity_physical_movements.Activity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.almeida.natanaels.identity_physical_movements.Dialog.RightAnswerDialog;
import com.almeida.natanaels.identity_physical_movements.Firebase.Storage;
import com.almeida.natanaels.identity_physical_movements.Interfaces.Classifier;
import com.almeida.natanaels.identity_physical_movements.Interfaces.Monitoring;
import com.almeida.natanaels.identity_physical_movements.R;
import com.almeida.natanaels.identity_physical_movements.Sensors.MonitoringService;

public class EvaluationActivity extends AppCompatActivity implements SensorEventListener,
        Classifier, Monitoring {

    private Storage mStorage = new Storage();
    private MonitoringService mMonitoringService;

    private Button btn_executar;
    private Button btn_sim;
    private Button btn_nao;

    private TextView txt_resposta;
    private LinearLayout progress;

    private String mLabel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitivty_evaluation);

        btn_nao = findViewById(R.id.btn_no);
        btn_nao.setOnClickListener(btnNao_OnClick());

        btn_sim = findViewById(R.id.btn_yes);
        btn_sim.setOnClickListener(btnSim_OnClick());

        txt_resposta = findViewById(R.id.txt_answer);
        progress = findViewById(R.id.progress);

        btn_executar = findViewById(R.id.btn_run);
        btn_executar.setOnClickListener(btnExecutar_OnClick());

        mMonitoringService = new MonitoringService(this);
    }

    private View.OnClickListener btnNao_OnClick() {
        final Classifier classifier = this;
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                Fragment frag = getSupportFragmentManager().findFragmentByTag("dialog");

                if(frag != null) {
                    ft.remove(frag);
                }

                RightAnswerDialog dialog = new RightAnswerDialog(mMonitoringService.getValues(),
                        classifier);
                dialog.show(ft, "dialog");
            }
        };
    }


    private View.OnClickListener btnSim_OnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStorage.classification(mLabel + "positive");
                statusButtons(false);
            }
        };
    }

    private View.OnClickListener btnExecutar_OnClick() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              mMonitoringService.execute();
            }
        };
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
       mMonitoringService.SensorChanged(event);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void classification(String label, int audio) {
        if(label == null || label.isEmpty())
            label = "Sem resposta";

        mLabel = label;
        txt_resposta.setText(String.format("Você está %s", label));
        MediaPlayer.create(this, audio).start();
        statusButtons(true);

    }

    @Override
    public void loading(int visibility) {
        progress.setVisibility(visibility);
    }

    @Override
    public void statusButtons(boolean status) {
        btn_sim.setEnabled(status);
        btn_nao.setEnabled(status);
    }

    @Override
    public void start() {
        btn_executar.setText(R.string.parar);
        txt_resposta.setText("");
        statusButtons(false);
    }

    @Override
    public void stop() {
        btn_executar.setText(R.string.executar);
        mMonitoringService.classifier(this);
    }

    @Override
    public String getLabel() {
        return null;
    }
}