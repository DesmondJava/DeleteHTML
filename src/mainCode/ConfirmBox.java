package maincode;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    private static boolean result = false;

    //Простое окошко, например, используется для выхода из программы
    //Кнопки ДА и НЕТ
    public static boolean display(String title, String message){
        Stage confirmWindow = new Stage();
        confirmWindow.setTitle(title);
        confirmWindow.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label();
        label.setText(message);

        Button buttonYes = new Button();
        buttonYes.setMinWidth(40);
        buttonYes.setText("Да");
        buttonYes.setOnAction(e -> {
            result = true;
            confirmWindow.close();
        });

        Button buttonNo = new Button();
        buttonNo.setMinWidth(40);
        buttonNo.setText("Нет");
        buttonNo.setOnAction(e -> {
            result = false;
            confirmWindow.close();
        });

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(buttonYes, buttonNo);

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, hbox);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(12, 12, 12, 12));
        confirmWindow.setScene(new Scene(layout));
        confirmWindow.showAndWait();
        return result;
    }

    //Окно предупреждения о не правильном использовании программы, например пустой буффер и нажать Выполнить
    //Кнопка Хорошо
    public static void displayWarning(String message){
        Stage confirmWindow = new Stage();
        confirmWindow.setTitle("Предупреждение!");
        confirmWindow.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label();
        label.setText(message);


        final ImageView imv = new ImageView();
        final Image image2 = new Image("/resources/warning.png");
        imv.setImage(image2);

        Button okeyButton = new Button();
        okeyButton.setText("Хорошо");
        okeyButton.setOnAction(e -> {
            confirmWindow.close();
        });

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(imv, label);
        hbox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);
        layout.getChildren().addAll(hbox, okeyButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(12, 12, 12, 12));
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("styles/warning.css");
        confirmWindow.setScene(scene);
        confirmWindow.showAndWait();
    }

    //Окно при успешном выполнении задания
    //Кнопки Сохранить как и Отлично
    public static boolean displaySucces(){
        Stage confirmWindow = new Stage();
        confirmWindow.setTitle("Успех!");
        confirmWindow.initModality(Modality.APPLICATION_MODAL);

        final boolean[] isSaveAs = {false};

        Label label = new Label();
        label.setText("Операция была успешно выполнена!\n" +
                "Вы можете вставить полученый текст в любой удобный Вам редактор.");

        Button saveAsButton = new Button();
        saveAsButton.setText("Сохранить как...");
        saveAsButton.setOnAction(e -> {
            isSaveAs[0] = true;
            confirmWindow.close();
        });

        Button okeyButton = new Button();
        okeyButton.setText("Отлично!");
        okeyButton.setOnAction(e -> {
            confirmWindow.close();
        });

        HBox hbox = new HBox(10);
        hbox.setAlignment(Pos.CENTER);
        hbox.getChildren().addAll(saveAsButton, okeyButton);

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, hbox);
        layout.setPadding(new Insets(12, 12, 12, 12));
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("styles/success.css");
        confirmWindow.setScene(scene);
        confirmWindow.showAndWait();
        return isSaveAs[0];
    }

    //Использовуется для ошибок и информатировании пользователя
    //Кнопка окей
    public static void displayError(String title, String message){
        Stage confirmWindow = new Stage();
        confirmWindow.setTitle(title);
        confirmWindow.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label();
        label.setText(message);

        final ImageView imv = new ImageView();
        final Image image2 = new Image("/resources/error.png");
        imv.setImage(image2);

        Button okeyButton = new Button();
        okeyButton.setText("Ok");
        okeyButton.setMinWidth(40);
        okeyButton.setOnAction(e -> {
            confirmWindow.close();
        });

        HBox hbox = new HBox(10);
        hbox.getChildren().addAll(imv, label);
        hbox.setAlignment(Pos.CENTER);

        VBox layout = new VBox(10);

        layout.getChildren().addAll(hbox, okeyButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15, 15, 15, 15));
        Scene scene = new Scene(layout);
        scene.getStylesheets().add("styles/error.css");
        confirmWindow.setScene(scene);
        confirmWindow.showAndWait();
    }

    public static void displaySaveOk(String title, String message){
        Stage confirmWindow = new Stage();
        confirmWindow.setTitle(title);
        confirmWindow.initModality(Modality.APPLICATION_MODAL);

        Label label = new Label();
        label.setText(message);

        Button okeyButton = new Button();
        okeyButton.setText("Ok");
        okeyButton.setMinWidth(40);
        okeyButton.setOnAction(e -> {
            confirmWindow.close();
        });

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, okeyButton);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(15, 15, 15, 15));
        confirmWindow.setScene(new Scene(layout));
        confirmWindow.showAndWait();
    }

}
