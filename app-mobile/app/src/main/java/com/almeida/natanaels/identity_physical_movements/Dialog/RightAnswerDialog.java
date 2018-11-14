package com.almeida.natanaels.identity_physical_movements.Dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.almeida.natanaels.identity_physical_movements.Entities.MovementsLabels;
import com.almeida.natanaels.identity_physical_movements.Firebase.Storage;
import com.almeida.natanaels.identity_physical_movements.Interfaces.Classifier;
import com.almeida.natanaels.identity_physical_movements.R;

import java.util.Map;
import java.util.Objects;

@SuppressLint("ValidFragment")
public class RightAnswerDialog extends AppCompatDialogFragment {

    private Spinner sp_rotulo;

    private Storage mStorage = new Storage();
    private final Map<String, Object> mValues;

    private Classifier mClassifier;

    public RightAnswerDialog(Map<String, Object> values, Classifier classifier) {
        super();
        this.mValues = values;
        this.mClassifier = classifier;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_right_answer, container, false);

        Button btn_salvar = view.findViewById(R.id.btn_salvar);
        Button btn_cancelar = view.findViewById(R.id.btn_cancelar);

        sp_rotulo = view.findViewById(R.id.sp_label);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                Objects.requireNonNull(getContext()), R.array.label_en_us_array,
                android.R.layout.simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_rotulo.setAdapter(adapter);

        btn_salvar.setOnClickListener(btnSalvar_OnClick());
        btn_cancelar.setOnClickListener(btnCancelar_OnClick());

        return view;
    }

    private View.OnClickListener btnSalvar_OnClick() {
        return new View.OnClickListener() {
            @SuppressLint("ShowToast")
            @Override
            public void onClick(View v) {
                if(mValues != null) {
                    String label = movement();
                    mValues.put("label", label);
                    mStorage.save(label + "_test", mValues);
                    mStorage.classification( label + "negative");
                    Toast.makeText(getContext(), "Resposta salva!", Toast.LENGTH_SHORT);
                    mClassifier.statusButtons(false);
                    dismiss();
                }
            }
        };
    }

    public String movement(){
        String value = (String)sp_rotulo.getSelectedItem();
        return MovementsLabels.getLabelForDesc(value);
    }

    private View.OnClickListener btnCancelar_OnClick(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        };
    }
}