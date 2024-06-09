package com.example.graduateinfo.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.graduateinfo.common.ErrorCode;
import com.example.graduateinfo.exception.BusinessException;
import com.example.graduateinfo.mapper.GraduatesMapper;
import com.example.graduateinfo.mapper.StudentClassesMapper;
import com.example.graduateinfo.model.domain.Graduates;
import com.example.graduateinfo.model.domain.StudentClasses;
import com.example.graduateinfo.model.request.GraduateAddRequest;
import com.example.graduateinfo.model.request.GraduateSearchRequest;
import com.example.graduateinfo.model.request.GraduateUpdateRequest;
import com.example.graduateinfo.service.GraduatesService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 20891
* @description 针对表【Graduates(毕业生信息表)】的数据库操作Service实现
* @createDate 2024-06-04 22:10:38
*/
@Service
@Slf4j
public class GraduatesServiceImpl extends ServiceImpl<GraduatesMapper, Graduates>
    implements GraduatesService {

    @Resource
    private StudentClassesMapper studentClassesMapper;

    @Resource
    private GraduatesMapper graduatesMapper;

    /**
     * 添加
     *
     * @param graduateAddRequest
     * @return
     */
    @Override
    public Integer addStudent(GraduateAddRequest graduateAddRequest) {
        if (graduateAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //1.填充数据
        Graduates graduates = new Graduates();
        graduates.setStudentName("未知");
        graduates.setGender("未知");
        graduates.setBirthdate("未知");
        graduates.setGraduationyear("未知");
        graduates.setEmploymentstatus("未知");
        graduates.setMajorname("未知");
        graduates.setClassname("未知");

        //2.班级、专业要存在  查找班级即可
        QueryWrapper<StudentClasses> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("className",graduateAddRequest.getClassName());
        StudentClasses studentClasses = studentClassesMapper.selectOne(queryWrapper);
        if (studentClasses == null) {
            log.info("班级不存在");
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //3.将班级、专业信息存入数据
        graduates.setMajorid(studentClasses.getMajorid());
        graduates.setMajorname(studentClasses.getMajorname());
        graduates.setClassid(studentClasses.getClassid());
        graduates.setClassname(studentClasses.getClassname());
        //4.判空   不为空的都存入数据
        if (!StringUtils.isBlank(graduateAddRequest.getStudentName())) {
            graduates.setStudentName(graduateAddRequest.getStudentName());
        }
        if (!StringUtils.isBlank(graduateAddRequest.getGender())) {
            graduates.setGender(graduateAddRequest.getGender());
        }
        if (!StringUtils.isBlank(graduateAddRequest.getBirthdate())) {
            graduates.setBirthdate(graduateAddRequest.getBirthdate());
        }
        if (!StringUtils.isBlank(graduateAddRequest.getGraduationYear())) {
            graduates.setGraduationyear(graduateAddRequest.getGraduationYear());
        }
        if (!StringUtils.isBlank(graduateAddRequest.getEmploymentStatus())) {
            graduates.setEmploymentstatus(graduateAddRequest.getEmploymentStatus());
        }
        //5.存入数据库
        int insertResult = graduatesMapper.insert(graduates);
        if (insertResult < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return insertResult;
    }

    /**
     * 模糊查询
     *
     * @param pagedNum
     * @param pageSize
     * @param graduateSearchRequest
     * @return
     */
    @Override
    public IPage<Graduates> searchClasses(Integer pagedNum, Integer pageSize, GraduateSearchRequest graduateSearchRequest) {
        if (pagedNum < 0 || pageSize < 0 || graduateSearchRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        QueryWrapper<Graduates> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("studentName",graduateSearchRequest.getStudentName())
                .or()
                .like("gender",graduateSearchRequest.getGender())
                .or()
                .like("employmentStatus",graduateSearchRequest.getEmploymentStatus())
                .or()
                .like("graduationYear",graduateSearchRequest.getGraduationYear())
                .or()
                .like("majorName",graduateSearchRequest.getMajorName())
                .or()
                .like("className",graduateSearchRequest.getClassName());
        queryWrapper.orderByDesc("GraduateID");
        Page<Graduates> page = new Page<>(pagedNum, pageSize);
        IPage<Graduates> graduatesIPage = graduatesMapper.selectPage(page,queryWrapper);

        if (graduatesIPage == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        return graduatesIPage;
    }

    /**
     * 更新
     *
     * @param graduateUpdateRequest
     * @return
     */
    @Override
    public int updateInfo(GraduateUpdateRequest graduateUpdateRequest) {
        if (graduateUpdateRequest == null || StringUtils.isBlank(graduateUpdateRequest.getStudentName())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        //班级、专业是否存在
        QueryWrapper<StudentClasses> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("className",graduateUpdateRequest.getClassName());
        StudentClasses studentClasses = studentClassesMapper.selectOne(queryWrapper);
        if (studentClasses == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        QueryWrapper<Graduates> queryWrapper1 = new QueryWrapper<>();
        queryWrapper1.eq("GraduateID",graduateUpdateRequest.getGraduateId());
        Graduates graduates = graduatesMapper.selectOne(queryWrapper1);

        if (graduates == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        graduates.setStudentName(graduateUpdateRequest.getStudentName());
        graduates.setGender(graduateUpdateRequest.getGender());
        graduates.setBirthdate(graduateUpdateRequest.getBirthdate());
        graduates.setMajorid(studentClasses.getMajorid());
        graduates.setClassid(studentClasses.getClassid());
        graduates.setGraduationyear(graduateUpdateRequest.getGraduationYear());
        graduates.setEmploymentstatus(graduateUpdateRequest.getEmploymentStatus());
        graduates.setMajorname(studentClasses.getMajorname());
        graduates.setClassname(studentClasses.getClassname());

        return graduatesMapper.update(graduates,queryWrapper1);
    }
}




