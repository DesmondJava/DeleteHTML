package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.jsoup.Jsoup;



public class DeleteHTMLtext extends Application {

    Stage window;
    Scene scene;
    Button buttonExecute;
    Button buttonPastle;
    Button buttonRemoveTextFromTextField;
    TextField inputHTMLfield;


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
        madeBy.setText("This program made by Vadym Shevchenko");
        madeBy.setAlignment(Pos.BOTTOM_RIGHT);

        //Form
        inputHTMLfield = new TextField();
        inputHTMLfield.setPrefColumnCount(35);

        //Button
        buttonExecute = new Button("Выполнить");
        buttonExecute.setOnAction(e -> removeHTMLfromClipboard());
        buttonPastle = new Button("Вставить");
        buttonPastle.setOnAction(e -> pastleTextField());
        buttonRemoveTextFromTextField = new Button("Очистить");
        buttonRemoveTextFromTextField.setOnAction(e -> removeTextFromTextField());

        //Layout
        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(inputHTMLfield, buttonPastle, buttonRemoveTextFromTextField);
        hbox.setAlignment(Pos.CENTER);

        HBox hboxMadeBy = new HBox(10);
        hboxMadeBy.getChildren().addAll(madeBy);
        hboxMadeBy.setAlignment(Pos.BASELINE_RIGHT);

        VBox layout = new VBox(13);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(description, hbox, buttonExecute, hboxMadeBy);
        layout.setAlignment(Pos.CENTER);

        scene = new Scene(layout, 670, 280);
        window.setScene(scene);
        window.show();
    }

    // Button 'Execute'
    private void removeHTMLfromClipboard() {
        String textHTML = inputHTMLfield.getText();

        //Если текстовое поле пустое то предупреждаем пользователя об этом
        if(textHTML.isEmpty()){
            ConfirmBox.displaySucces("Предупреждение!", "Пожалуйста вставьте сначала ваш текст в текстовую форму", "Хорошо");
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
        ConfirmBox.displaySucces("Успех", "Операция была успешно выполнена!\n" +
                "Вы можете вставить полученый текст в любой удобный Вам редактор.", "Отлично!");
    }

    // Button 'Вставить'
    private void pastleTextField(){
        Clipboard clipboard = Clipboard.getSystemClipboard();

        if(!clipboard.getContentTypes().contains(DataFormat.PLAIN_TEXT)){
            ConfirmBox.displaySucces("Предупреждение!", "Ваш буфер обмена не содержит текста\nПожалуйста, скопируйте HTML текст в буффер", "Хорошо");
            return;
        }
        String textFromClipboard = (String) clipboard.getContent(DataFormat.PLAIN_TEXT);
        if(textFromClipboard.isEmpty()){
            ConfirmBox.displaySucces("Предупреждение!", "Ваш буфер обмена пуст\nСкопируйте HTML текст в буффер", "Хорошо");
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

    public static void main(String[] args) {
        launch(args);
    }
}
