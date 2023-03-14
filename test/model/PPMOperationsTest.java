package model;

import org.junit.Test;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import controller.AppController;
import utils.ImageUtil;
import view.ImageLogView;

import static org.junit.Assert.*;

public class PPMOperationsTest {
  @Test
  public void testLoad() throws IOException {
    StringBuffer out = new StringBuffer();
    ImageOperations model = new PPMOperations();
    ImageLogView view = new ImageLogView(out);
    AppController controller = new AppController(model,view);
    Reader in = new StringReader("load images/sample.ppm sample-test\n" +
            "save images/sample-test.ppm sample-test\n");
    controller.go(in);
    Image sampleImage = ImageUtil.readPPM("images/sample.ppm","sample");
    Image loadedSampleImage = ImageUtil.readPPM("images/sample-test.ppm","sample-test");
    assertEquals(sampleImage,loadedSampleImage);
  }

}