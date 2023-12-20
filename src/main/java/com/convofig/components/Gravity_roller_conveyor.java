package com.convofig.components;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Gravity_roller_conveyor extends Label {

    private final String H1, H2, P;
    private String PolySide;
    private final double Width, Height;
    private final int preScaleWidth, preScaleHeight;
    private String title;
    private final String Component_name;
    private final String Type_code;
    private final String[] excelData;
    private int rotation = 0;
    private final double scaleFactor;
    private Text titleText;

    public Gravity_roller_conveyor(double scaleFactor, int preScaleWidth, int preScaleHeight, double Width, double Height, String H1, String H2, String P, String title) {
        super();
        this.scaleFactor = scaleFactor;
        this.H1 = H1;
        this.H2 = H2;
        this.P = P;
        this.title = title;
        this.Height = Height;
        this.Width = Width;
        this.preScaleHeight = preScaleHeight;
        this.preScaleWidth = preScaleWidth;
        Component_name = "Motorized roller conveyor";
        Type_code = "G5601";
        excelData = new String[22];
        Arrays.fill(excelData, "");
        createComponent();
        populateData();
    }

    public String getDataForSave() {
        return rotation + "," + scaleFactor + "," + preScaleWidth + "," + preScaleHeight + "," + Width + "," + Height + "," + H1 + "," + H2 + "," + P + "," + title;
    }

    public double getWidthForLoad() {
        return Width;
    }

    public double getHeightLoad() {
        return Height;
    }

    void populateData() {
        excelData[0] = title;
        excelData[1] = Type_code;
        excelData[2] = Component_name;
        excelData[3] = "1";
        excelData[4] = Integer.toString(preScaleWidth);
        excelData[5] = preScaleHeight + "+70";
        excelData[7] = H1;
        excelData[12] = P;
        if (Objects.equals(PolySide, "Left")) excelData[16] = "L";
        else excelData[16] = "R";
    }

    public String[] getDataForExcel() {
        return excelData;
    }

    public void modifyTitle(String newTitle) {
        title = newTitle;
        titleText.setText(newTitle);
    }

    public String getCurrentName() {
        return title;
    }

    private void createComponent() {
        Group group = new Group();

        Rectangle rectangle = new Rectangle(Width, Height);
        rectangle.fillProperty().set(null);
        rectangle.setStroke(Color.WHITE);
        group.getChildren().add(rectangle);

        Line topLine = new Line(0, 70 * scaleFactor, Width, 70 * scaleFactor);
        topLine.setStroke(Color.WHITE);
        group.getChildren().add(topLine);

        Line middleLine = new Line(0, Height / 2, Width, Height / 2);
        middleLine.setStroke(Color.RED);
        group.getChildren().add(middleLine);

        drawArrow(group, Height / 2);

        Line botLine = new Line(0, Height - 70 * scaleFactor, Width, Height - 70 * scaleFactor);
        botLine.setStroke(Color.WHITE);
        group.getChildren().add(botLine);

        group.getChildren().add(drawRectangle(30, 70 , 40, preScaleHeight-70));
        group.getChildren().add(drawRectangle(90, 70 , 40, preScaleHeight-70));

        titleText = new Text(title);
        titleText.setFill(Color.MAGENTA);
        titleText.setX(160 * scaleFactor); // Adjust the X position based on your requirement
        titleText.setY(Height / 2 - 30 * scaleFactor); // Adjust the Y position based on your requirement
        group.getChildren().add(titleText);

        Text textH = new Text("H1 = " + H1);
        textH.setFill(Color.LIGHTGREEN);
        textH.setStyle("-fx-font: 10 arial;");
        textH.relocate(160 * scaleFactor, Height / 2 + 10 * scaleFactor);
        group.getChildren().add(textH);

        Text textV = new Text("H2 = " + H2);
        textV.setFill(Color.LIGHTGREEN);
        textV.setStyle("-fx-font: 10 arial;");
        textV.relocate(160 * scaleFactor, Height / 2 + 45 * scaleFactor);
        group.getChildren().add(textV);

        Text textP = new Text("P = " + P);
        textP.setFill(Color.CYAN);
        textP.setStyle("-fx-font: 10 arial;");
        textP.relocate(160 * scaleFactor, Height / 2 + 80 * scaleFactor);
        group.getChildren().add(textP);

        setGraphic(group);
    }

    public Gravity_roller_conveyor copyComponent() {
        Gravity_roller_conveyor copy = new Gravity_roller_conveyor(scaleFactor, preScaleWidth, preScaleHeight, Width, Height, H1, H2, P, title);
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

    private void drawArrow(Group group, double y) {
        double x = 160 * scaleFactor;
        double[] arrowPoints = {
                x, y,
                x, y - 7 * scaleFactor,
                x + 60 * scaleFactor, y - 7 * scaleFactor,
                x + 60 * scaleFactor, y - 15 * scaleFactor,
                x + 120 * scaleFactor, y,
                x + 60 * scaleFactor, y + 15 * scaleFactor,
                x + 60 * scaleFactor, y + 7 * scaleFactor,
                x, y + 7 * scaleFactor
        };

        Polygon arrow = new Polygon(arrowPoints);
        arrow.setStroke(Color.YELLOW);
        arrow.setFill(Color.TRANSPARENT); // Set to transparent if you don't want to fill the arrow

        group.getChildren().add(arrow);
    }

    private Rectangle drawRectangle(double x1, double y1, double width, double height) {
        Rectangle rectangle = new Rectangle(x1 * scaleFactor, y1 * scaleFactor, width * scaleFactor, height * scaleFactor);
        rectangle.setStroke(Color.WHITE);
        rectangle.setFill(Color.TRANSPARENT);
        return rectangle;
    }

}
