package edu.uta.se1.team6.tapem.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.uta.se1.team6.tapem.Activities.EventDetailsActivity;
import edu.uta.se1.team6.tapem.Activities.MainActivity;
import edu.uta.se1.team6.tapem.Core.CallBack;
import edu.uta.se1.team6.tapem.Helpers.DialogUtils;
import edu.uta.se1.team6.tapem.Models.EventDTO;
import edu.uta.se1.team6.tapem.R;

/**
 * Created by yashodhan on 3/24/18.
 */

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    List<EventDTO> eventsList = new ArrayList<>();
    private LayoutInflater inflater;
    private Context context;

    public EventAdapter(Context context, List<EventDTO> eventsList) {
        this.eventsList = eventsList;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.event_card, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final EventDTO current = eventsList.get(position);
        Log.d("Event", "onBindViewHolder: " + current);
        holder.title.setText(current.getName());

        holder.statusMessage.setText(current.getStatus());

        holder.cardContentHolder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Event", "onClick: " + current);
                goToDetails(current);
            }
        });

        if (MainActivity.user.getType().equals(context.getString(R.string.user_type_caterer_staff)) || MainActivity.user.getType().equals(context.getString(R.string.user_type_caterer))) {
            holder.cancelAction.setVisibility(View.GONE);
        }
        if (Objects.equals(current.getStatus(), context.getString(R.string.event_active))) {
            holder.cancelAction.setVisibility(View.VISIBLE);
            holder.cancelledMessage.setVisibility(View.GONE);
            if (MainActivity.user.getType().equals(context.getString(R.string.user_type_caterer_staff)) || MainActivity.user.getType().equals(context.getString(R.string.user_type_caterer))) {
                holder.cancelAction.setVisibility(View.GONE);
            }
        } else if (current.getStatus().equals(R.string.event_cancelled)){
            holder.cancelAction.setVisibility(View.GONE);
            holder.cancelledMessage.setVisibility(View.VISIBLE);
            if (MainActivity.user.getType().equals(context.getString(R.string.user_type_caterer_staff)) || MainActivity.user.getType().equals(context.getString(R.string.user_type_caterer))) {
                holder.cancelAction.setVisibility(View.GONE);
            }
        }

        if (holder.cancelAction.getVisibility() == View.VISIBLE) {
            holder.cancelAction.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DialogUtils.AlertDialog((Activity) context, context.getString(R.string.dialog_prompt_cancel_event), new CallBack() {
                        @Override
                        public void onCallBack(Object object, String result) {
                            current.setStatus(context.getString(R.string.event_cancelled));
                            notifyDataSetChanged();
                        }
                    });
                }
            });
        }
    }



    private void goToDetails(EventDTO current) {
        Intent intent = new Intent(context, EventDetailsActivity.class);
        intent.putExtra(context.getString(R.string.event_details_key), current);
        context.startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return eventsList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private CardView cardRoot;
        private RelativeLayout cardContentHolder;
        private TextView title, cancelledMessage, statusMessage;
        private ImageView image;
        private TextView description;
        private TextView dateData;
        private TextView locationData;
        private View seperator;
        private RelativeLayout buttonBar;
        private Button cancelAction, viewAction;
        public ViewHolder(View view) {
            super(view);
            cardRoot = view.findViewById(R.id.cardRoot);
            cardContentHolder = view.findViewById(R.id.cardContentHolder);
            title = view.findViewById(R.id.title);
            statusMessage = view.findViewById(R.id.statusMessage);
            cancelledMessage = view.findViewById(R.id.cancelledMessage);
            image = view.findViewById(R.id.image);
            description = view.findViewById(R.id.description);
            dateData = view.findViewById(R.id.dateData);
            locationData = view.findViewById(R.id.locationData);
            seperator = view.findViewById(R.id.seperator);
            buttonBar = view.findViewById(R.id.buttonBar);
            cancelAction = view.findViewById(R.id.cancelAction);
            viewAction = view.findViewById(R.id.viewAction);
        }
    }
}
