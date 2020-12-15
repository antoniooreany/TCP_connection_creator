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
        // Hostname Label
        Label hostnameLabel = new Label("request:");
        grid.add(hostnameLabel, 0, 1);
        TextField hostnameTextField = new TextField("google.com");
        final CharSequence hostname = hostnameTextField.getCharacters();
        grid.add(hostnameTextField, 1, 1);

        // TODO Create Label with the current Request



        // Response Label
        Label responseLabel = new Label("response:");
        grid.add(responseLabel, 0, 2);
        final Label responseTextField = new Label("no response yet");
        grid.add(responseTextField, 1, 2);
        // Button
        Button btn = new Button("make a request");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 4);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                HttpClient.ConnectTCP("https://www." + hostname + "/");
                responseTextField.setText(HttpClient.getResponse());
            }
        });
        RadioButton[] radioButtons = new RadioButton[BUTTON_NAMES.length];
        VBox vBox = new VBox();
        ToggleGroup radioGroup = new ToggleGroup();
        for (int i = 0; i < BUTTON_NAMES.length; i++) {
            radioButtons[i] = new RadioButton(BUTTON_NAMES[i]);
            radioButtons[i].setToggleGroup(radioGroup);
            vBox.getChildren().add(radioButtons[i]);
            final int finalI = i;
            radioButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    BUTTON_NAMES[finalI] = HttpClient.getMethod();
                }
            });
        }
        radioButtons[0].setSelected(true);
        vBox.getChildren().add(grid);
        Scene scene = new Scene(vBox, 200, 100);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}