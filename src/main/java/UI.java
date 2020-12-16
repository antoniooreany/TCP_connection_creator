import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class UI extends Application {
    private static final String[] BUTTON_NAMES = new String[]{"HEAD", "GET", "POST"};

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane grid = new GridPane();

        // Radio-buttons
        RadioButton[] radioButtons = new RadioButton[BUTTON_NAMES.length];
        VBox vBox = new VBox();
        ToggleGroup radioGroup = new ToggleGroup();
        TextField[] radiobuttonLabels = new TextField[BUTTON_NAMES.length];
        for (int i = 0; i < BUTTON_NAMES.length; i++) {
            radioButtons[i] = new RadioButton(BUTTON_NAMES[i]);
            radioButtons[i].setToggleGroup(radioGroup);
            vBox.getChildren().add(radioButtons[i]);
            radiobuttonLabels[i] = new TextField(BUTTON_NAMES[i]);
            final int finalI = i;
            radioButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    BUTTON_NAMES[finalI] = HttpClient.getMethod();
                }
            });
            grid.add(radioButtons[i], 0,i);
            grid.add(radiobuttonLabels[i], 1, i);
        }
        radioButtons[0].setSelected(true);
        vBox.getChildren().add(grid);

        // Hostname Label, Field
        Label hostnameLabel = new Label("request:");
        TextField hostnameTextField = new TextField("google.com");
        final CharSequence hostname = hostnameTextField.getCharacters();
        grid.add(hostnameLabel, 0, 3);
        grid.add(hostnameTextField, 1, 3);

        // TODO Create Label with the current Request


        // Response Label, Label
        Label responseLabel = new Label("response:");
        final Label responseTextField = new Label("no response yet");
        grid.add(responseLabel, 0, 4);
        grid.add(responseTextField, 1, 4);

        // Button
        Button btn = new Button("make a request");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                HttpClient.ConnectTCP("https://www." + hostname + "/");
                responseTextField.setText(HttpClient.getResponse());
            }
        });
        grid.add(hbBtn, 1, 5);


        Scene scene = new Scene(vBox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}