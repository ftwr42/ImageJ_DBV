import ij.plugin.PlugIn;

import static java.lang.Thread.sleep;

public class Test_Plugin implements PlugIn {

    @Override
    public void run(String s) {

        ImageTools tools = new ImageTools().withLoadedImage("/Users/ftwr/WORKSPACES/W_UNI/W_DIGIBILD/HelloWorld_DigiBild/images/greyAutopsie.jpg");
        tools.showImage();

        IJTools.makeSimpleHistogram1k8(tools, 256, 150);


    }
}
