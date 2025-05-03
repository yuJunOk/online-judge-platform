package com.example.ojs.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.ojs.pojo.domain.QuestionSubmitDo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.ojs.pojo.dto.questionsubmit.QuestionSubmitAddDto;
import com.example.ojs.pojo.dto.questionsubmit.QuestionSubmitQueryPageDto;
import com.example.ojs.pojo.vo.QuestionSubmitVo;
import com.example.ojs.pojo.vo.UserVo;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author pengYuJun
* @description 针对表【tb_question_submit(题目提交表)】的数据库操作Service
* @createDate 2025-05-03 16:27:59
*/
public interface QuestionSubmitService extends IService<QuestionSubmitDo> {

    /**
     * 题目提交
     *
     * @param questionSubmitAddDto 题目提交信息
     * @param request
     * @return
     */
    Long doQuestionSubmit(QuestionSubmitAddDto questionSubmitAddDto, HttpServletRequest request);

    /**
     * 获取查询条件
     *
     * @param questionSubmitQueryPageDto
     * @return
     */
    QueryWrapper<QuestionSubmitDo> getQueryWrapper(QuestionSubmitQueryPageDto questionSubmitQueryPageDto);

    /**
     * 获取题目封装
     *
     * @param questionSubmit
     * @param request
     * @return
     */
    QuestionSubmitVo getQuestionSubmitVo(QuestionSubmitDo questionSubmit, HttpServletRequest request);

    /**
     * 分页获取题目封装
     *
     * @param questionSubmitPage
     * @param loginUser
     * @return
     */
    Page<QuestionSubmitVo> getQuestionSubmitVoPage(Page<QuestionSubmitDo> questionSubmitPage, HttpServletRequest request);
}
