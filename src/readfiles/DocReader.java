package readfiles;

import maincode.ConfirmBox;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;


/**
 * Created by Администратор on 07.07.2015.
 */
public class DocReader implements ReadFromFile {

    private File file;

    public DocReader(File file){
        this.file = file;
    }

    @Override
    public String readFile() {
        String text = "";
        try (FileInputStream fis = new FileInputStream(file.getAbsolutePath())){
            HWPFDocument doc = new HWPFDocument(fis);
            WordExtractor we = new WordExtractor(doc);
            text = we.getText();
        } catch (java.io.IOException e) {
            String message = e.getMessage();
            ConfirmBox.displayError(message);
            e.printStackTrace();
        }
        return text;
    }
}
