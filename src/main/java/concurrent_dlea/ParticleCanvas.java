package ch1;

import java.awt.*;

public class ParticleCanvas extends Canvas {
    private Particle particles[] = new Particle[0];

    ParticleCanvas(int size) {
        setSize(new Dimension(size, size));
    }

    /**
     * setter for particles
     * @param ps particles array
     */
    protected synchronized void setParticles(Particle[] ps) {
        if (ps == null) {
            throw new IllegalArgumentException("Cannot set particles to null");
        }
        particles = ps;
    }

    /**
     * getter for particles
     * @return particles array
     */
    public synchronized Particle[] getParticles() {
        return particles;
    }

    public void paint(Graphics g) {
        Particle[] ps = getParticles();
        for (int i = 0; i < ps.length; i++) {
            ps[i].draw(g);
        }
    }
}
