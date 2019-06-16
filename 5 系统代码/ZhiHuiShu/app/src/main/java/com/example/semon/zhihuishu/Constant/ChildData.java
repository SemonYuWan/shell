package com.example.semon.zhihuishu.Constant;

/**
 * 孩子实体类
 */
public class ChildData {
    private String id;//id
    private String name;//姓名
    private String sex;
    private String birthday;
    private String parent_id;
    private String school_id;
    private String class_id;
    private String remark;//备注

    public ChildData() {
    }

    public ChildData(String id, String name, String sex, String birthday, String parent_id,
                     String school_id, String class_id) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.birthday = birthday;
        this.parent_id = parent_id;
        this.school_id = school_id;
        this.class_id = class_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
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
