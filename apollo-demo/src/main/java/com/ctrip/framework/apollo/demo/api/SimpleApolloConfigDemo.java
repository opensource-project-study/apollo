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
package com.ctrip.framework.apollo.demo.api;

import com.google.common.base.Charsets;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Jason Song(song_s@ctrip.com)
 */
public class SimpleApolloConfigDemo {
  private static final Logger logger = LoggerFactory.getLogger(SimpleApolloConfigDemo.class);
  private String DEFAULT_VALUE = "undefined";
  private Config config;

  public SimpleApolloConfigDemo() {
    ConfigChangeListener changeListener = new ConfigChangeListener() {
      @Override
      public void onChange(ConfigChangeEvent changeEvent) {
        logger.info("Changes for namespace {}", changeEvent.getNamespace());
        for (String key : changeEvent.changedKeys()) {
          ConfigChange change = changeEvent.getChange(key);
          logger.info("Change - key: {}, oldValue: {}, newValue: {}, changeType: {}",
              change.getPropertyName(), change.getOldValue(), change.getNewValue(),
              change.getChangeType());
        }
      }
    };
    config = ConfigService.getAppConfig();
    config.addChangeListener(changeListener);
  }

  private String getConfig(String key) {
    String result = config.getProperty(key, DEFAULT_VALUE);
    logger.info(String.format("Loading key : %s with value: %s", key, result));
    return result;
  }

  public static void main(String[] args) throws IOException {

      // set AppId
      // refer to https://www.apolloconfig.com/#/zh/usage/java-sdk-user-guide
      System.setProperty("app.id", "apollo-demo1");
      // set Apollo Meta Server
      // 跳过Apollo Meta Server服务发现
      // refer to https://www.apolloconfig.com/#/zh/usage/java-sdk-user-guide?id=_1222-%e8%b7%b3%e8%bf%87apollo-meta-server%e6%9c%8d%e5%8a%a1%e5%8f%91%e7%8e%b0
      System.setProperty("apollo.config-service", "http://192.168.3.129:18080");
      // set Environment
      // refer to https://www.apolloconfig.com/#/zh/usage/java-sdk-user-guide?id=_1241-environment
//      System.setProperty("env", "DEV");

    SimpleApolloConfigDemo apolloConfigDemo = new SimpleApolloConfigDemo();
    System.out.println(
        "Apollo Config Demo. Please input key to get the value. Input quit to exit.");
    while (true) {
      System.out.print("> ");
      String input = new BufferedReader(new InputStreamReader(System.in, Charsets.UTF_8)).readLine();
      if (input == null || input.length() == 0) {
        continue;
      }
      input = input.trim();
      if (input.equalsIgnoreCase("quit")) {
        System.exit(0);
      }
      apolloConfigDemo.getConfig(input);
    }
  }
}
