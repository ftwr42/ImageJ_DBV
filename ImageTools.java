import ij.IJ;
import ij.ImagePlus;
import ij.gui.NewImage;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;

public class ImageTools {

    private ImageProcessor imageProcessor;
    private ImagePlus imagePlus;
    int[] rgb = new int[3];

    public ImageProcessor getImageProcessor() {
        return imageProcessor;
    }

    public ImageTools withImage(String path) {
        loadImage(path);
        return this;
    }

    public ImageTools withImage(int width, int height, String title) {
        createRGBImage(width, height, title);
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

    ImageProcessor createRGBImage(int width, int height, String title) {

//        imagePlus = NewImage.createRGBImage(title, width, height, 1, NewImage.FILL_WHITE);
//        imageProcessor = imagePlus.getProcessor();
        imageProcessor = new ByteProcessor(width, height);
        imagePlus = new ImagePlus(title, imageProcessor);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                imageProcessor.set(i, j, 255);
            }
        }

        return imageProcessor;
    }

    public int[] getRGBofPixel(int pixel) {
        rgb[0] = (int)(pixel & 0xff0000) >> 16;
        rgb[1] = (int)(pixel & 0x00ff00) >> 8;
        rgb[2] = (int)(pixel & 0x0000ff);
        return rgb;
    }

    public int getPixelOfRGB(int[] rgb) {
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


        int width = imageProcessor.getWidth();
        int height = imageProcessor.getHeight();
        String title = imagePlus.getTitle();

        ImageTools cloneTools = new ImageTools();
        ImageProcessor cloneIP = cloneTools.createRGBImage(width, height, "clone_" + title);

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                cloneIP.set(j,i, imageProcessor.get(j,i));
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
