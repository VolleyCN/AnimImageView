package com.volleycn.sample;

/**
 * @Describe
 * @Date : 2020/4/9
 * @Email : zhangmeng@newstylegroup.com
 * @Author : MENG
 */
public class UserInfoEntity {
    private int iconRes;
    private String name;
    private String info;
    private boolean online;

    public UserInfoEntity(int iconRes, String name, String info,boolean online) {
        this.iconRes = iconRes;
        this.name = name;
        this.info = info;
        this.online = online;
    }

    public int getIconRes() {
        return iconRes;
    }

    public void setIconRes(int iconRes) {
        this.iconRes = iconRes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
}
