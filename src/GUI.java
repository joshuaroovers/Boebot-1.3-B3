import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUI extends Application {
    private StringBuilder commands;
    private ListView<String> outputTextArea;
    private int commandCount = 0;

    @Override
    public void start(Stage stage) {


        stage.setTitle("BoeBot");
        stage.show();


        double sizeModifier = 1.5;
        double prefWidth = 50*sizeModifier;
        double prefHeight = 50*sizeModifier;
        Color arrowColor = Color.MEDIUMSEAGREEN;


        ArrayList<Button> allButtons = new ArrayList<>();
        commands = new StringBuilder();

        //#region direction button initialization
        Button buttonForward = new Button("",createArrowImageView(arrowColor, prefWidth));
        buttonForward.setOnAction(event -> {
            System.out.println("Forward");
            buttonController("Forward","v");
        });
        allButtons.add(buttonForward);

        Button buttonLeft = new Button("",createArrowImageView(270, arrowColor, prefWidth));
        buttonLeft.setOnAction(event -> {
            System.out.println("Go left");
            buttonController("Left","l");
        });
        allButtons.add(buttonLeft);

        Button buttonRight = new Button("",createArrowImageView(90, arrowColor, prefWidth));
        buttonRight.setOnAction(event -> {
            System.out.println("Go right");
            buttonController("Right","r");
        });
        allButtons.add(buttonRight);
        //#endregion
        //#region misc command button initialization
        Button buttonSend = new Button("send");
        buttonSend.setOnAction(event -> {
            System.out.println("Send");
            sendCommands();
        });
        allButtons.add(buttonSend);

        Button buttonStop = new Button("stop");
        buttonStop.setOnAction(event -> {
            System.out.println("stopped");
            sendStopCommand();
        });
        allButtons.add(buttonStop);
        //#endregion

        ArrayList<Button> clawButtons = new ArrayList<>();
        //region claw button initialization
        Button buttonClawOpen = new Button("open claw");
        buttonClawOpen.setOnAction(event -> {
            System.out.println("Gripper open");
            buttonController("Open Claw", "o");
        });
        clawButtons.add(buttonClawOpen);

        Button buttonClawClose = new Button("close claw");
        buttonClawClose.setOnAction(event -> {
            System.out.println("Gripper closed");
            buttonController("Close Claw", "c");
        });
        clawButtons.add(buttonClawClose);
        //#endregion

        ArrayList<Button> destroyCommandsButtons = new ArrayList<>();
        //#region destroy command buttons initialization
        Button buttonDelete = new Button("delete");
        buttonDelete.setOnAction(event -> {
            System.out.println("Last Command Deleted");
            deleteCommand();
        });
        destroyCommandsButtons.add(buttonDelete);

        Button buttonClear = new Button("clear");
        buttonClear.setOnAction(event -> {
            System.out.println("Commands Cleared");
            clearCommands();
        });
        destroyCommandsButtons.add(buttonClear);
        //#endregion

        VBox mainBox = new VBox();


        double prefInset = 10*sizeModifier;
        double fontSize = 15*sizeModifier;


        outputTextArea  = new ListView<>();
        outputTextArea.setPrefHeight(fontSize*6);
        outputTextArea.setPrefWidth((prefWidth*3)+prefInset);
        outputTextArea.setStyle("-fx-font-size:"+fontSize/1.34+";");

        VBox clearDeleteButtons = new VBox(0);
        HBox clawOpenClawCloseButtons = new HBox(prefInset);


        //#region button styling
        for(Button button : allButtons){
            button.setPadding(new Insets(0));
            button.setFont(new Font(fontSize));
            button.setPrefWidth(prefWidth);
            button.setPrefHeight(prefHeight);
        }

        for(Button button : destroyCommandsButtons){
            button.setTextOverrun(OverrunStyle.CLIP);
            button.setFont(new Font(fontSize/1.3));
            button.setPrefWidth(prefWidth);
            button.setPrefHeight(prefHeight/2.0);
            clearDeleteButtons.getChildren().add(button);
        }

        for(Button button : clawButtons){
            button.setPrefHeight(prefHeight);
            button.setFont(new Font(fontSize/1.1));
            button.setPrefWidth((prefWidth*1.5)+(prefInset/2.0));
            clawOpenClawCloseButtons.getChildren().add(button);
        }
        //#endregion

        GridPane gridPane = new GridPane();

        gridPane.add(buttonForward, 1, 0);
        gridPane.add(clearDeleteButtons,2,0);
        gridPane.add(buttonLeft, 0, 1);
        gridPane.add(buttonRight, 2, 1);
        gridPane.add(buttonSend, 1, 1);
        gridPane.add(buttonStop, 0, 0);
        gridPane.add(clawOpenClawCloseButtons, 0, 2, 3,1);
        gridPane.add(outputTextArea, 0, 3, 3, 1); // Span TextArea across


        gridPane.setHgap(prefInset);
        gridPane.setVgap(prefInset);
        gridPane.setPadding(new Insets(prefInset));

        mainBox.getChildren().add(gridPane);
        Scene scene = new Scene(mainBox);

        stage.setScene(scene);
        stage.show();
    }


    /**
     * buttonController
     * @author Ryan Hao, Joshua Roovers
     * adds a command string to the commands StringBuilder, increases the commandCount counter and uses it in
     * a new item string and adds this new string to the outputTextArea (and auto-scrolls to the new item)
     * @param commandDescription a String of a short description of the command
     * @param command a String of a single character that acts as a command
     */
    private void buttonController(String commandDescription, String command){
        commands.append(command);

        commandCount++;
        String newCommandLine = commandCount + ". " + commandDescription;
        outputTextArea.getItems().add(newCommandLine);
        outputTextArea.scrollTo(outputTextArea.getItems().indexOf(outputTextArea.getItems().get(outputTextArea.getItems().size()-1)));
    }

    /**
     * clearCommands
     * @author Joshua Roovers
     * empty the commands StringBuilder, reset the commandsCount counter and remove all items from outputTextArea
     */
    private void clearCommands() {
        commands.delete(0, commands.length());

        commandCount = 0;
        outputTextArea.getItems().clear();
    }

    /**
     * deleteCommand
     * @author Joshua Roovers
     * deletes the last command in the commands StringBuilder, reduce the commandCount counter and remove the
     * last item of outputTextArea
     *
     */
    private void deleteCommand() {
        if(commands.length() > 0){
            commands.delete(commands.length()-1, commands.length());
            commandCount--;
            outputTextArea.getItems().remove(outputTextArea.getItems().size()-1, outputTextArea.getItems().size());
        }else{
            System.out.println("nothing to delete");
        }
    }


    /**
     * sendCommands
     * sends the commands StringBuilder to the BoeBot Controller to be decoded //todo
     */
    private void sendCommands() {
        System.out.println(commands.toString());
    }

    /**
     * sendStopCommand
     * sends a signal to the BoeBot Controller to stop the wheels //todo
     */
    private void sendStopCommand() {
        System.out.println("stop the wheels through motorHelper");
    }


    //#region arrow SVG
    /**
     * @author Joshua Roovers
     * SVG method to create a transparent arrow PNG for the directional buttons, see methods for further explanation
     */

    //creates a rotated arrow as an ImageView
    private ImageView createArrowImageView(double rotationAngle, Color color, Double size) {
        //create new SVGPath
        SVGPath arrowPath = new SVGPath();
        //Set cursor to (0,100). draw  to (50,0). draw line to (100,100). draw a line back to starting point closing the path
        arrowPath.setContent("M 0 100 L 50 0 L 100 100 Z");
        arrowPath.setFill(color);

        //create new stackPane and add the arrowPath to it
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(arrowPath);

        // Set a snapshot parameter to capture the SVGPath as an image with a transparent background
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        //make new imageView from the snapshot of the stackPane
        ImageView image = new ImageView(stackPane.snapshot(parameters, null));

        //set sizing
        image.setFitHeight(size);
        image.setFitWidth(size);
        //set rotation
        image.setRotate(rotationAngle);


        return image;
    }

    //createArrowImageView() overflow for no rotation
    private ImageView createArrowImageView(Color color, Double size) {
        return createArrowImageView(0, color, size);
    }

    //#endregion

}
