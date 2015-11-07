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
        while (running) {
            try {
                // update game state
                for (int i=0; i<m_panel.droids.size(); i++) {
                    GameSurfaceView.Droid d = m_panel.droids.get(i);
                    d.move();
                }

                m_panel.ship.move(m_panel.touch_x);
                for (int i=0; i<m_panel.shots.size(); i++) {
                    GameSurfaceView.Shot s = m_panel.shots.get(i);
                    if (s.m_y > 0) {
                        s.m_y -= 15;
                    }
                    else {
                        m_panel.shots.remove(i);
                        i--;
                    }
                    for (int j=0; j<m_panel.droids.size(); j++) {
                        GameSurfaceView.Droid d = m_panel.droids.get(j);
                        if (d.hits(s)) {
                            m_panel.sounds.playShortResource(R.raw.slave_seismic);

                            m_panel.droids.remove(j);
                            j--;
                        }
                    }

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
