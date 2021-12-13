package com.jira.test.demo;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/issue")
public class TestRestController {
    private final JiraService jiraService;

    public TestRestController(JiraService jiraService) {
        this.jiraService = jiraService;
    }

    @GetMapping
    public void issue() {
        System.out.println("Info");
    }

    @PostMapping("/createIssue")
    public ResponseEntity createIssue(@RequestHeader(value = "authorization-token") String bearerToken) {
        boolean returnValue = jiraService.createIssue(bearerToken);
        return ResponseEntity.ok(returnValue);
    }
}
