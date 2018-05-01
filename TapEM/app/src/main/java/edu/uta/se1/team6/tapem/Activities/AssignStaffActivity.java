package edu.uta.se1.team6.tapem.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Helpers.DialogUtils;
import edu.uta.se1.team6.tapem.Models.UserDTO;
import edu.uta.se1.team6.tapem.R;
import edu.uta.se1.team6.tapem.Services.GetCatererStaffTask;

public class AssignStaffActivity extends BaseActivity {

    private CoordinatorLayout rootComponent;
    private AppBarLayout appBarLayout;
    private Toolbar toolbar;
    private ListView staffRecycler;
    private Button assignButton;
    private List<String> assignedRs = new ArrayList<>();
    private List<String> availablesStaff = new ArrayList<>();
    List<UserDTO> staffs = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assign_staff);
        rootComponent = findViewById(R.id.root_component);
        appBarLayout = findViewById(R.id.appBarLayout);
        toolbar = findViewById(R.id.toolbar);
        staffRecycler = findViewById(R.id.staffRecycler);
        assignButton = findViewById(R.id.assignButton);
        configToolbar();

        new GetCatererStaffTask(new CallBack() {
            @Override
            public void onCallBack(Object object, String result) {
                staffs = new ArrayList<>(Arrays.asList((UserDTO[]) object));

                for (UserDTO model:staffs) {
                    availablesStaff.add(model.getFirstName() + " " + model.getLastName());
                }
                staffRecycler.setAdapter(new ArrayAdapter<String>(AssignStaffActivity.this, android.R.layout.simple_list_item_checked, availablesStaff));
                staffRecycler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CheckedTextView currentBox = (CheckedTextView) view;
                        currentBox.setChecked(true);
                        assignedRs.add(availablesStaff.get(position));
                    }
                });
            }
        }).execute();

        assignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (assignedRs.size() > 0) {
                    returnWithResult();
                } else {
                    noSelectedExit();
                }
            }
        });
    }

    private void configToolbar() {
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noSelectedExit();
            }
        });
    }

    private void returnWithResult(){
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK,returnIntent);
        finish();
    }

    private void noSelectedExit(){
        DialogUtils.AlertDialog(this, "Are you sure you want to go cancel this operation?", new CallBack() {
            @Override
            public void onCallBack(Object object, String result) {
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_CANCELED, returnIntent);
                finish();
            }
        });
    }
}