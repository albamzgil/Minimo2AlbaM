package edu.upc.dsa.minimo2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private List<User> users;
    Context context;

    public Adapter(Context context, List<User> users) {
        this.users = users;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public ImageView image;

        public ViewHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.followerName);
            image = v.findViewById(R.id.imageFollower);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(context);
        View v =
                inflater.inflate(R.layout.follower, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        User user = users.get(position);
        holder.txtName.setText(user.getLogin());
        Picasso.with(context).load(user.getAvatar_url()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
}
