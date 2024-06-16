package com.example.graduateinfo.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 班级信息表
 * @TableName StudentClasses
 */
@TableName(value ="StudentClasses")
@Data
public class StudentClasses implements Serializable {
    /**
     * 班级id
     */
    @TableId(type = IdType.AUTO)
    private Integer classId;

    /**
     * 班级名称
     */
    private String className;

    /**
     * 专业id
     */
    private Integer majorId;

    /**
     * 专业名称
     */
    private String majorName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}