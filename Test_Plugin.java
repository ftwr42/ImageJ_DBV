import ij.plugin.PlugIn;

import static java.lang.Thread.sleep;

public class Test_Plugin implements PlugIn {

    @Override
    public void run(String s) {

//        ImageTools imageTools = new ImageTools().withImage("/Users/ftwr/WORKSPACES/W_UNI/W_DIGIBILD/lennaGrey.gif");
//        imageTools.showImage();

//        ImageTools imageTools = IJTools.createImageCircle(300, 300);

        ImageTools tools = new ImageTools().withLoadedImage("/Users/ftwr/WORKSPACES/W_UNI/W_DIGIBILD/HelloWorld_DigiBild/images/lennaGrey.png");
        tools.showImage();

        IJTools.makeHistogram1k8(tools, 256, 150);

    }
}
