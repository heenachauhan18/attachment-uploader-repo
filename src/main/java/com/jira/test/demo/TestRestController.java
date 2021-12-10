package com.jira.test.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestController {
    private final JiraService jiraService;

    public TestRestController(JiraService jiraService) {
        this.jiraService = jiraService;
    }

    @PostMapping("/createIssue")
    public ResponseEntity createIssue() {
        boolean returnValue = jiraService.createIssue();
        return ResponseEntity.ok(returnValue);
    }
}
