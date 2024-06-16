package com.example.graduateinfo.service;

import com.example.graduateinfo.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 20891
* @description 针对表【User(用户信息表)】的数据库操作Service
* @createDate 2024-06-09 13:18:15
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param userAccount 账户
     * @param userPassword 密码
     * @param checkPassword 校验密码
     * @return 用户id
     */
    long userRegister(String userAccount,String userPassword,String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount 账户
     * @param userPassword 密码
     * @param request 请求
     * @return 用户脱敏信息
     */
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 用户脱敏
     *
     * @param user 用户对象
     * @return 脱敏用户信息
     */
    User getSafetyUser(User user);

    /**
     * 退出登录
     *
     * @param request 获取session信息
     * @return
     */
    int userLogout(HttpServletRequest request);

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    boolean isAdmin(HttpServletRequest request);

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    User getLoginUser(HttpServletRequest request);
}
