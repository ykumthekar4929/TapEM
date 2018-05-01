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
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Helpers.Utils;
import edu.uta.se1.team6.tapem.Models.UserDTO;
import edu.uta.se1.team6.tapem.R;
import edu.uta.se1.team6.tapem.Services.RegisterTask;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar toolBar;
    private TextView birthdaySelector, signInLink;
    private RadioButton optionMale;
    private RadioButton optionFemale;
    private Spinner planetsSpinner;
    private EditText fName_textField, lName_textField, email_textField, id_textField, password_textField;
    private Button register_button;
    private RadioGroup sexGroup;
    DatePickerDialog birthdayPicker;
    UserDTO userDTO = new UserDTO();
    String role = null;
    String bd_bup = null;

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
        email_textField = findViewById(R.id.email_textField);
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
//        birthdayPicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener()) {
//            @Override
//            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                String bdayString = "Birtday: " + month + " - " + dayOfMonth + " - " + year;
//                birthdaySelector.setText(bdayString);
//                birthdayPicker.hide();
//                Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
//                String date = DateFormat.format("dd-MM-yyyy hh:mm:ss", cal).toString();
//
//            }
//        });

        birthdayPicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String bdayString = "Birtday: " + month + " - " + dayOfMonth + " - " + year;
                birthdaySelector.setText(bdayString);
                Calendar cal = new GregorianCalendar(year, month, dayOfMonth);
                String date = DateFormat.format("yyyy-MM-dd hh:mm", cal).toString();
                Log.d("Birth date", "onDateSet: "+date);
                bd_bup = date;
//                userDTO.setBirthDate(date);
                birthdayPicker.hide();
            }
        });

        birthdaySelector.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                birthdayPicker.show();
            }
        });



        final List<String> roles = new ArrayList<>();
        roles.add(0, "Please select a role");
        roles.addAll(Utils.getUserTypes());
        planetsSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, roles));



//        planetsSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                role = roles.get(position);
//            }
//        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!validateFields()) {

                    Log.d("Register", "User DTO: " + new Gson().toJson(userDTO));
                    Log.d("=>", "birtdate: "+userDTO.getBirth_date());

                    new RegisterTask(userDTO, new CallBack() {
                        @Override
                        public void onCallBack(Object object, String result) {
                            Log.d("Result of Register", "onCallBack: " + object.toString());
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
//
                        }
                    }).execute();

                }

            }
        });

    }
    private boolean validateFields(){
        boolean hasError = false;


        if (fName_textField.getText().toString().length() > 0) {
            userDTO.setFirstName(fName_textField.getText().toString());

        } else {
            hasError = true;
            fName_textField.setError("Please check");
        }

        if (lName_textField.getText().toString().length() > 0) {
            userDTO.setLastName(lName_textField.getText().toString());

        } else {
            hasError = true;
            lName_textField.setError("Please check");
        }

        if (id_textField.getText().toString().length() > 0) {
            userDTO.setMavID(id_textField.getText().toString());
        } else {
            hasError = true;
            id_textField.setError("Please check");
        }

        int sex_id = sexGroup.getCheckedRadioButtonId();

        if (sex_id == R.id.option_male) {
            userDTO.setSex("Male");
        } else if(sex_id == R.id.option_female) {
            userDTO.setSex("Female");
        }

        if (password_textField.getText().toString().length() > 4) {
            userDTO.setPassword(password_textField.getText().toString());
        } else {
            hasError = true;
            password_textField.setError("Please check, must be greater than 4");
        }

        if (email_textField.getText().toString().length() > 4) {
            userDTO.setEmail_id(email_textField.getText().toString());
        } else {
            hasError = true;
            email_textField.setError("Please check");
        }

        if (!planetsSpinner.getSelectedItem().equals("Please select a role")) {
            role = planetsSpinner.getSelectedItem().toString().toUpperCase();
            userDTO.setRole(role);
        } else {
            hasError = true;
            Toast.makeText(RegisterActivity.this, "Please choose a role", Toast.LENGTH_SHORT).show();
        }

        if (bd_bup != null) {
            Log.d("bdbup", "bdbup: " + bd_bup);
            userDTO.setBirth_date(bd_bup);

        } else {
            hasError = true;
            Toast.makeText(RegisterActivity.this, "Please choose birthdate", Toast.LENGTH_SHORT).show();
        }

        userDTO.setStatus("REQUESTED");
        return hasError;

    }




}
