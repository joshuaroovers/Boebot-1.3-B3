import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.geometry.Insets;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUI extends Application {
    private StringBuilder commands;
    private Label commandBar;

    @Override
    public void start(Stage stage) {


        stage.setTitle("BoeBot");
        stage.show();

        VBox mainBox = new VBox();

        ArrayList<Button> allButtons = new ArrayList<>();

        Color arrowColor = Color.MEDIUMSEAGREEN;

        commands = new StringBuilder();

        Button buttonForward = new Button("",createArrowImageView(arrowColor, 50));
        buttonForward.setOnAction(event -> {
            System.out.println("forward");
            addCommand("v");
        });
        allButtons.add(buttonForward);

        Button buttonLeft = new Button("",createArrowImageView(270, arrowColor, 50));
        buttonLeft.setOnAction(event -> {
            System.out.println("Go left");
            addCommand("l");
        });
        allButtons.add(buttonLeft);

        Button buttonRight = new Button("",createArrowImageView(90, arrowColor, 50));
        buttonRight.setOnAction(event -> {
            System.out.println("Go right");
            addCommand("r");
        });
        allButtons.add(buttonRight);


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

        Button buttonClawOpen = new Button("open\nclaw");
        buttonClawOpen.setOnAction(event -> {
            System.out.println("Gripper open");
            addCommand("o");
        });
        allButtons.add(buttonClawOpen);

        Button buttonClawClose = new Button("close\nclaw");
        buttonClawClose.setOnAction(event -> {
            System.out.println("Gripper closed");
            addCommand("c");
        });

        allButtons.add(buttonClawClose);

        ArrayList<Button> commandButtons = new ArrayList<>();

        Button buttonDelete = new Button("delete");
        buttonDelete.setOnAction(event -> {
            System.out.println("Last Command Deleted");
            deleteCommand();
        });
        commandButtons.add(buttonDelete);

        Button buttonClear = new Button("clear");
        buttonClear.setOnAction(event -> {
            System.out.println("Commands Cleared");
            clearCommands();
        });
        commandButtons.add(buttonClear);

        VBox clearDeleteButtons = new VBox(0);

        int prefWidth = 50;
        int prefHeight = 50;

        for(Button button : allButtons){
            button.setPadding(new Insets(0));
            button.setPrefWidth(prefWidth);
            button.setPrefHeight(prefHeight);
        }

        for(Button button : commandButtons){
            button.setTextOverrun(OverrunStyle.CLIP);
            button.setPrefWidth(prefWidth);
            button.setPrefHeight(prefHeight/2.0);
            clearDeleteButtons.getChildren().add(button);
        }


        GridPane gridPane = new GridPane();

        gridPane.add(buttonForward, 1, 0);
        gridPane.add(clearDeleteButtons,2,0);
        gridPane.add(buttonLeft, 0, 1);
        gridPane.add(buttonRight, 2, 1);
        gridPane.add(buttonSend, 1, 1);
        gridPane.add(buttonStop, 2, 2);
        gridPane.add(buttonClawOpen, 0, 2);
        gridPane.add(buttonClawClose, 1, 2);


        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));
        commandBar = new Label();
        //commandBar.setStyle("-fx-border-top: 1px solid black;");
        commandBar.setPadding(new Insets(5));
        mainBox.getChildren().add(commandBar);
        mainBox.getChildren().add(gridPane);
        Scene scene = new Scene(mainBox);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * updateCommands
     * update commandBar label in GUI with current value of the commands StringBuilder
     * @Author Joshua Roovers
     */
    private void updateCommands(){
        commandBar.setText(commands.toString());
    }

    /**
     * addCommand
     * adds a string to the commands StringBuilder and updates it in the GUI using updateCommands()
     * @param newCommand the String that gets added to the commands StringBuilder
     * @Author  Joshua Roovers
     */
    private void addCommand(String newCommand){
        commands.append(newCommand);
        updateCommands();
    }

    /**
     * clearCommands
     * empty the commands StringBuilder and update it in the GUI using updateCommands()
     * @Author Joshua Roovers
     */
    private void clearCommands() {
        commands.delete(0, commands.length());
        updateCommands();
    }

    /**
     * deleteCommand
     * deletes the last command in the commands StringBuilder and update it in the GUI using updateCommands()
     * @Author Joshua Roovers
     */
    private void deleteCommand() {
        if(commands.length() > 0){
            commands.delete(commands.length()-1, commands.length());
            updateCommands();
        }else{
            System.out.println("nothing to delete");
        }
    }


    /**
     * sendCommands
     * sends the commands StringBuilder to the BoeBot Controller to be decoded
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
     * SVG methods to create a transparent arrow PNG for the directional buttons, see methods for further explanation
     */

    //creates a rotated arrow as an ImageView
    private ImageView createArrowImageView(double rotationAngle, Color color, int size) {
        //create new SVGPath
        SVGPath arrowPath = new SVGPath();
        //Set cursor to (0,100). draw  to (50,0). draw line to (100,100). draw a line to starting point closing the path
        arrowPath.setContent("M 0 100 L 50 0 L 100 100 Z");
        arrowPath.setFill(color);

        //make new imageView from the image made from the arrowPath using SVGToTransparentImage()
        ImageView image = new ImageView(SVGToTransparantImage(arrowPath));

        image.setFitHeight(size);
        image.setFitWidth(size);
        //set rotation
        image.setRotate(rotationAngle);


        return image;
    }

    //createArrowImageView() overflow for no rotation
    private ImageView createArrowImageView(Color color, int size) {
        return createArrowImageView(0, color, size);
    }

    //converts SVGPath to Image with a transparent background
    private Image SVGToTransparantImage(SVGPath svgPath) {
        //make a new image pane and add the SVGPath
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(svgPath);

        // Set a snapshot parameter to capture the SVGPath as an image with a transparent background
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        return stackPane.snapshot(parameters, null);
    }
    //#endregion


}
