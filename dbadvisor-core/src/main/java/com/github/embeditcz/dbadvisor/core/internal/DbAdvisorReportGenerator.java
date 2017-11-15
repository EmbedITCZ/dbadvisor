package com.github.embeditcz.dbadvisor.core.internal;

import com.github.embeditcz.dbadvisor.core.internal.issue.IssueObjectMapper;
import com.github.embeditcz.dbadvisor.core.issue.Issue;
import com.github.embeditcz.dbadvisor.core.issue.IssueRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;

import static java.util.logging.Level.INFO;
import static java.util.logging.Level.SEVERE;

@Log
@Component
@RequiredArgsConstructor
class DbAdvisorReportGenerator {

    private final DbAdvisorProperties properties;
    private final IssueRepository issueRepository;
    private final IssueObjectMapper issueObjectMapper;

    public void generate() {
        List<Issue> issues = issueRepository.getIssues();
        generateJsonReport(issues);
        generateHtmlReport(issues);
    }

    private void generateJsonReport(List<Issue> issues) {
        try {
            File reportFile = resolveReportFile(".json");
            issueObjectMapper.getObjectMapper().writeValue(reportFile, issues);
            log.log(INFO, "The issues json report is generated in " + reportFile);
        } catch (Exception e) {
            log.log(SEVERE, "Unable to write json report.", e);
        }
    }

    private void generateHtmlReport(List<Issue> issues) {
        File reportFile = resolveReportFile(".html");
        try (BufferedReader templateReader = createHtmlReportTemplateReader()) {
            try (PrintWriter reportWriter = createHtmlReportWriter(reportFile)) {
                templateReader.lines().forEach(line -> processTemplateLine(line, reportWriter, issues));
                reportWriter.flush();
                log.log(INFO, "The issues html report is generated in " + reportFile);
            }
        } catch (Exception e) {
            log.log(SEVERE, "Unable to write html report.", e);
        }
    }

    private PrintWriter createHtmlReportWriter(File reportFile) throws IOException {
        return new PrintWriter(new BufferedWriter(new FileWriter(reportFile)));
    }

    private BufferedReader createHtmlReportTemplateReader() {
        InputStream templateInputStream = getClass().getClassLoader().getResourceAsStream("report-template.html");
        return new BufferedReader(new InputStreamReader(templateInputStream));
    }

    private void processTemplateLine(String line, PrintWriter reportWriter, List<Issue> issues) {
        if (line.trim().equals("let data = [];")) {
            reportWriter.print("let data = ");
            try {
                issueObjectMapper.getObjectMapper().writeValue(reportWriter, issues);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
            reportWriter.println(";");

        } else {
            reportWriter.println(line);
        }
    }

    private File resolveReportFile(String extension) {
        String name = properties.getReportName();
        String location = properties.getReportLocation();
        File reportDir = new File(location);
        reportDir.mkdirs();
        return new File(reportDir, name + extension);
    }

}
