package com.example.graduateinfo.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 毕业生信息表
 * @author 20891
 * @TableName Graduates
 */
@TableName(value ="Graduates")
@Data
public class Graduates implements Serializable {
    /**
     * 毕业生信息表id
     */
    @TableId(type = IdType.AUTO)
    private Integer graduateid;

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
     * 专业id
     */
    private Integer majorid;

    /**
     * 班级id
     */
    private Integer classid;

    /**
     * 毕业年份
     */
    private String graduationyear;

    /**
     * 就业状态
     */
    private String employmentstatus;

    /**
     * 专业名称
     */
    private String majorname;

    /**
     * 班级名称
     */
    private String classname;

    /**
     * 逻辑删除  0-正常 1-删除
     */
    @TableLogic
    private Integer isDeleted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}