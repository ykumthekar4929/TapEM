package edu.uta.se1.team6.tapem.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edu.uta.se1.team6.tapem.Activities.ProfileActivity;
import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Helpers.DialogUtils;
import edu.uta.se1.team6.tapem.Models.UserDTO;
import edu.uta.se1.team6.tapem.R;
import edu.uta.se1.team6.tapem.Services.DeleteUserTask;
import edu.uta.se1.team6.tapem.Services.UpdateUserTask;

/**
 * Created by yashodhan on 3/25/18.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {
    List<UserDTO> usersList = new ArrayList<>();
    Context context;
    LayoutInflater inflater;

    public UsersAdapter(List<UserDTO> usersList, Context context) {
        this.usersList = usersList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.users_row, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final UserDTO currentUser = usersList.get(position);
        holder.userName.setText(currentUser.getFirstName() + " " + currentUser.getLastName());
        holder.userMavID.setText(currentUser.getMavID());
        holder.userType.setText(currentUser.getRole());

        if (currentUser.getStatus().equals(context.getString(R.string.user_active))) {
            holder.cancelAction.setText(context.getString(R.string.delete_user));
            holder.approveAction.setText(context.getString(R.string.view_details));
            holder.approveAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                Intent intent = new Intent(context, ProfileActivity.class);
                intent.putExtra(context.getString(R.string.user_data_key), currentUser);
                intent.putExtra(context.getString(R.string.is_admin_key), true);
                context.startActivity(intent);

                }
            });

            holder.cancelAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                DialogUtils.AlertDialog(((Activity) context), context.getString(R.string.delete_user_confirm_mesg), new CallBack() {
                    @Override
                    public void onCallBack(Object object, String result) {
                    new DeleteUserTask(currentUser, new CallBack() {
                        @Override
                        public void onCallBack(Object object, String result) {
                            Toast.makeText(context, "User deleted", Toast.LENGTH_SHORT).show();
                            usersList.remove(currentUser);
                            notifyDataSetChanged();
                        }
                    }).execute();
                    }
                });
                }
            });

        } else if (currentUser.getStatus().equals(context.getString(R.string.user_pending))) {
            holder.approveAction.setText(context.getString(R.string.approve));
            holder.cancelAction.setText(context.getString(R.string.decline));
            holder.approveAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                currentUser.setStatus(context.getString(R.string.user_active));
                notifyDataSetChanged();
                new UpdateUserTask(currentUser, new CallBack() {
                    @Override
                    public void onCallBack(Object object, String result) {
                        Toast.makeText(context, "User approved", Toast.LENGTH_SHORT).show();

                    }
                }).execute();
                }
            });
            holder.cancelAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                currentUser.setStatus("DECLINED");
                new UpdateUserTask(currentUser, new CallBack() {
                    @Override
                    public void onCallBack(Object object, String result) {
                        usersList.remove(currentUser);
                        Toast.makeText(context, "Approval not granted", Toast.LENGTH_SHORT).show();
                        notifyDataSetChanged();
                    }
                }).execute();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView userName;
        private TextView userMavID;
        private TextView userType;
        private Button cancelAction;
        private Button approveAction;

        ViewHolder(View view) {
            super(view);
            userName = view.findViewById(R.id.userName);
            userMavID = view.findViewById(R.id.userMavID);
            userType = view.findViewById(R.id.userType);
            cancelAction = view.findViewById(R.id.cancelAction);
            approveAction = view.findViewById(R.id.approveAction);
        }

    }
}
