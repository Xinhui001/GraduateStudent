package com.example.graduateinfo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.graduateinfo.model.domain.Graduates;
import com.example.graduateinfo.model.domain.StudentClasses;
import com.example.graduateinfo.model.request.GraduateAddRequest;
import com.example.graduateinfo.model.request.GraduateSearchRequest;
import com.example.graduateinfo.model.request.GraduateUpdateRequest;
import com.example.graduateinfo.model.request.StudentClassesAddRequest;

/**
* @author 20891
* @description 针对表【Graduates(毕业生信息表)】的数据库操作Service
* @createDate 2024-06-04 22:10:38
*/
public interface GraduatesService extends IService<Graduates> {

    /**
     * 添加
     *
     * @param graduateAddRequest
     * @return
     */
    Integer addStudent(GraduateAddRequest graduateAddRequest);

    /**
     * 模糊查询
     *
     * @param pagedNum
     * @param pageSize
     * @param graduateSearchRequest
     * @return
     */
    IPage<Graduates> searchClasses(Integer pagedNum, Integer pageSize, GraduateSearchRequest graduateSearchRequest);

    /**
     * 更新
     *
     * @param graduateUpdateRequest
     * @return
     */
    int updateInfo(GraduateUpdateRequest graduateUpdateRequest);
}
