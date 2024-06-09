package com.example.graduateinfo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.graduateinfo.common.BaseResponse;
import com.example.graduateinfo.common.ErrorCode;
import com.example.graduateinfo.common.ResultUtils;
import com.example.graduateinfo.exception.BusinessException;
import com.example.graduateinfo.model.domain.StudentClasses;
import com.example.graduateinfo.model.request.StudentClassesAddRequest;
import com.example.graduateinfo.model.request.StudentClassesUpdateRequest;
import com.example.graduateinfo.service.StudentClassesService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * @author 20891
 */
@RestController
@RequestMapping("/class")
public class StudentClassesController {

    @Resource
    private StudentClassesService studentClassesService;

    @PostMapping("/add")
    public BaseResponse<Integer> add(@RequestBody StudentClassesAddRequest studentClassesAddRequest){
        if (studentClassesAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer result = studentClassesService.addStudentClass(studentClassesAddRequest);

        return ResultUtils.success(result);
    }

    @GetMapping("/selectPage")
    public BaseResponse<IPage<StudentClasses>> selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                          @RequestParam(defaultValue = "5") Integer pageSize,
                                                          StudentClassesAddRequest studentClasses) {

        if (pageNum < 0 || pageSize < 0 || studentClasses == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        IPage<StudentClasses> studentClassesIPage = studentClassesService.searchClasses(pageNum, pageSize, studentClasses);

        return ResultUtils.success(studentClassesIPage);
    }

    @PostMapping("/update")
    public BaseResponse<Integer> update(@RequestBody StudentClassesUpdateRequest studentClassesUpdateRequest) {
        if (studentClassesUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int updateInfo = studentClassesService.updateInfo(studentClassesUpdateRequest);

        return ResultUtils.success(updateInfo);
    }
}
