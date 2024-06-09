package com.example.graduateinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.graduateinfo.common.ErrorCode;
import com.example.graduateinfo.exception.BusinessException;
import com.example.graduateinfo.mapper.MajorsMapper;
import com.example.graduateinfo.mapper.StudentClassesMapper;
import com.example.graduateinfo.model.domain.Majors;
import com.example.graduateinfo.model.domain.StudentClasses;
import com.example.graduateinfo.model.request.StudentClassesAddRequest;
import com.example.graduateinfo.model.request.StudentClassesUpdateRequest;
import com.example.graduateinfo.service.StudentClassesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 20891
* @description 针对表【StudentClasses(班级信息表)】的数据库操作Service实现
* @createDate 2024-06-04 22:09:51
*/
@Service
@Slf4j
public class StudentClassesServiceImpl extends ServiceImpl<StudentClassesMapper, StudentClasses>
    implements StudentClassesService {

    @Resource
    private MajorsMapper majorsMapper;

    @Resource
    private StudentClassesMapper studentClassesMapper;

    /**
     * 添加
     *
     * @param studentClassesAddRequest
     * @return
     */
    @Override
    public Integer addStudentClass(StudentClassesAddRequest studentClassesAddRequest) {
        //1.校验参数不为空
        if (studentClassesAddRequest == null || StringUtils.isBlank(studentClassesAddRequest.getClassName())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //2.专业要存在
        QueryWrapper<Majors> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("majorName", studentClassesAddRequest.getMajorName());
        Majors majorResult = majorsMapper.selectOne(queryWrapper);
        if (majorResult == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //3.班级名称不重复
        QueryWrapper<StudentClasses> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("className", studentClassesAddRequest.getClassName());
        Long resultCount = studentClassesMapper.selectCount(queryWrapper1);
        if (resultCount > 0) {
            log.info("班级名称不得相同");
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        StudentClasses studentClass = new StudentClasses();
        studentClass.setClassname(studentClassesAddRequest.getClassName());
        studentClass.setMajorid(majorResult.getMajorid());
        studentClass.setMajorname(studentClassesAddRequest.getMajorName());

        int insertResult = studentClassesMapper.insert(studentClass);
        if (insertResult < 0) {
            log.info("存入数据库失败");
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return insertResult;
    }

    /**
     * 查询班级 分页模糊查询
     *
     * @param pagedNum
     * @param pageSize
     * @param studentClasses
     * @return
     */
    @Override
    public IPage<StudentClasses> searchClasses(Integer pagedNum, Integer pageSize, StudentClassesAddRequest studentClasses) {
        if (pagedNum < 0 || pageSize < 0 || studentClasses == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        LambdaQueryWrapper<StudentClasses> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.like(StudentClasses::getClassname, studentClasses.getClassName())
                .or()
                .like(StudentClasses::getMajorname, studentClasses.getMajorName());
        queryWrapper.orderByDesc(StudentClasses::getClassid);
        Page<StudentClasses> page = new Page<>(pagedNum, pageSize);
        IPage<StudentClasses> studentClassesIPage = studentClassesMapper.selectPage(page,queryWrapper);

        if (studentClassesIPage == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        return studentClassesIPage;
    }

    /**
     * 更新
     *
     * @param studentClassesUpdateRequest
     * @return
     */
    @Override
    public int updateInfo(StudentClassesUpdateRequest studentClassesUpdateRequest) {
        if (studentClassesUpdateRequest == null || StringUtils.isBlank(studentClassesUpdateRequest.getClassName())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //查看专业是否存在
        QueryWrapper<Majors> majorsQueryWrapper = new QueryWrapper<>();
        majorsQueryWrapper.eq("majorName",studentClassesUpdateRequest.getMajorName());
        Majors majorResult = majorsMapper.selectOne(majorsQueryWrapper);
        if (majorResult == null) {
            log.info("更新时，专业名称不存在");
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //班级名称不得相同
        QueryWrapper<StudentClasses> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("className",studentClassesUpdateRequest.getClassName());
        Long result = studentClassesMapper.selectCount(queryWrapper1);
        if (result > 0) {
            log.info("班级名称有重复");
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //根据id从数据库中查出老数据
        QueryWrapper<StudentClasses> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ClassID",studentClassesUpdateRequest.getClassId());
        StudentClasses studentClasses = studentClassesMapper.selectOne(queryWrapper);
        if (studentClasses == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //更新老数据
        studentClasses.setClassname(studentClassesUpdateRequest.getClassName());
        studentClasses.setMajorid(majorResult.getMajorid());
        studentClasses.setMajorname(majorResult.getMajorname());

        return studentClassesMapper.update(studentClasses, queryWrapper);
    }

}




