package com.example.ojs.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ojs.common.ResponseCode;
import com.example.ojs.constant.CommonConstant;
import com.example.ojs.exception.BusinessException;
import com.example.ojs.judge.JudgeService;
import com.example.ojs.pojo.domain.QuestionDo;
import com.example.ojs.pojo.domain.QuestionSubmitDo;
import com.example.ojs.pojo.dto.questionsubmit.QuestionSubmitAddDto;
import com.example.ojs.pojo.dto.questionsubmit.QuestionSubmitQueryPageDto;
import com.example.ojs.pojo.enums.QuestionSubmitLanguageEnum;
import com.example.ojs.pojo.enums.QuestionSubmitStatusEnum;
import com.example.ojs.pojo.vo.QuestionSubmitVo;
import com.example.ojs.pojo.vo.UserVo;
import com.example.ojs.service.QuestionService;
import com.example.ojs.service.QuestionSubmitService;
import com.example.ojs.mapper.QuestionSubmitMapper;
import com.example.ojs.service.UserService;
import com.example.ojs.utils.SqlUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
* @author pengYuJun
* @description 针对表【tb_question_submit(题目提交表)】的数据库操作Service实现
* @createDate 2025-05-03 16:27:59
*/
@Service
public class QuestionSubmitServiceImpl extends ServiceImpl<QuestionSubmitMapper, QuestionSubmitDo>
    implements QuestionSubmitService{

    @Resource
    private UserService userService;

    @Resource
    private QuestionService questionService;

    @Resource
    @Lazy
    private JudgeService judgeService;

    @Override
    public Long doQuestionSubmit(QuestionSubmitAddDto questionSubmitAddDto, HttpServletRequest request) {
        // 是否已登录
        UserVo loginUser = userService.getCurrentUser(request);
        long userId = loginUser.getId();
        // 校验编程语言是否合法
        String language = questionSubmitAddDto.getLanguage();
        QuestionSubmitLanguageEnum languageEnum = QuestionSubmitLanguageEnum.getEnumByValue(language);
        if (languageEnum == null) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "编程语言错误");
        }
        long questionId = questionSubmitAddDto.getQuestionId();
        // 判断实体是否存在，根据类别获取实体
        QuestionDo question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND);
        }
        // 每个用户串行提交题目
        QuestionSubmitDo questionSubmit = new QuestionSubmitDo();
        questionSubmit.setUserId(userId);
        questionSubmit.setQuestionId(questionId);
        questionSubmit.setCode(questionSubmitAddDto.getCode());
        questionSubmit.setLanguage(language);
        // 设置初始状态
        questionSubmit.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        questionSubmit.setJudgeInfo("{}");
        boolean save = this.save(questionSubmit);
        if (!save){
            throw new BusinessException(ResponseCode.ERROR, "数据插入失败");
        }
        Long questionSubmitId = questionSubmit.getId();
        // 执行判题服务
        CompletableFuture.runAsync(() -> {
            judgeService.doJudge(questionSubmitId);
        });
        return questionSubmitId;
    }

    @Override
    public QueryWrapper<QuestionSubmitDo> getQueryWrapper(QuestionSubmitQueryPageDto questionSubmitQueryPageDto) {
        QueryWrapper<QuestionSubmitDo> queryWrapper = new QueryWrapper<>();
        if (questionSubmitQueryPageDto == null) {
            return queryWrapper;
        }
        String language = questionSubmitQueryPageDto.getLanguage();
        Integer status = questionSubmitQueryPageDto.getStatus();
        Long questionId = questionSubmitQueryPageDto.getQuestionId();
        Long userId = questionSubmitQueryPageDto.getUserId();
        String sortField = questionSubmitQueryPageDto.getSortField();
        String sortOrder = questionSubmitQueryPageDto.getSortOrder();

        // 拼接查询条件
        queryWrapper.eq(StringUtils.isNotBlank(language), "language", language);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "user_id", userId);
        queryWrapper.eq(ObjectUtils.isNotEmpty(questionId), "question_id", questionId);
        queryWrapper.eq(QuestionSubmitStatusEnum.getEnumByValue(status) != null, "status", status);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                StrUtil.toUnderlineCase(sortField));
        return queryWrapper;
    }

    @Override
    public QuestionSubmitVo getQuestionSubmitVo(QuestionSubmitDo questionSubmit, HttpServletRequest request) {
        QuestionSubmitVo questionSubmitVo = QuestionSubmitVo.objToVo(questionSubmit);
        // 脱敏：仅本人和管理员能看见自己（提交 userId 和登录用户 id 不同）提交的代码
        UserVo loginUser = userService.getCurrentUser(request);
        long userId = loginUser.getId();
        // 处理脱敏
        if (userId != questionSubmit.getUserId() && !userService.isAdmin(request)) {
            questionSubmitVo.setCode(null);
        }
        return questionSubmitVo;
    }

    @Override
    public Page<QuestionSubmitVo> getQuestionSubmitVoPage(Page<QuestionSubmitDo> questionSubmitPage, HttpServletRequest request) {
        List<QuestionSubmitDo> questionSubmitList = questionSubmitPage.getRecords();
        Page<QuestionSubmitVo> questionSubmitVoPage = new Page<>(questionSubmitPage.getCurrent(), questionSubmitPage.getSize(), questionSubmitPage.getTotal());
        if (CollectionUtils.isEmpty(questionSubmitList)) {
            return questionSubmitVoPage;
        }
        List<QuestionSubmitVo> questionSubmitVoList = questionSubmitList.stream()
                .map(questionSubmit -> getQuestionSubmitVo(questionSubmit, request))
                .toList();
        questionSubmitVoPage.setRecords(questionSubmitVoList);
        return questionSubmitVoPage;
    }
}




