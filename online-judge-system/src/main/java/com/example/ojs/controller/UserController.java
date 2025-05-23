package com.example.ojs.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.example.ojs.common.ResponseCode;
import com.example.ojs.common.ResponseEntity;
import com.example.ojs.exception.BusinessException;
import com.example.ojs.pojo.domain.UserDo;
import com.example.ojs.pojo.dto.user.UserLoginDto;
import com.example.ojs.pojo.dto.user.UserQueryPageDto;
import com.example.ojs.pojo.dto.user.UserRegisterDto;
import com.example.ojs.pojo.vo.UserVo;
import com.example.ojs.service.UserService;
import com.example.ojs.utils.CommonUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import static com.example.ojs.constant.UserConstant.*;

/**
 * @author pengYuJun
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Long> userRegister(@RequestBody UserRegisterDto userRegisterDto, HttpServletRequest request) {
        if (userRegisterDto == null) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "参数为空");
        }
        String loginName = userRegisterDto.getLoginName();
        String loginPwd = userRegisterDto.getLoginPwd();
        String checkPwd = userRegisterDto.getCheckPwd();
        String email = userRegisterDto.getEmail();
        String captcha = userRegisterDto.getCaptcha();
        if (CommonUtils.isAnyBlank(loginName, loginPwd, checkPwd, email, captcha)) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "参数为空");
        }
        long result = userService.userRegister(loginName, loginPwd, checkPwd, email,  captcha, request);
        return ResponseEntity.success(result);
    }

    @PostMapping("/login")
    public ResponseEntity<UserVo> userLogin(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request) {
        if (userLoginDto == null) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "参数为空");
        }
        String loginName = userLoginDto.getLoginName();
        String loginPwd = userLoginDto.getLoginPwd();
        if (CommonUtils.isAnyBlank(loginName, loginPwd)) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "参数为空");
        }
        UserVo user = userService.userLogin(loginName, loginPwd, request);
        return ResponseEntity.success(user);
    }

    @PostMapping("/logout")
    public ResponseEntity<Integer> userLogout(HttpServletRequest request) {
        if (request == null) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "会话无效");
        }
        int result = userService.userLogout(request);
        return ResponseEntity.success(result);
    }

    @GetMapping("/current")
    public ResponseEntity<UserVo> getCurrentUser(HttpServletRequest request) {
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserVo currentUser = (UserVo) userObj;
        // 再查询，用户数据是否有效
        // todo: 校验用户是否合法
        try{
            Long userId = currentUser.getId();
            UserDo userDo = userService.getById(userId);
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(userDo, userVo);
            return ResponseEntity.success(userVo);
        }catch (Exception e){
            return ResponseEntity.failed(ResponseCode.UNAUTHORIZED);
        }
    }

    @PostMapping("/page")
    public ResponseEntity<IPage<UserVo>> getUserPage(@RequestBody UserQueryPageDto userQueryPageDto, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED, "无权限访问");
        }
        IPage<UserVo> userPage = userService.searchUser(userQueryPageDto);
        return ResponseEntity.success(userPage);
    }

    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED, "无权限访问");
        }
        if (id <= 0) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "删除请求id值无效");
        }
        boolean result = userService.removeById(id);
        if (result) {
            return ResponseEntity.success(true);
        }else {
            return ResponseEntity.failed("删除失败");
        }
    }

    @PostMapping("/update")
    public ResponseEntity<Boolean> updateUser(@RequestBody UserDo userDo, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED, "无权限访问");
        }
        if (userDo.getId() == null || userDo.getId() <= 0) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "更新请求id值无效");
        }
        boolean result = userService.updateById(userDo);
        if (result) {
            return ResponseEntity.success(true);
        }else {
            return ResponseEntity.failed("更新失败");
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Boolean> addUser(@RequestBody UserDo userDo, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ResponseCode.UNAUTHORIZED, "无权限访问");
        }
        boolean result = userService.addUser(userDo);
        if (result) {
            return ResponseEntity.success(true);
        }else {
            return ResponseEntity.failed("新增失败");
        }
    }

    @GetMapping("mailCaptcha")
    public ResponseEntity<Boolean> getMailCaptcha(String email, HttpServletRequest request) {
        String captcha = userService.getMailCaptcha(email, request);
        if (StringUtils.hasText(captcha)){
            request.getSession().setAttribute("captcha", captcha);
            return ResponseEntity.success(true);
        }else {
            return ResponseEntity.failed("获取验证码失败");
        }
    }

    /**
     * 是否为管理员
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        //
        UserVo user = (UserVo) request.getSession().getAttribute(USER_LOGIN_STATE);
        return user != null && user.getUserRole() == ADMIN_ROLE;
    }

}
