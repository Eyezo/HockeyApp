package com.example.eyezo.hockeyapp;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MedInformation extends AppCompatActivity {
    EditText edMedName, edMedPlan, edMedNumber, edAllergies, edParentOneCell, edParentTwoCell;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_med_information);

        edMedName = findViewById(R.id.edMedAidName);
        edMedNumber = findViewById(R.id.edMedAidNumb);
        edMedPlan = findViewById(R.id.edMedAidPlan);
        edAllergies = findViewById(R.id.edPlayerAllergies);
        edParentOneCell = findViewById(R.id.edParentCellNo);
        edParentTwoCell = findViewById(R.id.ed2ndParentCell);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setIcon(R.mipmap.logo_2);
        actionBar.setTitle("Med Info");
    }
    public void btnSaveMedInfo(View v)
    {
        /*
         * saves the players medical info and returns RESULT_OK the AddPlayer activity
         */
        String medName = edMedName.getText().toString().trim();
        String medPlan = edMedPlan.getText().toString().trim();
        String medNumb = edMedNumber.getText().toString().trim();
        String allergies = edAllergies.getText().toString().trim();
        String parent1 = edParentOneCell.getText().toString().trim();
        String parent2 = edParentTwoCell.getText().toString().trim();

        if(medName.isEmpty() || medPlan.isEmpty() || medNumb.isEmpty() || allergies.isEmpty()||
                parent1.isEmpty() || parent2.isEmpty())
        {

            UtilityHelper.customToast(this, "Please enter all fields");

        }
        else
        {
            Intent intent = new Intent();
            intent.putExtra("medName", medName);
            intent.putExtra("medPlan", medPlan);
            intent.putExtra("medNumb", medNumb);
            intent.putExtra("allergies",allergies);
            intent.putExtra("parent1", parent1);
            intent.putExtra("parent2",parent2);
            setResult(RESULT_OK, intent);
            MedInformation.this.finish();
        }
    }
}
