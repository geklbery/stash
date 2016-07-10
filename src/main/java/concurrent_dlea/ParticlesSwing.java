package ch1;

import javax.swing.*;
import java.awt.*;

/**
 * Created by admin on 07.07.2016.
 */
public class ParticlesSwing {

    protected Thread[] threads = null; // null when not running
    protected final ParticleCanvas canvas = new ParticleCanvas(500);

    public void init() {
        //Create and set up the window.
        JFrame frame = new JFrame("Particles");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(canvas);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    protected Thread makeThread(final Particle p) {
        Runnable runloop = new Runnable() {
            public void run() {
                try {
                    for (; ; ) {
                        p.move();
                        canvas.repaint();
                        Thread.sleep(100);
                    }
                } catch (InterruptedException e) {
                    return;
                }
            }
        };
        return new Thread(runloop);
    }

    public synchronized void start() {
        int n=10;
        if (threads == null) { // bypass if already started
            Particle[] particles = new Particle[n];
            for (int i = 0; i < n; i++) {
                particles[i] = new Particle(250, 250);
                canvas.setParticles(particles);
            }

            threads = new Thread[n];
            for (int i = 0; i < n; i++) {
                threads[i] = makeThread(particles[i]);
                threads[i].start();
            }
        }
    }

    public synchronized void stop() {
        if (threads != null) {
            for (int i = 0; i < threads.length; i++) {
                threads[i].interrupt();
            }
            threads=null;
        }
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        final ParticlesSwing swing = new ParticlesSwing();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                swing.init();
                swing.start();
            }
        });
    }
}
