package com.example.ojs.pojo.dto.questionsubmit;

import com.example.ojs.pojo.dto.PageDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author pengYuJun
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class QuestionSubmitQueryPageDto extends PageDto implements Serializable {

    /**
     * 编程语言
     */
    private String language;

    /**
     * 提交状态
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 用户 id
     */
    private Long userId;

    @Serial
    private static final long serialVersionUID = 1L;
}