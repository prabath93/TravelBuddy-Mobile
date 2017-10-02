package com.example.prabaths.models;

/**
 * Created by prabath s on 5/2/2016.
 */
public class SectionItem implements Item{

    private String title;
    private String subTitle;
    private int resIcon;

    public SectionItem(int resIcon, String subTitle, String title) {
        this.resIcon = resIcon;
        this.subTitle = subTitle;
        this.title = title;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public boolean isSection() {
        return true;
    }
}
