package com.convofig.components;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import java.util.Arrays;

public class Motorized_roller_conveyor extends Label {

    private String H, V, P;
    private int No_MDR, side;
    private double Width, Height;
    private String title;
    private String Component_name, Type_code;
    private String[] excelData = new String[22];
    private int rotation = 0;
    private Group group;
    private Rectangle square;

    //public Motorized_roller_conveyor(String H, String V, String P, String title, int No_MDR, int side, int preScaleWidth, int preScaleHeight) {
    public Motorized_roller_conveyor(double Width, double Height) {
        super();
        this.H = H;
        this.V = V;
        this.P = P;
        this.title = title;
        this.No_MDR = No_MDR;
        this.side = side;
        this.Height = Height;
        this.Width = Width;
        Component_name = "Motorized roller conveyor";
        Type_code = "G5301A";
        excelData = new String[22];
        Arrays.fill(excelData, "");
        createComponent();

        square = new Rectangle(35, 35, Color.RED);
        square.setVisible(false);
        square.setFill(Color.TRANSPARENT);
        square.setStroke(Color.CYAN);
        square.getStrokeDashArray().addAll(5d);
        group.getChildren().add(square);
        setGraphic(group);

        // Adjust the layout to center the square
        widthProperty().addListener((observable, oldValue, newValue) -> square.setX((newValue.doubleValue() - square.getWidth()) / 2));
        heightProperty().addListener((observable, oldValue, newValue) -> square.setY((newValue.doubleValue() - square.getHeight()) / 2));
    }

    public void showSquare() {
        square.setVisible(true);
    }

    public void hideSquare() {
        square.setVisible(false);
    }

    void populateData() {
        excelData[0] = title;
        excelData[1] = Type_code;
        excelData[2] = Component_name;
        excelData[3] = "1";
        //excelData[4] = Integer.toString((int) preScaleWidth);
        //excelData[5] = Integer.toString((int) (preScaleHeight - 70));
        excelData[6] = V;
        excelData[7] = H;
        excelData[12] = P;
        excelData[15] = Integer.toString(No_MDR);
        if (side == 0) excelData[16] = "L";
        else excelData[16] = "R";
    }

    public String[] getDataForExcel() {
        return excelData;
    }

    private void createComponent() {

        Rectangle rectangle = new Rectangle(Width, Height);
        rectangle.fillProperty().set(null);
        rectangle.setStrokeWidth(2);
        rectangle.setStroke(Color.RED);

        Text text = new Text("MRC");
        text.setFill(Color.WHITE);
        text.setX(Width / 2 - 5); // Adjust the X position based on your requirement
        text.setY(Height / 2 - 10); // Adjust the Y position based on your requirement

        // Create a line in the middle of the rectangle
        Line line = new Line(0, Height / 2, Width, Height / 2);
        line.setStrokeWidth(2);
        line.setStroke(Color.BLUE);

        // Add both the rectangle and the line to a Group
        group = new Group(rectangle, line, text);
        setGraphic(group);
    }

    public Motorized_roller_conveyor copyComponent() {
        Motorized_roller_conveyor copy = new Motorized_roller_conveyor(Width, Height);
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
