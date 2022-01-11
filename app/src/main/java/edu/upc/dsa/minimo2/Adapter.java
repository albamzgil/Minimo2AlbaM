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

    private List<Repos> repos;
    Context context;

    public Adapter(Context context, List<Repos> repos) {
        this.repos = repos;
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtName;
        public TextView txtLanguage;

        public ViewHolder(View v) {
            super(v);
            txtName = v.findViewById(R.id.nameRepo);
            txtLanguage = v.findViewById(R.id.lenguaje);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(context);
        View v =
                inflater.inflate(R.layout.repositories, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Repos repo = repos.get(position);
        holder.txtName.setText(repo.getName());
        holder.txtLanguage.setText(repo.getLanguage());
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }
}
