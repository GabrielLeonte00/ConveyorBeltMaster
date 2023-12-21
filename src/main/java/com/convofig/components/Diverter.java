package com.convofig.components;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;


public class Diverter extends Label {

    private int rotation = 0;
    private Rectangle bounds;
    private Rectangle dragBounds;
    private Group group;

    public Diverter() {
        super();
        createComponent();
    }

    public String getDataForSave() {
        return String.valueOf(rotation);
    }

    public double getWidthForLoad() {
        return 120;
    }

    public double getHeightLoad() {
        return 120;
    }

    public void mouseEnteredDragZone() {
        dragBounds.setVisible(true);
    }

    public void mouseExitedDragZone() {
        dragBounds.setVisible(false);
    }

    private void createComponent() {

        group = new Group();

        double Width = 120;
        double Height = 120;
        double scaleFactor = 0.4;

        bounds = new Rectangle(Width, Height);
        bounds.fillProperty().set(null);
        bounds.setStroke(Color.TRANSPARENT);
        group.getChildren().add(bounds);

        Rectangle rectangle = new Rectangle(Width, Height);
        rectangle.fillProperty().set(null);
        rectangle.setStrokeWidth(2);
        rectangle.setStroke(Color.RED);
        group.getChildren().add(rectangle);

        Text text = new Text("D");
        text.setFill(Color.WHITE);
        text.setX(55); // Adjust the X position based on your requirement
        text.setY(50); // Adjust the Y position based on your requirement
        group.getChildren().add(text);

        // Create a line in the middle of the rectangle
        Line line = new Line(0, 60, 120, 60);
        line.setStrokeWidth(2);
        line.setStroke(Color.BLUE);
        group.getChildren().add(line);

        dragBounds = new Rectangle(Width / 2 - 100 * scaleFactor, Height / 2 - 100 * scaleFactor, 200 * scaleFactor, 200 * scaleFactor);
        dragBounds.setFill(Color.rgb(127, 255, 212, 0.25));
        dragBounds.setStroke(Color.rgb(127, 255, 212, 0.5));
        dragBounds.setVisible(false);
        group.getChildren().add(dragBounds);

        // Add both the rectangle and the line to a Group
        setGraphic(group);
    }


    public Diverter copyComponent() {
        Diverter copy = new Diverter();
        copy.setLayoutX(getLayoutX() + getWidth() / 2 + 25); // Example: Adjust the layout for the copy
        copy.setLayoutY(getLayoutY() + getHeight() / 2 + 25);
        copy.setNewRotation(rotation);

        return copy;
    }

    public void setNewRotation(int rotation) {
        this.rotation = (this.rotation + rotation) % 360;
        updateRotation();
    }

    public void resetRotation() {
        rotation = 0;
        updateRotation();
    }

    private void updateRotation() {
        bounds.setRotate(rotation);
        group.setRotate(rotation);
        dragBounds.setRotate(-rotation);
    }

}
