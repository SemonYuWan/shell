package com.example.semon.zhihuishu.Constant;

/**
 * 学校实体类
 */
public class SchoolData {
    private String id;
    private String school_name;
    private String school_address;
    private String school_tel;
    private String school_headmasterId;
    private String school_summary;//学校概况
    private String school_teacher;//师资队伍
    private String school_awards;//所获奖项

    private String remark;

    public SchoolData() {
    }

    public SchoolData(String id, String school_name, String school_address, String school_tel, String
            school_headmasterId, String remark) {
        this.id = id;
        this.school_name = school_name;
        this.school_address = school_address;
        this.school_tel = school_tel;
        this.school_headmasterId = school_headmasterId;
        this.remark = remark;
    }

    public SchoolData(String id, String school_name, String school_address, String school_tel,
                      String school_headmasterId, String school_summary, String school_teacher,
                      String school_awards, String remark) {
        this.id = id;
        this.school_name = school_name;
        this.school_address = school_address;
        this.school_tel = school_tel;
        this.school_headmasterId = school_headmasterId;
        this.school_summary = school_summary;
        this.school_teacher = school_teacher;
        this.school_awards = school_awards;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getSchool_address() {
        return school_address;
    }

    public void setSchool_address(String school_address) {
        this.school_address = school_address;
    }

    public String getSchool_tel() {
        return school_tel;
    }

    public void setSchool_tel(String school_tel) {
        this.school_tel = school_tel;
    }

    public String getSchool_headmasterId() {
        return school_headmasterId;
    }

    public void setSchool_headmasterId(String school_headmasterId) {
        this.school_headmasterId = school_headmasterId;
    }

    public String getSchool_summary() {
        return school_summary;
    }

    public void setSchool_summary(String school_summary) {
        this.school_summary = school_summary;
    }

    public String getSchool_teacher() {
        return school_teacher;
    }

    public void setSchool_teacher(String school_teacher) {
        this.school_teacher = school_teacher;
    }

    public String getSchool_awards() {
        return school_awards;
    }

    public void setSchool_awards(String school_awards) {
        this.school_awards = school_awards;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
