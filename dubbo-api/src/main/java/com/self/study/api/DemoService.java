package com.self.study.api;


import javax.validation.constraints.NotNull;

public interface DemoService {

    String sayHello(@NotNull String name);
}
