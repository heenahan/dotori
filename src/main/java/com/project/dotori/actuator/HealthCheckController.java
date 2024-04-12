package com.project.dotori.actuator;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/actuator")
@RestController
public class HealthCheckController {

    @GetMapping("/health-check")
    public String checkHealth() {
        return "OK";
    }
}
