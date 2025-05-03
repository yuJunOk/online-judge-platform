package com.example.ojs.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ojs.annoation.AuthCheck;
import com.example.ojs.common.ResponseCode;
import com.example.ojs.common.ResponseEntity;
import com.example.ojs.constant.UserConstant;
import com.example.ojs.exception.BusinessException;
import com.example.ojs.exception.ThrowUtils;
import com.example.ojs.pojo.domain.QuestionDo;
import com.example.ojs.pojo.domain.QuestionSubmitDo;
import com.example.ojs.pojo.dto.IdDto;
import com.example.ojs.pojo.dto.question.*;
import com.example.ojs.pojo.dto.questionsubmit.QuestionSubmitAddDto;
import com.example.ojs.pojo.dto.questionsubmit.QuestionSubmitQueryPageDto;
import com.example.ojs.pojo.vo.QuestionSubmitVo;
import com.example.ojs.pojo.vo.QuestionVo;
import com.example.ojs.pojo.vo.UserVo;
import com.example.ojs.service.QuestionService;
import com.example.ojs.service.QuestionSubmitService;
import com.example.ojs.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 题目接口
 *
 */
@RestController
@RequestMapping("/question")
@Slf4j
public class QuestionController {

    @Resource
    private QuestionService questionService;

    @Resource
    private UserService userService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    // region 增删改查

    /**
     * 创建
     *
     * @param questionAddDto
     * @param request
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Long> addQuestion(@RequestBody QuestionAddDto questionAddDto, HttpServletRequest request) {
        if (questionAddDto == null) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        QuestionDo question = new QuestionDo();
        BeanUtils.copyProperties(questionAddDto, question);
        List<String> tags = questionAddDto.getTags();
        if (tags != null) {
            question.setTags(JSONUtil.toJsonStr(tags));
        }
        List<JudgeCaseDto> judgeCase = questionAddDto.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(JSONUtil.toJsonStr(judgeCase));
        }
        JudgeConfigDto judgeConfig = questionAddDto.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }
        questionService.validQuestion(question, true);
        UserVo loginUser = userService.getCurrentUser(request);
        question.setUserId(loginUser.getId());
        question.setFavourNum(0);
        question.setThumbNum(0);
        boolean result = questionService.save(question);
        ThrowUtils.throwIf(!result, ResponseCode.ERROR);
        long newQuestionId = question.getId();
        return ResponseEntity.success(newQuestionId);
    }

    /**
     * 删除
     *
     * @param idDto
     * @param request
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity<Boolean> deleteQuestion(@RequestBody IdDto idDto, HttpServletRequest request) {
        if (idDto == null || idDto.getId() <= 0) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        UserVo user = userService.getCurrentUser(request);
        long id = idDto.getId();
        // 判断是否存在
        QuestionDo oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ResponseCode.NOT_FOUND);
        // 仅本人或管理员可删除
        if (!oldQuestion.getUserId().equals(user.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ResponseCode.FORBIDDEN);
        }
        boolean b = questionService.removeById(id);
        return ResponseEntity.success(b);
    }

    /**
     * 更新（仅管理员）
     *
     * @param questionUpdateDto
     * @return
     */
    @PostMapping("/update")
    @AuthCheck(anyRole = {}, mustRole = UserConstant.ADMIN_ROLE)
    public ResponseEntity<Boolean> updateQuestion(@RequestBody QuestionUpdateDto questionUpdateDto) {
        if (questionUpdateDto == null || questionUpdateDto.getId() <= 0) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        QuestionDo question = new QuestionDo();
        BeanUtils.copyProperties(questionUpdateDto, question);
        List<String> tags = questionUpdateDto.getTags();
        if (tags != null) {
            question.setTags(JSONUtil.toJsonStr(tags));
        }
        List<JudgeCaseDto> judgeCase = questionUpdateDto.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(JSONUtil.toJsonStr(judgeCase));
        }
        JudgeConfigDto judgeConfig = questionUpdateDto.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }
        // 参数校验
        questionService.validQuestion(question, false);
        long id = questionUpdateDto.getId();
        // 判断是否存在
        QuestionDo oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ResponseCode.NOT_FOUND);
        boolean result = questionService.updateById(question);
        return ResponseEntity.success(result);
    }

    /**
     * 根据 id 获取
     *
     * @param id
     * @return
     */
    @GetMapping("/get")
    public ResponseEntity<QuestionDo> getQuestionById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        QuestionDo question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
        UserVo loginUser = userService.getCurrentUser(request);
        // 不是本人或管理员，不能直接获取所有信息
        if (!question.getUserId().equals(loginUser.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ResponseCode.FORBIDDEN);
        }
        return ResponseEntity.success(question);
    }

    /**
     * 根据 id 获取（脱敏）
     *
     * @param id
     * @return
     */
    @GetMapping("/get/vo")
    public ResponseEntity<QuestionVo> getQuestionVoById(long id, HttpServletRequest request) {
        if (id <= 0) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        QuestionDo question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
        return ResponseEntity.success(questionService.getQuestionVo(question, request));
    }

    /**
     * 分页获取列表（封装类）
     *
     * @param questionQueryPageDto
     * @param request
     * @return
     */
    @PostMapping("/list/page/vo")
    public ResponseEntity<Page<QuestionVo>> listQuestionVoByPage(@RequestBody QuestionQueryPageDto questionQueryPageDto,
                                                                 HttpServletRequest request) {
        long current = questionQueryPageDto.getCurrent();
        long size = questionQueryPageDto.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ResponseCode.PARAMS_ERROR);
        Page<QuestionDo> questionPage = questionService.page(new Page<>(current, size), questionService.getQueryWrapper(questionQueryPageDto));
        return ResponseEntity.success(questionService.getQuestionVoPage(questionPage, request));
    }

    /**
     * 分页获取当前用户创建的资源列表
     *
     * @param questionQueryPageDto
     * @param request
     * @return
     */
    @PostMapping("/my/list/page/vo")
    public ResponseEntity<Page<QuestionVo>> listMyQuestionVoByPage(@RequestBody QuestionQueryPageDto questionQueryPageDto,
                                                                   HttpServletRequest request) {
        if (questionQueryPageDto == null) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        UserVo loginUser = userService.getCurrentUser(request);
        questionQueryPageDto.setUserId(loginUser.getId());
        long current = questionQueryPageDto.getCurrent();
        long size = questionQueryPageDto.getPageSize();
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ResponseCode.PARAMS_ERROR);
        Page<QuestionDo> questionPage = questionService.page(new Page<>(current, size), questionService.getQueryWrapper(questionQueryPageDto));
        return ResponseEntity.success(questionService.getQuestionVoPage(questionPage, request));
    }

    /**
     * 分页获取题目列表（仅管理员）
     *
     * @param questionQueryPageDto
     * @param request
     * @return
     */
    @PostMapping("/list/page")
    @AuthCheck(anyRole = {}, mustRole = UserConstant.ADMIN_ROLE)
    public ResponseEntity<Page<QuestionDo>> listQuestionByPage(@RequestBody QuestionQueryPageDto questionQueryPageDto,
                                                   HttpServletRequest request) {
        long current = questionQueryPageDto.getCurrent();
        long size = questionQueryPageDto.getPageSize();
        Page<QuestionDo> questionPage = questionService.page(new Page<>(current, size), questionService.getQueryWrapper(questionQueryPageDto));
        return ResponseEntity.success(questionPage);
    }

    // endregion

    /**
     * 编辑（用户）
     *
     * @param questionEditDto
     * @param request
     * @return
     */
    @PostMapping("/edit")
    public ResponseEntity<Boolean> editQuestion(@RequestBody QuestionEditDto questionEditDto, HttpServletRequest request) {
        if (questionEditDto == null || questionEditDto.getId() <= 0) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        QuestionDo question = new QuestionDo();
        BeanUtils.copyProperties(questionEditDto, question);
        List<String> tags = questionEditDto.getTags();
        if (tags != null) {
            question.setTags(JSONUtil.toJsonStr(tags));
        }
        List<JudgeCaseDto> judgeCase = questionEditDto.getJudgeCase();
        if (judgeCase != null) {
            question.setJudgeCase(JSONUtil.toJsonStr(judgeCase));
        }
        JudgeConfigDto judgeConfig = questionEditDto.getJudgeConfig();
        if (judgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(judgeConfig));
        }
        // 参数校验
        questionService.validQuestion(question, false);
        UserVo loginUser = userService.getCurrentUser(request);
        long id = questionEditDto.getId();
        // 判断是否存在
        QuestionDo oldQuestion = questionService.getById(id);
        ThrowUtils.throwIf(oldQuestion == null, ResponseCode.NOT_FOUND);
        // 仅本人或管理员可编辑
        if (!oldQuestion.getUserId().equals(loginUser.getId()) && !userService.isAdmin(request)) {
            throw new BusinessException(ResponseCode.FORBIDDEN);
        }
        boolean result = questionService.updateById(question);
        return ResponseEntity.success(result);
    }

    /**
     * 提交题目
     *
     * @param questionSubmitAddDto
     * @param request
     * @return 提交记录的 id
     */
    @PostMapping("/question_submit/do")
    public ResponseEntity<Long> doQuestionSubmit(@RequestBody QuestionSubmitAddDto questionSubmitAddDto,
                                               HttpServletRequest request) {
        if (questionSubmitAddDto == null || questionSubmitAddDto.getQuestionId() <= 0) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        // 登录才能提交
        long questionSubmitId = questionSubmitService.doQuestionSubmit(questionSubmitAddDto, request);
        return ResponseEntity.success(questionSubmitId);
    }

    /**
     * 分页获取题目提交列表（除了管理员外，普通用户只能看到非答案、提交代码等公开信息）
     *
     * @param questionSubmitQueryPageDto
     * @param request
     * @return
     */
    @PostMapping("/question_submit/list/page")
    public ResponseEntity<Page<QuestionSubmitVo>> listQuestionSubmitByPage(@RequestBody QuestionSubmitQueryPageDto questionSubmitQueryPageDto,
                                                                           HttpServletRequest request) {
        long current = questionSubmitQueryPageDto.getCurrent();
        long size = questionSubmitQueryPageDto.getPageSize();
        // 从数据库中查询原始的题目提交分页信息
        Page<QuestionSubmitDo> questionSubmitPage = questionSubmitService.page(new Page<>(current, size),
                questionSubmitService.getQueryWrapper(questionSubmitQueryPageDto));
        final UserVo loginUser = userService.getCurrentUser(request);
        // 返回脱敏信息
        return ResponseEntity.success(questionSubmitService.getQuestionSubmitVoPage(questionSubmitPage, request));
    }



}
