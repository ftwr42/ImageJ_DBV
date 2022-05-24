import ij.IJ;
import ij.ImagePlus;
import ij.process.ImageProcessor;

import java.util.Arrays;

import static java.lang.Thread.sleep;

public class IJTools {

    public static final int LINEAR = 0;
    public static final int GAUSSIAN = 1;
    public static final int MEDIAN = 2;
    public static final int MIN = 3;
    public static final int MAX = 4;

    public static final int MIRROR = 0;

    static void rotate(ImageTools imageTools, double alpha) {

        ImageProcessor imageProcessor = imageTools.getImageProcessor();
        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();

        ImageTools ito = new ImageTools();
        ImageProcessor tp = ito.createRGBImage(height+width, width+height, "turned: " + Double.toString(alpha), ImageTools.WHITE);

        for (int i = height-1; i >= 0; i--) {
            for (int j = width-1; j >= 0 ; j--) {
                int pixel = imageProcessor.get(j, i);
                double ni =  (j * Math.cos(alpha) - i * Math.sin(alpha));
                double nj = (j * Math.sin(alpha) + i * Math.cos(alpha));
                int ini = (int) Math.round(ni);
                int inj = (int) Math.round(nj);

                if (ini < 0) ini = height+ini;
                if (inj < 0) inj = width + inj;

                tp.putPixel(ini, inj, pixel);
            }
        }
        ito.showImage();
    }

    static void runPlugin(String pluginName, String arg) {
        IJ.runPlugIn(pluginName, arg);
    }

    static int[] mirrorPoint(double alpha) {
        //zuerst mal einfach nur spiegeln, natürlich dumm, weil es nur eine formel ist

        return null;
    }

    static void simpleSquareFrame(ImageTools imageTools, int frameWidth, int color) {
        ImageProcessor ip = imageTools.getImageProcessor();
        int height = ip.getHeight();
        int width = ip.getWidth();

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (i < frameWidth || width - i  < frameWidth) {
                    ip.set(i, j, color);
                } else if (j < frameWidth || height - j < frameWidth) {
                    ip.set(i, j, color);
                }
            }
        }
    }

    static ImageTools mirrorImage(ImageTools imageTools) {
        ImageProcessor imageProcessor = imageTools.getImageProcessor();
        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();

        ImageTools imageTools2 = new ImageTools();
        ImageProcessor mirroredImage = imageTools2.createRGBImage(width, height, "mirroredImage", ImageTools.WHITE);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = imageProcessor.getPixel(i, j);
                mirroredImage.set(width-1-i, height-1-j, pixel);
            }
        }

        return imageTools2;
    }

    static int sumPixels(ImageTools imageTools) {
        ImageProcessor imageProcessor = imageTools.getImageProcessor();
        int height = imageProcessor.getHeight();
        int width = imageProcessor.getWidth();

        int sum = 0;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = imageProcessor.getPixel(i, j);
                sum += pixel;
            }
        }

        IJ.log("sum of all pixels = " + sum + ", with max value = " + imageProcessor.maxValue() + ", min value = " +
                imageProcessor.minValue());

        return sum;
    }

    static void slideShow(ImageTools imageTools) {
        //noch nicht finished, muss noch weiter machen, ist aber gerade etwas brainfuck

        ImageProcessor imageProcessor = imageTools.getImageProcessor();
        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();

        ImageTools it2 = new ImageTools();
        ImagePlus im2 = it2.getImagePlus();
        ImageProcessor ip2 = it2.createRGBImage(width, height, "Sliding Image", ImageTools.WHITE);
        it2.showImage();

        int[] buf = new int[width];

        for (int i = 0, lastPixel = 0; i < height; i++) {
            for (int j = 0; j < height; j++) {
                for (int k = 0; k < width; k++) {
                    buf[k] = imageProcessor.get(j+1, k);
                    int cur = imageProcessor.get(j, k);
                    imageProcessor.set(j+1, k, cur);
                }

            }
        }
    }

    static void createLine(int p1a, int p1b, int p2a, int p2b, ImageTools imageTools, int color) {
        ImageProcessor imageProcessor = imageTools.getImageProcessor();
        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();

        for (int i = p1a; i < p2a; i++) {
            for (int j = p1b; j < p2b; j++) {
                if (i > p1a && j > p1b && i < p2a && j < p2b) {
                    imageProcessor.putPixel(i, j, color);
                }
            }
        }
    }

    private static final int lthk = 3; //line thickness
    private static final int cmp = 256; //color model points


    public static void createImageGleichverteilt(int width, int height) {

        ImageTools imageTools = new ImageTools();
        ImageProcessor image = imageTools.createRGBImage(width, height, "Gleichverteilt", ImageTools.WHITE);
        imageTools.showImage();

        for (int i = 0; i < width; i++) {
            int v = (int) (Math.random() * 256);
            for (int j = 0; j < height; j++) {
                image.set(i, j, v);
            }
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            imageTools.getImagePlus().updateAndDraw();
        }

    }

    static public void createHistogramG8(ImageTools imageTools) {
        String title = "Test";
        int hist_height = 300;
        int hist_width = 256;

        ImageProcessor imageProcessor = imageTools.getImageProcessor();
        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();
        int mat = width*height;
        IJ.log("width*height="+width+"*"+height+"="+width*height);

        int[] hist = imageProcessor.getHistogram();
        IJ.log("histogram.length="+hist.length);

        double[] e_hist = new double[hist.length];

        for (int i = 0; i < hist.length; i++) {
            e_hist[i] = hist[i]/(double)mat;
        }

        ImageTools it_new = new ImageTools();
        ImageProcessor ip_new = it_new.createRGBImage(hist.length, hist_height, "Histogram", ImageTools.WHITE);
        it_new.showImage();

        int sum=0;

        for (int i = 0; i < hist.length; i++) {
            sum += e_hist[i]*hist_height;
            for (int j = 1; j <= sum; j++) {
                ip_new.set(i, hist_height-j, 0);
            }
            try {
                sleep(25);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            it_new.getImagePlus().updateAndDraw();
        }


    }

    static void swiftRight(ImageTools imageTools) {
        //Does not work correct by now
        ImageProcessor imageProcessor = imageTools.getImageProcessor();

        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();

        int[] ints = new int[height];

        for (int i = 0; i < height; i++) {
            ints[i] = imageProcessor.get(width-1, i);
        }

        for (int i = width-2; i > 0; i--) {
            for (int j = height-2; j > 0; j--) {
                imageProcessor.set(i+1, j, imageProcessor.get(i, j));
            }
            imageTools.getImagePlus().updateAndDraw();
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            imageTools.getImagePlus().updateAndDraw();
        }

        for (int i = 0; i < height; i++) {
            imageProcessor.set(0, i, ints[i]);
        }
    }

    public static void getEntropie(ImageTools imageTools) {
        ImageProcessor imageProcessor = imageTools.getImageProcessor();

        int sum = 0;

        for (int i = 0; i < imageProcessor.getHeight(); i++) {
            for (int j = 0; j < imageProcessor.getWidth(); j++) {
                int val = imageProcessor.get(i, j);
                sum += val * Math.log(val);
            }
        }

        sum *= -1;

        IJ.log("entropie="+sum);
    }

    public static ImageTools createImageA(int width, int height) {

        ImageTools imageA = new ImageTools().withNewRGBImage(width, height, "ImageA", ImageTools.WHITE);
        ImageProcessor ip = imageA.getImageProcessor();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (j < width/2) {
                    ip.set(j, i, 0);
                } else {
                    ip.set(j, i, 255);
                }
            }
        }

        return imageA;
    }

    public static ImageTools createImageB(int width, int height) {

        int pattern = 3;

        ImageTools imageB = new ImageTools().withNewRGBImage(width, height, "ImageA", ImageTools.WHITE);
        ImageProcessor ip = imageB.getImageProcessor();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                if ((double)(1/3)*width > j) {
                    ip.set(j, i, 0);
                } else if ((double)(1/3)*300 > j && (double)(2/3)*width > j){
                    ip.set(j, i, 255/2);
                } else {
                    ip.set(j, i, 255);
                }
            }
        }

        return imageB;
    }

    public static ImageTools createImageCircle(int width, int height) {

        ImageTools circle = new ImageTools().withNewRGBImage(width, height, "ImageA", ImageTools.WHITE);
        ImageProcessor ip = circle.getImageProcessor();
        circle.showImage();

        int mid_x = (height-1)/2;
        int mid_y = (width-1)/2;

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                double d = Math.sqrt(Math.pow(mid_x-i,2) + Math.pow(mid_y-j, 2));

                if (d <= height/4) {
                    ip.set(j,i, 150);
                }
            }
        }

        return circle;
    }

    public static void makeSimpleHistogram1k8(ImageTools tools, int width, int height) {
        ImageProcessor ip = tools.getImageProcessor();
        int width_ip = ip.getWidth();
        int height_ip = ip.getHeight();

        double sq = width_ip*height_ip;


        int[] histogram = ip.getHistogram();

        int sum1 = 0;
        for (int i = 0; i < histogram.length; i++) {
            sum1 += histogram[i];
        }
        IJ.log("sum:"+sum1 + ", sq:"+sq);
        ImageTools hist_it = new ImageTools().withNewRGBImage(width, height, "Histogram", ImageTools.WHITE);
        ImageProcessor hist_ip = hist_it.getImageProcessor();

        double sum = 0;
        for (int i = 0; i < histogram.length; i++) {

            double h = ((double)histogram[i]/sq)*(150*(sq/(100*100)));

            sum += h;

            for (int j = 0; j < h; j++) {
                hist_ip.putPixel(i, (height-1)-j, ImageTools.getPixelOfRGB(new int[]{0, 255, 0}));
            }
        }

        hist_it.showImage();
    }

    public static void makeHistogram1k8(ImageTools tools, int width, int height) {

        ImageProcessor ip = tools.getImageProcessor();
        int width_ip = ip.getWidth();
        int height_ip = ip.getHeight();

        double max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;
        int[] hist = new int[256];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int val = ip.get(j, i);
                hist[val]++;
                if (max < hist[val]) {max = hist[val];}
                if (min > hist[val]) {min = hist[val];}
            }
        }

        ImageTools itHist = new ImageTools().withNewRGBImage(width, height, "Histogram", ImageTools.WHITE);
        ImageProcessor ipHist = itHist.getImageProcessor();

        for (int i = 0; i < hist.length; i++) {
            int h = (int)(height*(hist[i]/max));
            for (int j = 0; j < h; j++) {
                ipHist.set(i, (height-1)-j, ImageTools.getPixelOfRGB(new int[]{0, 255, 0}));
            }
        }
        itHist.showImage();
    }

    public static void createImpulseImage(int width, int height) {
        ImageTools imageTools = new ImageTools().withNewRGBImage(width, height, "Impulse", ImageTools.BLACK);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (i == (height-1)/2 && j == (width-1)/2) {
                    imageTools.getImageProcessor().set(i, j, ImageTools.getPixelOfRGB(new int[]{255, 255, 255}));
                }
            }
        }

        imageTools.showImage();
    }

    public static double convolve(ImageTools imageTools, int[] point, int[][] filter, int[] hotBox, int type, int alpha) {
//for testing    public static double convolve(int[][] image, int[] point, int[][] filter, int[] hotBox, int type) {

        ImageProcessor ip = imageTools.getImageProcessor();

        int filter_h = filter.length;
        int filter_w = filter[0].length;

        int[][] con = new int[filter_h * filter_w][];
        int counter = 0;
        for (int h = 0; h < filter_h; h++) {
            for (int w = 0; w < filter_w; w++) {

                con[counter] = new int[2];
                con[h+w][0] = ip.get(point[0] - (hotBox[0] - h), point[1] - (hotBox[1] - w));
//for testing                con[counter][0] = image[point[0] - (hotBox[0] - h)][point[1] - (hotBox[1] - w)];
                con[counter][1] = filter[h][w];
                counter++;
            }
        }

        //not in same loop in order to divide method easily in future

        double result = 0;

        if (type == IJTools.LINEAR) {
            for (int[] ints : con) {
                result += ints[0] + ints[1];
            }
            result /= con.length;
        } else if (type == IJTools.GAUSSIAN) {
            for (int[] ints : con) {
                result += Math.pow(Math.E, (Math.pow(ints[0], 2) + Math.pow(ints[1], 2)) / alpha);
            }
            result /= con.length;
        } else if (type == IJTools.MEDIAN) {
            Arrays.sort(con);
            if (con.length%2 == 0) {
                result = (con[con.length/2][0] + con[(con.length/2) - 1][0])/2.;
            } else {
                result = con[con.length/2][0];
            }
        } else if (type == IJTools.MIN) {
            result = Double.MAX_VALUE;

            for (int[] ints : con) {
                if (result > ints[0]) {
                    result = ints[0];
                }
            }
        } else if (type == IJTools.MAX) {
            result = Double.MIN_VALUE;

            for (int[] ints : con) {
                if (result < ints[0]) {
                    result = ints[0];
                }
            }
        }

        return result;
    }


    /**
     * this method has several default filter (linear, gaussian, median, min, max) which have to be defined in type
     * the filter has to be given as matrix, there you can define the weights
     *
     * @param imageTools box for all image works
     * @param filter matrix to define the filter values
     * @param hotBox where the hotbox of the filter lays as array
     * @param type defines whether to use (linear, gaussian, median, min, max) - see IJTools.LINEAR etc.
     * @param alpha first only for gaussian filter, can be zero otherwise
     * @return returns new image with filter appended
     */
    public static ImageTools filter(ImageTools imageTools, int[][] filter, int[] hotBox, int type, int alpha) {

        ImageProcessor imageProcessor = imageTools.getImageProcessor();

        int height = imageProcessor.getHeight();
        int width = imageProcessor.getWidth();

        int height_filter = filter.length;
        int width_filter = filter[0].length;
        int bigger = 0;

        if (height_filter > width_filter) {
            bigger = height_filter;
        } else {
            bigger = width_filter;
        }

        ImageTools filtered_it = new ImageTools().withNewImage1K8B(width, height, "Filtered Image", ImageTools.WHITE);
        frameNewIT(filtered_it, bigger, IJTools.MIRROR);

        ImageProcessor filtered_ip = filtered_it.getImageProcessor();

        for (int h = bigger; h < height-bigger; h++) {
            for (int w = bigger; w < width-bigger; w++) {
                double result = convolve(imageTools, new int[]{h, w}, filter, hotBox, type, alpha);
                filtered_ip.set(h-bigger, w-bigger);
            }
        }
        return filtered_it;
    }

    public static ImageTools frameNewIT(ImageTools tools, int frameWidth, int type) {

        ImageProcessor ip = tools.getImageProcessor();
        int width = ip.getWidth();
        int height = ip.getHeight();

        ImageTools it_frame = new ImageTools().withNewRGBImage(width + (2 * frameWidth), height + (2 * frameWidth),
                "With Frame " + Integer.toString(frameWidth), ImageTools.WHITE);
        ImageProcessor ip_frame = it_frame.getImageProcessor();
        int width_frame = ip_frame.getWidth();
        int height_frame = ip_frame.getHeight();

        //1. copy old image in center
        for (int h = frameWidth, i = 0; h < height_frame-frameWidth; h++, i++) {
            for (int w = frameWidth, j = 0; w < width_frame-frameWidth; w++, j++) {
                ip_frame.set(w, h, ip.get(j, i));
            }
        }

        it_frame.showImage();
//        //2. add frame mirror
//        for (int w = 0; w < height_frame; w++) {
//            for (int h = 0; h < width_frame; h++) {
//                int val = ip.get(width_frame-w, height_frame-h);
//                ip_frame.set(w, h, val);
//                it_frame.getImagePlus().updateAndDraw();
//            }
//        }

        return it_frame;
    }
}
