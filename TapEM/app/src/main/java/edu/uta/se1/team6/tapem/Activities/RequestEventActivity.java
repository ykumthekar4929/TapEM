package edu.uta.se1.team6.tapem.Activities;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import edu.uta.se1.team6.tapem.Adapters.VenuesAdapter;
import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Helpers.Utils;
import edu.uta.se1.team6.tapem.Models.EventDTO;
import edu.uta.se1.team6.tapem.Models.VenueModel;
import edu.uta.se1.team6.tapem.R;
import edu.uta.se1.team6.tapem.Services.RequestEventTask;

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
    private Button confirmAction, dateBox, fromBox, toBox;
    private EditText title, description;
    private EventDTO eventDTO = new EventDTO();
    private CheckBox breakfastOption, lunchOption, supperOption, alcoholOption;
    int fromYear, fromMonth, fromDayOfMonth, fromHour, fromMinute, toHour, toMinute;
    private TimePickerDialog fromTimePicker, toTimePicker;
    List<String> meals = new ArrayList<>();
    private VenueModel selectedVenue;
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
        breakfastOption = findViewById(R.id.breakfastOption);
        lunchOption = findViewById(R.id.lunchOption);
        supperOption = findViewById(R.id.supperOption);
        alcoholOption= findViewById(R.id.alcoholOption);


        venueSpinner.setAdapter(new VenuesAdapter(this, Utils.getVenues()));
//        venueSpinner.setSelection(0);
        venueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedVenue = Utils.getVenues().get(position);
                eventDTO.setLocation(selectedVenue.getName());

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        foodVenueSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Utils.getFoodTypes()));

        foodVenueSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                eventDTO.setMeal_type(Utils.getFoodTypes().get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        confirmAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calculateCosts();

                if (validateFields()) {
                    new RequestEventTask(eventDTO, new CallBack() {
                        @Override
                        public void onCallBack(Object object, String result) {
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
                    }).execute();
                }
            }
        });

        breakfastOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    meals.add("Breakfast");
                } else {
                    if (meals.contains("Breakfast")) {
                        meals.remove("Breakfast");
                    }
                }
                calculateCosts();
            }
        });

        lunchOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    meals.add("Lunch");
                } else {
                    if (meals.contains("Lunch")) {
                        meals.remove("Lunch");
                    }
                }
                calculateCosts();
            }
        });

        supperOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    meals.add("Supper");
                } else {
                    if (meals.contains("Supper")) {
                        meals.remove("Supper");
                    }
                }
                calculateCosts();            }
        });

        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);
        final int mHour = c.get(Calendar.HOUR_OF_DAY);
        final int mMin = c.get(Calendar.MINUTE);



        dateBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("====>", "onClick: ");
                DatePickerDialog datePickerDialog = new DatePickerDialog(RequestEventActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // dd.MM.yyyy
                        dateBox.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year);
                        fromYear = year;
                        fromMonth = monthOfYear;
                        fromDayOfMonth= dayOfMonth;
                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        fromBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog fromTimePicker = new TimePickerDialog(RequestEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        fromHour = hourOfDay;
                        fromMinute = minute;
                        fromBox.setText(hourOfDay + ":" + minute );
                        toBox.callOnClick();

                    }
                }, mHour + 2, mMin, true);
                fromTimePicker.setTitle("From");
                fromTimePicker.show();
            }
        });

        toBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog toTimePicker = new TimePickerDialog(RequestEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        toHour = hourOfDay;
                        toMinute = minute;
                        toBox.setText(hourOfDay + ":" + minute );
                    }
                }, fromHour + 2, fromMinute, true);
                toTimePicker.setTitle("To");
                toTimePicker.show();
            }
        });


        formalitySection.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                calculateCosts();
            }
        });

        alcoholOption.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                calculateCosts();
            }
        });


    }


    private String getStrDateTimeStamp(int year, int month, int dayOfMonth, int hourOfDay,
                                  int minute){

        Calendar cal = new GregorianCalendar(year, month, dayOfMonth, hourOfDay, minute);
        String date = DateFormat.format("yyyy-MM-dd hh:mm", cal).toString();
        return date;
    }

    private void calculateCosts(){

        int cost = 0;
        int multiple = 0;
        int numberofPeople = 0;
        int alcoholOptionValue = 0;
        int costOfHall = 0;

        Log.d("=>>", "venue: " + selectedVenue.getName() + " " + selectedVenue.getCapacity());
        numberofPeople = selectedVenue.getCapacity();
        costOfHall= selectedVenue.getCapacity() * selectedVenue.getCostPerCapacity();

        if (meals.contains("Breakfast")) {
            multiple = multiple + 8;
        }

        if (meals.contains("Lunch")) {
            multiple = multiple + 12;
        }

        if (meals.contains("Supper")) {
            multiple = multiple + 18;
        }
        boolean formalityOption = false;

        if (formalitySection.getCheckedRadioButtonId() == R.id.formalOption) {
            formalityOption = true;
            eventDTO.setMeal_formality("Formal");

        }else {
            eventDTO.setMeal_formality("Informal");
        }

        if (alcoholOption.isChecked()) {
            cost = numberofPeople * (multiple + alcoholOptionValue);
            eventDTO.setServing_alcohol(true);
        }else {
            eventDTO.setServing_alcohol(false);
        }

        if (formalityOption) {
            cost = (int) (cost * 1.5);

        }

        cost = cost + costOfHall;
        Log.d("==>>", "dets: "+multiple +" " + numberofPeople + " "+ alcoholOption + " " + costOfHall);
        Log.d("==>>", "hall cost: "+costOfHall);
        Log.d("==>>", "calculateCosts: "+cost);
        costView.setText("" + getString(R.string.estimated_cost) + " " + cost);
        eventDTO.setCost(cost);


    }
    private boolean validateFields() {
        boolean isValid = true;

        if (title.getText().toString().length() > 1) {
            eventDTO.setTitle(title.getText().toString());
        } else {
            isValid = false;
            title.setError("Please Check");
        }

        if (description.getText().toString().length() > 1) {
            eventDTO.setDescription(description.getText().toString());
        } else {
            isValid = false;
            description.setError("Please Check");
        }

        String fromDate = getStrDateTimeStamp(fromYear, fromMonth, fromDayOfMonth, fromHour, fromMinute);
        String toDate = getStrDateTimeStamp(fromYear, fromMonth, fromDayOfMonth, toHour, toMinute);
        eventDTO.setFrom_t_stamp(fromDate);
        eventDTO.setTo_t_stamp(toDate);

//        String meals = StringUtils.join(slist, ',');
        String mealsString = meals.toString().replace(", ", ",").replaceAll("[\\[.\\]]", "");
        eventDTO.setMeals(mealsString);
        eventDTO.setStatus("REQUESTED");
        eventDTO.setCreated_by_user(MainActivity.user.getId());
        return isValid;

    }
}
