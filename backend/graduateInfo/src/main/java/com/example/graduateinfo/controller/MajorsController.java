package com.example.graduateinfo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.graduateinfo.common.BaseResponse;
import com.example.graduateinfo.common.ErrorCode;
import com.example.graduateinfo.common.ResultUtils;
import com.example.graduateinfo.exception.BusinessException;
import com.example.graduateinfo.model.domain.Majors;
import com.example.graduateinfo.service.MajorsService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 20891
 */
@RestController
@RequestMapping("/major")
public class MajorsController {

    @Resource
    private MajorsService majorsService;

    @PostMapping("/add")
    public BaseResponse<Integer> addMajor(@RequestBody Majors majors) {
        if (StringUtils.isBlank(majors.getMajorName())) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        Majors majors1 = new Majors();
        majors1.setMajorName(majors.getMajorName());

        majorsService.addMajor(majors1);
        return ResultUtils.success(majors1.getMajorId());
    }

    @GetMapping("/selectPage")
    public BaseResponse<PageInfo<Majors>> searchMajor(@RequestParam(defaultValue = "1") Integer pageNum,
                                                      @RequestParam(defaultValue = "5") Integer pageSize,
                                                      String majorName) {
        if (pageNum < 0 || pageSize < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        if (StringUtils.isBlank(majorName)) {
            List<Majors> list = majorsService.list();
            return ResultUtils.success(PageInfo.of(list));
        }
        PageInfo<Majors> majorsIPage = majorsService.searchMajor(pageNum, pageSize, majorName);
        if (majorsIPage == null) {
            return ResultUtils.error(ErrorCode.NULL_ERROR,"不存在该专业");
        }
        return ResultUtils.success(majorsIPage);
    }

    @PostMapping("/update")
    public BaseResponse<Integer> update(@RequestBody Majors majors) {
        if (majors == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        int updateInfo = majorsService.updateInfo(majors);

        return ResultUtils.success(updateInfo);
    }
}
