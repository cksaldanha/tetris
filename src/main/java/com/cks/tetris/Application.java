package com.cks.tetris;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author colin.saldanha
 */
@SpringBootApplication
public class Application {

    private static JFrame frame;

    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(Application.class)
                .headless(false)
                .run(args);

        EventQueue.invokeLater(() -> {
            JFrame app = context.getBean("mainWindow", JFrame.class);
            app.setVisible(true);
        });
    }
}
