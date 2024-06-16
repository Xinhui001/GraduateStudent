package com.example.graduateinfo.model.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 专业信息表
 * @TableName Majors
 */
@TableName(value ="Majors")
@Data
public class Majors implements Serializable {
    /**
     * 专业id
     */
    @TableId(type = IdType.AUTO)
    private Integer majorId;

    /**
     * 专业名称
     */
    private String majorName;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}