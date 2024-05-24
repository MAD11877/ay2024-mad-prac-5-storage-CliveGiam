package sg.edu.np.mad.madpractical5;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        User user = new User("John Doe","MAD Developer",1,false);
        TextView tvName =  findViewById(R.id.tvName);
        TextView tvDescription = findViewById(R.id.tvDescription);

        Button btnFollow  = findViewById(R.id.btnFollow);
        btnFollow.setText("Follow");

        Intent receive = getIntent();
        String name = receive.getStringExtra("Name");
        String desc = receive.getStringExtra("Description");
        int id = receive.getIntExtra("Id", 1);
        user.setId(id);
        Toast.makeText(getApplicationContext(), String.valueOf(id), Toast.LENGTH_LONG).show();


        tvName.setText(name);
        tvDescription.setText(desc);

        Bundle tempUser = new Bundle();
        DatabaseHandler dbHandler = new DatabaseHandler(this, null, null, 1);
        btnFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.followed = !(user.followed);
                User updatedUser = new User(user.getName(),user.getDescription(), user.getId(), user.getFollowed());
                Intent sendFollow = new Intent("User_follow");
                if(user.followed) {
                    btnFollow.setText("Unfollow");
                    tempUser.putBoolean("Followed", true);
                    updatedUser.setFollowed(true);
                    Toast.makeText(getApplicationContext(), "Followed", Toast.LENGTH_LONG).show();
                }
                else {
                    btnFollow.setText("Follow");
                    tempUser.putBoolean("Followed", false);
                    updatedUser.setFollowed(false);
                    Toast.makeText(getApplicationContext(), "Unfollowed", Toast.LENGTH_LONG).show();
                }
                dbHandler.updateUser(updatedUser);
                sendBroadcast(sendFollow);
            }
        });


    }

}