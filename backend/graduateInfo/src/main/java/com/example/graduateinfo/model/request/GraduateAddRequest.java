package com.example.graduateinfo.model.request;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 20891
 */
@Data
public class GraduateAddRequest implements Serializable {

    private static final long serialVersionUID = -2268571685438552108L;

    /**
     * 毕业生姓名
     */
    private String studentName;

    /**
     * 毕业生性别
     */
    private String gender;

    /**
     * 毕业生生日
     */
    private String birthdate;

    /**
     * 毕业年份
     */
    private String graduationYear;

    /**
     * 就业状态
     */
    private String employmentStatus;

    /**
     * 专业名称
     */
    private String majorName;

    /**
     * 班级名称
     */
    private String className;
}
