package com.example.ojs.pojo.vo;

import cn.hutool.json.JSONUtil;
import com.example.ojs.pojo.domain.QuestionDo;
import com.example.ojs.pojo.dto.question.JudgeCaseDto;
import com.example.ojs.pojo.dto.question.JudgeConfigDto;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 题目封装类
 * @author pengYuJun
 * @TableName question
 */
@Data
public class QuestionVo implements Serializable {
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
     * 题目提交数
     */
    private Integer submitNum;

    /**
     * 题目通过数
     */
    private Integer acceptedNum;

    /**
     * 判题配置（json 对象）
     */
    private JudgeConfigDto judgeConfig;

    /**
     * 判题案例
     */
    private String judgeCase;

    /**
     * 点赞数
     */
    private Integer thumbNum;

    /**
     * 收藏数
     */
    private Integer favourNum;

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
     * 创建题目人的信息
     */
    private UserVo userVo;

    /**
     * 包装类转对象
     *
     * @param questionVo
     * @return
     */
    public static QuestionDo voToObj(QuestionVo questionVo) {
        if (questionVo == null) {
            return null;
        }
        QuestionDo question = new QuestionDo();
        BeanUtils.copyProperties(questionVo, question);
        List<String> tagList = questionVo.getTags();
        if (tagList != null) {
            question.setTags(JSONUtil.toJsonStr(tagList));
        }
        JudgeConfigDto voJudgeConfig = questionVo.getJudgeConfig();
        if (voJudgeConfig != null) {
            question.setJudgeConfig(JSONUtil.toJsonStr(voJudgeConfig));
        }
        return question;
    }

    /**
     * 对象转包装类
     *
     * @param question
     * @return
     */
    public static QuestionVo objToVo(QuestionDo question) {
        if (question == null) {
            return null;
        }
        QuestionVo questionVo = new QuestionVo();
        BeanUtils.copyProperties(question, questionVo);
        List<String> tagList = JSONUtil.toList(question.getTags(), String.class);
        questionVo.setTags(tagList);
        String judgeConfigStr = question.getJudgeConfig();
        questionVo.setJudgeConfig(JSONUtil.toBean(judgeConfigStr, JudgeConfigDto.class));
        return questionVo;
    }

    @Serial
    private static final long serialVersionUID = 1L;
}