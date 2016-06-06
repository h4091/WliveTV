package com.ywl5320.wlivetv.Entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by ywl on 15-10-17.
 */
public class VideoBean extends BmobObject {

    private String tvName;
    private String tvUrl;
    private Boolean isCanPlay;

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public String getTvUrl() {
        return tvUrl;
    }

    public void setTvUrl(String tvUrl) {
        this.tvUrl = tvUrl;
    }

    public Boolean getIsCanPlay() {
        return isCanPlay;
    }

    public void setIsCanPlay(Boolean isCanPlay) {
        this.isCanPlay = isCanPlay;
    }
}
