package com.example.ojs.judge.codesandbox;

import com.example.ojs.judge.codesandbox.impl.ExampleCodeSandbox;
import com.example.ojs.judge.codesandbox.impl.RemoteCodeSandbox;
import com.example.ojs.judge.codesandbox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂（根据字符串参数创建指定的代码沙箱实例）
 * @author pengYuJun
 */
public class CodeSandboxFactory {

    /**
     * 创建代码沙箱示例
     *
     * @param type 沙箱类型
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        return switch (type) {
            case "remote" -> new RemoteCodeSandbox();
            case "thirdParty" -> new ThirdPartyCodeSandbox();
            default -> new ExampleCodeSandbox();
        };
    }
}
