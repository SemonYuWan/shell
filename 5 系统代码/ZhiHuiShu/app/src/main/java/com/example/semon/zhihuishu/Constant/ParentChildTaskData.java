package com.example.semon.zhihuishu.Constant;

/**
 * 亲子任务实体类
 */
public class ParentChildTaskData {
    private String id;//id
    private String task_title;//
    private String task_context;
    private String task_image;
    private String school_id;
    private String class_id;
    private String creater;
    private String createTime;
    private String remark;//备注

    public ParentChildTaskData() {
    }

    public ParentChildTaskData(String id, String task_title, String task_context, String
            task_image, String school_id, String class_id, String creater, String createTime,
                               String remark) {
        this.id = id;
        this.task_title = task_title;
        this.task_context = task_context;
        this.task_image = task_image;
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

    public String getTask_title() {
        return task_title;
    }

    public void setTask_title(String task_title) {
        this.task_title = task_title;
    }

    public String getTask_context() {
        return task_context;
    }

    public void setTask_context(String task_context) {
        this.task_context = task_context;
    }

    public String getTask_image() {
        return task_image;
    }

    public void setTask_image(String task_image) {
        this.task_image = task_image;
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
