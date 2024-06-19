package com.example.graduateinfo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.graduateinfo.common.BaseResponse;
import com.example.graduateinfo.common.ErrorCode;
import com.example.graduateinfo.common.ResultUtils;
import com.example.graduateinfo.exception.BusinessException;
import com.example.graduateinfo.model.domain.StudentClasses;
import com.example.graduateinfo.model.domain.User;
import com.example.graduateinfo.model.request.StudentClassesAddRequest;
import com.example.graduateinfo.model.request.StudentClassesUpdateRequest;
import com.example.graduateinfo.service.StudentClassesService;
import com.example.graduateinfo.service.UserService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import static com.example.graduateinfo.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author 20891
 */
@RestController
@RequestMapping("/class")
public class StudentClassesController {

    @Resource
    private StudentClassesService studentClassesService;

    @Resource
    private UserService userService;

    @PostMapping("/add")
    public BaseResponse<Integer> add(@RequestBody StudentClassesAddRequest studentClassesAddRequest, HttpServletRequest request){
        if (studentClassesAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        Integer result = studentClassesService.addStudentClass(studentClassesAddRequest);

        return ResultUtils.success(result);
    }

    @GetMapping("/selectPage")
    public BaseResponse<PageInfo<StudentClasses>> selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                             @RequestParam(defaultValue = "5") Integer pageSize,
                                                             StudentClassesAddRequest studentClasses,HttpServletRequest request) {

        if (pageNum < 0 || pageSize < 0 || studentClasses == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        PageInfo<StudentClasses> studentClassesIPage = studentClassesService.searchClasses(pageNum, pageSize, studentClasses);

        return ResultUtils.success(studentClassesIPage);
    }

    @PostMapping("/update")
    public BaseResponse<Integer> update(@RequestBody StudentClassesUpdateRequest studentClassesUpdateRequest,HttpServletRequest request) {
        if (studentClassesUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        int updateInfo = studentClassesService.updateInfo(studentClassesUpdateRequest);

        return ResultUtils.success(updateInfo);
    }
}
