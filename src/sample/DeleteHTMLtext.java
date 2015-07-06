package sample;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.getChildren().addAll(hbox, buttonExecute);
        layout.setAlignment(Pos.CENTER);


        scene = new Scene(layout, 600, 100);
        window.setScene(scene);
        window.show();
    }

    private void removeTextFromTextField() {
        inputHTMLfield.setText("");
    }

    private void removeHTMLfromClipboard() {
        String textHTML = inputHTMLfield.getText();

        if(textHTML.isEmpty()){
            ConfirmBox.displaySucces("Предупреждение!", "Пожалуйста вставьте сначала ваш текст в текстовую форму", "Хорошо");
            return;
        }

        String result = Jsoup.parse(textHTML).text();



        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(result);
        clipboard.setContent(content);
        inputHTMLfield.setText("");
        ConfirmBox.displaySucces("Успех", "Операция была успешно выполнена!\n" +
                "Вы можете вставить полученый текст в любой удобный Вам редактор.", "Отлично!");
    }

    private void pastleTextField(){
        Clipboard clipboard = Clipboard.getSystemClipboard();
        String textFromClipboard = (String) clipboard.getContent(DataFormat.PLAIN_TEXT);
        inputHTMLfield.setText(textFromClipboard);
    }

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
