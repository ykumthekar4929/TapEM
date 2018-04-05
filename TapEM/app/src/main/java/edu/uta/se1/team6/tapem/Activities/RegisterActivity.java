package edu.uta.se1.team6.tapem.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import edu.uta.se1.team6.tapem.Helpers.Utils;
import edu.uta.se1.team6.tapem.R;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private TextView birthdaySelector, signInLink;
    private RadioButton optionMale;
    private RadioButton optionFemale;
    private Spinner planetsSpinner;
    private EditText fName_textField, lName_textField, id_textField, password_textField;
    private Button register_button;
    private RadioGroup sexGroup;
    DatePickerDialog birthdayPicker;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        toolBar = findViewById(R.id.toolBar);
        birthdaySelector = findViewById(R.id.birthdaySelector);
        optionMale = findViewById(R.id.option_male);
        optionFemale = findViewById(R.id.option_female);
        planetsSpinner = findViewById(R.id.typeSpinner);
        fName_textField = findViewById(R.id.fName_textField);
        lName_textField = findViewById(R.id.lName_textField);
        id_textField = findViewById(R.id.id_textField);
        password_textField = findViewById(R.id.password_textField);
        register_button= findViewById(R.id.register_button);
        sexGroup = findViewById(R.id.sexGroup);
        signInLink = findViewById(R.id.signIn_link);

        signInLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        birthdayPicker = new DatePickerDialog(this);
        birthdayPicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String bdayString = "Birtday: " + month + " - " + dayOfMonth + " - " + year;
                birthdaySelector.setText(bdayString);
                birthdayPicker.hide();
            }
        });

        birthdaySelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthdayPicker.show();
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RegisterActivity.this);
                alertDialogBuilder.setTitle("Registration successful");
                alertDialogBuilder.setMessage("Your registration is succesful, you will be notified once the admin approves you!");
                alertDialogBuilder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alertDialogBuilder.show();
            }
        });

        List<String> roles = new ArrayList<>();
        roles.add(0, "Please select a role");
        roles.addAll(Utils.getUserTypes());
        planetsSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roles));


    }



}
