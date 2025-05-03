package com.example.ojs.pojo.dto.question;

import lombok.Data;

/**
 * 题目用例
 * @author pengYuJun
 */
@Data
public class JudgeCaseDto {

    /**
     * 输入用例
     */
    private String input;

    /**
     * 输出用例
     */
    private String output;
}
