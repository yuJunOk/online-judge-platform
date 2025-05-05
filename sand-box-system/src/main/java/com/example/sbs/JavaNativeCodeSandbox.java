package com.example.sbs;

import com.example.sbs.model.ExecuteCodeRequest;
import com.example.sbs.model.ExecuteCodeResponse;
import org.springframework.stereotype.Component;

/** 直接复用模板方法
 * @author pengYuJun
 */
@Component
public class JavaNativeCodeSandbox extends BaseJavaCodeSandboxTemplate {

    @Override
    public ExecuteCodeResponse executeCode(ExecuteCodeRequest executeCodeRequest) {
        return super.executeCode(executeCodeRequest);
    }

}
