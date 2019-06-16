package com.example.semon.zhihuishu.Constant;

/**
 * 学生作品实体类
 */
public class OpusData {
    private String id;
    private String opus_title;
    private String opus_context;
    private String opusImage;
    private String school_id;
    private String class_id;
    private String creater;
    private String createTime;
    private String remark;

    public OpusData() {
    }

    public OpusData(String id, String opus_title, String opus_context, String opusImage, String
            school_id, String class_id, String creater, String createTime, String remark) {
        this.id = id;
        this.opus_title = opus_title;
        this.opus_context = opus_context;
        this.opusImage = opusImage;
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

    public String getOpus_title() {
        return opus_title;
    }

    public void setOpus_title(String opus_title) {
        this.opus_title = opus_title;
    }

    public String getOpus_context() {
        return opus_context;
    }

    public void setOpus_context(String opus_context) {
        this.opus_context = opus_context;
    }

    public String getOpusImage() {
        return opusImage;
    }

    public void setOpusImage(String opusImage) {
        this.opusImage = opusImage;
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
