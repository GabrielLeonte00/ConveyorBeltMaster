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

public class Diverter extends Label {

    private final String H, V, P;
    private String PolySide;
    private final double Width, Height;
    private final int preScaleWidth, preScaleHeight;
    private double newWidth;
    private double newHeight;
    private String title;
    private final String Component_name;
    private final String Type_code;
    private final String[] excelData;
    private int rotation = 0;
    private final double scaleFactor;
    private Text titleText;
    private Rectangle bounds;
    private Rectangle dragBounds;
    private Group group;

    public Diverter(double scaleFactor, int preScaleWidth, int preScaleHeight, double Width, double Height, String PolySide, String H, String V, String P, String title) {
        super();
        this.scaleFactor = scaleFactor;
        this.H = H;
        this.V = V;
        this.P = P;
        this.title = title;
        this.PolySide = PolySide;
        this.Height = Height;
        this.Width = Width;
        this.preScaleHeight = preScaleHeight;
        this.preScaleWidth = preScaleWidth;

        Component_name = "Diverter";
        Type_code = "ST01";
        excelData = new String[22];
        Arrays.fill(excelData, "");
        createComponent();
        populateData();
    }

    public String getDataForSave() {
        return rotation + "," + scaleFactor + "," + preScaleWidth + "," + preScaleHeight + "," + Width + "," + Height + "," + PolySide + "," + H + "," + V + "," + P + "," + title;
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

    private void createComponent() {
        group = new Group();
        Group groupDiverter = new Group();

        Rectangle rectangle = new Rectangle(Width, Height);
        rectangle.fillProperty().set(null);
        rectangle.setStroke(Color.WHITE);
        groupDiverter.getChildren().add(rectangle);

        Line topLine = new Line(0, 35 * scaleFactor, Width, 35 * scaleFactor);
        topLine.setStroke(Color.WHITE);
        groupDiverter.getChildren().add(topLine);

        Line botLine = new Line(0, Height - 35 * scaleFactor, Width, Height - 35 * scaleFactor);
        botLine.setStroke(Color.WHITE);
        groupDiverter.getChildren().add(botLine);

        Rectangle firstBar = new Rectangle(5 * scaleFactor, 35 * scaleFactor, 40 * scaleFactor, Height - 70 * scaleFactor);
        firstBar.fillProperty().set(null);
        firstBar.setStroke(Color.WHITE);
        groupDiverter.getChildren().add(firstBar);

        Rectangle secondBar = new Rectangle(Width - 5 * scaleFactor - 40 * scaleFactor, 35 * scaleFactor, 40 * scaleFactor, Height - 70 * scaleFactor);
        secondBar.fillProperty().set(null);
        secondBar.setStroke(Color.WHITE);
        groupDiverter.getChildren().add(secondBar);

        Rectangle thirdBar = new Rectangle(Width - 20 * scaleFactor - 80 * scaleFactor, 35 * scaleFactor, 40 * scaleFactor, Height - 70 * scaleFactor);
        thirdBar.fillProperty().set(null);
        thirdBar.setStroke(Color.WHITE);
        groupDiverter.getChildren().add(thirdBar);

        prepForCircles(groupDiverter);

        drawArrow(groupDiverter, Height / 2);

        Line middleLine = new Line(0, Height / 2, Width, Height / 2);
        middleLine.setStroke(Color.RED);
        groupDiverter.getChildren().add(middleLine);

        titleText = new Text(title);
        titleText.setFill(Color.MAGENTA);
        titleText.setStyle("-fx-font: 16 arial;");
        titleText.setX(100 * scaleFactor); // Adjust the X position based on your requirement
        titleText.setY(Height / 2 - 30 * scaleFactor); // Adjust the Y position based on your requirement
        groupDiverter.getChildren().add(titleText);

        Text textH = new Text("H = " + H);
        textH.setFill(Color.LIGHTGREEN);
        textH.setStyle("-fx-font: 10 arial;");
        textH.relocate(100 * scaleFactor, Height / 2 + 10 * scaleFactor);
        groupDiverter.getChildren().add(textH);

        Text textV = new Text("V = " + V);
        textV.setFill(Color.BLUE);
        textV.setStyle("-fx-font: 10 arial;");
        textV.relocate(100 * scaleFactor, Height / 2 + 45 * scaleFactor);
        groupDiverter.getChildren().add(textV);

        Text textP = new Text("P = " + P);
        textP.setFill(Color.CYAN);
        textP.setStyle("-fx-font: 10 arial;");
        textP.relocate(100 * scaleFactor, Height / 2 + 80 * scaleFactor);
        //groupDiverter.getChildren().add(textP);

        group.getChildren().add(groupDiverter);

        Group groupTerminalBox = new Group();

        Rectangle terminalBox = new Rectangle(380 * scaleFactor, 180 * scaleFactor);
        terminalBox.fillProperty().set(null);
        terminalBox.setStroke(Color.WHITE);
        groupTerminalBox.getChildren().add(terminalBox);

        Text tBox = new Text("T B");
        tBox.setFill(Color.WHITE);
        tBox.setStyle("-fx-font: 40 serif;");
        tBox.relocate(180 * scaleFactor - 60 * scaleFactor, 90 * scaleFactor);
        groupTerminalBox.getChildren().add(tBox);
        group.getChildren().add(groupTerminalBox);

        if (Objects.equals(PolySide, "Left")) {
            groupTerminalBox.relocate(0, 0);
            groupDiverter.relocate(0, 220 * scaleFactor);
        } else {
            groupTerminalBox.relocate(0, Height + 40 * scaleFactor);
            groupDiverter.relocate(0, 0);
        }

        newWidth = Width + 380 * scaleFactor;
        newHeight = Height + 220 * scaleFactor;

        bounds = new Rectangle(newWidth, newHeight);
        bounds.fillProperty().set(null);
        bounds.setStroke(Color.TRANSPARENT);
        group.getChildren().add(bounds);

        dragBounds = new Rectangle(newWidth / 2 - 100 * scaleFactor, newHeight / 2 - 100 * scaleFactor, 200 * scaleFactor, 200 * scaleFactor);
        dragBounds.setFill(Color.rgb(127, 255, 212, 0.25));
        dragBounds.setStroke(Color.rgb(127, 255, 212, 0.5));
        dragBounds.setVisible(false);
        group.getChildren().add(dragBounds);


        setGraphic(group);
    }

    public Diverter copyComponent() {
        Diverter copy = new Diverter(scaleFactor, preScaleWidth, preScaleHeight, Width, Height, PolySide, H, V, P, title);
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

    private void drawArrow(Group group, double y) {
        double x = 100 * scaleFactor;
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

    private void drawCircles(Group group, double startX, double endX, double radius, double distanceX, double distanceY) {
        double redLinePosX = startX + 3 * radius;
        Line redLine = new Line(redLinePosX, 35 * scaleFactor, redLinePosX, Height - 35 * scaleFactor);
        redLine.setStroke(Color.RED);
        group.getChildren().add(redLine);

        Color whiteTransparent = Color.rgb(255, 255, 255, 0.60);

        for (double y = Height / 2 - radius * 2 - distanceY; y > 35 * scaleFactor + distanceY + radius; y -= 2 * radius + distanceY) {
            for (double x = startX + radius + distanceX; x < endX - radius - distanceX; x += distanceX + radius * 2) {
                Circle circle = new Circle(x, y, radius);
                circle.setFill(null);
                circle.setStroke(whiteTransparent); // Set the border color for the small circles
                group.getChildren().add(circle);
            }
        }

        for (double x = startX + radius + distanceX; x < endX - radius - distanceX; x += distanceX + radius * 2) {
            Circle circle = new Circle(x, Height / 2, radius);
            circle.setFill(null);
            circle.setStroke(whiteTransparent); // Set the border color for the small circles
            group.getChildren().add(circle);
        }

        for (double y = Height / 2 + radius * 2 + distanceY; y < Height - 35 * scaleFactor - distanceY - radius; y += 2 * radius + distanceY) {
            for (double x = startX + radius + distanceX; x < endX - radius - distanceX; x += distanceX + radius * 2) {
                Circle circle = new Circle(x, y, radius);
                circle.setFill(null);
                circle.setStroke(whiteTransparent); // Set the border color for the small circles
                group.getChildren().add(circle);
            }
        }
    }

    private void prepForCircles(Group group) {
        double startX = 50 * scaleFactor;
        double endX = Width - 100 * scaleFactor;
        double radius = 0;
        double distanceY = 0;
        switch (preScaleHeight) {
            case 417:
                distanceY = 17 * scaleFactor;
                break;
            case 517:
                distanceY = 12 * scaleFactor;
                break;
            case 617:
                distanceY = 20 * scaleFactor;
                break;
            case 717:
                distanceY = 16 * scaleFactor;
                break;
            case 817:
                distanceY = 25 * scaleFactor;
                break;
            case 917:
                distanceY = 21 * scaleFactor;
                break;
        }

        switch (preScaleWidth) {
            case 500:
                radius = 30 * scaleFactor;
                break;
            case 600:
                radius = 32.5 * scaleFactor;
                distanceY = distanceY - 5 * scaleFactor;
                break;
            case 720:
                radius = 35 * scaleFactor;
                distanceY = distanceY - 10 * scaleFactor;
                break;
        }

        drawCircles(group, startX, endX, radius, 20 * scaleFactor, distanceY);

    }
}
