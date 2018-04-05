package edu.uta.se1.team6.tapem.Activities;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.uta.se1.team6.tapem.Adapters.UsersAdapter;
import edu.uta.se1.team6.tapem.Models.UserDTO;
import edu.uta.se1.team6.tapem.R;

public class UsersListActivity extends BaseActivity {

    private CoordinatorLayout rootComponent;
    private Toolbar toolbar;
    private NestedScrollView scrollView;
    private RecyclerView usersRecycler;
    private List<UserDTO> usersList = new ArrayList<>();

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

        if (getIntent().hasExtra(getString(R.string.users_list_key))) {
            usersList = (List<UserDTO>) getIntent().getSerializableExtra(getString(R.string.users_list_key));
            Log.d("Users", "onCreate: "+usersList);
        }
        usersRecycler.setLayoutManager(new LinearLayoutManager(this));
        usersRecycler.setAdapter(new UsersAdapter(usersList, this));

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (usersRecycler.getVisibility() == View.VISIBLE) {
            usersRecycler.getAdapter().notifyDataSetChanged();
        }
    }
}
