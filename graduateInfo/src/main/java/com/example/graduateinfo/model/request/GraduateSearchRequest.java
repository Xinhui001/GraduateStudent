package com.example.graduateinfo.model.request;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author 20891
 */
@Data
public class GraduateSearchRequest implements Serializable {

    private static final long serialVersionUID = 3466475787338121496L;

    /**
     * 毕业生姓名
     */
    private String studentName;

    /**
     * 毕业生性别
     */
    private String gender;

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
