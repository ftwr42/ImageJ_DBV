import ij.IJ;
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class ImageTools {

    public static final Integer WHITE = 0;
    private ImageProcessor imageProcessor;
    private ImagePlus imagePlus;

    public ImageProcessor getImageProcessor() {
        return imageProcessor;
    }

    public ImageTools withLoadedImage(String path) {
        loadImage(path);
        return this;
    }

    public ImageTools withNewImage(int width, int height, String title, int fill) {
        createRGBImage(width, height, title, fill);
        return this;
    }

    public void setImageProcessor(ImageProcessor imageProcessor) {
        this.imageProcessor = imageProcessor;
    }

    public ImagePlus getImagePlus() {
        return imagePlus;
    }

    public void setImagePlus(ImagePlus imagePlus) {
        this.imagePlus = imagePlus;
    }

    ImageProcessor createRGBImage(int width, int height, String title, int fill) {

        imagePlus = NewImage.createRGBImage(title, width, height, 1, NewImage.FILL_WHITE);
        imageProcessor = imagePlus.getProcessor();
        if (fill == ImageTools.WHITE) {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    imageProcessor.set(i, j, 255);
                }
            }
        } else {
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    imageProcessor.set(i, j, 0);
                }
            }
        }

        return imageProcessor;
    }

    public static int[] getRGBofPixel(int pixel) {
        int[] rgb = new int[3];
        rgb[0] = (int)(pixel & 0xff0000) >> 16;
        rgb[1] = (int)(pixel & 0x00ff00) >> 8;
        rgb[2] = (int)(pixel & 0x0000ff);
        return rgb;
    }

    public static int getPixelOfRGB(int[] rgb) {
        return ((rgb[0] & 0xff)<<16) + ((rgb[1] & 0xff)<<8) + (rgb[2] & 0xff);
    }

    void loadImage(String path) {
        imagePlus = IJ.openImage(path);
        imageProcessor = imagePlus.getChannelProcessor();
    }

    void showImage() {
        imagePlus.show();
    }

    public void createHistogramG8() {

    }

    public ImageTools clone() {

//        ImageProcessor duplicate_ipr = imageProcessor.duplicate();
//        ImagePlus duplicate_ipl = new ImagePlus("Duplicate_" + imagePlus.getTitle(), duplicate_ipr);
//
//        ImageTools duplicate_ito = new ImageTools().with(duplicate_ipr, duplicate_ipl);
//
//        return duplicate_ito;

//todo        test duplicate

        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();
        String title = imagePlus.getTitle();

        ImageTools cloneTools = new ImageTools();
//        ImageProcessor cloneIP = cloneTools.createRGBImage(width, height, "clone_" + title);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
//                cloneIP.set(j,i, imageProcessor.get(j,i));
            }
        }

        return cloneTools;
    }

    private ImageTools with(ImageProcessor duplicate_ipr, ImagePlus duplicate_ipl) {
        this.imageProcessor = duplicate_ipr;
        this.imagePlus = duplicate_ipl;
        return this;
    }
}
