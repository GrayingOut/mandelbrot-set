import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

/**
 * The main class
 */
public final class Main {

    /*
     * The max number of iterations per loop
     * 
     * Smaller causes it to form more gradually
     */
    private double maxIterations = 1;

    /**
     * The width and height of the Mandelbrot Set
     */
    private final double size = 1000;

    /**
     * The main method
     * 
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        new Main();
    }

    public Main() {
        /* Create the JFrame */
        JFrame frame = new JFrame("Mandelbrot Set");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 1000);
        frame.setResizable(false);
        frame.setLayout(new BorderLayout());
        
        /* Create the canvas */
        Canvas canvas = new Canvas();
        frame.add(canvas);
        
        /* Show JFrame */
        frame.setVisible(true);
        
        /* Create buffer strategy */
        canvas.createBufferStrategy(2);
        BufferStrategy bs = canvas.getBufferStrategy();

        /* Algorithm from https://en.wikipedia.org/wiki/Mandelbrot_set#Computer_drawings */
        while (true) {
            /* Get draw graphics */
            Graphics g = bs.getDrawGraphics();

            /* For each pixel */
            for (double pY = 0; pY < size; pY++) {
                for (double pX = 0; pX < size; pX++) {
                    double scaledX = ((pX/size) * 2.47f)-2f;
                    double scaledY = ((pY/size) * 2.24f)-1.12f;
                    
                    double x = 0;
                    double y = 0;
                    double iteration = 0;
                    
                    while (x*x + y*y <= 4 && iteration < maxIterations) {
                        double tempX = x*x - y*y + scaledX;
                        y = 2*x*y + scaledY;
                        x = tempX;
                        iteration++;
                    }
                    
                    /* Draw pixel */
                    g.setColor(Color.getHSBColor(
                        (float)(iteration/maxIterations),
                        (float)(iteration/maxIterations),
                        (float)((maxIterations-iteration)/maxIterations)));
                    
                    g.fillRect((int)(pX), (int)(pY), 1, 1);
                }
            }
            
            /* Increase accuracy through each loop */
            maxIterations++;

            /* Dispose of graphics and show buffer */
            g.dispose();
            
            if (!bs.contentsLost()) {
                bs.show();
            }
        }
    }
}
