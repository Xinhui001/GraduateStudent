package com.example.graduateinfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.graduateinfo.common.ErrorCode;
import com.example.graduateinfo.exception.BusinessException;
import com.example.graduateinfo.model.domain.User;
import com.example.graduateinfo.service.UserService;
import com.example.graduateinfo.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.graduateinfo.constant.UserConstant.ADMIN_ROLE;
import static com.example.graduateinfo.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author 20891
* @description 针对表【User(用户信息表)】的数据库操作Service实现
* @createDate 2024-06-09 13:18:15
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    /**
     * 盐值
     */
    private static final String SALT = "jxh";


    @Resource
    private UserMapper userMapper;

    /**
     * 用户注册
     *
     * @param userName   账户
     * @param userPassword  密码
     * @param checkPassword 校验密码
     * @return 用户id
     */
    @Override
    public long userRegister(String userName, String userPassword, String checkPassword) {
        //账户、密码、校验码不为空
        if (StringUtils.isAnyBlank(userName, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        //账户长度不小于4
        if (userName.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户长度不小于4");
        }
        //账户中不得包含特殊字符
        String validPattern = "[ _`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t";
        Matcher matcher = Pattern.compile(validPattern).matcher(userName);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户中不得包含特殊字符");
        }
        //密码和校验码不小于8
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码和校验码不小于8");
        }
        //密码和校验码要相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码和校验码要相同");
        }
        //账户不可重复
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.eq("userName", userName);
        Long selectCount = userMapper.selectCount(queryWrapper);
        if (selectCount > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户不可重复");
        }

        //对密码加密
//        final String SALT = "jxh";
        String safetyPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());

        //存入数据库
        User user = new User();
        user.setUserName(userName);
        user.setUserPassword(safetyPassword);
        int saveResult = userMapper.insert(user);
        if (saveResult < 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "存入数据库失败");
        }

        return user.getUserId();
    }

    /**
     * 用户登录
     *
     * @param userName  账户
     * @param userPassword 密码
     * @param request      请求
     * @return 用户脱敏信息
     */
    @Override
    public User userLogin(String userName, String userPassword, HttpServletRequest request) {
        //账户、密码、校验码不为空
        if (StringUtils.isAnyBlank(userName, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        //账户长度不小于4
        if (userName.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户长度不小于4");
        }
        //账户中不得包含特殊字符
        String validPattern = "[ _`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]|\\n|\\r|\\t";
        Matcher matcher = Pattern.compile(validPattern).matcher(userName);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户中不得包含特殊字符");
        }
        //密码和校验码不小于8
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码和校验码不小于8");
        }
        String encodePassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        QueryWrapper<User> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("userName", userName);
        userQueryWrapper.eq("userPassword", encodePassword);
        User user = userMapper.selectOne(userQueryWrapper);
        if (user == null) {
            log.info("user login failed,userName cannot match userPassword");
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户不存在");
        }
        //脱敏用户信息
        User safetyUser = getSafetyUser(user);
        //记录用户登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);

        //返回用户脱敏信息
        return safetyUser;
    }

    /**
     * 用户脱敏
     *
     * @param user 用户对象
     * @return 脱敏用户信息
     */
    @Override
    public User getSafetyUser(User user) {
        if (user == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        User safetyUser = new User();
        safetyUser.setUserId(user.getUserId());
        safetyUser.setUserName(user.getUserName());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setCreateTime(user.getCreateTime());
        return safetyUser;
    }

    /**
     * 退出登录
     *
     * @param request 获取session信息
     * @return
     */
    @Override
    public int userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute(USER_LOGIN_STATE);
        return 1;
    }

    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    @Override
    public boolean isAdmin(HttpServletRequest request) {
        //仅管理员可查询
        User user = (User) request.getSession().getAttribute(USER_LOGIN_STATE);

        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

    /**
     * 获取当前登录用户
     *
     * @param request
     * @return
     */
    @Override
    public User getLoginUser(HttpServletRequest request) {
        // 先判断是否已登录
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        User currentUser = (User) userObj;
        if (currentUser == null || currentUser.getUserId() == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        // 从数据库查询（追求性能的话可以注释，直接走缓存）
        long userId = currentUser.getUserId();
        currentUser = this.getById(userId);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR);
        }
        return currentUser;
    }
}




