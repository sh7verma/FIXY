package com.app.fixy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.app.fixy.models.LocationModel;

import java.util.ArrayList;

/**
 * Created by Applify on 8/11/2016.
 */
public class Db extends SQLiteOpenHelper {

    static final int dbversion = 1;
    public static final String DATABASE = "fixy_database";

    public static final String LOCATION_TABLE = "location_table";
    public static final String ID = "id";
    public static final String LABEL_NAME = "Label_name";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String TYPE = "type";
    public static final String PLACE_ID = "place_id";
    public static final String PLACE = "place";
    public static final String HOUSE_NO = "house_no";
    public static final String LANDMARK = "landmark";
    public Db(Context context) {
        super(context, DATABASE, null, dbversion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String location_qry = "create table if not exists " + LOCATION_TABLE
                + " (" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT " + " ," + LABEL_NAME + " TEXT" + " ," + PLACE + " TEXT" + " ,"
                + LATITUDE + " TEXT" + " ," + TYPE + " TEXT" + " ," + PLACE_ID + " TEXT" + " ," + LONGITUDE + " TEXT"
                + " ," + HOUSE_NO + " TEXT" + " ," + LANDMARK + " TEXT" + ")";
        db.execSQL(location_qry);
//        String profile_qry = "create table if not exists " + PROFILE_TABLE
//                + " (" + USER_ID + " TEXT " + " ," + NAME + " TEXT" + " ,"
//                + NUMBER + " TEXT" + " ," + COUNTRY_CODE + " TEXT"
//                + " ," + PROFILE_PIC + " TEXT" + " ," + LANGUAGE
//                + " TEXT" + " ," + STATUS + " TEXT" + " ," + ONLINE_STATUS + " TEXT"
//                + " ," + PUSH_TOKEN + " TEXT ," + CURRENT_USER + " TEXT ," + LINK_PHONE_ID + " TEXT ,"
//                + ACCESS_TOKEN + " TEXT ," + DOB + " TEXT ," + GENDER + " Text ," + THEME_COLOR + " TEXT ,"
//                + EMAIL + " Text ," + YAPYAP_ID + " TEXT ," + PROFILE_PIC_THUMB + " TEXT )";
//        db.execSQL(profile_qry);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public int checkRedundentName(String name) {
        SQLiteDatabase db = this.getReadableDatabase();
        String qry = "select * from " + LOCATION_TABLE + " where " + LABEL_NAME + " ='" + name + "'";
        Cursor cur = null, cursorMembers = null;
        int count=0;
        try {
            cur = db.rawQuery(qry, null);
            count = cur.getCount();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return count;
    }

    public void addHomeWorkLocation(LocationModel locationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db_read = this.getReadableDatabase();
        String qry = "select * from " + LOCATION_TABLE + " where " + TYPE
                + " = '" + locationModel.getType() + "'";
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db_read.rawQuery(qry, null);
            ContentValues values = new ContentValues();
            values.put(LABEL_NAME, locationModel.getLabelName());
            values.put(LATITUDE, locationModel.getLatitude());
            values.put(LONGITUDE, locationModel.getLongitude());
            values.put(TYPE, locationModel.getType());
            values.put(PLACE, locationModel.getPlace());
            values.put(HOUSE_NO, locationModel.getHouse_flat());
            values.put(LANDMARK, locationModel.getLandmark());
            if (cur.getCount() > 0) {
                db.update(LOCATION_TABLE, values, TYPE + " = '" + locationModel.getType() + "'", null);

            } else {
                db.insertOrThrow(LOCATION_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("exc db", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
            db.close();
        }
    }
//nit
//    ugc
//        physc
//    upsc
//
//    public physc
//    gk
//    getUpsc() {
//        return upsc;
//    }
public void deleteCustomAddress(LocationModel locationModel) {
    SQLiteDatabase db = this.getWritableDatabase();
    db.beginTransaction();
    int a = db.delete(LOCATION_TABLE, ID + " = '" + locationModel.getID() +  "'", null);
    db.setTransactionSuccessful();
    db.endTransaction();
}
    public void addCustomLocation(LocationModel locationModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        SQLiteDatabase db_read = this.getReadableDatabase();
        String qry = "select * from " + LOCATION_TABLE + " where " + ID
                + " = '" + locationModel.getID() +  "'";
//         String qry = "select * from " + LOCATION_TABLE + " where " + ID
//                + " = '" + locationModel.getID() + "' and " + LONGITUDE + "='" + lng + "' and " + TYPE + "='" + InterConst.OTHER + "'";
        Cursor cur = null;
        db.beginTransaction();
        try {
            cur = db_read.rawQuery(qry, null);
            ContentValues values = new ContentValues();
            values.put(LABEL_NAME, locationModel.getLabelName());
            values.put(LATITUDE, locationModel.getLatitude());
            values.put(LONGITUDE, locationModel.getLongitude());
            values.put(TYPE, locationModel.getType());
            values.put(PLACE, locationModel.getPlace());
            values.put(HOUSE_NO, locationModel.getHouse_flat());
            values.put(LANDMARK, locationModel.getLandmark());

            if (cur.getCount() > 0) {
                db.update(LOCATION_TABLE, values, ID + " = '" + locationModel.getID() + "'", null);

            } else {
                db.insertOrThrow(LOCATION_TABLE, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e("exc db", "is " + e);
        } finally {
            db.endTransaction();
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
            db.close();
        }
    }


    public ArrayList<LocationModel> getLocations() {
        ArrayList<LocationModel> arrModel = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String qry = "select * from " + LOCATION_TABLE +" ORDER BY "+ TYPE +" ASC";
        Cursor cur = null, cursorMembers = null;
        try {
            cur = db.rawQuery(qry, null);
            Log.d("databse", "count " + cur.getCount());
            if (cur.getCount() > 0) {
                cur.moveToFirst();
                while (!cur.isAfterLast()) {
                    LocationModel locationModel = new LocationModel();
                    locationModel.setID(cur.getString(0));
                    locationModel.setLabelName(cur.getString(1));
                    locationModel.setPlace(cur.getString(2));
                    locationModel.setLatitude(cur.getString(3));
                    locationModel.setType(cur.getInt(4));
                    locationModel.setLongitude(cur.getString(6));
                    locationModel.setHouse_flat(cur.getString(7));
                    locationModel.setLandmark(cur.getString(8));
                    arrModel.add(locationModel);
                    cur.moveToNext();
                }
            } else {
                return arrModel;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cur != null && !cur.isClosed()) {
                cur.close();
            }
            if (cursorMembers != null && !cursorMembers.isClosed()) {
                cursorMembers.close();
            }
        }
        return arrModel;
    }
    public void delete_records() {
        try {
            SQLiteDatabase db = this.getWritableDatabase();

//            String qry = "DELETE FROM " + PROFILE_TABLE + " ";
//            db.execSQL(qry);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
