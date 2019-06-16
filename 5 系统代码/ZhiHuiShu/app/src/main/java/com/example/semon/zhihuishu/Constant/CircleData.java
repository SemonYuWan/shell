package com.example.semon.zhihuishu.Constant;

/**
 * 班级圈数据结构
 */
public class CircleData {
    private String id;//ID
    private String content;//内容
    private String image;//图片
    private String creater;//创建者
    private String creater_id;//创建者
    private String class_id;//班级
    private String createTime;//创建时间
    private String remark;//备注

    public CircleData() {
    }

    public CircleData(String id, String content, String image, String creater, String creater_id,
                      String class_id, String createTime, String remark) {
        this.id = id;
        this.content = content;
        this.image = image;
        this.creater = creater;
        this.creater_id = creater_id;
        this.class_id = class_id;
        this.createTime = createTime;
        this.remark = remark;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreater() {
        return creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    public String getCreater_id() {
        return creater_id;
    }

    public void setCreater_id(String creater_id) {
        this.creater_id = creater_id;
    }

    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
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
