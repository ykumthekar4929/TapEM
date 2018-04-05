package edu.uta.se1.team6.tapem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import edu.uta.se1.team6.tapem.Helpers.Utils;
import edu.uta.se1.team6.tapem.Models.UserDTO;
import edu.uta.se1.team6.tapem.R;

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

                Intent intent = new Intent(LoginActivity.this, MainActivity.class);

                UserDTO user;

                if (id_textField.getText().toString().equals("1010101010")) {
                    user = Utils.initiateDummy("1010101010", getString(R.string.user_type_admin));
                    intent.putExtra(getString(R.string.user_data_key), user);

                } else if (id_textField.getText().toString().equals("2020202020")){
                    user = Utils.initiateDummy("2020202020", getString(R.string.user_type_general));
                    intent.putExtra(getString(R.string.user_data_key), user);

                } else if (id_textField.getText().toString().equals("3030303030")) {
                    user = Utils.initiateDummy("3030303030", getString(R.string.user_type_caterer));
                    intent.putExtra(getString(R.string.user_data_key), user);

                } else if (id_textField.getText().toString().equals("4040404040")) {
                    user = Utils.initiateDummy("4040404040", getString(R.string.user_type_caterer_staff));
                    intent.putExtra(getString(R.string.user_data_key), user);


                }
//                    user = Utils.initiateDummy("1010101010", getString(R.string.user_type_caterer));
//                    Gson gson = new Gson();
//                    String json = gson.toJson(user);
//                    Log.d("User", "Initiate: " + json);
//                    Utils.saveSharedPrefs(LoginActivity.this, getString(R.string.user_data_key), json);

                startActivity(intent);
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

