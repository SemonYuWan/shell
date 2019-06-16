package com.example.semon.zhihuishu.Constant;

/**
 * 发现文章数据结构
 */
public class DiscoveryData {
    private String id;//ID
    private String title;//发现标题
    private String conext;//发现内容
    private String image;//发现图片
    private String creater;//创建者
    private String createTime;//创建时间
    private String like;//点赞

    public DiscoveryData() {
    }

    public DiscoveryData(String id, String title, String conext, String image, String creater,
                         String createTime, String like) {
        this.id = id;
        this.title = title;
        this.conext = conext;
        this.image = image;
        this.creater = creater;
        this.createTime = createTime;
        this.like = like;
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

    public String getConext() {
        return conext;
    }

    public void setConext(String conext) {
        this.conext = conext;
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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLike() {
        return like;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
