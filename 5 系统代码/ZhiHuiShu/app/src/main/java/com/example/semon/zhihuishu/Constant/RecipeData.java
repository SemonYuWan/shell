package com.example.semon.zhihuishu.Constant;

/**
 * 食谱数据结构
 */
public class RecipeData {
    private String id;//ID
    private String recipe;//食谱
    private String recipe_image;//
    private String schoolId;//班级id
    private String check;//检查
    private String creater;//创建者
    private String createTime;//创建时间
    private String remark;//备注

    public RecipeData() {
    }

    public RecipeData(String id, String recipe, String recipe_image, String schoolId, String
            check, String creater, String createTime, String remark) {
        this.id = id;
        this.recipe = recipe;
        this.recipe_image = recipe_image;
        this.schoolId = schoolId;
        this.check = check;
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

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }

    public String getRecipe_image() {
        return recipe_image;
    }

    public void setRecipe_image(String recipe_image) {
        this.recipe_image = recipe_image;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getCheck() {
        return check;
    }

    public void setCheck(String check) {
        this.check = check;
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
