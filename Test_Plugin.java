import ij.plugin.PlugIn;

import static java.lang.Thread.sleep;

public class Test_Plugin implements PlugIn {

    @Override
    public void run(String s) {

        ImageTools tools = new ImageTools().withLoadedImage("/Users/ftwr/WORKSPACES/W_UNI/W_DIGIBILD/HelloWorld_DigiBild/images/greyAutopsie.jpg");
        tools.showImage();

        int[][] filter = {
                {0, 1, 2},
                {3, 4, 5},
                {6, 7, 8}
        };
        
        ImageTools imageTools = IJTools.frameNewIT(tools, 40, IJTools.MIRROR);
        imageTools.showImage();

    }
}
