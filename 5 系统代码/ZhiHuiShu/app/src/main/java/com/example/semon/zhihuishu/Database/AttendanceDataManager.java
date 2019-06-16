package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * 考勤数据库操作
 */
public class AttendanceDataManager {
    private static final String TAG = "AttendanceDataManager";
    public static final String TABLE_NAME_ATTENDANCE = "attendance";
    public static final String ID = "_id";
    public static final String CHIRD_ID = "child_id";
    public static final String ATT_TIME = "time";
    public static final String REMARK = "remark";

    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;


    public AttendanceDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "AttendanceDataManager construction!");
    }
    //打开数据库
    public void openDataBase() throws SQLException {
        mDatabaseHelper = new DataManagerHelper(mContext);
        mSQLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    //关闭数据库
    public void closeDataBase() throws SQLException {
        mDatabaseHelper.close();
    }

    public void AddAttendanceData(){
        ContentValues values = new ContentValues();
//        values.put(ID,);
    }
}
