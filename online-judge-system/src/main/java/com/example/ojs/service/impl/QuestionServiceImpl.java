package com.example.ojs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ojs.common.ResponseCode;
import com.example.ojs.constant.CommonConstant;
import com.example.ojs.exception.BusinessException;
import com.example.ojs.exception.ThrowUtils;
import com.example.ojs.pojo.domain.QuestionDo;
import com.example.ojs.pojo.domain.UserDo;
import com.example.ojs.pojo.dto.question.QuestionQueryPageDto;
import com.example.ojs.pojo.vo.QuestionVo;
import com.example.ojs.pojo.vo.UserVo;
import com.example.ojs.service.QuestionService;
import com.example.ojs.mapper.QuestionMapper;
import com.example.ojs.service.UserService;
import com.example.ojs.utils.SqlUtils;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author pengYuJun
* @description 针对表【tb_question(题目表)】的数据库操作Service实现
* @createDate 2025-05-03 16:24:14
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, QuestionDo>
    implements QuestionService{

    @Resource
    private UserService userService;
    
    @Override
    public void validQuestion(QuestionDo question, boolean add) {
        if (question == null) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR);
        }
        String title = question.getTitle();
        String content = question.getContent();
        String tags = question.getTags();
        String answer = question.getAnswer();
        String judgeCase = question.getJudgeCase();
        String judgeConfig = question.getJudgeConfig();
        // 创建时，参数不能为空
        if (add) {
            ThrowUtils.throwIf(StringUtils.isAnyBlank(title, content, tags), ResponseCode.PARAMS_ERROR);
        }
        // 有参数则校验
        if (StringUtils.isNotBlank(title) && title.length() > 80) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "标题过长");
        }
        if (StringUtils.isNotBlank(content) && content.length() > 8192) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "内容过长");
        }
        if (StringUtils.isNotBlank(answer) && answer.length() > 8192) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "答案过长");
        }
        if (StringUtils.isNotBlank(judgeCase) && judgeCase.length() > 8192) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "判题用例过长");
        }
        if (StringUtils.isNotBlank(judgeConfig) && judgeConfig.length() > 8192) {
            throw new BusinessException(ResponseCode.PARAMS_ERROR, "判题配置过长");
        }
    }

    @Override
    public QueryWrapper<QuestionDo> getQueryWrapper(QuestionQueryPageDto questionQueryPageDto) {
        QueryWrapper<QuestionDo> queryWrapper = new QueryWrapper<>();
        if (questionQueryPageDto == null) {
            return queryWrapper;
        }
        Long id = questionQueryPageDto.getId();
        String title = questionQueryPageDto.getTitle();
        String content = questionQueryPageDto.getContent();
        List<String> tags = questionQueryPageDto.getTags();
        String answer = questionQueryPageDto.getAnswer();
        Long userId = questionQueryPageDto.getUserId();
        String sortField = questionQueryPageDto.getSortField();
        String sortOrder = questionQueryPageDto.getSortOrder();

        // 拼接查询条件
        queryWrapper.like(StringUtils.isNotBlank(title), "title", title);
        queryWrapper.like(StringUtils.isNotBlank(content), "content", content);
        queryWrapper.like(StringUtils.isNotBlank(answer), "answer", answer);
        if (!tags.isEmpty()) {
            for (String tag : tags) {
                queryWrapper.like("tags", "\"" + tag + "\"");
            }
        }
        queryWrapper.eq(ObjectUtils.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjectUtils.isNotEmpty(userId), "userId", userId);
        queryWrapper.eq("isDelete", false);
        queryWrapper.orderBy(SqlUtils.validSortField(sortField), sortOrder.equals(CommonConstant.SORT_ORDER_ASC),
                sortField);
        return queryWrapper;
    }

    @Override
    public QuestionVo getQuestionVo(QuestionDo question, HttpServletRequest request) {
        QuestionVo questionVo = QuestionVo.objToVo(question);
        // 1. 关联查询用户信息
        Long userId = question.getUserId();
        UserDo user = null;
        if (userId != null && userId > 0) {
            user = userService.getById(userId);
        }
        UserVo userVo = new UserVo();
        if (user != null) {
            BeanUtils.copyProperties(user, userVo);
        }
        questionVo.setUserVo(userVo);
        return questionVo;
    }

    @Override
    public Page<QuestionVo> getQuestionVoPage(Page<QuestionDo> questionPage, HttpServletRequest request) {
        List<QuestionDo> questionList = questionPage.getRecords();
        Page<QuestionVo> questionVoPage = new Page<>(questionPage.getCurrent(), questionPage.getSize(), questionPage.getTotal());
        if (questionList.isEmpty()) {
            return questionVoPage;
        }
        // 1. 关联查询用户信息
        Set<Long> userIdSet = questionList.stream().map(QuestionDo::getUserId).collect(Collectors.toSet());
        Map<Long, List<UserDo>> userIdUserListMap = userService.listByIds(userIdSet).stream()
                .collect(Collectors.groupingBy(UserDo::getId));
        // 填充信息
        List<QuestionVo> questionVoList = questionList.stream().map(question -> {
            QuestionVo questionVo = QuestionVo.objToVo(question);
            Long userId = question.getUserId();
            UserDo user = null;
            if (userIdUserListMap.containsKey(userId)) {
                user = userIdUserListMap.get(userId).get(0);
            }
            UserVo userVo = new UserVo();
            if (user != null) {
                BeanUtils.copyProperties(user, userVo);
            }
            questionVo.setUserVo(userVo);
            return questionVo;
        }).collect(Collectors.toList());
        questionVoPage.setRecords(questionVoList);
        return questionVoPage;
    }
}




