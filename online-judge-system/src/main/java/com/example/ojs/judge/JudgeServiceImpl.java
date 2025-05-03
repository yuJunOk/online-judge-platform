package com.example.ojs.judge;

import cn.hutool.json.JSONUtil;
import com.example.ojs.common.ResponseCode;
import com.example.ojs.exception.BusinessException;
import com.example.ojs.judge.codesandbox.CodeSandbox;
import com.example.ojs.judge.codesandbox.CodeSandboxFactory;
import com.example.ojs.judge.codesandbox.CodeSandboxProxy;
import com.example.ojs.judge.codesandbox.model.ExecuteCodeRequest;
import com.example.ojs.judge.codesandbox.model.ExecuteCodeResponse;
import com.example.ojs.judge.codesandbox.model.JudgeInfo;
import com.example.ojs.judge.strategy.JudgeContext;
import com.example.ojs.pojo.domain.QuestionDo;
import com.example.ojs.pojo.domain.QuestionSubmitDo;
import com.example.ojs.pojo.dto.question.JudgeCaseDto;
import com.example.ojs.pojo.enums.QuestionSubmitStatusEnum;
import com.example.ojs.service.QuestionService;
import com.example.ojs.service.QuestionSubmitService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${codesandbox.type:example}")
    private String type;


    @Override
    public QuestionSubmitDo doJudge(long questionSubmitId) {
        // 1）传入题目的提交 id，获取到对应的题目、提交信息（包含代码、编程语言等）
        QuestionSubmitDo questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "提交信息不存在");
        }
        Long questionId = questionSubmit.getQuestionId();
        QuestionDo question = questionService.getById(questionId);
        if (question == null) {
            throw new BusinessException(ResponseCode.NOT_FOUND, "题目不存在");
        }
        // 2）如果题目提交状态不为等待中，就不用重复执行了
        if (!questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ResponseCode.ERROR, "题目正在判题中");
        }
        // 3）更改判题（题目提交）的状态为 “判题中”，防止重复执行
        QuestionSubmitDo questionSubmitUpdate = new QuestionSubmitDo();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.RUNNING.getValue());
        boolean update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ResponseCode.ERROR, "题目状态更新错误");
        }
        // 4）调用沙箱，获取到执行结果
        CodeSandbox codeSandbox = CodeSandboxFactory.newInstance(type);
        codeSandbox = new CodeSandboxProxy(codeSandbox);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取输入用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCaseDto> judgeCaseList = JSONUtil.toList(judgeCaseStr, JudgeCaseDto.class);
        List<String> inputList = judgeCaseList.stream().map(JudgeCaseDto::getInput).collect(Collectors.toList());
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandbox.executeCode(executeCodeRequest);
        List<String> outputList = executeCodeResponse.getOutputList();
        // 5）根据沙箱的执行结果，设置题目的判题状态和信息
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(outputList);
        judgeContext.setJudgeCaseList(judgeCaseList);
        judgeContext.setQuestion(question);
        judgeContext.setQuestionSubmit(questionSubmit);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 6）修改数据库中的判题结果
        questionSubmitUpdate = new QuestionSubmitDo();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        update = questionSubmitService.updateById(questionSubmitUpdate);
        if (!update) {
            throw new BusinessException(ResponseCode.ERROR, "题目状态更新错误");
        }
        return questionSubmitService.getById(questionId);
    }
}