package readfiles;

import maincode.ConfirmBox;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

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
            for(XWPFParagraph text : paragraphs){
                sb.append(text.getText());
            }
            result = sb.toString();

        } catch(org.apache.poi.POIXMLException | IOException e){
            String message = e.getMessage();
            ConfirmBox.displayError("Error", "Произошла неизвестная ошибка. Возможно в файле присутствуют картинки или текст в таблице\n" + message);
            e.printStackTrace();
        }
        return result;
    }
}
