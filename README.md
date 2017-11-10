# DBadvisor

[![Sonatype Snapshot](https://img.shields.io/nexus/s/https/oss.sonatype.org/com.github.embeditcz.dbadvisor/dbadvisor-core.svg)](https://oss.sonatype.org/content/repositories/snapshots/com/github/embeditcz/dbadvisor/dbadvisor-core/)
[![Build Status](https://travis-ci.org/EmbedITCZ/dbadvisor.svg?branch=master)](https://travis-ci.org/EmbedITCZ/dbadvisor)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/d999b060669f46a0aeff4448c0834c19)](https://www.codacy.com/app/mbocek/dbadvisor?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=EmbedITCZ/dbadvisor&amp;utm_campaign=Badge_Grade)

Clever tool for pointing out potential issues between java application and database.

## Usage

### Dependencies

* java 8
* spring framework
* datasource-proxy


### Integration to java project
At first we have to depend on maven artifact:
```xml
<dependency>
    <groupId>com.github.embeditcz.dbadvisor</groupId>
    <artifactId>dbadvisor</artifactId>
</dependency>
```

At second point we have to enable DBadvisor in spring configuration:
```java
@Configuration
@EnableDbAdvisor
public class MyAppConfiguration {
    
}
```

### Configuration
Configuration for DBadvisor is provided by environment. The list of all environment variables:

| Property name                              | Default value |
| ---                                        | ---           |
| dbadvisor.nplus1.enabled                   | true          |
| dbadvisor.nplus1.threshold                 | 10            |
| dbadvisor.oracle.costBase.enabled          | true          |
| dbadvisor.oracle.costBase.threshold        | 100           |
| dbadvisor.oracle.cpuCost.enabled           | true          |
| dbadvisor.oracle.cpuCost.threshold         | 100000        |
| dbadvisor.oracle.executionPlan.enabled     | true          |
| dbadvisor.oracle.executionPlan.ignoreBatch | false         |
| dbadvisor.oracle.fullAccess.enabled        | true          |
| dbadvisor.oracle.fullAccess.threshold      | 1             |
| dbadvisor.oracle.ioCost.enabled            | true          |
| dbadvisor.oracle.ioCost.threshold          | 1000          |
| dbadvisor.slowQuery.enabled                | true          |
| dbadvisor.slowQuery.ignoreBatch            | false         |
| dbadvisor.slowQuery.threshold              | 1000          |
| dbadvisor.report.name                      | dbadvisor     |
| dbadvisor.report.location                  | _temporary folder, see_ `java.io.tmpdir` _system property_ |
| dbadvisor.report.on-shutdown-hook          | true          |
| dbadvisor.max-issues-count-per-type        | 100           |
