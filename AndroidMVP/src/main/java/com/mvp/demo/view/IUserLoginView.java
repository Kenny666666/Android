package com.mvp.demo.view;


import com.mvp.demo.bean.User;

/**
 * Created by zhy on 15/6/19.
 */
public interface IUserLoginView {
    String getUserName();

    String getPassword();

    void clearUserName();

    void clearPassword();

    void showLoading();

    void hideLoading();

    void toMainActivity(User user);

    void showFailedError();

}
