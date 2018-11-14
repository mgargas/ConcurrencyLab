package lab5.task1;
import com.opencsv.CSVWriter;
import com.sun.org.apache.xpath.internal.operations.Bool;


import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import javax.swing.JFrame;
public class Mandelbrot extends JFrame{
    private final int MAX_ITER = 570;
    private final double ZOOM = 150;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;

    public Mandelbrot() {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                I.setRGB(x, y, iter | (iter << 8));
            }
        }
    }

    public int getMAX_ITER() {
        return MAX_ITER;
    }

    public double getZOOM() {
        return ZOOM;
    }

    public BufferedImage getI() {
        return I;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException, IOException {

        File file = new File("./measurements.csv");
        FileWriter outputFile = new FileWriter(file);
        CSVWriter writer = new CSVWriter(outputFile);
        String[] header = { "threadsAmount","tasksAmount", "time" };
        writer.writeNext(header);
        Integer[] threadsAmount = {1, 4, 8};
        Integer[] tasksAmount = {1, 4, 8, 10, 40, 80, 600*800};
        Mandelbrot mandelbrot = new Mandelbrot();
        mandelbrot.setVisible(true);
        for(Integer  threads: threadsAmount){
            for(Integer tasks: tasksAmount){
                System.out.println(threads + " " + tasks);
                ExecutorService executorService = Executors.newFixedThreadPool(threads);
                List<Future> futureList = new ArrayList<>();
                int dx = mandelbrot.getWidth()/tasks;
                int dy = mandelbrot.getHeight()/tasks;
                for(int i=0; i<10; i++) {
                    Long startTime = System.nanoTime();
                    int x = 0;
                    int y = 0;
                    for (int j = 0; j < tasks; j++) {
                        Future future = executorService.
                                submit(new MandelbrotDrawer(mandelbrot, x, x + dx, y, y + dy));
                        x += dx;
                        y += dy;
                        futureList.add(future);
                    }
                    for (Future future : futureList) {
                        future.get();
                    }
                    Long endTime = System.nanoTime();
                    String[] csvRecord = {String.valueOf(threads), String.valueOf(tasks), String.valueOf(endTime - startTime)};
                    writer.writeNext(csvRecord);
                }
            }

        }
        System.out.println("I am finishing!");
        writer.close();

        //executorService.submit(new MandelbrotDrawer(mandelbrot, 0, mandelbrot.getWidth(), 0, mandelbrot.getHeight()));
    }
}
