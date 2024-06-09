package com.example.graduateinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.graduateinfo.common.ErrorCode;
import com.example.graduateinfo.common.ResultUtils;
import com.example.graduateinfo.exception.BusinessException;
import com.example.graduateinfo.mapper.MajorsMapper;
import com.example.graduateinfo.model.domain.Majors;
import com.example.graduateinfo.model.domain.StudentClasses;
import com.example.graduateinfo.service.MajorsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 20891
* @description 针对表【Majors(专业信息表)】的数据库操作Service实现
* @createDate 2024-06-04 22:10:34
*/
@Service
@Slf4j
public class MajorsServiceImpl extends ServiceImpl<MajorsMapper, Majors>
    implements MajorsService {

    @Resource
    private MajorsMapper majorsMapper;

    /**
     * 增加
     *
     * @param major
     * @return
     */
    @Override
    public Integer addMajor(Majors major) {
        if (major == null || StringUtils.isBlank(major.getMajorname())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Majors> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("majorName",major.getMajorname());
        Majors majors = majorsMapper.selectOne(queryWrapper);
        if (majors != null) {
            log.info("专业名称已存在");
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"专业名称重复");
        }
        //存入数据库
        int saveResult = majorsMapper.insert(major);
        if (saveResult < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"存入数据库失败");
        }
        return saveResult;
    }

    /**
     * 根据专业名称查询
     *
     * @param pagedNum
     * @param pageSize
     * @param majorName
     * @return
     */
    @Override
    public IPage<Majors> searchMajor(Integer pagedNum, Integer pageSize, String majorName) {
        if (pagedNum < 0 || pageSize < 0 || StringUtils.isBlank(majorName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<Majors> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Majors::getMajorname,majorName);
        queryWrapper.orderByDesc(Majors::getMajorid);
        Page<Majors> page = new Page<>(pagedNum, pageSize);
        IPage<Majors> majorsIPage = majorsMapper.selectPage(page,queryWrapper);

        if (majorsIPage == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return majorsIPage;
    }

    /**
     * 更新
     *
     * @param majors
     * @return
     */
    @Override
    public int updateInfo(Majors majors) {
        if (majors == null || StringUtils.isBlank(majors.getMajorname())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Majors> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("majorName", majors.getMajorname());
        Majors majorResult = majorsMapper.selectOne(queryWrapper1);
        if (majorResult != null) {
            log.info("专业名称有重复");
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<Majors> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("MajorID",majors.getMajorid());
        Majors oldMajor = majorsMapper.selectOne(queryWrapper);
        if (oldMajor == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        oldMajor.setMajorname(majors.getMajorname());

        return majorsMapper.update(oldMajor,queryWrapper);
    }
}




