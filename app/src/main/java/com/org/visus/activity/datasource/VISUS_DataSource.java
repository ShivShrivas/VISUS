package com.org.visus.activity.datasource;

import static com.org.visus.activity.sqlite.VISUS_SQLiteHelper.tblMyAction;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.org.visus.activity.sqlite.VISUS_SQLiteHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class VISUS_DataSource {
    public VISUS_SQLiteHelper visus_sqLiteHelper;
    SQLiteDatabase sqLiteDatabase;
    Context context;

    public VISUS_DataSource(Context context) {
        this.context = context;
        visus_sqLiteHelper = new VISUS_SQLiteHelper(context);
    }

    public void open() {
        sqLiteDatabase = visus_sqLiteHelper.getWritableDatabase();
    }

    public void close() {
        sqLiteDatabase.close();
    }

    public String getCurrentDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy kk:mm:ss", new Locale("en"));
        return sdf.format(new Date());
    }

    public int delete_tblMyAction() {
        return sqLiteDatabase.delete(tblMyAction, null, null);
    }

    /*public long insertVillage(String villageKey, String villageName) {
        long i = 0;
        ContentValues values = new ContentValues();
        values.put(SUMER_SQLiteHelper.villageKeyMaster, villageKey);
        values.put(SUMER_SQLiteHelper.villageNameMaster, villageName);
        i = sqLiteDatabase.insert(tblVillageDetails, null, values);
        return i;
    }*/

    /*public List<Village> getVillageDetails() {
        List<Village> list = new ArrayList<>();
        Village village;
        String sql = "select common_id,trim(villageKeyMaster) AS villageKeyMaster,trim(villageNameMaster) AS villageNameMaster From tblVillageDetails WHERE villageNameMaster!='&nbsp;' AND villageNameMaster!='' ORDER BY trim(villageNameMaster)";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            village = new Village();
            village.setVillageKey(cursor.getString(cursor.getColumnIndex(SUMER_SQLiteHelper.villageKeyMaster)));
            village.setVillageName(cursor.getString(cursor.getColumnIndex(SUMER_SQLiteHelper.villageNameMaster)));
            list.add(village);
            cursor.moveToNext();
        }
        return list;
    }*/


}
