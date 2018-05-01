package edu.uta.se1.team6.tapem.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Objects;

import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Helpers.DialogUtils;
import edu.uta.se1.team6.tapem.Helpers.Utils;
import edu.uta.se1.team6.tapem.Models.EventDTO;
import edu.uta.se1.team6.tapem.Models.VenueModel;
import edu.uta.se1.team6.tapem.R;
import edu.uta.se1.team6.tapem.Services.GetEventData;
import edu.uta.se1.team6.tapem.Services.UpdateEventTask;

public class EventDetailsActivity extends AppCompatActivity {
    private CoordinatorLayout rootComponent;
    private Toolbar toolbar;
    private NestedScrollView scrollView;
    private RelativeLayout contentHolder;
    private TextView title;
    private TextView description;
    private TextView dateData;
    private TextView locationData;
    private TextView detailsTitle;
    private LinearLayout capacitySection;
    private LinearLayout foodSection;
    private LinearLayout catererSection;
    private TextView cancelledMessage;
    private EventDTO eventDetails;
    private Button cancelAction, planAction;
    private TextView catererEmail, catererName, formalityDesc, cuisine, capacityCount, meal_types, alcoholOption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        rootComponent = findViewById(R.id.root_component);
        toolbar = findViewById(R.id.toolbar);
        scrollView = findViewById(R.id.scrollView);
        contentHolder = findViewById(R.id.contentHolder);
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        dateData = findViewById(R.id.dateData);
        locationData = findViewById(R.id.locationData);
        detailsTitle = findViewById(R.id.detailsTitle);
        capacitySection = findViewById(R.id.capacitySection);
        foodSection = findViewById(R.id.foodSection);
        catererSection = findViewById(R.id.catererSection);
        cancelAction = findViewById(R.id.cancelAction);
        planAction = findViewById(R.id.planAction);
        cancelledMessage = findViewById(R.id.cancelledMessage);
        catererEmail = findViewById(R.id.catererEmail);
        catererName = findViewById(R.id.catererName);
        formalityDesc = findViewById(R.id.formalityDesc);
        cuisine = findViewById(R.id.cuisine);
        capacityCount = findViewById(R.id.capacityCount);
        meal_types = findViewById(R.id.meal_types);
        alcoholOption = findViewById(R.id.alcoholOption);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().hasExtra(getString(R.string.event_details_key))) {

            eventDetails = (EventDTO) getIntent().getSerializableExtra(getString(R.string.event_details_key));

            String event_id = eventDetails.getEvent();
            new GetEventData(event_id, new CallBack() {
                @Override
                public void onCallBack(Object object, String result) {
                    eventDetails = (EventDTO) object;
                    catererName.setText(eventDetails.getCaterer_name());
                    catererEmail.setText(eventDetails.getCaterer_email());
                }
            }).execute();
        }

        if (eventDetails.getStatus().equals(getString(R.string.event_active))) {
            renderActiveUI();
        } else if (eventDetails.getStatus().equals(getString(R.string.event_cancelled))){
            renderCancelledUI();
        } else if (eventDetails.getStatus().equals(getString(R.string.event_requested))) {
            renderReqUI();
        }

        formalityDesc.setText(eventDetails.getMeal_formality());
        cuisine.setText(eventDetails.getMeal_type());
        meal_types.setText(eventDetails.getMeals());
        capacityCount.setText(getCapacity(eventDetails.getLocation()));

    }

    private String getCapacity(String location) {
        int capacity = 0;
        List<VenueModel> venues = Utils.getVenues();
        for (VenueModel model: venues) {
            if (model.getName().equals(location)) {
                capacity = model.getCapacity();
            }
        }
        return String.valueOf(capacity);
    }

    private void renderActiveUI(){
        cancelledMessage.setVisibility(View.GONE);
        cancelAction.setVisibility(View.VISIBLE);
        planAction.setVisibility(View.GONE);
        if (MainActivity.user.getRole().equalsIgnoreCase(getString(R.string.user_type_caterer_staff)) || MainActivity.user.getRole().equals(getString(R.string.user_type_caterer))) {
            cancelAction.setVisibility(View.GONE);
        }
        cancelAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.AlertDialog(EventDetailsActivity.this, getString(R.string.dialog_prompt_cancel_event), new CallBack() {
                    @Override
                    public void onCallBack(Object object, String result) {
                        //Process here
                        eventDetails.setStatus(getString(R.string.event_cancelled));
                        cancelledMessage.setVisibility(View.VISIBLE);
                        cancelAction.setVisibility(View.GONE);
                        for (EventDTO item : MainActivity.eventsList) {
                            if (Objects.equals(item.getTitle(), eventDetails.getTitle())) {
                                item.setStatus(getString(R.string.event_cancelled));
                            }
                        }
                        updateToServer(eventDetails, "CANCELLED");

                    }
                });
            }
        });
    }

    private void renderCancelledUI(){
        cancelledMessage.setVisibility(View.VISIBLE);
        cancelAction.setVisibility(View.GONE);
        planAction.setVisibility(View.GONE);

    }

    private void renderReqUI(){
        cancelledMessage.setVisibility(View.GONE);
        cancelAction.setVisibility(View.GONE);
        planAction.setVisibility(View.GONE);

        if (MainActivity.user.getRole().equalsIgnoreCase(getString(R.string.user_type_caterer))) {
            planAction.setVisibility(View.VISIBLE);
            planAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(EventDetailsActivity.this, AssignStaffActivity.class);
                    startActivityForResult(intent, 1001);
                }
            });
        }

        if (MainActivity.user.getRole().equalsIgnoreCase(getString(R.string.user_type_general))) {
            cancelAction.setVisibility(View.VISIBLE);
            cancelAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateToServer(eventDetails, "CANCELLED");
                    cancelledMessage.setVisibility(View.VISIBLE);
                    cancelAction.setVisibility(View.GONE);
                }
            });
        }

        description.setText(eventDetails.getDescription());

        DateTime from_dt = new DateTime(eventDetails.getFrom_t_stamp());
        DateTime to_dt = new DateTime(eventDetails.getTo_t_stamp());
        DateTimeFormatter fmt = DateTimeFormat.forPattern("a");
        DateTimeFormatter fmt2 = DateTimeFormat.forPattern("M");
        String month = fmt2.print(from_dt);
        int date_string = from_dt.getDayOfMonth();
        int from_string = from_dt.getHourOfDay();
        String from_a = fmt.print(from_dt);
        int toStrinng = to_dt.getHourOfDay();
        String to_a = fmt.print(to_dt);
        String date_display = getMonthName(month) + " " +date_string + ", "+ from_string + " " + from_a + " to " + toStrinng + " " + to_a;
        dateData.setText(date_display);
        title.setText(eventDetails.getTitle());
        locationData.setText(eventDetails.getLocation());

        if (eventDetails.isServing_alcohol()) {
            alcoholOption.setText("Serving alcohol");
        } else {
            alcoholOption.setText("Not serving alcohol");

        }
    }

    private String getMonthName(String monthNumber){

        String month = "";
        switch (Integer.parseInt(monthNumber)) {
            case 1:
                month = "Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month = "Mar";
                break;
            case 4:
                month = "Apr";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month = "Jun";
                break;
            case 7:
                month = "Jul";
                break;
            case 8:
                month = "Aug";
                break;
            case 9:
                month = "Sep";
                break;
            case 10:
                month = "Oct";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month = "Dec";
                break;

        }
        return month;

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                eventDetails.setStatus(getString(R.string.event_active));
                renderActiveUI();
                updateToServer(eventDetails, "ACTIVE");
            }
        }
    }

    private void updateToServer(EventDTO eventDetails, String status) {
        new UpdateEventTask(Integer.parseInt(eventDetails.getEvent()), status, new CallBack() {
            @Override
            public void onCallBack(Object object, String result) {
                Toast.makeText(EventDetailsActivity.this, "Event Updated", Toast.LENGTH_SHORT).show();
            }
        }).execute();
    }
}
