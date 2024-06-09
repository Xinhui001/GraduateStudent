package com.example.graduateinfo.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 班级请求体
 *
 * @author 20891
 */
@Data
public class StudentClassesAddRequest implements Serializable {

    private static final long serialVersionUID = 1637790989003736365L;

    private String className;

    private String majorName;

}
