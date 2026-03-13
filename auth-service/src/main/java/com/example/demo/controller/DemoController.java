package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.demo.DemoMode;
import com.example.demo.demo.DemoModeService;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private final DemoModeService demoModeService;

    public DemoController(DemoModeService demoModeService) {
        this.demoModeService = demoModeService;
    }

    @PostMapping("/mode")
    public String setMode(@RequestParam DemoMode value) {
        demoModeService.setCurrentMode(value);
        return "Demo mode set to " + value.name();
    }

    @GetMapping("/mode")
    public String getMode() {
        return "Current demo mode is " + demoModeService.getCurrentMode().name();
    }
}
