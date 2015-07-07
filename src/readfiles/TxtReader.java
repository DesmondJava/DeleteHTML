package readfiles;

import maincode.ConfirmBox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by Администратор on 07.07.2015.
 */
public class TxtReader implements ReadFromFile {

    private File file;

    public TxtReader(File file){
        this.file = file;
    }

    @Override
    public String readFile() {
        String result = "";
        try {
            result = new String(Files.readAllBytes(Paths.get(file.getAbsolutePath())));
        } catch (IOException e) {
            String message = e.getMessage();
            ConfirmBox.displayError("Error", message);
            e.printStackTrace();
        }
        return result;
    }
}
