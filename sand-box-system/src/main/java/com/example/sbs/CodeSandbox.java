package com.example.sbs;

import com.example.sbs.model.ExecuteCodeRequest;
import com.example.sbs.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 * @author pengYuJun
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest);
}