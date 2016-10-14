package com.example.somayyeh.booklisting;

import android.graphics.Bitmap;

/**
 * Created by somayyeh on 8/15/16.
 */
public class BookSpec
{
    private String mTitle;
    private String mAuthor;
    private String mSmallThumbnail;
    private Bitmap mImage;

    public BookSpec(String title, String author, Bitmap image, String smallThumbnail)
    {
       mTitle = title;
        mAuthor = author;
        mSmallThumbnail = smallThumbnail;
        mImage = image;

    }

    public String getTitle()
    {
        return mTitle;
    }
    public String getAuthor()
    {
        return mAuthor;
    }
    public String getSmallThumbnail()
    {
        return mSmallThumbnail;
    }
    public Bitmap getImage()
    {
        return mImage;
    }


}
