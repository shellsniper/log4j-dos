# Log4j2 Dos Verification

Fork from https://github.com/EmYiQing/Log4j2DoS

漏洞环境仅用于安全研究，禁止非法用途，造成的后果使用者负责

## 背景

Log4j2.15.0 有DOS风险。

2.15.0以下版本，如果使用ThreadContext来记录日志，如在日志pattern中有 ${ctx:xxx}，并且调用 ThreadContext.put{“xxx”,“用户可控数据”}， 添加JVM参数和环境变量无效

2.15.0默认配置不存在dos风险，但如果使用了lookup功能，或者使用ThreadContext打印日志，也存在DOS安全风险

## 复现 

### 第一种情况
在`log4j2.xml`中配置
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="warn" name="MyApp" packages="">
    <appenders>
        <console name="STDOUT" target="SYSTEM_OUT">
            <PatternLayout>
                <pattern>%d %p %c{1.} [%t] $${ctx:message} %m%n</pattern>
            </PatternLayout>
        </console>
    </appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="STDOUT"/>
        </Root>
    </Loggers>
</Configuration>
```

注: message需要base64encode


### 第二种情况


漏洞复现URL：`http://localhost:8080/poc1?message=payload`
在`log4j2.xml`中配置
```xml
<?xml version="1.0" encoding="UTF-8"?>
<Configuration status="warn" name="MyApp" packages="">
    <appenders>
        <console name="STDOUT" target="SYSTEM_OUT">
            <PatternLayout pattern="%msg{lookups}%n"/>
        </console>
    </appenders>
    <Loggers>
        <Root level="info">
            <AppenderRef ref="STDOUT"/>
        </Root>
    </Loggers>
</Configuration>
```

漏洞复现URL：`http://localhost:8080/poc2?message=payload`

注: message需要base64encode