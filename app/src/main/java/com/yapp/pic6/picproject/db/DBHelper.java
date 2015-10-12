package com.yapp.pic6.picproject.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.yapp.pic6.picproject.dao.Gallery;

import java.util.ArrayList;
import java.util.HashMap;

public class DBHelper extends SQLiteOpenHelper {

   public static final String DATABASE_NAME = "PIC.db";
   public static final String GALLERYS_TABLE_NAME = "gallerys";
   public static final String GALLERYS_COLUMN_ID = "id";
   public static final String GALLERYS_COLUMN_NAME = "name";
   public static final String GALLERYS_COLUMN_ORDER = "gorder";
   public static final String GALLERYS_COLUMN_IMAGE_PATH = "imgpath";

   private HashMap hp;

   public DBHelper(Context context)
   {
      super(context, DATABASE_NAME , null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase db) {
      // TODO Auto-generated method stub
      db.execSQL(
      "create table " + GALLERYS_TABLE_NAME +
      "(id integer primary key, name text,gorder text,imgpath)"
      );
   }

   @Override
   public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
      // TODO Auto-generated method stub
      db.execSQL("DROP TABLE IF EXISTS "+ GALLERYS_TABLE_NAME);
      onCreate(db);
   }

   public boolean insertGallery (String name, String order)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("name", name);
      contentValues.put("gorder", order);

      db.insert(GALLERYS_TABLE_NAME, null, contentValues);
      return true;
   }
   
   public Cursor getData(int id){
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from " + GALLERYS_TABLE_NAME + " where id="+id+"", null );
      return res;
   }
   
   public int numberOfRows(){
      SQLiteDatabase db = this.getReadableDatabase();
      int numRows = (int) DatabaseUtils.queryNumEntries(db, GALLERYS_TABLE_NAME);
      return numRows;
   }
   
   public boolean updateGallery (Integer id, String name, String order)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put("name", name);
      contentValues.put("gorder", order);
      db.update(GALLERYS_TABLE_NAME, contentValues, "id = ? ", new String[] { Integer.toString(id) } );
      return true;
   }

   public Integer deleteGallery (Integer id)
   {
      SQLiteDatabase db = this.getWritableDatabase();
      return db.delete(GALLERYS_TABLE_NAME,
      "id = ? ", 
      new String[] { Integer.toString(id) });
   }
   
   public ArrayList<Gallery> getAllGallerys()
   {
      ArrayList<Gallery> array_list = new ArrayList<Gallery>();
      
      //hp = new HashMap();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor res =  db.rawQuery( "select * from " + GALLERYS_TABLE_NAME, null );
      res.moveToFirst();
      
      while(res.isAfterLast() == false){

         Gallery gallery = new Gallery();
         gallery.setId(res.getInt(0));
         gallery.setName(res.getString(1));
         gallery.setOrder(res.getString(2));
         Log.v("out-data", String.valueOf(res.getInt(0)) + "//" + res.getString(1) +"//"+ res.getString(2));
         array_list.add(gallery);
         res.moveToNext();
      }
   return array_list;
   }
}