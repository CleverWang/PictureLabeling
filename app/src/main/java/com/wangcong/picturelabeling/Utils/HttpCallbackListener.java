package com.wangcong.picturelabeling.Utils;

/**
 * Created by Wang Cong on 2016/12/10.
 */

public interface HttpCallbackListener {
    void onFinish(String response);

    void onError(Exception e);
}

