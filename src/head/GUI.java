package head;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.geometry.Insets;
import javafx.scene.shape.SVGPath;
import javafx.stage.Stage;

import java.util.ArrayList;

public class GUI extends Application {

    @Override
    public void start(Stage stage) {


        stage.setTitle("BoeBot");
        stage.show();

        ArrayList<Button> allButtons = new ArrayList<>();


        Button buttonForward = new Button("",createArrowImageView(Color.RED, 50));
        buttonForward.setOnAction(event ->{
            System.out.println("forward");

//            @Override
//            public void handle(ActionEvent actionEvent) {
//                System.out.println("Go foward");
//            }
        });
        allButtons.add(buttonForward);

        Button buttonLeft = new Button("",createArrowImageView(270, Color.RED, 50));
        buttonLeft.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Go left");
            }
        });
        allButtons.add(buttonLeft);

        Button buttonRight = new Button("",createArrowImageView(90, Color.RED, 50));
        buttonRight.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Go right");
            }
        });
        allButtons.add(buttonRight);


        Button buttonSend = new Button("send");
        buttonSend.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Send");
            }
        });
        allButtons.add(buttonSend);

        Button buttonStop = new Button("stop");
        buttonStop.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("stopped");
            }
        });
        allButtons.add(buttonStop);

        Button buttonClawOpen = new Button("open\nclaw");
        buttonClawOpen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Gripper open");
            }
        });
        allButtons.add(buttonClawOpen);

        Button buttonClawClose = new Button("close\nclaw");
        buttonClawClose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Gripper closed");
            }
        });
        allButtons.add(buttonClawClose);

        for(Button button : allButtons){
            button.setPadding(new Insets(0));
            button.setPrefWidth(50);
            button.setPrefHeight(50);
        }


        GridPane gridPane = new GridPane();
        gridPane.add(buttonForward, 1, 0);
        gridPane.add(buttonLeft, 0, 1);
        gridPane.add(buttonRight, 2, 1);
        gridPane.add(buttonSend, 1, 1);
        gridPane.add(buttonStop, 1, 2);
        gridPane.add(buttonClawOpen, 0, 2);
        gridPane.add(buttonClawClose, 2, 2);


        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10));


        Scene scene = new Scene(gridPane);

        stage.setScene(scene);
        stage.show();
    }


    // Helper method to create a rotated arrow as an ImageView
    private ImageView createArrowImageView(double rotationAngle, Color color, int size) {
        //create new SVGPath
        SVGPath arrowPath = new SVGPath();
        //Set cursor to (0,100). draw  to (50,0). draw line to (100,100). draw a line to starting point closing the path
        arrowPath.setContent("M 0 100 L 50 0 L 100 100 Z");
        arrowPath.setFill(color);

        //arrowPath.getTransforms().add(new Rotate(rotationAngle, 0.5, 0.5)); // Rotate around the center
        ImageView image = new ImageView(svgPathToTransparantImage(arrowPath));
        image.setFitHeight(size);
        image.setFitWidth(size);
        image.setRotate(rotationAngle);
        return image;
    }

    private ImageView createArrowImageView(Color color, int size) {
        return createArrowImageView(0, color, size);
    }

    // Helper method to convert SVGPath to Image for use in ImageView
    private Image svgPathToTransparantImage(SVGPath svgPath) {
        //make a new image pane and add the SVGPath
        StackPane stackPane = new StackPane();
        stackPane.getChildren().add(svgPath);

        // Set a snapshot parameter to capture the SVGPath as an image with a transparent background
        SnapshotParameters parameters = new SnapshotParameters();
        parameters.setFill(Color.TRANSPARENT);

        return stackPane.snapshot(parameters, null);
    }

    

}
