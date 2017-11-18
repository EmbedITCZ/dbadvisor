package com.github.embeditcz.dbadvisor.core.internal;

import com.github.embeditcz.dbadvisor.core.internal.issue.IssueObjectMapper;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;
import static org.mockito.Mockito.when;

public class DbAdvisorReportGeneratorTest {

    @Rule
    public TemporaryFolder tmpDir = new TemporaryFolder();
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    @Mock
    public IssueRepository issueRepository;
    @Mock
    public DbAdvisorProperties properties;

    private DbAdvisorReportGenerator reportGenerator;

    @Before
    public void init() {
        reportGenerator = new DbAdvisorReportGenerator(properties, issueRepository, new IssueObjectMapper());
    }

    @Test
    public void shouldCreateReports() {
        List<Issue> issues = new ArrayList<>();
        issues.add(issue("slow", "select 1", 10));
        issues.add(issue("slow", "select 2", 20));
        issues.add(issue("slow", "select 3", 30));

        when(properties.getReportName()).thenReturn("dbadvisor");
        when(properties.getReportLocation()).thenReturn(tmpDir.getRoot().getAbsolutePath());
        when(issueRepository.getIssues()).thenReturn(issues);

        reportGenerator.generate();

        File jsonFile = new File(tmpDir.getRoot(), "dbadvisor.json");
        assertThat(jsonFile).exists().isFile();
        assertThat(contentOf(jsonFile))
            .startsWith("[ {")
            .contains("\"type\" : \"slow\"")
            .endsWith("} ]");

        File htmlFile = new File(tmpDir.getRoot(), "dbadvisor.html");
        assertThat(htmlFile).exists().isFile();
        assertThat(contentOf(htmlFile).trim())
            .startsWith("<!DOCTYPE html>")
            .contains("let data = [ {")
            .contains("\"type\" : \"slow\"")
            .endsWith("</html>");
    }

    @Test
    public void shouldCreateMissingDirectoriesInLocation() {
        String tmpDirPath = tmpDir.getRoot().getAbsolutePath();
        String reportLocation = tmpDirPath + File.separator + "foo" + File.separator + "bar";

        when(properties.getReportName()).thenReturn("dbadvisor");
        when(properties.getReportLocation()).thenReturn(reportLocation);

        reportGenerator.generate();

        assertThat(new File(reportLocation, "dbadvisor.json")).exists();
    }

    private Issue issue(String type, String query, long weight) {
        return new Issue(type, query, "Lorem ipsum dolor sit amet, consectetuer adipiscing elit.", weight, "ms", LocalDateTime.now(), null, null);
    }

}
