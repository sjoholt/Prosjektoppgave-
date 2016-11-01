package tungrocken.example.com.tungrocken;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import tungrocken.example.com.tungrocken.domain.Article;

/**
 * Created by Demyx-Laptop on 01.11.2016.
 */


public class ArticleAdapter extends ArrayAdapter<Article> {
    public ArticleAdapter(Context context, List<Article> articleList) {
        super(context, 0, articleList);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.content_article,parent,false);
        }

        ImageView photoUrl = (ImageView)convertView.findViewById(R.id.photoUrl);
        photoUrl.setImageURI(Uri.parse(article.getPhotoUrl()));
        TextView title = (TextView)convertView.findViewById(R.id.title2);
        title.setText(article.getTitle());
        TextView ingress = (TextView)convertView.findViewById(R.id.ingress);
        ingress.setText(article.getIngress());
        TextView content = (TextView)convertView.findViewById(R.id.content);
        content.setText(article.getContent());
        TextView youtubeUrl = (TextView)convertView.findViewById(R.id.youtubeUrl);
        youtubeUrl.setText(article.getYoutubeUrl());


        return convertView;
    }
}
