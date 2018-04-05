package edu.uta.se1.team6.tapem.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import edu.uta.se1.team6.tapem.Adapters.VenuesAdapter;
import edu.uta.se1.team6.tapem.Helpers.Utils;
import edu.uta.se1.team6.tapem.R;

public class RequestEventActivity extends AppCompatActivity {

    private CoordinatorLayout rootComponent;
    private Toolbar toolbar;
    private NestedScrollView scrollView;
    private Spinner venueSpinner;
    private LinearLayout timeSection;
    private TextView mealsTitle;
    private Spinner foodVenueSpinner;
    private TextView formalityTitle;
    private RadioGroup formalitySection;
    private RadioButton informalOption;
    private RadioButton formalOption;
    private TextView costView;
    private TextView requestEventButton;
    private Button confirmAction;
    private EditText title, description, fromBox, toBox, dateBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_event);

        rootComponent = findViewById(R.id.root_component);
        toolbar = findViewById(R.id.toolbar);
        scrollView = findViewById(R.id.scrollView);
        venueSpinner = findViewById(R.id.venueSpinner);
        timeSection = findViewById(R.id.timeSection);
        mealsTitle = findViewById(R.id.meals_title);
        foodVenueSpinner = findViewById(R.id.foodVenueSpinner);
        formalityTitle = findViewById(R.id.formalityTitle);
        formalitySection = findViewById(R.id.formalitySection);
        informalOption = findViewById(R.id.informalOption);
        formalOption = findViewById(R.id.formalOption);
        costView = findViewById(R.id.costView);
        confirmAction = findViewById(R.id.confirmAction);
        requestEventButton = findViewById(R.id.requestEventButton);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        fromBox = findViewById(R.id.fromBox);
        toBox = findViewById(R.id.toBox);
        dateBox = findViewById(R.id.dateBox);


        venueSpinner.setAdapter(new VenuesAdapter(this, Utils.getVenues()));
        foodVenueSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Utils.getFoodTypes()));

        confirmAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(RequestEventActivity.this);
                alertDialogBuilder.setTitle("Event requested succesfully");
                alertDialogBuilder.setMessage("Your registration is succesful, you will be notified once the caterer plans accordingly!");
                alertDialogBuilder.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alertDialogBuilder.show();

            }
        });
    }
}
