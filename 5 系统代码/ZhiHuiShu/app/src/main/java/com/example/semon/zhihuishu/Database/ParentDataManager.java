package com.example.semon.zhihuishu.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.semon.zhihuishu.Constant.ChildData;
import com.example.semon.zhihuishu.Constant.ParentData;

import java.util.ArrayList;
import java.util.List;

/**
 * 家长操作相关数据库操作
 */
public class ParentDataManager {
    //一些宏定义和声明
    private static final String TAG = "ParentDataManager";
    public static final String TABLE_NAME_PARENT = "parent";
    public static final String ID = "_id";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String NAME = "name";
    public static final String TEL = "tel";
    public static final String ADDRESS = "address";
    public static final String CHILD_ID = "child_id";
    public static final String CHILD_RELATIONSHIP = "child_relationship";
    public static final String REMARK = "remark";

    private Context mContext = null;
    private SQLiteDatabase mSQLiteDatabase = null;
    private DataManagerHelper mDatabaseHelper = null;


    public ParentDataManager(Context context) {
        mContext = context;
        Log.i(TAG, "ParentDataManager construction!");
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

    //添加新用户，即注册
    public long insertParentData(ParentData parentData) {
        ContentValues values = new ContentValues();
        values.put(ID, parentData.getId());
        values.put(USER, parentData.getUser());
        values.put(PASSWORD, parentData.getPassword());
        values.put(NAME, parentData.getName());
        values.put(TEL, parentData.getTel());
        values.put(ADDRESS, parentData.getAddress());
        values.put(CHILD_ID, parentData.getChild_id());
        values.put(CHILD_RELATIONSHIP, parentData.getChild_relationship());
        values.put(REMARK, parentData.getRemark());
        return mSQLiteDatabase.insert(TABLE_NAME_PARENT, ID, values);
    }

    //更新修改家长信息
    public boolean updateParentData(ParentData parentData) {
        ContentValues values = new ContentValues();
        values.put(ID, parentData.getId());
        values.put(USER, parentData.getUser());
        values.put(PASSWORD, parentData.getPassword());
        values.put(NAME, parentData.getName());
        values.put(TEL, parentData.getTel());
        values.put(ADDRESS, parentData.getAddress());
        values.put(CHILD_ID, parentData.getChild_id());
        values.put(CHILD_RELATIONSHIP, parentData.getChild_relationship());
        values.put(REMARK, parentData.getRemark());
        return mSQLiteDatabase.update(TABLE_NAME_PARENT, values, null, null) > 0;
    }

    //根据ID查找家长信息
    public ParentData findParentDataById(String id) throws SQLException {
        ParentData mParentData = new ParentData();
        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME_PARENT, null, ID
                + "=" + id, null, null, null, null, null);
        if (mCursor.getCount() == 1) {
            mCursor.moveToNext();
            mParentData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mParentData.setUser(mCursor.getString(mCursor.getColumnIndex(USER)));
            mParentData.setPassword(mCursor.getString(mCursor.getColumnIndex(PASSWORD)));
            mParentData.setName(mCursor.getString(mCursor.getColumnIndex(NAME)));
            mParentData.setTel(mCursor.getString(mCursor.getColumnIndex(TEL)));
            mParentData.setAddress(mCursor.getString(mCursor.getColumnIndex(ADDRESS)));
            mParentData.setChild_id(mCursor.getString(mCursor.getColumnIndex(CHILD_ID)));
            mParentData.setChild_relationship(mCursor.getString(mCursor.getColumnIndex(CHILD_RELATIONSHIP)));
            mParentData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
        }
        return mParentData;
    }

    //根据家长姓名查找家长信息
    public ParentData fetchParentDataById(String parentId) throws SQLException {
        ParentData mParentData = new ParentData();
        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME_PARENT, null, ID
                + "=?", new String[]{parentId}, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToNext();
            mParentData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            mParentData.setUser(mCursor.getString(mCursor.getColumnIndex(USER)));
            mParentData.setName(mCursor.getString(mCursor.getColumnIndex(NAME)));
            mParentData.setTel(mCursor.getString(mCursor.getColumnIndex(TEL)));
            mParentData.setAddress(mCursor.getString(mCursor.getColumnIndex(ADDRESS)));
            mParentData.setChild_id(mCursor.getString(mCursor.getColumnIndex(CHILD_ID)));
            mParentData.setChild_relationship(mCursor.getString(mCursor.getColumnIndex
                    (CHILD_RELATIONSHIP)));
            mParentData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
        }
        return mParentData;
    }

    //根据孩子姓名查找家长信息
    public Cursor fetchParentDataByChildName(String childName) throws SQLException {
        String fetchChildSql = "select * from " + ParentDataManager.TABLE_NAME_PARENT + " where "
                + ParentDataManager.CHILD_ID + " = (select " +
                ChildDataManager.ID + " from " + ChildDataManager.TABLE_NAME_CHILD + " where " +
                ChildDataManager.NAME + " = '?');";
        Cursor mCursor = mSQLiteDatabase.rawQuery(fetchChildSql, new String[]{childName});
        //        Cursor mCursor = mSQLiteDatabase.query(false, TABLE_NAME_CHILD, null, NAME
        //                + "=" + name, null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //根据id删除用户
    public boolean deleteParentDataById(String id) {
        return mSQLiteDatabase.delete(TABLE_NAME_PARENT, ID + "=?", new String[]{id}) > 0;
    }

    //根据用户名注销
    public boolean deleteParentDataByName(String name) {
        return mSQLiteDatabase.delete(TABLE_NAME_PARENT, NAME + "=?", new String[]{name}) > 0;
    }

    //根据用户名找用户，可以判断注册时用户名是否已经存在
    public int findUserByName(String userName) {
        Log.i(TAG, "findUserByName , userName=" + userName);
        int result = 0;
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_PARENT, null, NAME + "=" + userName,
                null, null, null, null);
        if (mCursor != null) {
            result = mCursor.getCount();
            mCursor.close();
            Log.i(TAG, "findUserByName , result=" + result);
        }
        return result;
    }

    //根据用户名和密码找用户，用于登录
    public String findParentByUserNameAndPwd(String userName, String pwd) {
        Log.i(TAG, "findUserByNameAndPwd");
        int result = 0;
        String userId = null;
        //        Cursor mCursor = mSQLiteDatabase.rawQuery("select * from "+TABLE_NAME_USER+"
        // where ?=? and ?=?",new String[]{USER_NAME,userName,USER_PWD,pwd});
        Cursor mCursor = mSQLiteDatabase.query(TABLE_NAME_PARENT, null, USER + "= '" + userName +
                        "' and " + PASSWORD + "='" + pwd + "'",
                null, null, null, null);
        if (mCursor != null) {
            result = mCursor.getCount();
            if (result == 1) {
                mCursor.moveToNext();
                userId = mCursor.getString(mCursor.getColumnIndex(ParentDataManager.ID));

            }

            mCursor.close();
            Log.i(TAG, "findUserByNameAndPwd , result=" + result);
        }
        return userId;
    }

    //查找班级中的所有家长
    public List findAllParentsByClass(String classId) {
        Log.i(TAG, "findAllParents");
        List<ParentData> parentDataList = new ArrayList<>();
        //        String findAllParentsSql = "select * from " + ParentDataManager
        // .TABLE_NAME_PARENT ;
        String findAllParentsSql = "select p.*,c." + ChildDataManager.NAME + " childName from " +
                ParentDataManager.TABLE_NAME_PARENT + " p, " + ChildDataManager.TABLE_NAME_CHILD
                + " c where p." + ParentDataManager.CHILD_ID + " = c." + ChildDataManager.ID + " " +
                "and c." + ChildDataManager.CLASS_ID + "=?;";
        Cursor mCursor = mSQLiteDatabase.rawQuery(findAllParentsSql, new String[]{classId});

        while (mCursor.moveToNext()) {
            ParentData parentData = new ParentData();
            parentData.setId(mCursor.getString(mCursor.getColumnIndex(ID)));
            parentData.setUser(mCursor.getString(mCursor.getColumnIndex(USER)));
            parentData.setName(mCursor.getString(mCursor.getColumnIndex(NAME)));
            parentData.setTel(mCursor.getString(mCursor.getColumnIndex(TEL)));
            parentData.setAddress(mCursor.getString(mCursor.getColumnIndex(ADDRESS)));
            parentData.setChild_id(mCursor.getString(mCursor.getColumnIndex("childName")));
            parentData.setChild_relationship(mCursor.getString(mCursor.getColumnIndex
                    (CHILD_RELATIONSHIP)));
            parentData.setRemark(mCursor.getString(mCursor.getColumnIndex(REMARK)));
            parentDataList.add(parentData);
        }
        return parentDataList;
    }

    //查找孩子信息
    public ChildData findChildDataByParentId(String parentId) {
        Log.i(TAG, "findChildClassIdByParentId");
        ChildData mChildData = new ChildData();
        String findChildClassIdByParentIdSql = "select c.* from "
                + ParentDataManager.TABLE_NAME_PARENT + " p," + ChildDataManager.TABLE_NAME_CHILD
                + " c where p." + ParentDataManager.ID + "=? and p." + ParentDataManager.CHILD_ID
                + "=c." + ChildDataManager.ID + ";";
        Cursor mCursor = mSQLiteDatabase.rawQuery(findChildClassIdByParentIdSql, new
                String[]{parentId});
        if (mCursor.getCount() ==1){
            mCursor.moveToNext();
            mChildData.setId(mCursor.getString(mCursor.getColumnIndex(ChildDataManager.ID)));
            mChildData.setName(mCursor.getString(mCursor.getColumnIndex(ChildDataManager.NAME)));
            mChildData.setSex(mCursor.getString(mCursor.getColumnIndex(ChildDataManager.SEX)));
            mChildData.setBirthday(mCursor.getString(mCursor.getColumnIndex(ChildDataManager.BIRTHDAY)));
            mChildData.setSchool_id(mCursor.getString(mCursor.getColumnIndex(ChildDataManager.SCHOOL_ID)));
            mChildData.setClass_id(mCursor.getString(mCursor.getColumnIndex(ChildDataManager.CLASS_ID)));
            mChildData.setRemark(mCursor.getString(mCursor.getColumnIndex(ChildDataManager.REMARK)));
        }
        return mChildData;
    }

    //查找孩子的所在班级
    public String findChildClassIdByParentId(String parentId) {
        Log.i(TAG, "findChildClassIdByParentId");
        String classId=null;
        String findChildClassIdByParentIdSql = "select c." + ChildDataManager.CLASS_ID + " classId from "
                + ParentDataManager.TABLE_NAME_PARENT + " p," + ChildDataManager.TABLE_NAME_CHILD
                + " c where p." + ParentDataManager.ID + "=? and p." + ParentDataManager.CHILD_ID
                + "=c." + ChildDataManager.ID + ";";
        Cursor mCursor = mSQLiteDatabase.rawQuery(findChildClassIdByParentIdSql, new
                String[]{parentId});
        if (mCursor.getCount() ==1){
            mCursor.moveToNext();
            classId = mCursor.getString(mCursor.getColumnIndex("classId"));
        }
        return classId;
    }

    //查找孩子的所在班级名称
    public String findChildClassNameByParentId(String parentId) {
        Log.i(TAG, "findChildClassIdByParentId");
        String className=null;
        String findChildClassIdByParentIdSql = "select mclass." + ClassDataManager.CLASS_NAME + " className from "
                + ParentDataManager.TABLE_NAME_PARENT + " p," + ChildDataManager.TABLE_NAME_CHILD
                + " c,"+ClassDataManager.TABLE_NAME_CLASS+" mclass where p." + ParentDataManager.ID + "=? and p." + ParentDataManager.CHILD_ID
                + "=c." + ChildDataManager.ID + " and c."+ChildDataManager.CLASS_ID+" = mclass."+ClassDataManager.ID+";";
        Cursor mCursor = mSQLiteDatabase.rawQuery(findChildClassIdByParentIdSql, new
                String[]{parentId});
        if (mCursor.getCount() ==1){
            mCursor.moveToNext();
            className = mCursor.getString(mCursor.getColumnIndex("className"));
        }
        return className;
    }

    //查找孩子的所在班级
    public String findChildSchoolIdByParentId(String parentId) {
        Log.i(TAG, "findChildSchoolIdByParentId");
        String classId=null;
        String findChildClassIdByParentIdSql = "select c." + ChildDataManager.SCHOOL_ID + " schoolId from "
                + ParentDataManager.TABLE_NAME_PARENT + " p," + ChildDataManager.TABLE_NAME_CHILD
                + " c where p." + ParentDataManager.ID + "=? and p." + ParentDataManager.CHILD_ID
                + "=c." + ChildDataManager.ID + ";";
        Cursor mCursor = mSQLiteDatabase.rawQuery(findChildClassIdByParentIdSql, new
                String[]{parentId});
        if (mCursor.getCount() ==1){
            mCursor.moveToNext();
            classId = mCursor.getString(mCursor.getColumnIndex("schoolId"));
        }
        return classId;
    }
}
