package ocrextract;
import net.sourceforge.tess4j.Tesseract;
import java.io.File;
public class OCRTest
{
    public static void main(String[] args) throws Exception
    {
        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("C:\\Program Files\\Tesseract-OCR\\tessdata");
        String text = tesseract.doOCR(new File("screen.png"));
        System.out.println(text);
    }
}
