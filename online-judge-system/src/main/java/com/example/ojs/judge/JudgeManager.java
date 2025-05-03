package com.example.ojs.judge;

import com.example.ojs.judge.codesandbox.model.JudgeInfo;
import com.example.ojs.judge.strategy.DefaultJudgeStrategy;
import com.example.ojs.judge.strategy.JavaLanguageJudgeStrategy;
import com.example.ojs.judge.strategy.JudgeContext;
import com.example.ojs.judge.strategy.JudgeStrategy;
import com.example.ojs.pojo.domain.QuestionSubmitDo;
import org.springframework.stereotype.Service;

/**
 * 判题管理（简化调用）
 * @author pengYuJun
 */
@Service
public class JudgeManager {

    /**
     * 执行判题
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext) {
        QuestionSubmitDo questionSubmit = judgeContext.getQuestionSubmit();
        String language = questionSubmit.getLanguage();
        JudgeStrategy judgeStrategy = new DefaultJudgeStrategy();
        if ("java".equals(language)) {
            judgeStrategy = new JavaLanguageJudgeStrategy();
        }
        return judgeStrategy.doJudge(judgeContext);
    }

}