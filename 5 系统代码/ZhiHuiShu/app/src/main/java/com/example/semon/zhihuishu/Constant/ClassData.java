package com.example.semon.zhihuishu.Constant;

/**
 * 班级实体类
 */
public class ClassData {
    private String id;
    private String class_name;
    private String school_id;
    private String class_manageId;//班主任ID
    private String class_teacher_count;//教师人数
    private String class_student_count;//学生人数
    private String remark;

    public ClassData() {
    }

    public ClassData(String id, String class_name, String school_id, String class_manageId, String
            class_teacher_count, String class_student_count, String remark) {
        this.id = id;
        this.class_name = class_name;
        this.school_id = school_id;
        this.class_manageId = class_manageId;
        this.class_teacher_count = class_teacher_count;
        this.class_student_count = class_student_count;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getClass_manageId() {
        return class_manageId;
    }

    public void setClass_manageId(String class_manageId) {
        this.class_manageId = class_manageId;
    }

    public String getClass_teacher_count() {
        return class_teacher_count;
    }

    public void setClass_teacher_count(String class_teacher_count) {
        this.class_teacher_count = class_teacher_count;
    }

    public String getClass_student_count() {
        return class_student_count;
    }

    public void setClass_student_count(String class_student_count) {
        this.class_student_count = class_student_count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
