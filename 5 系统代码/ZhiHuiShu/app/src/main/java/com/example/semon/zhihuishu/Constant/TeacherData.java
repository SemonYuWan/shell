package com.example.semon.zhihuishu.Constant;

/**
 * 教师实体类
 */
public class TeacherData {
    private String id;//教师ID
    private String user;//教师用户名
    private String password;//教师密码
    private String name;//教师姓名
    private String tel;//教师电话
    private String school_id;//教师所属学校ID
    private String class_id;//教师所属班级ID
    private String remark;//备注

    public TeacherData() {
    }

    public TeacherData(String id, String user, String password, String name, String tel, String
            school_id, String class_id) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.school_id = school_id;
        this.class_id = class_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
