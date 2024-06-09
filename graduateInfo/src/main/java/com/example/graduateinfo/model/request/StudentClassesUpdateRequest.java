package com.example.graduateinfo.model.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @author 20891
 */
@Data
public class StudentClassesUpdateRequest implements Serializable {

    private static final long serialVersionUID = 2808182940009249125L;

    private Integer classId;

    private String className;

    private String majorName;
}
