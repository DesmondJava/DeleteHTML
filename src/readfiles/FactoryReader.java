package readfiles;

import java.io.File;

/**
 * Created by Администратор on 07.07.2015.
 */
public class FactoryReader {

    public static ReadFromFile getReader(File file){
        ReadFromFile readFromFile = null;
        String fileName = file.getName();
        if(fileName.endsWith(".doc")){
            readFromFile = new DocReader(file);
        }
        return readFromFile;
    }
}
