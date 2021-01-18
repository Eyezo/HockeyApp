package com.example.eyezo.hockeyapp;

import android.app.Dialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

import dmax.dialog.SpotsDialog;

public class MedInfoUpdate extends AppCompatActivity {

    EditText edMedName, edMedPlan, edMedNumber, edAllergies, edParentOneCell, edParentTwoCell;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_info_update);

        edMedName = findViewById(R.id.edMedAidNameUpdate);
        edMedNumber = findViewById(R.id.edMedAidNumbUpdate);
        edMedPlan = findViewById(R.id.edMedAidPlanUpdate);
        edAllergies = findViewById(R.id.edPlayerAllergiesUpdate);
        edParentOneCell = findViewById(R.id.edParentCellNoUpdate);
        edParentTwoCell = findViewById(R.id.ed2ndParentCellUpdate);


        edMedName.setText(getIntent().getStringExtra("medName"));
        edMedNumber.setText(getIntent().getStringExtra("medNum"));
        edMedPlan.setText(getIntent().getStringExtra("medPlan"));
        edAllergies.setText(getIntent().getStringExtra("allergy"));
        edParentOneCell.setText(getIntent().getStringExtra("cell1"));
        edParentTwoCell.setText(getIntent().getStringExtra("cell2"));


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Add Player");
        actionBar.setIcon(R.mipmap.logo_2);
    }
    public void btnSaveMedInfoUpdate(View v)
    {
        /*
         * goes to backendless and finds the selected player and updates the medical info
         */
        dialog = new SpotsDialog.Builder().setContext(this).setTheme(R.style.Custom).build();
        dialog.show();
        String id = getIntent().getStringExtra("id_player");
        Backendless.Persistence.of(AddPlayer.class).findById(id, new AsyncCallback<AddPlayer>() {
            @Override
            public void handleResponse(AddPlayer response) {
                String medName = edMedName.getText().toString();
                String medPlan = edMedPlan.getText().toString();
                String medNumb = edMedNumber.getText().toString();
                String allergies = edAllergies.getText().toString();
                String parent1 = edParentOneCell.getText().toString();
                String parent2 = edParentTwoCell.getText().toString();

                response.setMedAidName(medName);
                response.setMedAidPlan(medPlan);
                response.setMedAidNumber(medNumb);
                response.setAllergies(allergies);
                response.setFirstParentNum(parent1);
                response.setSecondParentNum(parent2);

                Backendless.Persistence.save(response, new AsyncCallback<AddPlayer>() {
                    @Override
                    public void handleResponse(AddPlayer response) {
                        MedInfoUpdate.this.finish();
                        dialog.dismiss();

                        UtilityHelper.customToast(MedInfoUpdate.this, "Updated Successfully");
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        dialog.dismiss();
                        Toast.makeText(MedInfoUpdate.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                dialog.dismiss();
                Toast.makeText(MedInfoUpdate.this, "error "+ fault.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
