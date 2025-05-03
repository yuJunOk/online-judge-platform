package com.example.ojs.judge.codesandbox;

import com.example.ojs.judge.codesandbox.model.ExecuteCodeRequest;
import com.example.ojs.judge.codesandbox.model.ExecuteCodeResponse;

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