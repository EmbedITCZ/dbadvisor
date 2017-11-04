# DBADVISOR
[![Build Status](https://travis-ci.org/EmbedITCZ/dbadvisor.svg?branch=master)](https://travis-ci.org/EmbedITCZ/dbadvisor)

Automated tool for pointing out potential issues between java application and database.

## Integration to java project
At first we have to depend on maven artifact:
```xml
<dependency>
    <groupId>com.github.embeditcz.dbadvisor</groupId>
    <artifactId>dbadvisor</artifactId>
</dependency>
```

At second point we have to enable dbadvisor in spring configuration:
```java
@Configuration
@EnableDbAdvisor
public class MyAppConfiguration {
    
}
```

## Configuration
Configuration for dbadvisor is provided by environment. The list of all environment variables:
| Property name                              | Defaul value |
| ---                                        | ---          |
| dbadvisor.nplus1.enabled                   | true         |
| dbadvisor.nplus1.threshold                 | 10           |
| dbadvisor.oracle.costBase.enabled          | true         |
| dbadvisor.oracle.costBase.threshold        | 100          |
| dbadvisor.oracle.cpuCost.enabled           | true         |
| dbadvisor.oracle.cpuCost.threshold         | 100000       |
| dbadvisor.oracle.executionPlan.enabled     | true         |
| dbadvisor.oracle.executionPlan.ignoreBatch | false        |
| dbadvisor.oracle.fullAccess.enabled        | true         |
| dbadvisor.oracle.fullAccess.threshold      | 1            |
| dbadvisor.oracle.ioCost.enabled            | true         |
| dbadvisor.oracle.ioCost.threshold          | 1000         |
| dbadvisor.slowQuery.enabled                | true         |
| dbadvisor.slowQuery.ignoreBatch            | false        |
| dbadvisor.slowQuery.threshold              | 1000         |
