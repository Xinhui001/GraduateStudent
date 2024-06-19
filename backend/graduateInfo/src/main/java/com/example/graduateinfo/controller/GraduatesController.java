package com.example.graduateinfo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.graduateinfo.common.BaseResponse;
import com.example.graduateinfo.common.DeleteRequest;
import com.example.graduateinfo.common.ErrorCode;
import com.example.graduateinfo.common.ResultUtils;
import com.example.graduateinfo.exception.BusinessException;
import com.example.graduateinfo.model.domain.Graduates;
import com.example.graduateinfo.model.domain.User;
import com.example.graduateinfo.model.request.GraduateAddRequest;
import com.example.graduateinfo.model.request.GraduateSearchRequest;
import com.example.graduateinfo.model.request.GraduateUpdateRequest;
import com.example.graduateinfo.service.GraduatesService;
import com.example.graduateinfo.service.UserService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Lang;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.example.graduateinfo.constant.UserConstant.USER_LOGIN_STATE;

/**
 * @author 20891
 */
@RestController
@RequestMapping("/student")
public class GraduatesController {

    @Resource
    private GraduatesService graduatesService;

    @Resource
    private UserService userService;

    @PostMapping("/add")
    public BaseResponse<Integer> addStudent(@RequestBody GraduateAddRequest graduateAddRequest,HttpServletRequest request) {
        if (graduateAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        Integer addStudent = graduatesService.addStudent(graduateAddRequest);
        return ResultUtils.success(addStudent);
    }

    @GetMapping("/selectPage")
    public BaseResponse<PageInfo<Graduates>> selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                          @RequestParam(defaultValue = "5") Integer pageSize,
                                                          GraduateSearchRequest graduateSearchRequest, HttpServletRequest request) {

        if (pageNum < 0 || pageSize < 0 || graduateSearchRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        PageInfo<Graduates> graduates = graduatesService.searchClasses(pageNum, pageSize, graduateSearchRequest);

        return ResultUtils.success(graduates);
    }

    @PostMapping("/update")
    public BaseResponse<Integer> update(@RequestBody GraduateUpdateRequest graduateUpdateRequest,HttpServletRequest request) {
        if (graduateUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        int updateInfo = graduatesService.updateInfo(graduateUpdateRequest);

        return ResultUtils.success(updateInfo);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestBody DeleteRequest deleteRequest, HttpServletRequest request) {
        if (deleteRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long graduateId = deleteRequest.getGraduateId();
        if (graduateId <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 判断是否存在
        Graduates oldGraduate = graduatesService.getById(graduateId);
        if (oldGraduate == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 仅本人或管理员可删除
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);
        if (user == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = graduatesService.removeById(graduateId);
        return ResultUtils.success(b);
    }
}
