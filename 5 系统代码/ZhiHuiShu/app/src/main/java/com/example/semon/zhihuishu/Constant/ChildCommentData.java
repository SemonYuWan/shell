package com.example.semon.zhihuishu.Constant;

/**
 * 学生点评实体类
 */
public class ChildCommentData {
    private String id;//id
    private String comment_title;//点评
    private String comment_context;
    private String school_id;
    private String class_id;
    private String creater;
    private String createTime;
    private String remark;//备注

    public ChildCommentData() {
    }

    public ChildCommentData(String id, String comment_title, String comment_context, String
            school_id, String class_id, String creater, String createTime, String remark) {
        this.id = id;
        this.comment_title = comment_title;
        this.comment_context = comment_context;
        this.school_id = school_id;
        this.class_id = class_id;
        this.creater = creater;
        this.createTime = createTime;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getComment_title() {
        return comment_title;
    }

    public void setComment_title(String comment_title) {
        this.comment_title = comment_title;
    }

    public String getComment_context() {
        return comment_context;
    }

    public void setComment_context(String comment_context) {
        this.comment_context = comment_context;
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

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
