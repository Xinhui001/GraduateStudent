package com.example.graduateinfo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.graduateinfo.model.domain.Majors;
import com.example.graduateinfo.model.domain.StudentClasses;
import com.github.pagehelper.PageInfo;

/**
* @author 20891
* @description 针对表【Majors(专业信息表)】的数据库操作Service
* @createDate 2024-06-04 22:10:34
*/
public interface MajorsService extends IService<Majors> {
    /**
     *添加专业
     *
     * @param major
     * @return
     */
    Integer addMajor(Majors major);

    /**
     * 根据专业名称查询
     *
     * @param pagedNum
     * @param pageSize
     * @param majorName
     * @return
     */
    PageInfo<Majors> searchMajor(Integer pagedNum, Integer pageSize, String majorName);

    /**
     * 更新
     *
     * @param majors
     * @return
     */
    int updateInfo(Majors majors);
}
