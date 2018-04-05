package edu.uta.se1.team6.tapem.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Objects;

import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Helpers.DialogUtils;
import edu.uta.se1.team6.tapem.Models.EventDTO;
import edu.uta.se1.team6.tapem.R;

public class EventDetailsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private Button cancelAction;
    private Button planAction;
    private EventDTO eventDetails;
    private TextView cancelledMessage;
    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);
        toolbar = findViewById(R.id.toolbar);
        cancelAction = findViewById(R.id.cancelAction);
        planAction = findViewById(R.id.planAction);
        cancelledMessage = findViewById(R.id.cancelledMessage);
        title = findViewById(R.id.title);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().hasExtra(getString(R.string.event_details_key))) {
            eventDetails = (EventDTO) getIntent().getSerializableExtra(getString(R.string.event_details_key));
            title.setText(eventDetails.getName());
        }

        if (eventDetails.getStatus().equals(getString(R.string.event_active))) {
            renderActiveUI();
        } else if (eventDetails.getStatus().equals(getString(R.string.event_cancelled))){
            renderCancelledUI();
        } else if (eventDetails.getStatus().equals(getString(R.string.event_requested))) {
            renderReqUI();
        }
    }

    private void renderActiveUI(){
        cancelledMessage.setVisibility(View.GONE);
        cancelAction.setVisibility(View.VISIBLE);
        planAction.setVisibility(View.GONE);
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
                            if (Objects.equals(item.getName(), eventDetails.getName())) {
                                item.setStatus(getString(R.string.event_cancelled));
                            }
                        }
                    }
                });
            }
        });
        if (MainActivity.user.getType().equals(getString(R.string.user_type_caterer_staff)) || MainActivity.user.getType().equals(getString(R.string.user_type_caterer))) {
            cancelAction.setVisibility(View.GONE);
        }
    }

    private void renderCancelledUI(){
        cancelledMessage.setVisibility(View.VISIBLE);
        cancelAction.setVisibility(View.GONE);
        planAction.setVisibility(View.GONE);

    }

    private void renderReqUI(){
        cancelledMessage.setVisibility(View.GONE);
        cancelAction.setVisibility(View.GONE);
        planAction.setVisibility(View.VISIBLE);
        planAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventDetailsActivity.this, AssignStaffActivity.class);
                startActivityForResult(intent, 1001);
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001) {
            if (resultCode == Activity.RESULT_OK) {
                eventDetails.setStatus(getString(R.string.event_active));
                renderActiveUI();
            }
        }
    }
}
