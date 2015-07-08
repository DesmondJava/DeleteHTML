package maincode;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import readfiles.FactoryReader;
import readfiles.ReadFromFile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class DeleteHTMLtext extends Application {

    Stage window;
    Scene scene;
    Button buttonExecute;
    Button buttonPastle;
    Button buttonRemoveTextFromTextField;
    TextField inputHTMLfield;
    BorderPane layout;
    MenuItem saveFileButton;

    public static String pathForFileChooser = "";

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setOnCloseRequest(e -> {
            e.consume();
            closeProgram();
        });
        window.setTitle("Удалить HTML из буфера обмена");

        //Описание программы
        Label description = new Label();
        description.setText("В этой программе Вы можете удалить HTML теги с Вашего текста\n" +
                "Для этого вставьте HTML текст с буфера обмена в текстовую форму и нажмите кнопку 'Выполнить'\n" +
                "После чего программа перезапишет Ваш буфер обмена, после можете вставить готовый текст куда Вам угодно.\n" +
                "\nКнопки:\n" +
                "* Вставить - вставляет из буфера текст в текстовую форму\n" +
                "* Очистить - очищает текстовое поле, если вы допустили ошибки\n" +
                "* Выполнить - превращает HTML текст в простой текст без тегов.\n\n");

        Label madeBy = new Label();
        madeBy.setText("This program made by Vadym Shevchenko   ");
        madeBy.setAlignment(Pos.BOTTOM_RIGHT);
        madeBy.setId("vadym");

        //Menu
        //File
        Menu fileMenu = new Menu("_File");
        MenuItem openFile = new MenuItem("Открыть файл...");
        openFile.setOnAction(e -> openTextFile());
        fileMenu.getItems().add(openFile);
        saveFileButton = new MenuItem("Сохранить как...");
        saveFileButton.setDisable(true);
        saveFileButton.setOnAction(e -> saveFileFileChooser());
        fileMenu.getItems().add(saveFileButton);
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem clearBuffer = new MenuItem("Очистить буффер обмена...");
        clearBuffer.setOnAction(e -> clearBuffer());
        fileMenu.getItems().add(clearBuffer);
        fileMenu.getItems().add(new SeparatorMenuItem());
        MenuItem exit = new MenuItem("Выйти с программы...");
        exit.setOnAction(e -> closeProgram());
        fileMenu.getItems().add(exit);

        //Edit
        Menu editMenu = new Menu("_Edit");
        MenuItem paste = new MenuItem("Вставить");
        paste.setOnAction(e -> pastleTextField());
        editMenu.getItems().add(paste);
        MenuItem remove = new MenuItem("Очистить");
        remove.setOnAction(e -> removeTextFromTextField());
        editMenu.getItems().add(remove);
        MenuItem execute = new MenuItem("Выполнить");
        execute.setOnAction(e -> executeHTMLfromClipboard());
        editMenu.getItems().add(execute);

        //Main menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, editMenu);

        //Form
        inputHTMLfield = new TextField();
        inputHTMLfield.setPromptText("Вставьте Ваш HTML текст сюда...");
        inputHTMLfield.setPrefColumnCount(35);
        inputHTMLfield.setMinHeight(20);

        //Button
        buttonExecute = new Button("Выполнить");
        buttonExecute.setOnAction(e -> executeHTMLfromClipboard());
        buttonExecute.setMinWidth(60);
        buttonExecute.setId("executeButton");
        buttonPastle = new Button("Вставить");
        buttonPastle.setOnAction(e -> pastleTextField());
        buttonPastle.setMinWidth(60);
        buttonRemoveTextFromTextField = new Button("Очистить");
        buttonRemoveTextFromTextField.setOnAction(e -> removeTextFromTextField());
        buttonRemoveTextFromTextField.setMinWidth(60);

        //Layout
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(inputHTMLfield, buttonPastle, buttonRemoveTextFromTextField);
        hbox.setAlignment(Pos.CENTER);

        HBox hboxMadeBy = new HBox(10);
        hboxMadeBy.getChildren().addAll(madeBy);
        hboxMadeBy.setAlignment(Pos.BASELINE_RIGHT);
        hboxMadeBy.setId("vadym");

        VBox vertLayout = new VBox(13);
        vertLayout.setPadding(new Insets(20, 20, 20, 20));
        vertLayout.getChildren().addAll(description,hbox,buttonExecute);
        vertLayout.setAlignment(Pos.CENTER);


        layout = new BorderPane();
        layout.setTop(menuBar);
        layout.setCenter(vertLayout);
        layout.setBottom(hboxMadeBy);
        layout.setPadding(new Insets(0, 0, 10, 0));


        scene = new Scene(layout, 670, 320);
        scene.getStylesheets().add("styles/CustomStyle.css");
        window.setScene(scene);
        window.setMinHeight(260);
        window.setMinWidth(350);
        window.setMaximized(false);
        window.show();
    }

    private void clearBuffer() {
        saveFileButton.setDisable(true);
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        clipboard.clear();
    }


    // Button 'Execute'
    private void executeHTMLfromClipboard() {
        String textHTML = inputHTMLfield.getText();

        //Если текстовое поле пустое то предупреждаем пользователя об этом
        if(textHTML.isEmpty()){
            ConfirmBox.displayWarning("Предупреждение!", "Пожалуйста вставьте сначала ваш текст в текстовую форму", "Хорошо");
            return;
        }

        //Parsing HTML, copy from clipboard and modify text
        String result = Jsoup.parse(textHTML).text();
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(result);
        clipboard.setContent(content);

        //Show display that operation was successful!
        inputHTMLfield.setText("");
        boolean isSaveAsButton = ConfirmBox.displaySucces();
        if(isSaveAsButton){
            saveFileFileChooser();
        }
        saveFileButton.setDisable(false);
    }

    // Button 'Вставить'
    private void pastleTextField(){
        Clipboard clipboard = Clipboard.getSystemClipboard();

        if(!clipboard.getContentTypes().contains(DataFormat.PLAIN_TEXT)){
            ConfirmBox.displayWarning("Предупреждение!", "Ваш буфер обмена не содержит текста\nПожалуйста, скопируйте HTML текст в буффер", "Хорошо");
            return;
        }
        String textFromClipboard = (String) clipboard.getContent(DataFormat.PLAIN_TEXT);
        if(textFromClipboard.isEmpty()){
            ConfirmBox.displayWarning("Предупреждение!", "Ваш буфер обмена пуст\nСкопируйте HTML текст в буффер", "Хорошо");
            return;
        }
        inputHTMLfield.setText(textFromClipboard);
    }

    // Button 'Очистить'
    private void removeTextFromTextField() {
        inputHTMLfield.setText("");
    }

    // When you close the program we ask confirmation
    private void closeProgram(){
        boolean answer = ConfirmBox.display("Выход из программы", "Вы действительно хотите выйти?");
        if(answer) {
            window.close();
        }
    }

    private void saveFileFileChooser() {
        Stage confirmWindow = new Stage();
        confirmWindow.initModality(Modality.APPLICATION_MODAL);
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);



        Clipboard clipboard = Clipboard.getSystemClipboard();

        if(!clipboard.getContentTypes().contains(DataFormat.PLAIN_TEXT)){
            ConfirmBox.displayWarning("Предупреждение!", "Ваш буфер обмена не содержит текста\nПожалуйста, выполните для начала операцию 'Выполнить'", "Хорошо");
            return;
        }

        String textFromClipboard = (String) clipboard.getContent(DataFormat.PLAIN_TEXT);


        //Show save file dialog
        File file = fileChooser.showSaveDialog(confirmWindow);

        if(textFromClipboard.isEmpty()){
            ConfirmBox.displayWarning("Предупреждение!", "Ваш буфер обмена не содержит текста\nПожалуйста, выполните для начала операцию 'Выполнить'", "Хорошо");
            return;
        }
        if(file != null){
            saveFile(textFromClipboard, file);
        }
    }

    private void saveFile(String textFromClipboard, File file) {
        try (FileWriter fileWriter = new FileWriter(file)){
            fileWriter.write(textFromClipboard);
            fileWriter.close();
            ConfirmBox.displayError("Succesful", "Ваш текст успешно сохранен");
        } catch (IOException ex) {
            String message = ex.getMessage();
            ConfirmBox.displayError("Error", message);
            ex.printStackTrace();
        }
    }

    public void openTextFile(){
        Stage confirmWindow = new Stage();
        String result = "";
        confirmWindow.initModality(Modality.APPLICATION_MODAL);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("All text files", "*.doc", "*.docx", "*.txt"),
                new FileChooser.ExtensionFilter("All text files", "*.docx", "*.txt"),
//                new FileChooser.ExtensionFilter("Docs files", "*.doc", "*.docx"),   DONT WORK DocREADER
                new FileChooser.ExtensionFilter("Docs files", "*.docx"),
                new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        fileChooser.setTitle("Open Text File");
        if(pathForFileChooser != null && !pathForFileChooser.equals("")){
            File defaultDirectory = new File(pathForFileChooser);
            fileChooser.setInitialDirectory(defaultDirectory);
        }
        File file = fileChooser.showOpenDialog(confirmWindow);
        if (file != null) {
            result = readFromFile(file);
            pathForFileChooser = file.getParent();
        }
        inputHTMLfield.setText(result);

    }

    private String readFromFile(File file) {
        ReadFromFile readFromFile = FactoryReader.getReader(file);
        return readFromFile.readFile();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
