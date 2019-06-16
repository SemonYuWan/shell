package com.example.semon.zhihuishu.Constant;

/**
 * 通知实体类
 */
public class NoticeBean {
    private String id;
    private String title;
    private String context;
    private String school_id;
    private String createTime;
    private String type;
    private String remark;

    public NoticeBean() {
    }

    public NoticeBean(String id, String title, String context, String school_id, String
            createTime, String type, String remark) {
        this.id = id;
        this.title = title;
        this.context = context;
        this.school_id = school_id;
        this.createTime = createTime;
        this.type = type;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getSchool_id() {
        return school_id;
    }

    public void setSchool_id(String school_id) {
        this.school_id = school_id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
