package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.ClassStarData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClassStarDataManager {


    //一些宏定义和声明
    private static final String TAG = "ClassStarDataManager";
    public static final String TABLE_NAME_CLASSSTAR = "classStar";
    public static final String ID = "_id";
    public static final String SCHOOL_ID = "school_id";
    public static final String CLASS_ID = "class_id";
    public static final String CHILD_ID = "child_id";
    public static final String DATE = "date";
    public static final String REMARK = "remark";


    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;

    public ClassStarDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "ClassStarDataManager construction!");
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

    //查找班级中的所有班级之星
    public List findAllClassStarByClassId(String classId) {
        List<ClassStarData> classStarDataList = new ArrayList<>();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_CLASSSTAR, null, ID +"=?",new String[]{classId}, null,
                null, DATE + " DESC");
        //        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from "+TABLE_NAME_NOTICE,
        // null);
        while (mCursor.moveToNext()) {
            ClassStarData mClassStarData = new ClassStarData();
            mClassStarData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mClassStarData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            mClassStarData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            mClassStarData.setChild_id(mCursor.getString(mCursor.getColumnIndex(CHILD_ID)));
            mClassStarData.setDate(mCursor.getString(mCursor.getColumnIndex(DATE)));
            mClassStarData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            classStarDataList.add(mClassStarData);
        }
        return classStarDataList;
    }

    //查找当天班级之星
    public ClassStarData findTodayClassStarByClassId(String classId) {
        //        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_CLASSSTAR, null, null, null,
        // null,
        //                null, null);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        //        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from " +
        // TABLE_NAME_CLASSSTAR +
        //                " WHERE " + DATE + " = ?", new String[]{simpleDateFormat.format(date)});
        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from " + TABLE_NAME_CLASSSTAR +
                " where "+CLASS_ID+"=? order by "+DATE+" DESC;", new String[]{classId});

        ClassStarData mClassStarData = new ClassStarData();
        if (mCursor.getCount() >0) {
            mCursor.moveToNext();
            mClassStarData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mClassStarData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            mClassStarData.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            mClassStarData.setChild_id(mCursor.getString(mCursor.getColumnIndex(CHILD_ID)));
            mClassStarData.setDate(mCursor.getString(mCursor.getColumnIndex(DATE)));
            mClassStarData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
        }
        return mClassStarData;
    }

    //设置班级之星
    public long setTodayClassStar(ClassStarData mClassStarData) {
        ContentValues values = new ContentValues();
        values.put(ID, mClassStarData.getId());
        values.put(SCHOOL_ID, mClassStarData.getSchool_id());
        values.put(CLASS_ID, (mClassStarData.getClass_id()));
        values.put(CHILD_ID, (mClassStarData.getChild_id()));
        values.put(DATE,  (new Date()).toLocaleString());
        values.put(REMARK, mClassStarData.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_CLASSSTAR, null, values);
    }

    //修改班级之星
    public long updateClassStar(ClassStarData mClassStarData) {
        ContentValues values = new ContentValues();

        values.put(SCHOOL_ID, mClassStarData.getSchool_id());
        values.put(CLASS_ID, (mClassStarData.getClass_id()));
        values.put(CHILD_ID, (mClassStarData.getChild_id()));
        values.put(DATE, mClassStarData.getDate());
        values.put(REMARK, mClassStarData.getRemark());
        return mSQLiteDatabase.update(TABLE_NAME_CLASSSTAR, values, ID + "=?", new
                String[]{mClassStarData.getId()});
    }
}
