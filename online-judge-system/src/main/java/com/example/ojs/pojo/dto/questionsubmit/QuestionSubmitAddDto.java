package com.example.ojs.pojo.dto.questionsubmit;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author pengYuJun
 */
@Data
public class QuestionSubmitAddDto implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目 id
     */
    private Long questionId;

    @Serial
    private static final long serialVersionUID = 1L;
}