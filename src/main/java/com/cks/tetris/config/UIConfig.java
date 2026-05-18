package com.cks.tetris.config;

import com.cks.tetris.model.Color;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

@Configuration
public class UIConfig {

    public UIConfig() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }

    @Bean
    public Map<Color, java.awt.Color> colorMap() {
        return Map.of(
                Color.CYAN, new java.awt.Color(39, 245, 245),
                Color.YELLOW, new java.awt.Color(245, 245, 39),
                Color.PURPLE, new java.awt.Color(245, 39, 245),
                Color.GREEN, new java.awt.Color(39, 245, 39),
                Color.RED, new java.awt.Color(245, 39, 39),
                Color.NAVY, new java.awt.Color(39, 39, 245),
                Color.ORANGE, new java.awt.Color(245, 165, 39)
        );
    }

    @Bean
    public Font informationFont() {
        return new Font("Courier New", Font.BOLD, 14);
    }
}
