package com.example.ojs.judge;

import com.example.ojs.pojo.domain.QuestionSubmitDo;

/**
 * 判题服务
 */
public interface JudgeService {

    /**
     * 判题
     * @param questionSubmitId
     * @return
     */
    QuestionSubmitDo doJudge(long questionSubmitId);
}
