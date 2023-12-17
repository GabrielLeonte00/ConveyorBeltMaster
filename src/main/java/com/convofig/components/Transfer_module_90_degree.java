package com.convofig.components;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Transfer_module_90_degree extends Label {
    private int rotation = 0;

    public Transfer_module_90_degree() {
        super();
        createComponent();
    }

    private void createComponent() {
        Rectangle rectangle = new Rectangle(120, 120);
        rectangle.fillProperty().set(null);
        rectangle.setStrokeWidth(2);
        rectangle.setStroke(Color.RED);

        Text text = new Text("TM90D");
        text.setFill(Color.WHITE);
        text.setX(55); // Adjust the X position based on your requirement
        text.setY(50); // Adjust the Y position based on your requirement

        // Create a line in the middle of the rectangle
        Line line = new Line(0, 60, 120, 60);
        line.setStrokeWidth(2);
        line.setStroke(Color.BLUE);

        // Add both the rectangle and the line to a Group
        setGraphic(new Group(rectangle, line, text));
    }

    public Transfer_module_90_degree copyComponent() {
        Transfer_module_90_degree copy = new Transfer_module_90_degree();
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
        getGraphic().setRotate(rotation);
    }
}
