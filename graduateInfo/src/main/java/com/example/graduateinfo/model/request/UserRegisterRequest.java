package com.example.graduateinfo.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 注册请求体
 *
 * @author 20891
 */
@Data
public class UserRegisterRequest implements Serializable {

    private static final long serialVersionUID = 5357398299861607940L;

    private String userName;

    private String userPassword;

    private String checkPassword;

}
