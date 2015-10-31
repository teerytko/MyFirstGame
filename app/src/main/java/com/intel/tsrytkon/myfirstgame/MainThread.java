package com.intel.tsrytkon.myfirstgame;

/**
 * Created by tsrytkon on 10/31/15.
 */
public class MainThread extends Thread {
    private GameSurfaceView m_panel;

    public MainThread(GameSurfaceView mainPanel) {
        m_panel = mainPanel;
    }

    // flag to hold game state
    private boolean running;
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        int dx = 5;
        int dy = 5;
        int dw = m_panel.droid_width();
        int dh = m_panel.droid_width();
        while (running) {
            try {
                // update game state
                if (m_panel.droid_x+dw >= m_panel.getWidth() )
                    dx = -5;
                else if (m_panel.droid_x <= 0)
                    dx = 5;
                if (m_panel.droid_y+dy >= m_panel.getHeight() )
                    dy = -5;
                else if (m_panel.droid_y <= 0)
                    dy = 5;

                m_panel.droid_x += dx;
                m_panel.droid_y += dy;

                m_panel.ship_x += (m_panel.touch_x-m_panel.ship_x)/5;

                if (m_panel.shot_y > 0) {
                    m_panel.shot_y -= 15;
                    System.out.println("Shoot on way "+m_panel.shot_x+" "+m_panel.shot_y);
                }
                // render state to the screen
                m_panel.draw();
                Thread.sleep(10, 0);
            }
            catch (InterruptedException e) {
                System.out.println(e);
            }
        }
    }
}
