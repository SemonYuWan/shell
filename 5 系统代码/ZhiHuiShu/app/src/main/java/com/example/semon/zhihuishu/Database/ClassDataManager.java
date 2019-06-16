package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.ClassData;

import java.util.ArrayList;
import java.util.List;

/**
 * 班级相关数据库操作
 */
public class ClassDataManager {
    //一些宏定义和声明
    private static final String TAG = "ClassDataManager";
    public static final String TABLE_NAME_CLASS = "class";
    public static final String ID = "_id";
    public static final String CLASS_NAME = "class_name";
    public static final String SCHOOL_ID = "school_id";
    public static final String CLASS_MANAGE = "class_manage";
    public static final String CLASS_TEACHER_COUNT = "class_teacher_count";
    public static final String CLASS_STUDENT_COUNT = "class_student_count";
    public static final String REMARK = "remark";


    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;

    public ClassDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "ClassDataManager construction!");
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

    public List findAllClassBySchoolId(String schoolId){
        List <ClassData> classDataList = new ArrayList<>();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_CLASS, null, SCHOOL_ID + "=?",
                new  String[]{schoolId}, null, null, null, null);
        while (mCursor.moveToNext()){
            ClassData classData = new ClassData();
            classData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            classData.setClass_name(mCursor.getString(mCursor.getColumnIndex(CLASS_NAME)));
            classData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            classData.setClass_manageId(mCursor.getString(mCursor.getColumnIndex(CLASS_MANAGE)));
            classData.setClass_teacher_count(mCursor.getString(mCursor.getColumnIndex(CLASS_TEACHER_COUNT)));
            classData.setClass_student_count(mCursor.getString(mCursor.getColumnIndex(CLASS_STUDENT_COUNT)));
            classData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            classDataList.add(classData);
        }
        return classDataList;
    }

    public ClassData findClassById(String id){
        ClassData classData = new ClassData();
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_CLASS,null, ID +"=?", new String[]{id}, null, null,null,null);
        //        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from "+TABLE_NAME_CLASS,null);
        if (mCursor.getCount() ==1){
            mCursor.moveToNext();
            classData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            classData.setClass_name(mCursor.getString(mCursor.getColumnIndex(CLASS_NAME)));
            classData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            classData.setClass_manageId(mCursor.getString(mCursor.getColumnIndex(CLASS_MANAGE)));
            classData.setClass_teacher_count(mCursor.getString(mCursor.getColumnIndex(CLASS_TEACHER_COUNT)));
            classData.setClass_student_count(mCursor.getString(mCursor.getColumnIndex(CLASS_STUDENT_COUNT)));
            classData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
        }
        return classData;
    }

    //添加班级
    public long addClass(ClassData classData){
        ContentValues values = new ContentValues();
        values.put(ID,classData.getId());
        values.put(CLASS_NAME,classData.getClass_name());
        values.put(SCHOOL_ID,classData.getSchool_id());
        values.put(CLASS_MANAGE,classData.getClass_manageId());
        values.put(CLASS_TEACHER_COUNT,classData.getClass_teacher_count());
        values.put(CLASS_STUDENT_COUNT,classData.getClass_student_count());
        values.put(REMARK,classData.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_CLASS, null, values);
    }
    //更新修改班级
    public long updateClass(ClassData classData){
        ContentValues values = new ContentValues();
        values.put(ID,classData.getId());
        values.put(CLASS_NAME,classData.getClass_name());
        values.put(SCHOOL_ID,classData.getSchool_id());
        values.put(CLASS_MANAGE,classData.getClass_manageId());
        values.put(CLASS_TEACHER_COUNT,classData.getClass_teacher_count());
        values.put(CLASS_STUDENT_COUNT,classData.getClass_student_count());
        values.put(REMARK,classData.getRemark());
        return mSQLiteDatabase.update(TABLE_NAME_CLASS,values,ID + "=?",new String[] {classData.getId()});
    }

    //根据id删除用户
    public boolean deleteClassDataById(String id) {
        return mSQLiteDatabase.delete(TABLE_NAME_CLASS, ID + "=?", new String[]{id}) > 0;
    }

}
