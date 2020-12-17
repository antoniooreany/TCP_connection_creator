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

/**
 * This class is the user interface class
 *
 * @author Gorshkov Anton
 */
public class UI extends Application {
    private static final String[] BUTTON_NAMES = new String[]{"HEAD ", "GET ", "POST "};
    private final static int SCENE_HEIGHT = 100;
    private final static int SCENE_WIDTH = 200;
    private final static int NB_BTN_SPACING = 10;
    private TextField requestTextField;

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
        final TextField[] radiobuttonLabels = new TextField[BUTTON_NAMES.length];
        int i;
        for (i = 0; i < BUTTON_NAMES.length; i++) {
            radioButtons[i] = new RadioButton(BUTTON_NAMES[i]);
            radioButtons[i].setToggleGroup(radioGroup);
            vBox.getChildren().add(radioButtons[i]);
            radiobuttonLabels[i] = new TextField(/*BUTTON_NAMES[i]*/);
            final int finalI = i;
            radioButtons[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    HttpClient.setMethod(BUTTON_NAMES[finalI]);
                    radiobuttonLabels[finalI].setText(BUTTON_NAMES[finalI]);
                    requestTextField.setText(HttpClient.requestText);
                }
            });
            grid.add(radioButtons[i], 0, i);
            grid.add(radiobuttonLabels[i], 1, i);
        }
        radioButtons[0].setSelected(true);
        vBox.getChildren().add(grid);

        // Hostname Label, Field
        Label hostnameLabel = new Label("hostname: ");
        TextField hostnameTextField = new TextField("google.com");
        final CharSequence hostname = hostnameTextField.getCharacters();
        grid.add(hostnameLabel, 0, ++i);
        grid.add(hostnameTextField, 1, i);

        // TODO Create Label with the current Request
        // Request Label, Field
        Label requestLabel = new Label("request:");
        requestTextField = new TextField("requestTextField");
//        final CharSequence hostname = requestTextField.getCharacters();
        grid.add(requestLabel, 0, ++i);
        grid.add(requestTextField, 1, i);


        // Response Label, Label
        Label responseLabel = new Label("response:");
        final Label responseTextField = new Label("no response yet");
        grid.add(responseLabel, 0, ++i);
        grid.add(responseTextField, 1, i);

        // Button
        Button btn = new Button("make a request");
        HBox hbBtn = new HBox(NB_BTN_SPACING);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                HttpClient.ConnectTCP("https://www." + hostname + "/");
                responseTextField.setText(HttpClient.getResponse());
            }
        });
        grid.add(hbBtn, 1, ++i);

        Scene scene = new Scene(vBox, SCENE_WIDTH, SCENE_HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}