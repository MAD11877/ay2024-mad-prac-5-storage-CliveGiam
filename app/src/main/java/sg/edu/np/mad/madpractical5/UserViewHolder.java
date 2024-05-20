package sg.edu.np.mad.madpractical5;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class UserViewHolder extends RecyclerView.ViewHolder {
    ImageView smallimg;
    ImageView largeimg;
    TextView name;
    TextView desc;

    public UserViewHolder(View userView) {
        super(userView);
        smallimg = userView.findViewById(R.id.smallImageView);
        largeimg = userView.findViewById(R.id.largeImageView);
        name = userView.findViewById(R.id.nameView);
        desc = userView.findViewById(R.id.descView);
    }
}
