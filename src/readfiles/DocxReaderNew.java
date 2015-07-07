package readfiles;

import maincode.ConfirmBox;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Администратор on 07.07.2015.
 */
public class DocxReaderNew implements ReadFromFile {

    private File file;

    public DocxReaderNew(File file){
        this.file = file;
    }

    @Override
    public String readFile() {
        String result = "";
        try(FileInputStream fis = new FileInputStream(file.getAbsolutePath())){
            XWPFDocument document = new XWPFDocument(fis);
            List<XWPFParagraph> paragraphs = document.getParagraphs();
            StringBuilder sb = new StringBuilder();
            System.out.println("Total no of paragraph "+paragraphs.size());
            for(XWPFParagraph text : paragraphs){
                System.out.println(text.getText());
                sb.append(text.getText());
            }
            result = sb.toString();

        } catch(org.apache.poi.POIXMLException | IOException e){
            String message = e.getMessage();
            ConfirmBox.displayError(message);
            e.printStackTrace();
        }
        return result;
    }
}
