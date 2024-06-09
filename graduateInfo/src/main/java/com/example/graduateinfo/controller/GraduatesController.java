package com.example.graduateinfo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.graduateinfo.common.BaseResponse;
import com.example.graduateinfo.common.ErrorCode;
import com.example.graduateinfo.common.ResultUtils;
import com.example.graduateinfo.exception.BusinessException;
import com.example.graduateinfo.model.domain.Graduates;
import com.example.graduateinfo.model.domain.Majors;
import com.example.graduateinfo.model.domain.StudentClasses;
import com.example.graduateinfo.model.domain.User;
import com.example.graduateinfo.model.request.GraduateAddRequest;
import com.example.graduateinfo.model.request.GraduateSearchRequest;
import com.example.graduateinfo.model.request.GraduateUpdateRequest;
import com.example.graduateinfo.model.request.StudentClassesAddRequest;
import com.example.graduateinfo.service.GraduatesService;
import com.example.graduateinfo.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
    public BaseResponse<Integer> addStudent(@RequestBody GraduateAddRequest graduateAddRequest) {
        if (graduateAddRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Integer addStudent = graduatesService.addStudent(graduateAddRequest);
        return ResultUtils.success(addStudent);
    }

    @GetMapping("/selectPage")
    public BaseResponse<IPage<Graduates>> selectPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                                     @RequestParam(defaultValue = "5") Integer pageSize,
                                                     GraduateSearchRequest graduateSearchRequest) {

        if (pageNum < 0 || pageSize < 0 || graduateSearchRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }

        IPage<Graduates> graduatesIPage = graduatesService.searchClasses(pageNum, pageSize, graduateSearchRequest);

        return ResultUtils.success(graduatesIPage);
    }

    @PostMapping("/update")
    public BaseResponse<Integer> update(@RequestBody GraduateUpdateRequest graduateUpdateRequest) {
        if (graduateUpdateRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int updateInfo = graduatesService.updateInfo(graduateUpdateRequest);

        return ResultUtils.success(updateInfo);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> delete(@RequestBody GraduateUpdateRequest graduateUpdateRequest, HttpServletRequest request) {
        if (graduateUpdateRequest == null || graduateUpdateRequest.getGraduateId() <= 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        long id = graduateUpdateRequest.getGraduateId();
        // 判断是否存在
        Graduates oldGraduate = graduatesService.getById(id);
        if (oldGraduate == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 仅本人或管理员可删除
        if (!userService.isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }
        boolean b = graduatesService.removeById(id);
        return ResultUtils.success(b);
    }
}
