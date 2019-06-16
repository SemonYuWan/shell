package com.example.semon.zhihuishu.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库初始化类
 */

public class DataManagerHelper extends SQLiteOpenHelper {
    //声明及定义
    private static final String TAG = "DataManager";
    private static final String DB_NAME = "ZhiHuiShu_db";
    private static final int DB_VERSION = 1;
    private Context mContext = null;

    //    private SQLiteDatabase mSQLiteDatabase = null;
    //    private DataManagerHelper mDatabaseHelper = null;


    //创建user表
    private static final String TableCreate_User = "CREATE TABLE " + UserDataManager
            .TABLE_NAME_USER + " ("
            + UserDataManager.ID + " integer primary key," + UserDataManager.USER_NAME + " varchar,"
            + UserDataManager.USER_PWD + " varchar," + UserDataManager.USER_ROLE + " varchar );";

    //创建孩子表
    private static final String TableCreate_Child = "CREATE TABLE " + ChildDataManager
            .TABLE_NAME_CHILD + " ( " + ChildDataManager.ID + " varchar(25) NOT " +
            "NULL, " + ChildDataManager.NAME + " varchar(255) , " + ChildDataManager.SEX + " " +
            "varchar(255), " + ChildDataManager.BIRTHDAY +
            " varchar(255) , " + ChildDataManager.PARENT_ID + " varchar(25) , " +
            ChildDataManager.SCHOOL_ID + " varchar(25) , " + ChildDataManager
            .CLASS_ID + " varchar(25) , " + ChildDataManager.REMARK + " varchar(255) , PRIMARY " +
            "KEY (" + ChildDataManager.ID + "));";

    //创建家长表
    private static final String TableCreate_Parent = "CREATE TABLE " + ParentDataManager
            .TABLE_NAME_PARENT + " (" + ParentDataManager.ID + " varchar(25) NOT " +
            "NULL," + ParentDataManager.USER + " varchar(255), " + ParentDataManager.PASSWORD + "" +
            " varchar(255), " + ParentDataManager.NAME + " varchar(255), " +
            "" + ParentDataManager.TEL + " varchar(255), " + ParentDataManager.ADDRESS + " " +
            "varchar(255), " + ParentDataManager.CHILD_ID + " varchar(25), " +
            ParentDataManager.CHILD_RELATIONSHIP + " varchar(255), " + ParentDataManager.REMARK +
            " varchar(255), PRIMARY KEY (" + ParentDataManager.ID + "));";

    //创建教师表
    private static final String TableCreate_Teacher = "CREATE TABLE " + TeacherDataManager
            .TABLE_NAME_TEACHER + " (" + TeacherDataManager.ID + " varchar(25) " +
            "NOT NULL," + TeacherDataManager.USER + " varchar(255), " + TeacherDataManager
            .PASSWORD + " varchar(255), " + TeacherDataManager.NAME + " varchar" +
            "(255), " + TeacherDataManager.TEL + " varchar(255), " + TeacherDataManager.SCHOOL_ID
            + " varchar(25), " + TeacherDataManager.CLASS_ID + "" +
            " varchar(25), " + TeacherDataManager.REMARK + " varchar(255), PRIMARY KEY (" +
            TeacherDataManager.ID + "));";

    //创建考勤表
    private static final String TableCreate_Attendance = "CREATE TABLE " + AttendanceDataManager
            .TABLE_NAME_ATTENDANCE + " (" + AttendanceDataManager.ID + " " +
            "varchar(25) NOT NULL," + AttendanceDataManager.CHIRD_ID + " varchar(25)," +
            AttendanceDataManager.ATT_TIME + " datetime," +
            AttendanceDataManager.REMARK + " varchar(255), PRIMARY KEY (" + AttendanceDataManager
            .ID + "));";

    //创建通知表
    private static final String TableCreate_Notice = "CREATE TABLE " + NoticeDataManager
            .TABLE_NAME_NOTICE + " ( " + NoticeDataManager.ID + " " +
            "varchar(25) NOT NULL,  " + NoticeDataManager.TITLE + " varchar(255)," +
            " " + NoticeDataManager.CONTEXT + " varchar(255) , " + NoticeDataManager.SCHOOL_ID +
            " varchar" +
            "(25), " + NoticeDataManager.CREATE_TIME + " datetime(0) , " + NoticeDataManager.TYPE
            + " varchar(255), " +
            NoticeDataManager.REMARK + " varchar(255) , PRIMARY KEY (" + NoticeDataManager.ID +
            "));";

    //创建教学计划表
    private static final String TableCreate_TeachPlan = "CREATE TABLE " + TeachPlanDataManager
            .TABLE_NAME_TEACHPLAN + " (" + TeachPlanDataManager.ID + " varchar(25) NOT NULL, " +
            TeachPlanDataManager.PLAN_TITLE + " varchar(255), " + TeachPlanDataManager
            .PLAN_CONTEXT + " varchar(255), " + TeachPlanDataManager.SCHOOL_ID + " varchar(255), " +
            "" + TeachPlanDataManager.CLASS_ID + " varchar(255), " + TeachPlanDataManager.CREATER
            + " varchar(255), " + TeachPlanDataManager.CREATE_TIME + " datetime, " +
            TeachPlanDataManager.REMARK + " varchar(255), PRIMARY KEY (" + TeachPlanDataManager
            .ID + "));";

    //创建班级之星表
    private static final String TableCreate_ClassStar = "CREATE TABLE " + ClassStarDataManager
            .TABLE_NAME_CLASSSTAR + " (" + ClassStarDataManager.ID + " varchar(50) NOT NULL, " +
            ClassStarDataManager.SCHOOL_ID + " varchar(50), " + ClassStarDataManager.CLASS_ID + "" +
            " varchar(50), " + ClassStarDataManager.CHILD_ID + " varchar(50), " +
            ClassStarDataManager.DATE + " varchar(50)," + ClassStarDataManager.REMARK + " varchar" +
            "(255), PRIMARY KEY (" + ClassStarDataManager.ID + "));";

    //创建发现表
    private static final String TableCreate_Discovery = "CREATE TABLE " + DiscoveryDataManager
            .TABLE_NAME_DISCOVERY + " (" + DiscoveryDataManager.ID + " varchar(50), " +
            DiscoveryDataManager.TITLE + " varchar(255), " + DiscoveryDataManager.CONTEXT +
            " varchar(255), " + DiscoveryDataManager.IMAGE + " varchar(255), " +
            DiscoveryDataManager.CREATER + " varchar(50), " + DiscoveryDataManager.CREATE_TIME +
            " date," + DiscoveryDataManager.LIKE + " INTEGER," + "PRIMARY KEY(" +
            DiscoveryDataManager.ID + "));";

    //创建消息表
    private static final String TableCreate_Message = "CREATE TABLE " + MessageDataManager
            .TABLE_NAME_MESSAGE + " (" + MessageDataManager.ID + " varchar(50), " +
            MessageDataManager.TITLE + " varchar(255), " + MessageDataManager.CONTEXT +
            " varchar(255), " + MessageDataManager.IMAGE + " varchar(255), " +
            MessageDataManager.CREARER + " varchar(50), " + MessageDataManager.CREARE_TIME +
            " datetime(0)," + MessageDataManager.TYPE + " varchar(100)," + MessageDataManager
            .LIKE + " " +
            "INTEGER," + "PRIMARY KEY(" + MessageDataManager.ID + "));";

    //学校表
    private static final String TableCreate_School = "CREATE TABLE " + SchoolDataManager
            .TABLE_NAME_SCHOOL + " (" + SchoolDataManager.ID + " varchar(50)," +
            SchoolDataManager.SCHOOL_NAME + " varchar(255)," + SchoolDataManager.SCHOOL_ADDRESS +
            " varchar(255)," + SchoolDataManager.SCHOOL_TEL + " varchar(12)," + SchoolDataManager
            .SCHOOL_HEADMASTER_ID + " varchar(50), " + SchoolDataManager.SCHOOL_SUMMARY + " " +
            "varchar(255)," + SchoolDataManager.SCHOOL_TEACHER + " varchar(255)," +
            SchoolDataManager.SCHOOL_AWARDS + " varchar(255)," + SchoolDataManager.REMARK + " " +
            "varchar(255),PRIMARY KEY(" + SchoolDataManager.ID + "));";
    //班级表
    private static final String TableCreate_Class = "CREATE TABLE " + ClassDataManager
            .TABLE_NAME_CLASS + " (" + ClassDataManager.ID + " VARCHAR(50)," + ClassDataManager
            .CLASS_NAME + " VARCHAR(50)," + ClassDataManager.SCHOOL_ID + " VARCHAR(50)," +
            ClassDataManager.CLASS_MANAGE + " VARCHAR(50)," + ClassDataManager
            .CLASS_TEACHER_COUNT + " VARCHAR(3)," + ClassDataManager.CLASS_STUDENT_COUNT + " " +
            "VARCHAR(3)," + ClassDataManager.REMARK + " VARCHAR(255),PRIMARY KEY(" +
            ClassDataManager.ID + "));";
    //园长表
    private static final String TableCreate_Headmaster = "CREATE TABLE " + HeadmasterDataManager
            .TABLE_NAME_HEADMASTER + " (" + HeadmasterDataManager.ID + " VARCHAR(50)," +
            HeadmasterDataManager.USER + " VARCHAR(50)," + HeadmasterDataManager.PASSWORD + " " +
            "VARCHAR(50)," + HeadmasterDataManager.NAME + " VARCHAR(50)," + HeadmasterDataManager
            .TEL + " VARCHAR(12)," + HeadmasterDataManager.SCHOOL_ID + " VARCHAR(50)," +
            HeadmasterDataManager.REMARK + " VARCHAR(255),PRIMARY KEY(" + HeadmasterDataManager
            .ID + "));";
    //学生作品表
    private static final String TableCreate_Opus = "CREATE TABLE " + OpusDataManager
            .TABLE_NAME_OPUS + " (" + OpusDataManager.ID + " VARCHAR(50)," + OpusDataManager
            .OPUS_TITLE + " VARCHAR(150)," + OpusDataManager.OPUS_CONTEXT + " VARCHAR(255)," +
            OpusDataManager.OPUS_IMAGE + " VARCHAR(255)," + OpusDataManager.SCHOOL_ID + " VARCHAR" +
            "(50)," + OpusDataManager.CLASS_ID + " VARCHAR(50)," + OpusDataManager.CREATER + " " +
            "VARCHAR(50)," + OpusDataManager.CREATE_TIME + " datetime(0)," + OpusDataManager
            .REMARK + " VARCHAR(255),PRIMARY KEY(" + OpusDataManager.ID + "));";
    //点评学生表
    private static final String TableCreate_ChildComment = "CREATE TABLE " +
            ChildCommentDataManager.TABLE_NAME_CHILDCOMMENT + " (" + ChildCommentDataManager.ID +
            " VARCHAR(50)," + ChildCommentDataManager.COMMENT_TITLE + " VARCHAR(50)," +
            ChildCommentDataManager.COMMENT_CONTEXT + " VARCHAR(255)," + ChildCommentDataManager
            .SCHOOL_ID + " VARCHAR(50)," + ChildCommentDataManager.CLASS_ID + " VARCHAR(50)," +
            ChildCommentDataManager.CREATER + " VARCHAR(50)," + ChildCommentDataManager
            .CREATE_TIME + " datetime(0)," + ChildCommentDataManager.REMARK + " VARCHAR(255)," +
            "PRIMARY KEY(" + ChildCommentDataManager.ID + "));";
    //亲子任务表
    private static final String TableCreate_ParentChildTask = "CREATE TABLE " +
            ParentChildTaskDataManager.TABLE_NAME_PARENTCHILDTASK + " (" +
            ParentChildTaskDataManager.ID + " VARCHAR(50)," + ParentChildTaskDataManager
            .TASK_TITLE + " VARCHAR(50)," + ParentChildTaskDataManager.TASK_CONTEXT + " VARCHAR" +
            "(255)," + ParentChildTaskDataManager.TASK_IMAGE + " VARCHAR(255)," +
            ParentChildTaskDataManager.SCHOOL_ID + " VARCHAR(50)," + ParentChildTaskDataManager
            .CLASS_ID + " VARCHAR(50)," + ParentChildTaskDataManager.CREATER + " VARCHAR(50)," +
            ParentChildTaskDataManager.CREATE_TIME + " datetime(0)," + ParentChildTaskDataManager
            .REMARK + " VARCHAR(255)," + "PRIMARY KEY(" + ParentChildTaskDataManager.ID + "));";
    //食谱表
    private static final String TableCreate_Recipe = "CREATE TABLE " + RecipeDataManager
            .TABLE_NAME_RECIPE + " (" + RecipeDataManager.ID + " VARCHAR(50)," +
            RecipeDataManager.RECIPE + " VARCHAR(255)," + RecipeDataManager.RECIPE_IMAGE + " " +
            "VARCHAR(255)," + RecipeDataManager.SCHOOL_ID + " VARCHAR(50)," + RecipeDataManager
            .CHECK + " VARCHAR(255)," + RecipeDataManager.CREARER + " VARCHAR(50)," +
            RecipeDataManager.CREARE_TIME + " datetime(0)," + RecipeDataManager.REMARK + " " +
            "VARCHAR(255),PRIMARY KEY(" + RecipeDataManager.ID + "));";
    //动态表
    private static final String TableCreate_Circle = "CREATE TABLE " + CircleDataManager
            .TABLE_NAME_CIRCLE + " (" + CircleDataManager.ID + " VARCHAR(50)," +
            CircleDataManager.CONTENT + " VARCHAR(255)," + CircleDataManager.IMAGE + " VARCHAR" +
            "(255)," + CircleDataManager.CREARER_ID + " VARCHER(50)," + CircleDataManager.CREARER
            + " VARCHAR(50)," + CircleDataManager.CLASS_ID + " VARCHAR(50)," + CircleDataManager
            .CREARE_TIME + " datetime(0)," + CircleDataManager.REMARK + " VARCHAR(255),PRIMARY KEY(" + CircleDataManager.ID + "));";
    //文章点评表
    private static final String TableCreate_Comment = "CREATE TABLE CLASS (ID VARCHAR(50)," +
            "class_name VARCHAR(50),school_id VARCHAR(50),class_manageId VARCHAR(50)," +
            "class_teacher_count VARCHAR(3),class_student_count VARCHAR(3),remark VARCHAR(255)," +
            "PRIMARY KEY(ID));";
    //文章点赞表
    private static final String TableCreate_Like = "CREATE TABLE CLASS (ID VARCHAR(50),class_name" +
            " VARCHAR(50),school_id VARCHAR(50),class_manageId VARCHAR(50),class_teacher_count " +
            "VARCHAR(3),class_student_count VARCHAR(3),remark VARCHAR(255),PRIMARY KEY(ID));";


    public DataManagerHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "db.getVersion()=" + db.getVersion());
        db.execSQL("DROP TABLE IF EXISTS " + UserDataManager.TABLE_NAME_USER + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ChildDataManager.TABLE_NAME_CHILD + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ParentDataManager.TABLE_NAME_PARENT + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TeacherDataManager.TABLE_NAME_TEACHER + ";");
        db.execSQL("DROP TABLE IF EXISTS " + HeadmasterDataManager.TABLE_NAME_HEADMASTER + ";");
        db.execSQL("DROP TABLE IF EXISTS " + AttendanceDataManager.TABLE_NAME_ATTENDANCE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + NoticeDataManager.TABLE_NAME_NOTICE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + TeachPlanDataManager.TABLE_NAME_TEACHPLAN + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ClassStarDataManager.TABLE_NAME_CLASSSTAR + ";");
        db.execSQL("DROP TABLE IF EXISTS " + DiscoveryDataManager.TABLE_NAME_DISCOVERY + ";");
        db.execSQL("DROP TABLE IF EXISTS " + MessageDataManager.TABLE_NAME_MESSAGE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + SchoolDataManager.TABLE_NAME_SCHOOL + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ClassDataManager.TABLE_NAME_CLASS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ChildCommentDataManager.TABLE_NAME_CHILDCOMMENT + ";");
        db.execSQL("DROP TABLE IF EXISTS " + ParentChildTaskDataManager
                .TABLE_NAME_PARENTCHILDTASK + ";");
        db.execSQL("DROP TABLE IF EXISTS " + OpusDataManager.TABLE_NAME_OPUS + ";");
        db.execSQL("DROP TABLE IF EXISTS " + CircleDataManager.TABLE_NAME_CIRCLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + CircleDataManager.TABLE_NAME_CIRCLE + ";");
        db.execSQL("DROP TABLE IF EXISTS " + RecipeDataManager.TABLE_NAME_RECIPE + ";");


        //创建数据库基础表
        db.execSQL(TableCreate_User);
        Log.i(TAG, "db.execSQL(TableCreate_User)");
        //        Log.e(TAG, TableCreate_User);

        db.execSQL(TableCreate_Child);
        Log.i(TAG, "db.execSQL(TableCreate_Child)");
        //        Log.e(TAG, TableCreate_Child);

        db.execSQL(TableCreate_Parent);
        Log.i(TAG, "db.execSQL(TableCreate_Parent)");

        db.execSQL(TableCreate_Teacher);
        Log.i(TAG, "db.execSQL(TableCreate_Teacher)");

        db.execSQL(TableCreate_Headmaster);
        Log.i(TAG, "db.execSQL(TableCreate_Headmaster)");

        db.execSQL(TableCreate_Attendance);
        Log.i(TAG, "db.execSQL(TableCreate_Attendance)");

        db.execSQL(TableCreate_Notice);
        Log.i(TAG, "db.execSQL(TableCreate_Notice)");

        db.execSQL(TableCreate_TeachPlan);
        Log.i(TAG, "db.execSQL(TableCreate_TeachPlan)");

        db.execSQL(TableCreate_ClassStar);
        Log.i(TAG, "db.execSQL(TableCreate_ClassStar)");

        db.execSQL(TableCreate_Discovery);
        Log.i(TAG, "db.execSQL(TableCreate_Discovery)");

        db.execSQL(TableCreate_Message);
        Log.i(TAG, "db.execSQL(TableCreate_Message)");

        db.execSQL(TableCreate_School);
        Log.i(TAG, "db.execSQL(TableCreate_School)");

        db.execSQL(TableCreate_Class);
        Log.i(TAG, "db.execSQL(TableCreate_Class)");

        db.execSQL(TableCreate_ChildComment);
        Log.i(TAG, "db.execSQL(TableCreate_ChildComment)");

        db.execSQL(TableCreate_ParentChildTask);
        Log.i(TAG, "db.execSQL(TableCreate_ParentChildTask)");

        db.execSQL(TableCreate_Opus);
        Log.i(TAG, "db.execSQL(TableCreate_Opus)");

        db.execSQL(TableCreate_Circle);
        Log.i(TAG, "db.execSQL(TableCreate_Circle)");

        db.execSQL(TableCreate_Recipe);
        Log.i(TAG, "db.execSQL(TableCreate_Recipe)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "DataBaseManagementHelper onUpgrade");
        onCreate(db);
    }


}
