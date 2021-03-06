package edu.uta.se1.team6.tapem.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.uta.se1.team6.tapem.Adapters.EventAdapter;
import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Helpers.Utils;
import edu.uta.se1.team6.tapem.Models.EventDTO;
import edu.uta.se1.team6.tapem.Models.UserDTO;
import edu.uta.se1.team6.tapem.R;
import edu.uta.se1.team6.tapem.Services.GetActiveUsersTask;
import edu.uta.se1.team6.tapem.Services.GetEventsByStatusTask;
import edu.uta.se1.team6.tapem.Services.GetEventsTask;
import edu.uta.se1.team6.tapem.Services.GetRequestedUsersTask;

public class MainActivity extends BaseActivity {


    private DrawerLayout drawerLayout;
    private NavigationView navMenu;
    private CoordinatorLayout rootComponent;
    private Toolbar toolbar;
    public static UserDTO user;
    private RecyclerView eventsRecycler;
    private TextView requestEventButton;
    private Button newRegsBtn, browseUsersBtn;
    private LinearLayout adminPanel;
    public static List<EventDTO> eventsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = findViewById(R.id.drawer_layout);
        navMenu = findViewById(R.id.nav_menu);
        rootComponent = findViewById(R.id.root_component);
        toolbar = findViewById(R.id.toolbar);
        eventsRecycler = findViewById(R.id.eventsRecycler);
        requestEventButton = findViewById(R.id.requestEventButton);
        newRegsBtn = findViewById(R.id.newRegsBtn);
        browseUsersBtn = findViewById(R.id.browseUsersBtn);
        adminPanel = findViewById(R.id.adminPanel);

        user = (UserDTO) getIntent().getSerializableExtra(getString(R.string.user_data_key));
        configureToolbar();
        configureNavMenu();

        if (user.getRole().equalsIgnoreCase(getString(R.string.user_type_admin))) {
            renderAdminUI();
        } else if (user.getRole().equalsIgnoreCase(getString(R.string.user_type_general))) {
            renderGeneralUserUI();
        } else if (user.getRole().equalsIgnoreCase(getString(R.string.user_type_caterer))) {
            renderCatererUI();
        } else if (user.getRole().equalsIgnoreCase(getString(R.string.user_type_caterer_staff))) {
            renderStaffUI();
        }
    }

    private void configureNavMenu() {

        View headerview = navMenu.getHeaderView(0);
        RelativeLayout navHeaderRoot= headerview.findViewById(R.id.navHeaderRoot);
        navHeaderRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                intent.putExtra(getString(R.string.user_data_key), user);
                intent.putExtra(getString(R.string.is_admin_key), false);
                startActivity(intent);
            }
        });
        TextView logoutButton = navMenu.findViewById(R.id.logoutButton);

        TextView userName = headerview.findViewById(R.id.userName);
        String user_fullName = user.getFirstName() + " " + user.getLastName();
        userName.setText(user_fullName);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void configureToolbar(){
        toolbar.setNavigationIcon(ContextCompat.getDrawable(this, R.drawable.menu));
        toolbar.setTitle(getApplicationName(this));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(Gravity.START, true);
                String userCheck = Utils.readSharedPrefs(MainActivity.this, getString(R.string.user_data_key));
                Log.d("Splash", "userCheck: " + userCheck);
            }
        });
    }

    private void renderAdminUI(){
        requestEventButton.setVisibility(View.GONE);
        eventsRecycler.setVisibility(View.GONE);
        adminPanel.setVisibility(View.VISIBLE);
        newRegsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetRequestedUsersTask(new CallBack() {
                    @Override
                    public void onCallBack(Object object, String result) {
                        goToUsersList(new ArrayList<UserDTO>(Arrays.asList((UserDTO[]) object)));
                    }
                }).execute();
            }
        });
        browseUsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetActiveUsersTask(new CallBack() {
                    @Override
                    public void onCallBack(Object object, String result) {
                        goToUsersList(new ArrayList<UserDTO>(Arrays.asList((UserDTO[]) object)));
                    }
                }).execute();
            }
        });
    }

    private void renderGeneralUserUI(){
        requestEventButton.setVisibility(View.VISIBLE);
        eventsRecycler.setVisibility(View.VISIBLE);
        adminPanel.setVisibility(View.GONE);

        eventsRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        new GetEventsTask(user.getId(), new CallBack() {
            @Override
            public void onCallBack(Object object, String result) {
                
                eventsList = (List<EventDTO>) object;
                eventsRecycler.setAdapter(new EventAdapter(MainActivity.this, eventsList));

                if (eventsList.size() == 0) {
                    Toast.makeText(MainActivity.this, "No events available", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();

        requestEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RequestEventActivity.class);
                startActivity(intent);
            }
        });

    }

    private void renderCatererUI(){
        requestEventButton.setVisibility(View.GONE);
        eventsRecycler.setVisibility(View.GONE);
        adminPanel.setVisibility(View.VISIBLE);

        newRegsBtn.setText(R.string.event_request_btn_txt);
        newRegsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetEventsByStatusTask("REQUESTED", new CallBack() {
                    @Override
                    public void onCallBack(Object object, String result) {
                        goToEventsList((List<EventDTO>) object);
                    }
                }).execute();
            }
        });
        browseUsersBtn.setText(R.string.view_planned_events);
        browseUsersBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetEventsByStatusTask("ACTIVE", new CallBack() {
                    @Override
                    public void onCallBack(Object object, String result) {
                        goToEventsList((List<EventDTO>) object);
                    }
                }).execute();
            }
        });
    }

    private void renderStaffUI(){
        requestEventButton.setVisibility(View.GONE);
        eventsRecycler.setVisibility(View.VISIBLE);
        adminPanel.setVisibility(View.GONE);
        eventsRecycler.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        new GetEventsByStatusTask("ACTIVE", new CallBack() {
            @Override
            public void onCallBack(Object object, String result) {
                eventsList = (List<EventDTO>) object;
                eventsRecycler.setAdapter(new EventAdapter(MainActivity.this, eventsList));
                if (eventsList.size() == 0) {
                    Toast.makeText(MainActivity.this, "No events available", Toast.LENGTH_SHORT).show();
                }
            }
        }).execute();
    }

    private void goToUsersList(List<UserDTO> usersList) {
        Intent intent = new Intent(MainActivity.this, UsersListActivity.class);
        intent.putExtra(getString(R.string.users_list_key), (Serializable) usersList);
        startActivity(intent);
    }

    private void goToEventsList(List<EventDTO> eventsList) {
        Intent intent = new Intent(MainActivity.this, EventsListActivity.class);
        intent.putExtra(getString(R.string.event_list_data), (Serializable) eventsList);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (eventsRecycler.getVisibility() == View.VISIBLE) {
            if (user.getRole().equalsIgnoreCase(getString(R.string.user_type_general))) {

//                new GetEventsTask(user.getId(), new CallBack() {
//                    @Override
//                    public void onCallBack(Object object, String result) {
//                        eventsList = (List<EventDTO>) object;
//                        eventsRecycler.setAdapter(new EventAdapter(MainActivity.this, eventsList));
//
//                    }
//                }).execute();
                renderGeneralUserUI();

            } else if (user.getRole().equalsIgnoreCase(getString(R.string.user_type_caterer_staff))){
                renderStaffUI();
//                new GetEventsByStatusTask("ACTIVE", new CallBack() {
//                    @Override
//                    public void onCallBack(Object object, String result) {
//                        eventsList = (List<EventDTO>) object;
//                        eventsRecycler.setVisibility(View.VISIBLE);
//                        eventsRecycler.setAdapter(new EventAdapter(MainActivity.this, eventsList));
//                        eventsRecycler.getAdapter().notifyDataSetChanged();
//                        if (eventsList.size() == 0) {
//                            Toast.makeText(MainActivity.this, "No events available", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                }).execute();

            }

        }
    }
}
