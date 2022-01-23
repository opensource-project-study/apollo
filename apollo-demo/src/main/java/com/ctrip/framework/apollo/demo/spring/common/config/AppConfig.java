/*
 * Copyright 2021 Apollo Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.ctrip.framework.apollo.demo.spring.common.config;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;

import org.springframework.context.annotation.Configuration;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
@Configuration
@EnableApolloConfig(value = "application", order = 10)
public class AppConfig {

    static {
        // 跳过Apollo Meta Server服务发现
        // 参考：https://www.apolloconfig.com/#/zh/usage/java-sdk-user-guide?id=_1222-%e8%b7%b3%e8%bf%87apollo-meta-server%e6%9c%8d%e5%8a%a1%e5%8f%91%e7%8e%b0
        // Config Service部署在docker环境中，注册到Meta Server的是docker内网地址，本地开发环境无法直接连接，所以需要跳过Apollo Meta Server服务发现
        ///
        System.setProperty("apollo.config-service", "http://192.168.3.129:18080");
//        System.setProperty("apollo.config-service", "http://192.168.3.129:18080,http://192.168.3.129:18081,http://192.168.3.129:18082");
    }
}
