package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.SchoolData;

/**
 * 学校相关数据库操作
 */
public class SchoolDataManager {
    //一些宏定义和声明
    private static final String TAG = "SchoolDataManager";
    public static final String TABLE_NAME_SCHOOL = "school";
    public static final String ID = "_id";
    public static final String SCHOOL_NAME = "school_name";
    public static final String SCHOOL_ADDRESS = "school_address";
    public static final String SCHOOL_TEL = "school_tel";
    public static final String SCHOOL_HEADMASTER_ID = "school_headmaster_id";
    public static final String SCHOOL_SUMMARY = "school_summary";
    public static final String SCHOOL_TEACHER = "school_teacher";
    public static final String SCHOOL_AWARDS = "school_awards";
    public static final String REMARK = "remark";


    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;

    public SchoolDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "SchoolDataManager construction!");
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

    public SchoolData findSchoolBySchoolId(String schoolId){
        SchoolData schoolData = new SchoolData();
//        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_SCHOOL, null, null, null, null, null, null,null);
        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from "+TABLE_NAME_SCHOOL+" where "+ID+"=?",new String[]{schoolId});
        if(mCursor.getCount() == 1){
            mCursor.moveToNext();
            schoolData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            schoolData.setSchool_name(mCursor.getString(mCursor.getColumnIndex(SCHOOL_NAME)));
            schoolData.setSchool_address(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ADDRESS)));
            schoolData.setSchool_tel(mCursor.getString(mCursor.getColumnIndex(SCHOOL_TEL)));
            schoolData.setSchool_headmasterId(mCursor.getString(mCursor.getColumnIndex(SCHOOL_HEADMASTER_ID)));
            schoolData.setSchool_summary(mCursor.getString(mCursor.getColumnIndex(SCHOOL_SUMMARY)));
            schoolData.setSchool_teacher(mCursor.getString(mCursor.getColumnIndex(SCHOOL_TEACHER)));
            schoolData.setSchool_awards(mCursor.getString(mCursor.getColumnIndex(SCHOOL_AWARDS)));
            schoolData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
        }
        return schoolData;
    }
    //添加学校
    public long addSchool(SchoolData schoolData){
        ContentValues values = new ContentValues();
        values.put(ID,schoolData.getId());
        values.put(SCHOOL_NAME,schoolData.getSchool_name());
        values.put(SCHOOL_ADDRESS,schoolData.getSchool_address());
        values.put(SCHOOL_TEL,schoolData.getSchool_tel());
        values.put(SCHOOL_HEADMASTER_ID,schoolData.getSchool_headmasterId());
        values.put(SCHOOL_SUMMARY,schoolData.getSchool_summary());
        values.put(SCHOOL_TEACHER,schoolData.getSchool_teacher());
        values.put(SCHOOL_AWARDS,schoolData.getSchool_awards());
        values.put(REMARK,schoolData.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_SCHOOL, null, values);
    }
    //更新修改学校
    public long updateSchoolData(SchoolData schoolData){
        ContentValues values = new ContentValues();
        values.put(ID,schoolData.getId());
        values.put(SCHOOL_NAME,schoolData.getSchool_name());
        values.put(SCHOOL_ADDRESS,schoolData.getSchool_address());
        values.put(SCHOOL_TEL,schoolData.getSchool_tel());
        values.put(SCHOOL_HEADMASTER_ID,schoolData.getSchool_headmasterId());
        values.put(SCHOOL_SUMMARY,schoolData.getSchool_summary());
        values.put(SCHOOL_TEACHER,schoolData.getSchool_teacher());
        values.put(SCHOOL_AWARDS,schoolData.getSchool_awards());
        values.put(REMARK,schoolData.getRemark());
        return mSQLiteDatabase.update(TABLE_NAME_SCHOOL,values,ID + "=?",new String[] {schoolData.getId()});
    }

}
