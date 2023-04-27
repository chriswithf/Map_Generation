package View;

import javafx.scene.ImageCursor;
import javafx.scene.image.Image;
import org.junit.Test;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainViewTest {

    @Test
    public void checkCursor() throws IOException {
        URL url = new URL("https://cdn-icons-png.flaticon.com/512/67/67687.png");
        Dimension bestCursorSize  = Toolkit.getDefaultToolkit().getBestCursorSize(1, 1);
        javafx.scene.image.Image img = new Image(url.openStream(),bestCursorSize.width, bestCursorSize.height, false,false);
        ImageCursor cursor = new ImageCursor(img, bestCursorSize.width * 0.5, bestCursorSize.width * 0.5);

    }
}
