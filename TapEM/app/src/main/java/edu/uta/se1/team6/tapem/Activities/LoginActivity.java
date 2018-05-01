package edu.uta.se1.team6.tapem.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Models.UserDTO;
import edu.uta.se1.team6.tapem.R;
import edu.uta.se1.team6.tapem.Services.UserLoginTask;

public class LoginActivity extends AppCompatActivity {

    TextView registerLink;
    Button loginButton;
    EditText id_textField, password_textField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        registerLink = findViewById(R.id.registerLink);
        loginButton = findViewById(R.id.loginBtn);
        id_textField = findViewById(R.id.id_textField);
        password_textField = findViewById(R.id.password_textField);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (validateInputs(id_textField, password_textField)) {
                new UserLoginTask(id_textField.getText().toString(),
                    password_textField.getText().toString(),
                    new CallBack() {
                        @Override
                        public void onCallBack(Object object, String result) {
                            Log.d("==>", "onCallBack: " + object.getClass());
                            UserDTO user = (UserDTO) object;
                            if (user.getError() != null) {
                                if (user.getError().equals("Not Authorized to login")) {
                                    Log.d("Result of Register", "onCallBack: " + object.toString());
                                    android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(LoginActivity.this);
                                    alertDialogBuilder.setTitle("Unable to login");
                                    alertDialogBuilder.setMessage("Seems like your request has not yet been accepted by the admin, please try again later");
                                    alertDialogBuilder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                                    alertDialogBuilder.show();
                                }else if (user.getError().equals("Wrong password")) {
                                    password_textField.setError("Wrong password");
                                }
                            } else {
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.putExtra(getString(R.string.user_data_key), user);
                                startActivity(intent);
                            }
                        }
                    }).execute();
            }
            }
        });
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean validateInputs(EditText id_textField, EditText password_textField) {
        boolean valid = true;
        if (id_textField.getText().toString().length() != 10) {
            id_textField.setError("Please check the user ID");
            valid = false;
        }
        if (password_textField.getText().toString().length() <= 4) {
            password_textField.setError("Please check the password");
            valid = false;
        }
        return valid;
    }
}

