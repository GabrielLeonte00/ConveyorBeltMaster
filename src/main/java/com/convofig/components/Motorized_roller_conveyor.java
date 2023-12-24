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

public class Motorized_roller_conveyor extends Label {

    private String H;
    private String V;
    private String P;
    private final String No_MDR;
    private String PolySide;
    private final double Width, Height;
    private final int preScaleWidth, preScaleHeight;
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

    public Motorized_roller_conveyor(double scaleFactor, int preScaleWidth, int preScaleHeight, double Width, double Height, String PolySide, String No_MDR, String H, String V, String P, String title, String control) {
        super();
        this.scaleFactor = scaleFactor;
        this.H = H;
        this.V = V;
        this.P = P;
        this.title = title;
        this.PolySide = PolySide;
        this.No_MDR = No_MDR;
        this.Height = Height;
        this.Width = Width;
        this.control = control;
        this.preScaleHeight = preScaleHeight;
        this.preScaleWidth = preScaleWidth;
        Component_name = "Motorized roller conveyor";
        Type_code = "G5301A";
        excelData = new String[22];
        Arrays.fill(excelData, "");
        createComponent();
        populateData();
    }

    public String getDataForSave() {
        return rotation + "," + scaleFactor + "," + preScaleWidth + "," + preScaleHeight + "," + Width + "," + Height + "," + PolySide + "," + No_MDR + "," + H + "," + V + "," + P + "," + title + "," + control + "," + revert;
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
            excelData[14] = String.valueOf((Integer.parseInt(No_MDR) + 1) / 2);
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

        Rectangle rectangle = new Rectangle(Width, Height);
        rectangle.fillProperty().set(null);
        rectangle.setStroke(Color.WHITE);
        group.getChildren().add(rectangle);

        Line topLine = new Line(0, 35 * scaleFactor, Width, 35 * scaleFactor);
        topLine.setStroke(Color.WHITE);
        group.getChildren().add(topLine);

        Line botLine = new Line(0, Height - 35 * scaleFactor, Width, Height - 35 * scaleFactor);
        botLine.setStroke(Color.WHITE);
        group.getChildren().add(botLine);

        drawArrow(group, Height / 2);

        drawBars(group, 50);
        drawBars(group, preScaleWidth - 50);
        drawRollers(group);

        double xPositionCircles = 100 * scaleFactor;
        double circleDistance = 20;
        drawSmallCircles(group, xPositionCircles / 2, (double) 35 / 2 * scaleFactor, circleDistance);
        drawSmallCircles(group, Width - xPositionCircles / 2, (double) 35 / 2 * scaleFactor, circleDistance);
        drawSmallCircles(group, xPositionCircles / 2, Height - (double) 35 / 2 * scaleFactor, circleDistance);
        drawSmallCircles(group, Width - xPositionCircles / 2, Height - (double) 35 / 2 * scaleFactor, circleDistance);

        Line middleLine = new Line(0, Height / 2, Width, Height / 2);
        middleLine.setStroke(Color.RED);
        group.getChildren().add(middleLine);

        textGroup = new Group();
        titleText = new Text(title);
        titleText.setFill(Color.MAGENTA);
        titleText.setStyle("-fx-font: 16 arial;");
        titleText.setX(120 * scaleFactor); // Adjust the X position based on your requirement
        titleText.setY(Height / 2 - 30 * scaleFactor); // Adjust the Y position based on your requirement
        textGroup.getChildren().add(titleText);

        textH = new Text("H = " + H);
        textH.setFill(Color.LIGHTGREEN);
        textH.setStyle("-fx-font: 12 arial;");
        textH.relocate(120 * scaleFactor, Height / 2 + 20 * scaleFactor);
        textGroup.getChildren().add(textH);

        textV = new Text("V = " + V);
        textV.setFill(Color.DEEPSKYBLUE);
        textV.setStyle("-fx-font: 12 arial;");
        textV.relocate(120 * scaleFactor, Height / 2 + 55 * scaleFactor);
        textGroup.getChildren().add(textV);

        textP = new Text("P = " + P);
        textP.setFill(Color.CYAN);
        textP.setStyle("-fx-font: 12 arial;");
        textP.relocate(120 * scaleFactor, Height / 2 + 90 * scaleFactor);
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

    public Motorized_roller_conveyor copyComponent() {
        Motorized_roller_conveyor copy = new Motorized_roller_conveyor(scaleFactor, preScaleWidth, preScaleHeight, Width, Height, PolySide, No_MDR, H, V, P, title, control);
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

    public void resetComponent() {
        rotation = 0;
        updateRotation();
        updateRevert(1);
    }

    private void updateRotation() {
        bounds.setRotate(rotation);
        group.setRotate(rotation);
        dragBounds.setRotate(-rotation);
    }

    public void drawRollers(Group group) {
        int NO_rollers = Integer.parseInt(No_MDR);
        double yPosition = 0;
        if (Objects.equals(PolySide, "Left")) {
            yPosition = 35 * scaleFactor + 15 * scaleFactor;
        } else {
            yPosition = Height - 35 * scaleFactor - 15 * scaleFactor;
        }
        switch (NO_rollers) {
            case 1:
                drawRoller(group, Width / 3, yPosition);
                break;
            case 2:
                drawRoller(group, Width / 3, yPosition);
                drawRoller(group, 2 * Width / 3, yPosition);
                break;
            case 3:
                drawRoller(group, Width / 3, yPosition);
                drawRoller(group, Width / 2, yPosition);
                drawRoller(group, 2 * Width / 3, yPosition);
                break;
            case 4:
                drawRoller(group, Width / 4 + 5, yPosition);
                drawRoller(group, 3 * Width / 7, yPosition);
                drawRoller(group, 4 * Width / 7, yPosition);
                drawRoller(group, 3 * Width / 4 - 5, yPosition);

                break;
        }
    }

    public void drawRoller(Group group, double x, double y) {
        double radius = 15 * scaleFactor;
        Circle circle = new Circle(x, y, radius);
        circle.setStroke(Color.WHITE);
        circle.setFill(Color.TRANSPARENT);
        group.getChildren().add(circle);

        Arc arc1 = new Arc(x, y, radius, radius, 25, 90);
        arc1.setType(ArcType.ROUND);
        arc1.setFill(Color.WHITE);
        group.getChildren().add(arc1);

        Arc arc2 = new Arc(x, y, radius, radius, 205, 90);
        arc2.setType(ArcType.ROUND);
        arc2.setFill(Color.WHITE);
        group.getChildren().add(arc2);
    }

    private void drawArrow(Group group, double y) {
        double x = 120 * scaleFactor;
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
            drawZigZag(group, middleX - 25, 45, middleX - 25 + widthX(middleX, middleX - 25), 95);
            group.getChildren().add(drawRectangle(middleX - 30, 95, widthX(middleX, middleX - 30), preScaleHeight - 80));
            double nextY = 95 + preScaleHeight - 80;
            group.getChildren().add((drawRectangle(middleX - 25, nextY, widthX(middleX, middleX - 25), 10)));
            group.getChildren().add(drawRectangle(middleX - 20, nextY + 10, widthX(middleX, middleX - 20), 10)); //y = 70 + 10
        } else {
            group.getChildren().add(drawRectangle(middleX - 20, 35, widthX(middleX, middleX - 20), 10)); //y = 35 + 10
            group.getChildren().add((drawRectangle(middleX - 25, 45, widthX(middleX, middleX - 25), 10))); //y = 45 + 10
            group.getChildren().add(drawRectangle(middleX - 30, 55, widthX(middleX, middleX - 30), preScaleHeight - 80));
            double nextY = 55 + preScaleHeight - 80;
            group.getChildren().add(drawRectangle(middleX - 25, nextY, widthX(middleX, middleX - 25), 50));
            drawZigZag(group, middleX - 25, nextY, middleX - 25 + widthX(middleX, middleX - 25), nextY + 50);
            group.getChildren().add(drawRectangle(middleX - 15, nextY + 50, widthX(middleX, middleX - 15), 10));
        }
    }

    private void drawZigZag(Group group, double x1, double y1, double x2, double y2) {
        drawLine(group, x1, y1, x1 + 10, y1 + (double) 50 / 4);
        drawLine(group, x1, y1 + (double) 50 / 3, x1 + 10, y1 + (double) 50 / 4);
        drawLine(group, x1, y1 + (double) 50 / 3, x1 + 10, y1 + (double) 50 / 2);
        drawLine(group, x1, y1 + (double) 2 * 50 / 3, x1 + 10, y1 + (double) 50 / 2);
        drawLine(group, x1, y1 + (double) 2 * 50 / 3, x1 + 10, y1 + (double) 3 * 50 / 4);
        drawLine(group, x1, y2, x1 + 10, y1 + (double) 3 * 50 / 4);

        drawLine(group, x2 - 10, y1 + (double) 50 / 4, x2, y1);
        drawLine(group, x2 - 10, y1 + (double) 50 / 4, x2, y1 + (double) 50 / 3);
        drawLine(group, x2 - 10, y1 + (double) 50 / 2, x2, y1 + (double) 50 / 3);
        drawLine(group, x2 - 10, y1 + (double) 50 / 2, x2, y1 + (double) 2 * 50 / 3);
        drawLine(group, x2 - 10, y1 + (double) 3 * 50 / 4, x2, y1 + (double) 2 * 50 / 3);
        drawLine(group, x2 - 10, y1 + (double) 3 * 50 / 4, x2, y2);
    }

    private void drawLine(Group group, double x1, double y1, double x2, double y2) {
        Line zigzagLine = new Line(x1 * scaleFactor, y1 * scaleFactor, x2 * scaleFactor, y2 * scaleFactor);
        zigzagLine.setStroke(Color.WHITE);
        zigzagLine.setStrokeWidth(1);
        group.getChildren().add(zigzagLine);
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
