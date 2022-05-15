import ij.IJ;
import ij.ImagePlus;
import ij.gui.HistogramPlot;
import ij.gui.HistogramWindow;
import ij.plugin.PlugIn;
import ij.process.ImageProcessor;

import static java.lang.Thread.sleep;

public class Test_Plugin implements PlugIn {

    @Override
    public void run(String s) {

//        ImageTools imageTools = new ImageTools().withImage("/Users/ftwr/WORKSPACES/W_UNI/W_DIGIBILD/lennaGrey.gif");
//        imageTools.showImage();

//        ImageTools imageTools = IJTools.createImageCircle(300, 300);

        ImageTools tools = new ImageTools().withImage("/Users/ftwr/WORKSPACES/W_UNI/W_DIGIBILD/HelloWorld_DigiBild/images/grey.jpeg");
        tools.showImage();

        IJTools.swiftRight(tools);

    }
}
