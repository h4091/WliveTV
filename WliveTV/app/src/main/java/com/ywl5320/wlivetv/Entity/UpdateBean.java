package com.ywl5320.wlivetv.Entity;

import cn.bmob.v3.BmobObject;

/**
 * Created by ywl on 15-10-20.
 */
public class UpdateBean extends BmobObject {

    private Integer versionCode;
    private String url;
    private String updateMsg;

    public String getUpdateMsg() {
        return updateMsg;
    }

    public void setUpdateMsg(String updateMsg) {
        this.updateMsg = updateMsg;
    }

    public Integer getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(Integer versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
