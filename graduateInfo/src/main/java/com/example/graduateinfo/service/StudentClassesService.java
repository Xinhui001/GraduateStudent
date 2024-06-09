package com.example.graduateinfo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.graduateinfo.model.domain.StudentClasses;
import com.example.graduateinfo.model.request.StudentClassesAddRequest;
import com.example.graduateinfo.model.request.StudentClassesUpdateRequest;

/**
* @author 20891
* @description 针对表【StudentClasses(班级信息表)】的数据库操作Service
* @createDate 2024-06-04 22:09:51
*/
public interface StudentClassesService extends IService<StudentClasses> {

    /**
     * 添加
     *
     * @param studentClassesAddRequest
     * @return
     */
    Integer addStudentClass(StudentClassesAddRequest studentClassesAddRequest);

    /**
     * 查询班级
     *
     * @param pagedNum
     * @param pageSize
     * @param studentClasses
     * @return
     */
    IPage<StudentClasses> searchClasses(Integer pagedNum, Integer pageSize, StudentClassesAddRequest studentClasses);

    /**
     * 更新
     *
     * @param studentClassesUpdateRequest
     * @return
     */
    int updateInfo(StudentClassesUpdateRequest studentClassesUpdateRequest);
}
