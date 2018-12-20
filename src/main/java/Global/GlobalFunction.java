package Global;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.outerj.daisy.diff.DaisyDiff;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.imageio.ImageIO;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.sax.SAXTransformerFactory;
import javax.xml.transform.sax.TransformerHandler;
import javax.xml.transform.stream.StreamResult;

import org.openqa.selenium.chrome.ChromeDriver;

import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.io.*;
import java.net.URL;
import java.util.*;

public class GlobalFunction {
    private WebDriver driver;

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

    public GlobalFunction()
    {
        this.driver = new ChromeDriver();
    }

    public String cssproperty(WebElement element){

        JavascriptExecutor executor = (JavascriptExecutor)driver;
        String script = "var s = '';" +
                "var o = getComputedStyle(arguments[0]);" +
                "for(var i = 0; i < o.length; i++){" +
                "s+=o[i] + ':' + o.getPropertyValue(o[i])+';';}" +
                "return s;";
        return executor.executeScript(script, element).toString();
    }

    public Map<String,String> convertStringToMap(String cssString){
        List<String> key = new ArrayList<String>();
        List<String> value = new ArrayList<String>();
        List<String> myList = new ArrayList<String>(Arrays.asList(cssString.split(";")));
        for (int i=0;i<myList.size();i++){
            String strkey = myList.get(i);
            String splitSubstring = ":";
            key.add(i,strkey.substring(0,strkey.indexOf(splitSubstring)));
            value.add(i,strkey.substring(strkey.indexOf(splitSubstring),strkey.length()));
        }
        Map<String,String> cssElementkeyValues = new LinkedHashMap<String,String>();
        for (int i=0;i<key.size();i++){
            cssElementkeyValues.put(key.get(i),value.get(i));
        }
        return cssElementkeyValues;
    }

    public int compareFileText(String fILE_ONE2, String fILE_TWO2)throws Exception
    {

        File f1 = new File(fILE_ONE2); //OUTFILE
        File f2 = new File(fILE_TWO2); //INPUT

        FileReader fR1 = new FileReader(f1);
        FileReader fR2 = new FileReader(f2);

        BufferedReader reader1 = new BufferedReader(fR1);
        BufferedReader reader2 = new BufferedReader(fR2);

        String line1 = null;
        String line2 = null;
        int flag=1;
        while ((flag==1) &&((line1 = reader1.readLine()) != null)&&((line2 = reader2.readLine()) != null))
        {
            if (!line1.equalsIgnoreCase(line2))
                flag=0;
            else
                flag=1;
        }
        reader1.close();
        reader2.close();
        return 1;
    }

    public void createFile(String pageName, String pageSource) throws IOException {
        StringBuilder fileName1 = new StringBuilder("src/main/resources/");
        fileName1.append(pageName);
        fileName1.append("1.html");
        StringBuilder filename2 = new StringBuilder("src/main/resources/");
        filename2.append(pageName);
        filename2.append("2.html");
        File sourceFile1 = new File(fileName1.toString());
        File sourceFile2 = new File(filename2.toString());
        BufferedWriter writeSource;
        FileWriter fileWrite;

        if(!sourceFile1.exists() && !sourceFile2.exists())
        {
            sourceFile1.createNewFile();
            fileWrite = new FileWriter(sourceFile1);
            writeSource = new BufferedWriter(fileWrite);
            writeSource.write(pageSource);
        }
        else if(sourceFile1.exists() && !sourceFile2.exists() )
        {
            sourceFile2.createNewFile();
            fileWrite = new FileWriter(sourceFile2);
            writeSource = new BufferedWriter(fileWrite);
            writeSource.write(pageSource);
        }
        else if(sourceFile1.exists() && sourceFile2.exists())
        {
            sourceFile2.renameTo(sourceFile1);
            fileWrite = new FileWriter(sourceFile2);
            writeSource = new BufferedWriter(fileWrite);
            writeSource.write(pageSource);

        }

    }

    public void createImageFile(String pageName, URL imageURL) throws IOException {
        String fileName1 = "src/main/resources/" + pageName + "1.png";
        String filename2 = "src/main/resources/" + pageName + "2.png";
        File sourceFile1 = new File(fileName1);
        File sourceFile2 = new File(filename2);
        BufferedImage saveImage = ImageIO.read(imageURL);

        if(!sourceFile1.exists() && !sourceFile2.exists())
        {
            ImageIO.write(saveImage, "png", new File(fileName1));
        }
        else if(sourceFile1.exists() && !sourceFile2.exists() )
        {
            ImageIO.write(saveImage, "png", new File(filename2));

        }
        else if(sourceFile1.exists() && sourceFile2.exists())
        {
            sourceFile2.renameTo(sourceFile1);
            ImageIO.write(saveImage, "png", new File(filename2));
        }

    }

    public void daisyDiffTest(String htmlFile1,String htmlFile2) throws Exception {
        String html1 = usingBufferedReader(htmlFile1);
        String html2 = usingBufferedReader(htmlFile2);

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
            outputFile.createNewFile();
            PrintStream o = new PrintStream(new File(outputFile, "output.html"));
            // Store current System.out before assigning a new value
            PrintStream console = System.out;

            // Assign o to output stream
            System.setOut(o);
            System.out.println(HTML_file);

        } catch (SAXException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean testImageComparison(String file1,String file2) {
        boolean matchFlag = true;
        try {
            File fileInput = new File(file1);
            File fileOutPut = new File(file2);

            BufferedImage bufferfileinput = ImageIO.read(fileInput);
            DataBuffer bufferfileInput = bufferfileinput.getData().getDataBuffer();
            int sizefileInput = bufferfileInput.getSize();
            BufferedImage bufferfileOutPut = ImageIO.read(fileOutPut);
            DataBuffer datafileOutPut = bufferfileOutPut.getData().getDataBuffer();
            int sizefileOutPut = datafileOutPut.getSize();
            matchFlag = true;
            if (sizefileInput == sizefileOutPut) {
                for (int i = 0; i < sizefileInput; i++) {
                    if (bufferfileInput.getElem(i) != datafileOutPut.getElem(i)) {
                        matchFlag = false;
                        break;
                    }
                }
            } else {
                matchFlag = false;
            }
        }
         catch (IOException e) {
            e.printStackTrace();
        }
            return matchFlag;

    }

}