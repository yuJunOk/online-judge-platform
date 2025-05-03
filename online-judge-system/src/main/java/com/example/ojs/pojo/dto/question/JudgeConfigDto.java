package com.example.ojs.pojo.dto.question;

import lombok.Data;

/**
 * 题目配置
 * @author pengYuJun
 */
@Data
public class JudgeConfigDto {

    /**
     * 时间限制（ms）
     */
    private Long timeLimit;

    /**
     * 内存限制（KB）
     */
    private Long memoryLimit;

    /**
     * 堆栈限制（KB）
     */
    private Long stackLimit;
}