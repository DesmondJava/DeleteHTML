package readfiles;

import maincode.ConfirmBox;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;

import java.io.File;
import java.io.FileInputStream;


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
        } catch(org.apache.poi.poifs.filesystem.OfficeXmlFileException | java.io.IOException e){
            String message = e.getMessage();
            ConfirmBox.displayError("Error", "Произошла неизвестная ошибка. Возможно в файле присутствуют картинки или текст в таблице\n" + message);
            e.printStackTrace();
        }


        return text;
    }
}
