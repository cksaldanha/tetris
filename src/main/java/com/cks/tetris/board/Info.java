/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cks.tetris.board;

import com.cks.tetris.helper.GBC;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicLong;
import javax.swing.border.BevelBorder;

/**
 *
 * @author colin.saldanha
 */
public class Info extends JPanel {

    private final AtomicLong score;
    private long time;
    private final Timer timer;

    private JLabel lTime;
    private JLabel lScore;

    private Font font;

    public Info() {
        super(new GridBagLayout());
        int x = 0, y = 0;

        score = new AtomicLong(0);

        font = new Font("Courier New", Font.BOLD, 14);

        lScore = new JLabel("Score: 0");
        lScore.setFont(font);
        add(lScore, new GBC(x++, y).setAnchor(GBC.WEST).setWeight(0.5, 0).setInsets(10, 5, 5, 5));

        lTime = new JLabel();
        lTime.setFont(font);
        add(lTime, new GBC(x++, y).setAnchor(GBC.EAST).setWeight(0.5, 0).setInsets(10, 5, 5, 5));

        timer = new Timer(1000, new TimeUpdater());
        timer.setInitialDelay(0);
        timer.start();

        setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
    }

    public void addScore(long amt) {
        lScore.setText("Score: " + Long.toString(score.addAndGet(amt)));
    }

    public void pause() {
        timer.stop();
        lTime.setText("Time: -Paused-");
    }

    public void resume() {
        timer.start();
    }

    private class TimeUpdater implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            time += 1000;   //add one sec
            Duration d = Duration.ofMillis(time);
            long hours = d.toHours();
            d = d.minusHours(hours);
            long mins = d.toMinutes();
            d = d.minusMinutes(mins);
            long secs = d.getSeconds();
            String str = String.format("%02d:%02d:%02d", hours, mins, secs);
            lTime.setText("Time: " + str);
        }
    }
}
