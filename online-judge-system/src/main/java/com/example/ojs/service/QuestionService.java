package com.example.ojs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ojs.pojo.domain.QuestionDo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ojs.pojo.dto.question.QuestionQueryPageDto;
import com.example.ojs.pojo.vo.QuestionVo;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author pengYuJun
* @description 针对表【tb_question(题目表)】的数据库操作Service
* @createDate 2025-05-03 16:24:14
*/
public interface QuestionService extends IService<QuestionDo> {

    /**
     * 校验
     *
     * @param questionDo
     * @param add
     */
    void validQuestion(QuestionDo questionDo, boolean add);

    /**
     * 获取查询条件
     *
     * @param questionQueryPageDto
     * @return
     */
    QueryWrapper<QuestionDo> getQueryWrapper(QuestionQueryPageDto questionQueryPageDto);

    /**
     * 获取题目封装
     *
     * @param question
     * @param request
     * @return
     */
    QuestionVo getQuestionVo(QuestionDo question, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionPage
     * @param request
     * @return
     */
    Page<QuestionVo> getQuestionVoPage(Page<QuestionDo> questionPage, HttpServletRequest request);
}
