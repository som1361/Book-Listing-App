package com.example.somayyeh.booklisting;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by somayyeh on 8/16/16.
 */
public class bookListAdapter extends ArrayAdapter
{
    public bookListAdapter(Context context, List<BookSpec> booksList) {
        super(context, 0,booksList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_list, parent, false);
        }
        BookSpec currentItem = (BookSpec) getItem(position);

        TextView title = (TextView)listItemView.findViewById(R.id.title);
        title.setText(currentItem.getTitle());

        TextView author = (TextView)listItemView.findViewById(R.id.author);
        author.setText(currentItem.getAuthor());

        ImageView image = (ImageView)listItemView.findViewById(R.id.bookImage);
        image.setImageBitmap(currentItem.getImage());

        return listItemView;
    }
}
