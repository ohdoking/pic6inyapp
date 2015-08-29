package com.yapp.pic6.picproject.service;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import java.io.File;
import java.util.Date;

public class GalleryHelper {

    private ContentResolver cr;
    private Context context;

    private static final String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";
    private static final int INDEX_BUCKET_ID = 0;
    private static final int INDEX_MEDIA_TYPE = 1;
    private static final int INDEX_BUCKET_NAME = 2;


    public GalleryHelper(Context context) {
        this.context = context;
        cr = context.getContentResolver();
    }

    //GET New Picture
    public Cursor newPicture() {


        Cursor mImageCursor;
        //mImageCursor = cr.query(android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI, null, null, null ,null);
        String[] projection = {android.provider.MediaStore.Images.ImageColumns.DATA,
                android.provider.MediaStore.Images.ImageColumns.DATE_ADDED,
                android.provider.MediaStore.Images.ImageColumns.DATE_MODIFIED};
        String selection = android.provider.MediaStore.Images.ImageColumns.DATE_ADDED + " > ? and "
                + android.provider.MediaStore.Images.ImageColumns.DATE_ADDED + " == " + android.provider.MediaStore.Images.ImageColumns.DATE_MODIFIED;
        String before24hour = ((new Date().getTime() - (60 * 1000)) / 1000) + "";
        String[] selectionArgs = {before24hour};
        String sortOrder = android.provider.MediaStore.Images.ImageColumns.DATE_ADDED + " DESC";
        mImageCursor = cr.query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, sortOrder);

        mImageCursor.moveToNext();

        String filePath;
        String[] filePathColumn = {android.provider.MediaStore.MediaColumns.DATA};
        int columnIndex = mImageCursor.getColumnIndex(filePathColumn[0]);
        filePath = mImageCursor.getString(columnIndex);


//        GalleryCount.setText(filePath);
        // GalleryCount.setText(String.valueOf(mImageCursor.getLong(mIm
//        GalleryCount.setText(String.valueOf(mImageCursor.getCount()));
//        GalleryCount.setText(String.valueOf(mImageCursor.getString(mImageCursor.getColumnIndex("_data"))));
//          //  GalleryCount.setText(Environment.getExternalStorageDirectory().toString());
        //fileUMove(mImageCursor.getString(mImageCursor.getColumnIndex("_data")), Environment.getExternalStorageDirectory() + "/Pictures/Instagram/test.jpg");




        //delete
        //deleteImage(mImageCursor.getString(mImageCursor.getColumnIndex("_data")));




       /* String[] projection1 = {android.provider.MediaStore.Images.ImageColumns.DATA,
                android.provider.MediaStore.Images.ImageColumns.DATE_ADDED,
                android.provider.MediaStore.Images.ImageColumns.DATE_MODIFIED};
       selection = android.provider.MediaStore.Images.ImageColumns.DATE_ADDED +" > ?";
        before24hour = ((new Date().getTime() - (60 * 1000)) / 1000)+"";
        String[] selectionArgs2 = { before24hour };
        sortOrder = android.provider.MediaStore.Images.ImageColumns.DATE_ADDED + " DESC";
        Cursor mImageCursor2 = cr.query(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,projection1,selection,selectionArgs2,sortOrder);

        mImageCursor2.moveToNext();

        GalleryCount.setText(String.valueOf(mImageCursor2.getString(mImageCursor2.getColumnIndex("_data"))) +
                " // " + String.valueOf(mImageCursor2.getLong(mImageCursor2.getColumnIndex("date_added"))) +
                "//" + String.valueOf(mImageCursor2.getLong(mImageCursor2.getColumnIndex("date_modified"))));*/


               /* ageCursor.getColumnIndex("date_added"))
                + "//" + String.valueOf(mImageCursor.getLong(mImageCursor.getColumnIndex("date_modified")))));*/

        //_data=?", selectionArgs1);
//        Log.i("ohdoking", "2");
        return mImageCursor;
    }

    public void getGallery() {

        String[] PROJECTION_BUCKET = {
                android.provider.MediaStore.Images.ImageColumns.BUCKET_ID,
                android.provider.MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
                android.provider.MediaStore.Images.ImageColumns.DATE_TAKEN,
                android.provider.MediaStore.Images.ImageColumns.DATA};
        // We want to order the albums by reverse chronological order. We abuse the
        // "WHERE" parameter to insert a "GROUP BY" clause into the SQL statement.
        // The template for "WHERE" parameter is like:
        //    SELECT ... FROM ... WHERE (%s)
        // and we make it look like:
        //    SELECT ... FROM ... WHERE (1) GROUP BY 1,(2)
        // The "(1)" means true. The "1,(2)" means the first two columns specified
        // after SELECT. Note that because there is a ")" in the template, we use
        // "(2" to match it.
        String BUCKET_GROUP_BY =
                "1) GROUP BY 1,(2";
        String BUCKET_ORDER_BY = "MAX(datetaken) DESC";

        // Get the base URI for the People table in the Contacts content provider.
        Uri images = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cur = context.getContentResolver().query(
                images, PROJECTION_BUCKET, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY);

        Log.i("ListingImages", " query count=" + cur.getCount());

        if (cur.moveToFirst()) {
            String bucket;
            String date;
            String data;
            int bucketColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.BUCKET_DISPLAY_NAME);

            int dateColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.DATE_TAKEN);
            int dataColumn = cur.getColumnIndex(
                    MediaStore.Images.Media.DATA);

            do {
                // Get the field values
                bucket = cur.getString(bucketColumn);
                date = cur.getString(dateColumn);
                data = cur.getString(dataColumn);

                // Do something with the values.
                Log.i("ListingImages", " bucket=" + bucket
                        + "  date_taken=" + date
                        + "  _data=" + data);
            } while (cur.moveToNext());
        }
    }

    public void deleteImage(String value) {
        String[] selectionArgs0 = {value};
        cr.delete(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_data=?", selectionArgs0);
    }

    private void fileUMove(String inFileName, String outFileName) {

        File sourceLocation = new File(inFileName);
        File targetLocation = new File(outFileName);
        try {
            if (sourceLocation.renameTo(targetLocation)) {
                Log.i("ohdoking", inFileName);
                Log.i("ohdoking", outFileName);

                ContentValues values = new ContentValues();
                values.put("date_modified", ((new Date().getTime() - (60 * 60 * 24 * 1000)) / 1000));
                String[] selectionArgs1 = {outFileName};
                cr.update(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values, "_data=?", selectionArgs1);



            } else {

                Log.i("ohdoking", "fail");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public String getPath(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        Log.i("ohdoking",String.valueOf(cursor.getString(columnIndex)));
        return cursor.getString(columnIndex);
    }


}
