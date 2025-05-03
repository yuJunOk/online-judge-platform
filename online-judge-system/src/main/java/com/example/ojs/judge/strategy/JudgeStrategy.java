package com.example.ojs.judge.strategy;

import com.example.ojs.judge.codesandbox.model.JudgeInfo;

/**
 * 判题策略
 * @author pengYuJun
 */
public interface JudgeStrategy {

    /**
     * 执行判题
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}