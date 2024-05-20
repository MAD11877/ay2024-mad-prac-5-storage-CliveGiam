package sg.edu.np.mad.madpractical5;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.AlertDialog;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class UserAdapter extends RecyclerView.Adapter<UserViewHolder> {
    ArrayList<User> userlist;
    ListActivity environment;

    public UserAdapter(ArrayList<User> input, ListActivity activity)
    {
        userlist = input;
        environment = activity;
    }

    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View user = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_activity_list, parent, false);
        return new UserViewHolder(user);
    }

    public void onBindViewHolder(UserViewHolder holder, int position) {
        User temp = userlist.get(position);
        String tempName = temp.getName();
        holder.name.setText(tempName);
        holder.desc.setText(temp.getDescription());
        String last = tempName.substring(tempName.length() - 1);
        if (!last.equals("7")) {
            holder.largeimg.setVisibility(View.GONE);
        }
        else { holder.largeimg.setVisibility(View.VISIBLE); }

        holder.smallimg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View imgview) {
                AlertDialog.Builder builder = new AlertDialog.Builder(imgview.getContext());
                builder.setTitle("Profile");
                builder.setMessage(temp.getName());
                builder.setCancelable(true);
                builder.setPositiveButton("View", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int id){
                        Intent activity = new Intent(imgview.getContext(), MainActivity.class);
                        Bundle info = new Bundle();
                        info.putString("Name",temp.getName());
                        info.putString("Description",temp.description);
                        info.putInt("Id",temp.getId());
                        info.putBoolean("Followed",temp.getFollowed());
                        activity.putExtras(info);
                        environment.startActivity(activity);
                    }
                });
                builder.setNegativeButton("Close", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }


                });

                AlertDialog clickAlert = builder.create();
                clickAlert.show();

                BroadcastReceiver followListener = new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        if (intent.getAction().equals("User_follow")) {
                            boolean replace = intent.getBooleanExtra("Followed", false);
                            for(User x : userlist) {
                                if (x.getName().equals(tempName)) {
                                    x.setFollowed(replace);
                                    break;
                                }
                            }
                        }
                    }
                };

                LocalBroadcastManager.getInstance(environment).registerReceiver(followListener, new IntentFilter("User_follow"));


            }
        });



    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }
}
