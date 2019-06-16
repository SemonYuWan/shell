package com.example.semon.zhihuishu.Constant;

/**
 * 家长实体类
 */
public class ParentData {
    private String id;//id
    private String user;//用户名
    private String password;//密码
    private String name;//姓名
    private String tel;//手机号
    private String address;//地址
    private String child_id;//孩子
    private String child_relationship;//亲子关系
    private String remark;//备注

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getChild_id() {
        return child_id;
    }

    public void setChild_id(String child_name) {
        this.child_id = child_id;
    }

    public String getChild_relationship() {
        return child_relationship;
    }

    public void setChild_relationship(String child_relationship) {
        this.child_relationship = child_relationship;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ParentData(String id, String user, String password, String name, String tel, String
            address, String child_id, String child_relationship) {
        this.id = id;
        this.user = user;
        this.password = password;
        this.name = name;
        this.tel = tel;
        this.address = address;
        this.child_id = child_id;
        this.child_relationship = child_relationship;
    }

    public ParentData() {

    }
}
