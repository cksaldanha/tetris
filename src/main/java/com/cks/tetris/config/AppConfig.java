package com.cks.tetris.config;

import org.springframework.context.annotation.Configuration;

import javax.swing.*;

@Configuration
public class AppConfig {

    public AppConfig() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }
}
