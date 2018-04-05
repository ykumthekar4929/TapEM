package edu.uta.se1.team6.tapem.Activities;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Helpers.DialogUtils;
import edu.uta.se1.team6.tapem.Helpers.Utils;
import edu.uta.se1.team6.tapem.Models.UserDTO;
import edu.uta.se1.team6.tapem.R;

public class ProfileActivity extends AppCompatActivity {

    private UserDTO user;

    private Toolbar toolBar;
    private TextView birthdaySelector, signInLink;
    private RadioButton optionMale;
    private RadioButton optionFemale;
    private Spinner typeSpinner;
    private EditText fName_textField, lName_textField, id_textField, password_textField, new_password_textField, confirm_password_textField;
    private Button register_button, deleteButton;
    private RadioGroup sexGroup;
    private DatePickerDialog birthdayPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        toolBar = findViewById(R.id.toolBar);
        birthdaySelector = findViewById(R.id.birthdaySelector);
        optionMale = findViewById(R.id.option_male);
        optionFemale = findViewById(R.id.option_female);
        typeSpinner = findViewById(R.id.typeSpinner);
        fName_textField = findViewById(R.id.fName_textField);
        lName_textField = findViewById(R.id.lName_textField);
        id_textField = findViewById(R.id.id_textField);
        password_textField = findViewById(R.id.password_textField);
        new_password_textField = findViewById(R.id.new_password_textField);
        confirm_password_textField = findViewById(R.id.conf_password_textField);
        sexGroup = findViewById(R.id.sexGroup);
        deleteButton = findViewById(R.id.deleteButton);

        toolBar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.vector_drawable_ic_arrow_back___px));
        toolBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().hasExtra(getString(R.string.user_data_key))) {
            user = (UserDTO) getIntent().getSerializableExtra(getString(R.string.user_data_key));
        }

        boolean isAdmin;
        isAdmin = getIntent().getBooleanExtra(getString(R.string.is_admin_key), false);
        if (isAdmin) {
            renderAdminUI();
        } else {
            renderUserUI();
        }

        fName_textField.setText(user.getFirstName());
        lName_textField.setText(user.getLastName());

        id_textField.setText(user.getMavID());
        id_textField.setEnabled(false);
        typeSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Utils.getUserTypes()));

    }

    private void renderAdminUI(){
        new_password_textField.setVisibility(View.GONE);
        password_textField.setVisibility(View.GONE);
        confirm_password_textField.setVisibility(View.GONE);
        typeSpinner.setEnabled(true);
        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        deleteButton.setVisibility(View.VISIBLE);

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.AlertDialog(ProfileActivity.this, getString(R.string.delete_user_confirm_mesg), new CallBack() {
                    @Override
                    public void onCallBack(Object object, String result) {
                        Toast.makeText(ProfileActivity.this, "User deleted", Toast.LENGTH_SHORT).show();
                        finish();

                    }
                });
            }
        });
    }

    private void renderUserUI(){
        password_textField.setHint(R.string.old_passowrd);
        new_password_textField.setVisibility(View.VISIBLE);
        typeSpinner.setEnabled(false);
        deleteButton.setVisibility(View.GONE);
    }


    private boolean isViewingAsAdmin(UserDTO user){
        boolean decs = true;
        if (user.getType().equals(getString(R.string.user_type_admin))) {
            if (user.getMavID().equals(MainActivity.user.getMavID())) {
                decs = false;
            }
        } else {
            decs = false;
        }
        return decs;
    }

    private void defaultSexSelection(UserDTO user){
        if (user.getSex().equals(getString(R.string.male))) {
            optionMale.setSelected(true);
        } else {
            optionFemale.setSelected(true);
        }

    }
}
