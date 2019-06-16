package com.example.semon.zhihuishu.Constant;

/**
 * 教学计划
 */

public class TeachPlanData {
    private String id;//ID
    private String planTitle;//计划的标题
    private String planContext;//计划的内容
    private String schoolId;//所属学校
    private String classId;//所属班级
    private String creater;//创建者
    private String createTime;//创建时间
    private String remark;//备注

    public TeachPlanData() {
    }

    public TeachPlanData(String id, String planTitle, String planContext, String schoolId, String
            classId, String creater, String createTime, String remark) {
        this.id = id;
        this.planTitle = planTitle;
        this.planContext = planContext;
        this.schoolId = schoolId;
        this.classId = classId;
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

    public String getPlanTitle() {
        return planTitle;
    }

    public void setPlanTitle(String planTitle) {
        this.planTitle = planTitle;
    }

    public String getPlanContext() {
        return planContext;
    }

    public void setPlanContext(String planContext) {
        this.planContext = planContext;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
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
