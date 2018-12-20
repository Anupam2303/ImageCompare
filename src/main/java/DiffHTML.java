import org.junit.Test;
import org.outerj.daisy.diff.DaisyDiff;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Locale;


public class DiffHTML {

    @Test

    public void daisyDiffTest() throws Exception {
        String html1 = usingBufferedReader("input1.html");
        String html2 = usingBufferedReader("input2.html");

        try {
            StringWriter finalResult = new StringWriter();
            SAXTransformerFactory tf = (SAXTransformerFactory) SAXTransformerFactory.newInstance();
            TransformerHandler result = tf.newTransformerHandler();
            result.getTransformer().setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            result.getTransformer().setOutputProperty(OutputKeys.INDENT, "yes");
            result.getTransformer().setOutputProperty(OutputKeys.METHOD, "html");
            result.getTransformer().setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            result.setResult(new StreamResult(finalResult));

            ContentHandler postProcess = result;
            DaisyDiff.diffHTML(new InputSource(new StringReader(html1)), new InputSource(new StringReader(html2)), postProcess, "test", Locale.ENGLISH);

            System.out.println(finalResult.toString());

            String HTML_file="<html>\n" +
                    "<head>\n" +
                    "    <link rel=\"stylesheet\" type=\"text/css\" href=\"style.css\" media=\"screen\" />\n" +
                    "</head>\n" +
                    "<header>Diff(Removals - RED, Additons - Green)</header>\n" +
                    "<body>\n" +
                    finalResult.toString() +
                    "</body>\n" +
                    "</html>";

            // Creating a File object that represents the disk file.
            File outputFile = new File("src/main/resources/");
            PrintStream o = new PrintStream(new File(outputFile, "output.html"));

            // Store current System.out before assigning a new value
            PrintStream console = System.out;

            // Assign o to output stream
            System.setOut(o);
            System.out.println(HTML_file);

        } catch (SAXException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public static String usingBufferedReader(String filename)
    {

        File appDir = new File("src/main/resources/");
        File inputFile = new File(appDir, filename);
        StringBuilder contentBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile)))
        {

            String sCurrentLine;
            while ((sCurrentLine = br.readLine()) != null)
            {
                contentBuilder.append(sCurrentLine).append("\n");
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return contentBuilder.toString();
    }
}
