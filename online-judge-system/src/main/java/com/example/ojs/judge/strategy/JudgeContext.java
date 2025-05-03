package com.example.ojs.judge.strategy;

import com.example.ojs.judge.codesandbox.model.JudgeInfo;
import com.example.ojs.pojo.domain.QuestionDo;
import com.example.ojs.pojo.domain.QuestionSubmitDo;
import com.example.ojs.pojo.dto.question.JudgeCaseDto;
import lombok.Data;

import java.util.List;

/**
 * 上下文（用于定义在策略中传递的参数）
 * @author pengYuJun
 */
@Data
public class JudgeContext {

    private JudgeInfo judgeInfo;

    private List<String> inputList;

    private List<String> outputList;

    private List<JudgeCaseDto> judgeCaseList;

    private QuestionDo question;

    private QuestionSubmitDo questionSubmit;

}