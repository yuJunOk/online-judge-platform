package com.example.ojs.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.ojs.pojo.domain.QuestionDo;
import com.example.ojs.service.QuestionService;
import com.example.ojs.mapper.QuestionMapper;
import org.springframework.stereotype.Service;

/**
* @author pengYuJun
* @description 针对表【tb_question(题目表)】的数据库操作Service实现
* @createDate 2025-05-03 16:24:14
*/
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, QuestionDo>
    implements QuestionService{

}




