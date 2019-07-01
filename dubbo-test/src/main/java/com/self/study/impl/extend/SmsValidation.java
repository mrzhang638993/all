package com.self.study.impl.extend;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.validation.Validation;
import com.alibaba.dubbo.validation.Validator;

public class SmsValidation implements Validation {
    @Override
    public Validator getValidator(URL url) {
        return null;
    }
}
