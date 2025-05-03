package com.example.ojs.pojo.vo;

import cn.hutool.json.JSONUtil;
import com.example.ojs.pojo.domain.QuestionSubmitDo;
import com.example.ojs.pojo.dto.question.JudgeConfigDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 题目提交封装类
 * @author pengYuJun
 * @TableName question
 */
@Data
public class QuestionSubmitVo implements Serializable {
    /**
     * id
     */
    private Long id;

    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 判题信息
     */
    private JudgeConfigDto judgeInfo;

    /**
     * 判题状态（0 - 待判题、1 - 判题中、2 - 成功、3 - 失败）
     */
    private Integer status;

    /**
     * 题目 id
     */
    private Long questionId;

    /**
     * 创建用户 id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 提交用户信息
     */
    private UserVo userVo;

    /**
     * 对应题目信息
     */
    private QuestionVo questionVO;

    /**
     * 包装类转对象
     *
     * @param questionSubmitVO
     * @return
     */
    public static QuestionSubmitDo voToObj(QuestionSubmitVo questionSubmitVO) {
        if (questionSubmitVO == null) {
            return null;
        }
        QuestionSubmitDo questionSubmit = new QuestionSubmitDo();
        BeanUtils.copyProperties(questionSubmitVO, questionSubmit);
        JudgeConfigDto judgeInfoObj = questionSubmitVO.getJudgeInfo();
        if (judgeInfoObj != null) {
            questionSubmit.setJudgeInfo(JSONUtil.toJsonStr(judgeInfoObj));
        }
        return questionSubmit;
    }

    /**
     * 对象转包装类
     *
     * @param questionSubmit
     * @return
     */
    public static QuestionSubmitVo objToVo(QuestionSubmitDo questionSubmit) {
        if (questionSubmit == null) {
            return null;
        }
        QuestionSubmitVo questionSubmitVO = new QuestionSubmitVo();
        BeanUtils.copyProperties(questionSubmit, questionSubmitVO);
        String judgeInfoStr = questionSubmit.getJudgeInfo();
        questionSubmitVO.setJudgeInfo(JSONUtil.toBean(judgeInfoStr, JudgeConfigDto.class));
        return questionSubmitVO;
    }

    @Serial
    private static final long serialVersionUID = 1L;
}