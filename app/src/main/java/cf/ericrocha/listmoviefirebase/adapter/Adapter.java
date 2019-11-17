package cf.ericrocha.listmoviefirebase.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cf.ericrocha.listmoviefirebase.R;
import cf.ericrocha.listmoviefirebase.model.Movie;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private List<Movie> listMovie;

    public Adapter(List<Movie> list) {
        this.listMovie = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemList = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_list, parent, false);
        return new MyViewHolder(itemList);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Movie movie = listMovie.get( position );
        holder.title.setText( movie.getTitleMovie() );
        holder.year.setText( movie.getYear() );
    }

    @Override
    public int getItemCount() {
        return listMovie.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView year;
        public MyViewHolder(View itemView) {
            super(itemView);


            title = itemView.findViewById(R.id.textTitle);
            year = itemView.findViewById(R.id.textYear);
        }
    }
}