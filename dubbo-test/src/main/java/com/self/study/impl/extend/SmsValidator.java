package com.self.study.impl.extend;

import com.alibaba.dubbo.rpc.RpcException;
import com.alibaba.dubbo.validation.Validator;

public class SmsValidator implements Validator {

    private   SmsValidator  validator;

    public SmsValidator(SmsValidator validator){
        this.validator=validator;
    }
    @Override
    public void validate(String methodName, Class<?>[] parameterTypes, Object[] arguments) throws Exception {
        if (arguments.length==0||arguments==null){
            throw   new RpcException("参数额数为0或者为null",null);
        }
        //  后续的dubbo的参数校验的操作实现的。


    }
}
