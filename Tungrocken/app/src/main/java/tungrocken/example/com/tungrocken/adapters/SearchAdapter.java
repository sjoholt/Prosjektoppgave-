package tungrocken.example.com.tungrocken.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.List;

import tungrocken.example.com.tungrocken.R;
import tungrocken.example.com.tungrocken.domain.Article;
import tungrocken.example.com.tungrocken.domain.Server;

/**
 * Created by Team Tungrocken on 15.11.2016.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyGridViewHolder> {


    static SimpleDateFormat SDF = new SimpleDateFormat("EEE HH:mm");

    Server s = new Server();
    final String ip = s.serverUrl();


    public interface OnClickListener {
        void onClick(int position);
    }


    List<Article> articles;
    OnClickListener listener = position -> {};

    Context context;



    public SearchAdapter(@NonNull Context context, @NonNull List<Article> articles) {
        this.context = context;
        this.articles = articles;
    }


    public class MyGridViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView ingress;
        public TextView content;
        public TextView youtubeUrl;
        public ImageView photoUrl;
        public TextView search_error;

        public MyGridViewHolder(View view) {
            super(view);
            view.setOnClickListener(v -> {
                listener.onClick(getAdapterPosition());
            });
            this.title = (TextView)view.findViewById(R.id.title2);
            this.ingress = (TextView)view.findViewById(R.id.ingress);
            this.content = (TextView) view.findViewById(R.id.content);
            this.youtubeUrl = (TextView) view.findViewById(R.id.youtubeUrl);
            this.photoUrl = (ImageView) view.findViewById(R.id.photoUrl);
            this.search_error = (TextView) view.findViewById(R.id.search_error);
        }
    }

    @Override
    public MyGridViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layout = viewType;
        View itemView = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new MyGridViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyGridViewHolder holder, int position) {
        Article article = articles.get(position);


        holder.title.setText(article.getTitle());
        holder.ingress.setText(article.getIngress());
        //holder.content.setText(article.getContent());
        holder.content.setVisibility(View.GONE);
        //holder.youtubeUrl.setText(article.getYoutubeUrl());
        holder.youtubeUrl.setVisibility(View.GONE);
        //Picasso.with(context).load(ip + article.getPhotoUrl()).into(holder.photoUrl);
        holder.photoUrl.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(this.articles.isEmpty())
        {
            Log.i("Search error", "Search error");
            return R.layout.search_error;

        }
        else
        {
            Log.i("Søk", "søk");
            return R.layout.homearticles;
        }
    }

    public List<Article> getItems() {
        return articles;
    }
    public void setOnClickListener(@NonNull OnClickListener listener) {
        this.listener = listener;
    }



}