package com.assistant.ua.datasource.net.response;

import com.assistant.ua.datasource.net.entity.OtherUserEntity;

import java.util.List;

/**
 * Created by qinbaoyuan on 2018/11/12
 */
public class AllUserListResponse extends LFBaseResponse {
    private List<OtherUserEntity> data;

    public List<OtherUserEntity> getData() {
        return data;
    }

    public void setData(List<OtherUserEntity> data) {
        this.data = data;
    }
}
