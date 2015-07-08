package maincode;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    private static boolean result = false;

    public static boolean display(String title, String message){
        Stage confirmWindow = new Stage();
        confirmWindow.setTitle(title);
        confirmWindow.initModality(Modality.APPLICATION_MODAL);
        confirmWindow.setMinWidth(300);
        confirmWindow.setMinHeight(150);

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

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, buttonYes, buttonNo);
        layout.setAlignment(Pos.CENTER);
        confirmWindow.setScene(new Scene(layout));
        confirmWindow.showAndWait();
        return result;
    }

    public static void displayWarning(String title, String message, String nameButton){
        Stage confirmWindow = new Stage();
        confirmWindow.setTitle(title);
        confirmWindow.initModality(Modality.APPLICATION_MODAL);
        confirmWindow.setMinWidth(500);
        confirmWindow.setMinHeight(150);

        Label label = new Label();
        label.setText(message);

        Button okeyButton = new Button();
        okeyButton.setText(nameButton);
        okeyButton.setOnAction(e -> {
            confirmWindow.close();
        });

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, okeyButton);
        layout.setAlignment(Pos.CENTER);
        confirmWindow.setScene(new Scene(layout));
        confirmWindow.showAndWait();
    }

    public static boolean displaySucces(){
        Stage confirmWindow = new Stage();
        confirmWindow.setTitle("Успех!");
        confirmWindow.initModality(Modality.APPLICATION_MODAL);
        confirmWindow.setMinWidth(500);
        confirmWindow.setMinHeight(150);

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

    public static void displayError(String title, String message){
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
