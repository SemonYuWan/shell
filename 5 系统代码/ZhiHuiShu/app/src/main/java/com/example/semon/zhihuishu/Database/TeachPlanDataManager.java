package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.TeachPlanData;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeachPlanDataManager {
    private static final String TAG = "TeachPlanDataManager";
    public static final String TABLE_NAME_TEACHPLAN = "teachPlan";
    public static final String ID = "_id";
    public static final String PLAN_TITLE = "planTitle";
    public static final String PLAN_CONTEXT = "planContext";//计划的内容
    public static final String SCHOOL_ID = "schoolId";//所属学校
    public static final String CLASS_ID = "classId";//所属班级
    public static final String CREATER = "creater";//创建者
    public static final String CREATE_TIME = "createTime";//创建时间
    public static final String REMARK = "remark";//备注

    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;


    public TeachPlanDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "TeachPlanDataManager construction!");
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

    public List findAllTeachPlan(){
        List <TeachPlanData> teachPlanList = new ArrayList<>();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_TEACHPLAN, null, null, null, null, null, CREATE_TIME + " DESC");
        //        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from "+TABLE_NAME_NOTICE,null);
        while (mCursor.moveToNext()){
            TeachPlanData teachPlans = new TeachPlanData();
            teachPlans.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            teachPlans.setPlanTitle(mCursor.getString(mCursor.getColumnIndex(PLAN_TITLE)));
            teachPlans.setPlanContext(mCursor.getString(mCursor.getColumnIndex(PLAN_CONTEXT)));
            teachPlans.setSchoolId(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            teachPlans.setClassId(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            teachPlans.setCreater(mCursor.getString(mCursor.getColumnIndex(CREATER)));
            teachPlans.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREATE_TIME)));
            teachPlans.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            teachPlanList.add(teachPlans);
        }
        return teachPlanList;
    }

    public TeachPlanData findTeachPlanById(String id){

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_TEACHPLAN, null, ID + " = ?", new
                String[]{id}, null, null, null);
        TeachPlanData mTeachPlanData = new TeachPlanData();
        while (mCursor.moveToNext()){
            mTeachPlanData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mTeachPlanData.setPlanTitle(mCursor.getString(mCursor.getColumnIndex(PLAN_TITLE)));
            mTeachPlanData.setPlanContext(mCursor.getString(mCursor.getColumnIndex(PLAN_CONTEXT)));
            mTeachPlanData.setSchoolId(mCursor.getString(mCursor.getColumnIndex(SCHOOL_ID)));
            mTeachPlanData.setClassId(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            mTeachPlanData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREATER)));
            mTeachPlanData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREATE_TIME)));
            mTeachPlanData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
        }
        return mTeachPlanData;
    }

    //添加通知
    public long addTeachPlan(TeachPlanData teachPlans){
        ContentValues values = new ContentValues();
        values.put(ID,teachPlans.getId());
        values.put(PLAN_TITLE,teachPlans.getPlanTitle());
        values.put(PLAN_CONTEXT,teachPlans.getPlanContext());
        values.put(SCHOOL_ID,teachPlans.getSchoolId());
        values.put(CLASS_ID,teachPlans.getClassId());
        values.put(CREATER,teachPlans.getCreater());
        values.put(CREATE_TIME, (new Date()).toLocaleString());
        values.put(REMARK,teachPlans.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_TEACHPLAN, null, values);
    }

    //根据id删除
    public boolean deleteTeachPlanDataById(String id) {
        return mSQLiteDatabase.delete(TABLE_NAME_TEACHPLAN, ID + "=?", new String[]{id}) > 0;
    }
}
