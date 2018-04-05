package edu.uta.se1.team6.tapem.Activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.uta.se1.team6.tapem.Adapters.EventAdapter;
import edu.uta.se1.team6.tapem.Models.EventDTO;
import edu.uta.se1.team6.tapem.R;

public class EventsListActivity extends BaseActivity {

    private CoordinatorLayout rootComponent;
    private Toolbar toolbar;
    private NestedScrollView scrollView;
    private RecyclerView usersRecycler;
    private List<EventDTO> eventsList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_list);

        rootComponent = findViewById(R.id.root_component);
        toolbar = findViewById(R.id.toolbar);
        scrollView = findViewById(R.id.scrollView);
        usersRecycler = findViewById(R.id.usersRecycler);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (getIntent().hasExtra(getString(R.string.event_list_data))) {
            eventsList = (List<EventDTO>) getIntent().getSerializableExtra(getString(R.string.event_list_data));
//            Log.d("Users", "onCreate: "+usersList);
        }
        usersRecycler.setLayoutManager(new LinearLayoutManager(this));
        usersRecycler.setAdapter(new EventAdapter(this, eventsList));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (usersRecycler.getVisibility() == View.VISIBLE) {
            usersRecycler.getAdapter().notifyDataSetChanged();
        }
    }
}
