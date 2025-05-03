package com.example.ojs.pojo.dto.question;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * @author pengYuJun
 */
@Data
public class QuestionUpdateDto implements Serializable {

    /**
     * id
     */
    private Long id;

    /**
     * 标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 标签列表
     */
    private List<String> tags;

    /**
     * 题目答案
     */
    private String answer;

    /**
     * 判题用例
     */
    private List<JudgeCaseDto> judgeCase;

    /**
     * 判题配置
     */
    private JudgeConfigDto judgeConfig;

    @Serial
    private static final long serialVersionUID = 1L;
}