package com.goatenvmonitor;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {
    final String CREATE_TABLE_SQL = "create table tb_env (_id integer primary key autoincrement,goatHouseID,temperature,humidity,AmmoniaConcentration,airFlowRate)";//定义创建数据表的SQL语句

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, null, version);//重写构造方法并设置工厂为null

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);//创建数据表
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // 重写基类的onUpgrade()方法，以便数据库版本更新
        //提示版本更新并输出旧版本信息与新版本信息
        Log.i("main","--版本更新"+oldVersion+"-->"+newVersion);
    }
}
