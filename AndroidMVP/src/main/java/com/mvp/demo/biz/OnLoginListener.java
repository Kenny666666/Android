package com.mvp.demo.biz;

import com.mvp.demo.bean.User;

public interface OnLoginListener {
    void loginSuccess(User user);


    void loginFailed();
}
