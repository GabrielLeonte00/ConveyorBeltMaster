package com.convofig.components;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Objects;

public class Merge_conveyor extends Label {

    private String H;
    private String V;
    private String P;
    private final String No_MDR;
    private String PolySide;
    private final double Height;
    private double Width, lesserWidth;
    private final int preScaleHeight;
    private int preScaleWidth;
    private String title;
    private final String Component_name;
    private final String Type_code;
    private final String[] excelData;
    private int rotation = 0;
    private int revert = 1;
    private final double scaleFactor;
    private Text titleText, textH, textV, textP;
    private final String control;
    private Rectangle bounds;
    private Rectangle dragBounds;
    private Group group, textGroup;

    public Merge_conveyor(double scaleFactor, int preScaleHeight, double Height, String PolySide, String H, String V, String P, String title, String control) {
        super();
        this.scaleFactor = scaleFactor;
        this.H = H;
        this.V = V;
        this.P = P;
        this.title = title;
        this.PolySide = PolySide;
        this.No_MDR = "1";
        this.preScaleHeight = preScaleHeight;
        switch (preScaleHeight) {
            case 417:
                preScaleWidth = 988;
                lesserWidth = 205 * scaleFactor;
                Width = preScaleWidth * scaleFactor;
                break;
            case 517:
                preScaleWidth = 1169;
                lesserWidth = 213 * scaleFactor;
                Width = preScaleWidth * scaleFactor;
                break;
            case 617:
                preScaleWidth = 1349;
                lesserWidth = 219 * scaleFactor;
                Width = preScaleWidth * scaleFactor;
                break;
            case 717:
                preScaleWidth = 1529;
                lesserWidth = 226 * scaleFactor;
                Width = preScaleWidth * scaleFactor;
                break;
            case 817:
                preScaleWidth = 1709;
                lesserWidth = 233 * scaleFactor;
                Width = preScaleWidth * scaleFactor;
                break;
            case 917:
                preScaleWidth = 1889;
                lesserWidth = 240 * scaleFactor;
                Width = preScaleWidth * scaleFactor;
                break;
        }
        //
        this.Height = Height;
        this.control = control;
        Component_name = "Merge conveyor";
        Type_code = "G5304";
        excelData = new String[22];
        Arrays.fill(excelData, "");
        createComponent();
        populateData();
    }

    public String getDataForSave() {
        return rotation + "," + scaleFactor + "," + preScaleHeight + "," + Height + "," + PolySide + "," + H + "," + V + "," + P + "," + title + "," + control + "," + revert;
    }

    public double getWidthForLoad() {
        return Width;
    }

    public double getHeightLoad() {
        return Height;
    }

    public void mouseEnteredDragZone() {
        dragBounds.setVisible(true);
    }

    public void mouseExitedDragZone() {
        dragBounds.setVisible(false);
    }

    void populateData() {
        excelData[0] = title;
        excelData[1] = Type_code;
        excelData[2] = Component_name;
        excelData[3] = "1";
        excelData[4] = Integer.toString(preScaleWidth);
        excelData[5] = preScaleHeight + "+70";
        excelData[6] = V;
        excelData[7] = H;
        excelData[12] = P;
        excelData[13] = control;
        if (Objects.equals(control, "EQube AI") || Objects.equals(control, "EZQube"))
            excelData[14] = No_MDR;
        else if (Objects.equals(control, "Conveylinx-Eco") || Objects.equals(control, "Conveylinx AI2"))
            excelData[14] = No_MDR;
        excelData[15] = No_MDR;
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

    public void modifyH(String newValue) {
        H = newValue;
        textH.setText("H = " + newValue);
    }

    public void modifyV(String newValue) {
        V = newValue;
        textV.setText("V = " + newValue);
    }

    public void modifyP(String newValue) {
        P = newValue;
        textP.setText("P = " + newValue);
    }

    public void revertComponent() {
        revert = -revert;
        textGroup.setScaleX(revert);
        group.setScaleX(revert);
    }

    public void updateRevert(int revert) {
        this.revert = revert;
        textGroup.setScaleX(revert);
        group.setScaleX(revert);
    }

    public void changeSide() {
        group.getChildren().clear();
        if (Objects.equals(PolySide, "Left")) {
            PolySide = "Right";
        } else
            PolySide = "Left";
        createComponent();
        populateData();
        updateRotation();
    }

    public String getCurrentName() {
        return title;
    }

    public String getCurrentH() {
        return H;
    }

    public String getCurrentV() {
        return V;
    }

    public String getCurrentP() {
        return P;
    }

    private void createComponent() {
        group = new Group();

        bounds = new Rectangle(Width, Height);
        bounds.fillProperty().set(null);
        bounds.setStroke(Color.TRANSPARENT);
        group.getChildren().add(bounds);

        Line leftSide = new Line(0, 0, 0, Height);
        leftSide.setStroke(Color.WHITE);
        group.getChildren().add(leftSide);

        double lesserX = Width - (preScaleHeight * scaleFactor) * Math.sqrt(3);
        double lesserMiddleX = Width - ((double) preScaleHeight / 2 * scaleFactor) * Math.sqrt(3);

        if (Objects.equals(PolySide, "Left")) {
            Line topSide = new Line(0, 0, Width, 0);
            topSide.setStroke(Color.WHITE);
            group.getChildren().add(topSide);

            Line topLine = new Line(0, 35 * scaleFactor, Width, 35 * scaleFactor);
            topLine.setStroke(Color.WHITE);
            group.getChildren().add(topLine);

            Line bottomLine = new Line(0, Height - 35 * scaleFactor, lesserX, Height - 35 * scaleFactor);
            bottomLine.setStroke(Color.WHITE);
            group.getChildren().add(bottomLine);

            Line topRight = new Line(Width, 0, Width, 35 * scaleFactor);
            topRight.setStroke(Color.WHITE);
            group.getChildren().add(topRight);

            Line bottomSide = new Line(0, Height, lesserWidth, Height);
            bottomSide.setStroke(Color.WHITE);
            group.getChildren().add(bottomSide);

            Line rightSide = new Line(Width, 35 * scaleFactor, lesserWidth, Height);
            rightSide.setStroke(Color.WHITE);
            group.getChildren().add(rightSide);
        } else {
            Line topSide = new Line(0, 0, lesserWidth, 0);
            topSide.setStroke(Color.WHITE);
            group.getChildren().add(topSide);

            Line topLine = new Line(0, 35 * scaleFactor, lesserX, 35 * scaleFactor);
            topLine.setStroke(Color.WHITE);
            group.getChildren().add(topLine);

            Line bottomLine = new Line(0, Height - 35 * scaleFactor, Width, Height - 35 * scaleFactor);
            bottomLine.setStroke(Color.WHITE);
            group.getChildren().add(bottomLine);

            Line bottomRight = new Line(Width, Height - 35 * scaleFactor, Width, Height);
            bottomRight.setStroke(Color.WHITE);
            group.getChildren().add(bottomRight);

            Line bottomSide = new Line(0, Height, Width, Height);
            bottomSide.setStroke(Color.WHITE);
            group.getChildren().add(bottomSide);

            Line rightSide = new Line(lesserWidth, 0, Width, Height - 35 * scaleFactor);
            rightSide.setStroke(Color.WHITE);
            group.getChildren().add(rightSide);
        }

        drawBars(group, 35);
        drawBars(group, 115);

        double xPositionCircles = 100 * scaleFactor;
        double circleDistance = 20;
        drawSmallCircles(group, xPositionCircles / 2, (double) 35 / 2 * scaleFactor, circleDistance);
        drawSmallCircles(group, xPositionCircles / 2, Height - (double) 35 / 2 * scaleFactor, circleDistance);

        Line middleLine = new Line(0, Height / 2, lesserMiddleX, Height / 2);
        middleLine.setStroke(Color.RED);
        group.getChildren().add(middleLine);

        textGroup = new Group();
        titleText = new Text(title);
        titleText.setFill(Color.MAGENTA);
        titleText.setStyle("-fx-font: 16 arial;");
        titleText.setX(165 * scaleFactor); // Adjust the X position based on your requirement
        titleText.setY(Height / 2 - 30 * scaleFactor); // Adjust the Y position based on your requirement
        textGroup.getChildren().add(titleText);

        textH = new Text("H = " + H);
        textH.setFill(Color.LIGHTGREEN);
        textH.setStyle("-fx-font: 12 arial;");
        textH.relocate(165 * scaleFactor, Height / 2 + 20 * scaleFactor);
        textGroup.getChildren().add(textH);

        textV = new Text("V = " + V);
        textV.setFill(Color.DEEPSKYBLUE);
        textV.setStyle("-fx-font: 12 arial;");
        textV.relocate(165 * scaleFactor, Height / 2 + 55 * scaleFactor);
        textGroup.getChildren().add(textV);

        textP = new Text("P = " + P);
        textP.setFill(Color.CYAN);
        textP.setStyle("-fx-font: 12 arial;");
        textP.relocate(165 * scaleFactor, Height / 2 + 90 * scaleFactor);
        textGroup.getChildren().add(textP);
        group.getChildren().add(textGroup);

        dragBounds = new Rectangle(Width / 2 - 100 * scaleFactor, Height / 2 - 100 * scaleFactor, 200 * scaleFactor, 200 * scaleFactor);
        dragBounds.setFill(Color.rgb(127, 255, 212, 0.25));
        dragBounds.setStroke(Color.rgb(127, 255, 212, 0.5));
        dragBounds.setVisible(false);
        group.getChildren().add(dragBounds);

        textGroup.setScaleX(revert);
        group.setScaleX(revert);
        setGraphic(group);
    }

    public Merge_conveyor copyComponent() {
        Merge_conveyor copy = new Merge_conveyor(scaleFactor, preScaleHeight, Height, PolySide, H, V, P, title, control);
        copy.setLayoutX(getLayoutX() + getWidth() / 2 + 25); // Example: Adjust the layout for the copy
        copy.setLayoutY(getLayoutY() + getHeight() / 2 + 25);
        copy.setNewRotation(rotation);
        copy.updateRevert(revert);
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

    private void drawSmallCircles(Group group, double x, double y, double distance) {
        double positionX = x - distance * scaleFactor;
        for (int i = 0; i < 3; i++) {
            Circle circle = new Circle(positionX, y, 5 * scaleFactor, Color.WHITE);
            circle.setFill(null);
            positionX = positionX + distance * scaleFactor;
            circle.setStroke(Color.WHITE); // Set the border color for the small circles
            group.getChildren().add(circle);
        }
    }


    private void drawBars(Group group, double middleX) {
        if (Objects.equals(PolySide, "Left")) {
            group.getChildren().add(drawRectangle(middleX - 15, 35, widthX(middleX, middleX - 15), 10)); //y = 35+10
            group.getChildren().add(drawRectangle(middleX - 25, 45, widthX(middleX, middleX - 25), 50)); //y = 45+50
            group.getChildren().add(drawRectangle(middleX - 30, 95, widthX(middleX, middleX - 30), preScaleHeight - 80));
            double nextY = 95 + preScaleHeight - 80;
            group.getChildren().add((drawRectangle(middleX - 25, nextY, widthX(middleX, middleX - 25), 10)));
            group.getChildren().add(drawRectangle(middleX - 20, nextY + 10, widthX(middleX, middleX - 20), 10));
        } else {
            group.getChildren().add(drawRectangle(middleX - 20, 35, widthX(middleX, middleX - 20), 10)); //y = 35 + 10
            group.getChildren().add((drawRectangle(middleX - 25, 45, widthX(middleX, middleX - 25), 10))); //y = 45 + 10
            group.getChildren().add(drawRectangle(middleX - 30, 55, widthX(middleX, middleX - 30), preScaleHeight - 80));
            double nextY = 55 + preScaleHeight - 80;
            group.getChildren().add(drawRectangle(middleX - 25, nextY, widthX(middleX, middleX - 25), 50));
            group.getChildren().add(drawRectangle(middleX - 15, nextY + 50, widthX(middleX, middleX - 15), 10));
        }
    }

    private double widthX(double middleX, double x) {
        return (middleX - x) * 2;
    }

    private Rectangle drawRectangle(double x1, double y1, double width, double height) {
        Rectangle rectangle = new Rectangle(x1 * scaleFactor, y1 * scaleFactor, width * scaleFactor, height * scaleFactor);
        rectangle.setStroke(Color.WHITE);
        rectangle.setFill(Color.TRANSPARENT);
        return rectangle;
    }

}
