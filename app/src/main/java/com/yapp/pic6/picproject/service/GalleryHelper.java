package com.yapp.pic6.picproject.service;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.yapp.pic6.picproject.R;
import com.yapp.pic6.picproject.dao.Gallery;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class GalleryHelper {

    private ContentResolver cr;
    private Context context;

    private static final String BUCKET_GROUP_BY = "1) GROUP BY 1,(2";
    private static final int INDEX_BUCKET_ID = 0;
    private static final int INDEX_MEDIA_TYPE = 1;
    private static final int INDEX_BUCKET_NAME = 2;

    public static final String STRSAVEPATH = Environment.getExternalStorageDirectory()+"/Pictures/";
    public static final String TRASHPATH = STRSAVEPATH + "Trash/";


    public GalleryHelper(Context context) {
        this.context = context;
        cr = context.getContentResolver();

        if(!(getPreferences()==1)){
            makeDirectory("Trash");
            savePreferences(1);
        }
    }
    // �� �����ϱ�
    private void savePreferences(int value){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("first", value);
        editor.commit();
    }
    private Integer getPreferences(){
        SharedPreferences pref = context.getSharedPreferences("pref", context.MODE_PRIVATE);
        return pref.getInt("first", 0);
    }
    //GET New Picture
    public ArrayList<String> newPicture() {


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


        ArrayList<String> arr = new ArrayList<String>();
        if (mImageCursor.moveToFirst()) {
            String data;
            int dataColumn = mImageCursor.getColumnIndex(
                    MediaStore.Images.Media.DATA);

            do {
                // Get the field values
                data = mImageCursor.getString(dataColumn);
                // Do something with the values.
//                Log.i("ListingImages", " _data=" + data);
//                Log.i("new_old_Images",
//                        " mody=" + mImageCursor.getString(mImageCursor.getColumnIndex(android.provider.MediaStore.Images.ImageColumns.DATE_MODIFIED))
//                                +" // add=" + mImageCursor.getString(mImageCursor.getColumnIndex(MediaStore.Images.ImageColumns.DATE_ADDED))
//                );

                File f = new File(data);

                if(!f.getName().equals("myApp.PNG")){
                    arr.add(data);
                }
            } while (mImageCursor.moveToNext());
        }
        mImageCursor.close();

//        GalleryCount.setText(filePath);
        // GalleryCount.setText(String.valueOf(mImageCursor.getLong(mIm
//        GalleryCount.setText(String.valueOf(mImageCursor.getCount()));
//        GalleryCount.setText(String.valueOf(mImageCursor.getString(mImageCursor.getColumnIndex("_data"))));
//          //  GalleryCount.setText(Environment.getExternalStorageDirectory().toString());



//        fileUMove(mImageCursor.getString(mImageCursor.getColumnIndex("_data")), Environment.getExternalStorageDirectory() + "/Pictures/Instagram/test.jpg");




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


               /* ageCursor.getC    olumnIndex("date_added"))
                + "//" + String.valueOf(mImageCursor.getLong(mImageCursor.getColumnIndex("date_modified")))));*/

        //_data=?", selectionArgs1);
//        Log.i("ohdoking", "2");
        return arr;
    }

    public ArrayList<Gallery> getGallery() {

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

//        Log.i("ListingImages", " query count=" + cur.getCount());
        ArrayList<Gallery> arr = new ArrayList<Gallery>();
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

                File file = new File(data);

                Gallery gallery = new Gallery();
                gallery.setName(bucket);
                gallery.setImagePath( file.getParent());
                // Do something with the values.
//                Log.i("ListingImages", " bucket=" + bucket
//                        + "  date_taken=" + date
//                        + "  _data=" + data);

                if(!gallery.getName().equals("Trash")){
                    arr.add(gallery);
                }
            } while (cur.moveToNext());
        }



        return arr;
    }

    public void deleteImage(String value) {
        String[] selectionArgs0 = {value};
        cr.delete(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "_data=?", selectionArgs0);
    }

    public void fileUMove(String inFileName, String outFileName) {

        File sourceLocation = new File(inFileName);
        File targetLocation = new File(outFileName);
//        Log.i("ohdokingi", inFileName);
//        Log.i("ohdokingo", outFileName);
        try {
            if (sourceLocation.renameTo(targetLocation)) {

//                ContentValues values = new ContentValues();
//                values.put("date_modified", ((new Date().getTime() - (60 * 60 * 24 * 1000)) / 1000));
//                String[] selectionArgs1 = {outFileName};
//                cr.update(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values, "_data=?", selectionArgs1);

                removeMedia(context, sourceLocation.getAbsoluteFile());
                addMedia(context, targetLocation);


            } else {

//                Log.i("ohdoking", "fail");
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static void addMedia(Context c, File f) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(f));
        c.sendBroadcast(intent);
    }

    private static void removeMedia(Context c, File f) {
        ContentResolver resolver = c.getContentResolver();
        resolver.delete(android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI, android.provider.MediaStore.Images.Media.DATA + "=?", new String[]{f.getAbsolutePath()});
    }

    /*
         ���� ����
         params
            dir_path : ���� �̸�
     */
    public File makeDirectory(String dir_path){
        File dir = new File(STRSAVEPATH + dir_path);
        if (!dir.exists())
        {
            dir.mkdirs();

            Bitmap bm = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);


            File file = new File(STRSAVEPATH + dir_path, "myApp.PNG");
            FileOutputStream outStream = null;

            try {
                outStream = new FileOutputStream(file);
                bm.compress(Bitmap.CompressFormat.PNG, 100, outStream);
                outStream.flush();
                outStream.close();
                addMedia(context, file);
                String str = context.getResources().getString(R.string.new_album_done);
                Toast.makeText(context, dir_path + " " +str, Toast.LENGTH_LONG).show();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            String str = context.getResources().getString(R.string.new_album_error_same);
            Toast.makeText(context,str,Toast.LENGTH_SHORT).show();
        }
        return dir;
    }

    public String getPath(Uri uri) {
        String[] filePathColumn = {MediaStore.Images.Media.DATA};

        Cursor cursor = context.getContentResolver().query(uri, filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
//        Log.i("ohdoking",String.valueOf(cursor.getString(columnIndex)));
        return cursor.getString(columnIndex);
    }


    public void deleteTrash(){

        List<File> dirList = getDirFileList(TRASHPATH);
        for(int i=0; i<dirList.size(); i++) {
            String delFileName = dirList.get(i).getName();
            fileDelete(TRASHPATH + delFileName);
        }


        Toast.makeText(context, "Clear Trash", Toast.LENGTH_SHORT).show();
    }


    /*
        ������ �̵�
        params
            inFileName : ���� ���
     */
    public void moveTrash(String inFileName){


       // String[] directoryName = inFileName.split("\\\\");

        File f = new File(inFileName);
         String fileName = f.getName();

        fileUMove((inFileName), (TRASHPATH + fileName));
//        Log.i("ss",inFileName+"//"+TRASHPATH+fileName);
//        Toast.makeText(context, "Move Trash", Toast.LENGTH_SHORT).show();
    }

    //file delete
    public void fileDelete(String deleteFileName){
        File delFile = new File(deleteFileName);
        delFile.delete();
    }

    //read file list in folder
    public List<File> getDirFileList(String dirPath){
        List<File> dirFileList = null; // folder file lists
        File dir = new File(dirPath);
        if (dir.exists()) {
            File[] files;
            files = dir.listFiles();
            dirFileList = Arrays.asList(files);
        }

        return dirFileList;
    }

}
