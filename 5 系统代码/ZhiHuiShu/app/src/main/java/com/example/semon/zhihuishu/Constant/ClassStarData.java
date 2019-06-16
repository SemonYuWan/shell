package com.example.semon.zhihuishu.Constant;

/**
 * 班级之星实体类
 */

public class ClassStarData {
    private String id;//ID
    private String school_id;//学校ID
    private String class_id;//班级ID
    private String child_id;//学生ID
    private String date;//日期
    private String remark;//备注

    public ClassStarData() {
    }


    public ClassStarData(String id, String school_id, String class_id, String child_id, String
            date, String remark) {
        this.id = id;
        this.school_id = school_id;
        this.class_id = class_id;
        this.child_id = child_id;
        this.date = date;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_id) {
        this.child_id = child_id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
