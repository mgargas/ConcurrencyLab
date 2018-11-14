package lab5.task1;

import java.util.concurrent.Callable;

public class MandelbrotDrawer implements Callable<Boolean>{
    private Mandelbrot mandelbrot;
    private int x1;
    private int x2;
    private int y1;
    private int y2;

    public MandelbrotDrawer(Mandelbrot mandelbrot, int x1, int x2, int y1, int y2){
        this.mandelbrot = mandelbrot;
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    @Override
    public Boolean call() {
        double zx, zy, cX, cY, tmp;
        for (int y1 = 0; y1 < y2; y1++) {
            for (int x1 = 0; x1 < x2; x1++) {
                zx = zy = 0;
                cX = (x1 - 400) / mandelbrot.getZOOM();
                cY = (y1 - 300) / mandelbrot.getZOOM();
                int iter = mandelbrot.getMAX_ITER();
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                mandelbrot.getI().setRGB(x1, y1, iter | (iter << 8));
            }
        }
        return true;
    }
}
