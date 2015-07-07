package maincode;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    public static void displaySucces(String title, String message, String nameButton){
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

    public static void displayError(String message){
        Stage confirmWindow = new Stage();
        confirmWindow.setTitle("Error");
        confirmWindow.initModality(Modality.APPLICATION_MODAL);
        confirmWindow.setMinWidth(550);
        confirmWindow.setMinHeight(200);

        Label label = new Label();
        label.setText(message);

        Button okeyButton = new Button();
        okeyButton.setText("Ok");
        okeyButton.setOnAction(e -> {
            confirmWindow.close();
        });

        VBox layout = new VBox(10);

        layout.getChildren().addAll(label, okeyButton);
        layout.setAlignment(Pos.CENTER);
        confirmWindow.setScene(new Scene(layout));
        confirmWindow.showAndWait();
    }


}
