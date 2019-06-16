package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.CircleData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * 班级圈数据库操作
 */

public class CircleDataManager {
    //一些宏定义和声明
    private static final String TAG = "CircleDataManager";
    public static final String TABLE_NAME_CIRCLE = "circle";
    public static final String ID = "_id";
    public static final String CONTENT = "content";//内容
    public static final String IMAGE = "image";//图片
    public static final String CREARER = "creater";//创建人
    public static final String CREARER_ID = "creater_id";//创建人
    public static final String CLASS_ID = "class_id";//班级ID
    public static final String CREARE_TIME = "createTime";//创建时间
    public static final String REMARK = "remark";//备注


    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;

    public CircleDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "CircleDataManager construction!");
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

    //查找所有发现文章
    public List findAllCircle() {
        List<CircleData> circleDataList = new ArrayList<>();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_CIRCLE, null, null, null, null,
                null, CREARE_TIME + " DESC");

        while (mCursor.moveToNext()) {
            CircleData mCircleData = new CircleData();
            mCircleData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mCircleData.setContent(mCursor.getString(mCursor.getColumnIndex(CONTENT)));
            mCircleData.setImage(mCursor.getString(mCursor.getColumnIndex(IMAGE)));
            mCircleData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREARER)));
            mCircleData.setCreater_id(mCursor.getString(mCursor.getColumnIndex(CREARER_ID)));
            mCircleData.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            mCircleData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREARE_TIME)));
            mCircleData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            circleDataList.add(mCircleData);
        }
        return circleDataList;
    }

    //通过ID查找发现文章
    public CircleData findCircleById(String id) {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日");
        Date date = new Date(System.currentTimeMillis());
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_CIRCLE, null, ID + " = ?", new
                String[]{id}, null, null, null);

        CircleData mCircleData = new CircleData();
        while (mCursor.moveToNext()) {

            mCircleData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mCircleData.setContent(mCursor.getString(mCursor.getColumnIndex(CONTENT)));
            mCircleData.setImage(mCursor.getString(mCursor.getColumnIndex(IMAGE)));
            mCircleData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREARER)));
            mCircleData.setCreater_id(mCursor.getString(mCursor.getColumnIndex(CREARER_ID)));
            mCircleData.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            mCircleData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREARE_TIME)));
            mCircleData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
        }
        return mCircleData;
    }

    //通过班级id查所有班级圈
    public List findAllCircleByClassId(String classId){
        List<CircleData> circleDataList = new ArrayList<>();

        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_CIRCLE, null, CLASS_ID + "=?", new
                String[]{classId}, null, null, CREARE_TIME + " DESC");

        while (mCursor.moveToNext()) {
            CircleData mCircleData = new CircleData();
            mCircleData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mCircleData.setContent(mCursor.getString(mCursor.getColumnIndex(CONTENT)));
            mCircleData.setImage(mCursor.getString(mCursor.getColumnIndex(IMAGE)));
            mCircleData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREARER)));
            mCircleData.setCreater_id(mCursor.getString(mCursor.getColumnIndex(CREARER_ID)));
            mCircleData.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            mCircleData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREARE_TIME)));
            mCircleData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            circleDataList.add(mCircleData);
        }
        return circleDataList;
    }

    //通过班级id查所有班级圈
    public List findAllCircleBySchoolId(String schoolId){
        List<CircleData> circleDataList = new ArrayList<>();

        String findAllCircleBySchoolIdSql = "select c.* from " + TABLE_NAME_CIRCLE + " c," +
                ClassDataManager.TABLE_NAME_CLASS + " cl where cl." + ClassDataManager.SCHOOL_ID
                + "=? and c." + CLASS_ID + "=cl." + ClassDataManager.ID + " order by " + CREARE_TIME + " desc;";
        Cursor mCursor = mSQLiteDatabase.rawQuery(findAllCircleBySchoolIdSql,new String[]{schoolId});

        while (mCursor.moveToNext()) {
            CircleData mCircleData = new CircleData();
            mCircleData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mCircleData.setContent(mCursor.getString(mCursor.getColumnIndex(CONTENT)));
            mCircleData.setImage(mCursor.getString(mCursor.getColumnIndex(IMAGE)));
            mCircleData.setCreater(mCursor.getString(mCursor.getColumnIndex(CREARER)));
            mCircleData.setCreater_id(mCursor.getString(mCursor.getColumnIndex(CREARER_ID)));
            mCircleData.setClass_id(mCursor.getString(mCursor.getColumnIndex(CLASS_ID)));
            mCircleData.setCreateTime(mCursor.getString(mCursor.getColumnIndex(CREARE_TIME)));
            mCircleData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            circleDataList.add(mCircleData);
        }
        return circleDataList;
    }

    //添加发现文章
    public long addCircle(CircleData mCircleData) {
        ContentValues values = new ContentValues();
        values.put(ID, mCircleData.getId());
        values.put(CONTENT, (mCircleData.getContent()));
        values.put(IMAGE, (mCircleData.getImage()));
        values.put(CREARER, (mCircleData.getCreater()));
        values.put(CREARER_ID, (mCircleData.getCreater_id()));
        values.put(CLASS_ID, (mCircleData.getClass_id()));
        values.put(CREARE_TIME, mCircleData.getCreateTime());
        values.put(REMARK, mCircleData.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_CIRCLE, null, values);
    }
}
