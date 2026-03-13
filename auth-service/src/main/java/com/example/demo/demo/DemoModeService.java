package com.example.demo.demo;

import org.springframework.stereotype.Service;

@Service
public class DemoModeService {
    private DemoMode currentMode = DemoMode.NORMAL;

    public DemoMode getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(DemoMode mode) {
        this.currentMode = mode;
    }
}
