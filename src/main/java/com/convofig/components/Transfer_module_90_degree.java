package com.convofig.components;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Objects;

public class Transfer_module_90_degree extends Label {

    private String H;
    private String V1;
    private String V2;
    private final String P;
    private final double Width, Height;
    private final int preScaleWidth, preScaleHeight;
    private String title;
    private final String Component_name;
    private final String Type_code;
    private final String[] excelData;
    private int rotation = 0;
    private int mirror = 1;
    private Rectangle bounds;
    private Rectangle dragBounds;
    private Group group, textGroup;
    private final double scaleFactor;
    private Text titleText, textH, textV1, textV2;
    private final String control;

    public Transfer_module_90_degree(double scaleFactor, int preScaleWidth, int preScaleHeight, double Width, double Height, String H, String V1, String V2, String P, String title, String control) {
        super();
        this.scaleFactor = scaleFactor;
        this.H = H;
        this.V1 = V1;
        this.V2 = V2;
        this.P = P;
        this.title = title;
        this.Height = Height;
        this.Width = Width;
        this.control = control;
        this.preScaleHeight = preScaleHeight;
        this.preScaleWidth = preScaleWidth;
        Component_name = "90 degree transfer module";
        Type_code = "1TC05";
        excelData = new String[22];
        createComponent();
        populateData();
    }

    public String getDataForSave() {
        return rotation + "," + scaleFactor + "," + preScaleWidth + "," + preScaleHeight + "," + Width + "," + Height + "," + H + "," + V1 + "," + V2 + "," + P + "," + title + "," + control + "," + mirror;
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
        Arrays.fill(excelData, "");
        excelData[0] = title;
        excelData[1] = Type_code;
        excelData[2] = Component_name;
        excelData[3] = "1";
        excelData[4] = Integer.toString(preScaleWidth);
        excelData[5] = Integer.toString(preScaleHeight);
        excelData[6] = V1;
        excelData[7] = H;
        excelData[13] = control;
        if (Objects.equals(control, "EQube AI") || Objects.equals(control, "EZQube"))
            excelData[14] = "3";
        else if (Objects.equals(control, "Conveylinx-Eco") || Objects.equals(control, "Conveylinx AI2"))
            excelData[14] = "2";
        excelData[15] = "3";
    }

    public String[] getDataForExcel() {
        return excelData;
    }

    public void modifyTitle(String newValue) {
        title = newValue;
        titleText.setText(newValue);
        populateData();
    }

    public void modifyH(String newValue) {
        H = newValue;
        textH.setText("H = " + newValue);
        populateData();
    }

    public void modifyV(int NO, String newValue) {
        if (NO == 1) {
            V1 = newValue;
            textV1.setText("V1 = " + newValue);
        } else {
            V2 = newValue;
            textV2.setText("V2 = " + newValue);
        }
        populateData();
    }

    public void mirrorText() {
        mirror = -mirror;
        textGroup.setScaleX(mirror);
        titleText.setScaleY(mirror);
        textH.setScaleY(mirror);
        textV1.setScaleY(mirror);
        textV2.setScaleY(mirror);
    }

    public void updateMirrorText(int mirror) {
        this.mirror = mirror;
        textGroup.setScaleX(mirror);
        titleText.setScaleY(mirror);
        textH.setScaleY(mirror);
        textV1.setScaleY(mirror);
        textV2.setScaleY(mirror);
    }


    public String getCurrentName() {
        return title;
    }

    public String getCurrentH() {
        return H;
    }

    public String getCurrentV(int NO) {
        if (NO == 1) {
            return V1;
        } else {
            return V2;
        }
    }

    private void createComponent() {

        group = new Group();
        Group componentGroup = new Group();

        bounds = new Rectangle(Width, Height);
        bounds.fillProperty().set(null);
        bounds.setStroke(Color.TRANSPARENT);
        group.getChildren().add(bounds);

        Rectangle rectangle = new Rectangle(Width, Height);
        rectangle.fillProperty().set(null);
        rectangle.setStroke(Color.WHITE);
        componentGroup.getChildren().add(rectangle);

        Line topLine = new Line(0, 35 * scaleFactor, Width, 35 * scaleFactor);
        topLine.setStroke(Color.WHITE);
        componentGroup.getChildren().add(topLine);

        drawBars(componentGroup, (35 + 50) * scaleFactor);

        Rectangle topRectangle = new Rectangle(45 * scaleFactor, (35 + 90) * scaleFactor, Width - 105 * scaleFactor, 20 * scaleFactor);
        topRectangle.fillProperty().set(null);
        topRectangle.setStroke(Color.WHITE);
        componentGroup.getChildren().add(topRectangle);

        Line middleLine = new Line(0, Height / 2, Width, Height / 2);
        middleLine.setStroke(Color.RED);
        componentGroup.getChildren().add(middleLine);

        Rectangle botRectangle = new Rectangle(45 * scaleFactor, Height - (35 + 90 + 20) * scaleFactor, Width - 105 * scaleFactor, 20 * scaleFactor);
        botRectangle.fillProperty().set(null);
        botRectangle.setStroke(Color.WHITE);
        componentGroup.getChildren().add(botRectangle);

        drawBars(componentGroup, Height - (35 + 50) * scaleFactor);

        Line botLine = new Line(0, Height - 35 * scaleFactor, Width, Height - 35 * scaleFactor);
        botLine.setStroke(Color.WHITE);
        componentGroup.getChildren().add(botLine);

        Line leftSide = new Line(25 * scaleFactor, 35 * scaleFactor, 25 * scaleFactor, Height - 35 * scaleFactor);
        leftSide.setStroke(Color.WHITE);
        componentGroup.getChildren().add(leftSide);

        Line rightSide = new Line(Width - 25 * scaleFactor, 35 * scaleFactor, Width - 25 * scaleFactor, Height - 35 * scaleFactor);
        rightSide.setStroke(Color.WHITE);
        componentGroup.getChildren().add(rightSide);

        textGroup = new Group();
        titleText = new Text(title);
        titleText.setFill(Color.MAGENTA);
        titleText.setStyle("-fx-font: 16 arial;");
        titleText.setX(120 * scaleFactor); // Adjust the X position based on your requirement
        titleText.setY(Height / 2 - 15 * scaleFactor); // Adjust the Y position based on your requirement
        textGroup.getChildren().add(titleText);

        textH = new Text("H = " + H);
        textH.setFill(Color.LIGHTGREEN);
        textH.setStyle("-fx-font: 12 arial;");
        textH.relocate(120 * scaleFactor, Height / 2 + 20 * scaleFactor);
        textGroup.getChildren().add(textH);

        textV1 = new Text("V1 = " + V1);
        textV1.setFill(Color.DEEPSKYBLUE);
        textV1.setStyle("-fx-font: 12 arial;");
        textV1.relocate((120 + 150) * scaleFactor, Height / 2 + 20 * scaleFactor);
        textGroup.getChildren().add(textV1);

        textV2 = new Text("V2 = " + V2);
        textV2.setFill(Color.DEEPSKYBLUE);
        textV2.setStyle("-fx-font: 12 arial;");
        textV2.relocate((120 + 300) * scaleFactor, Height / 2 + 20 * scaleFactor);
        textGroup.getChildren().add(textV2);
        group.getChildren().add(textGroup);

        dragBounds = new Rectangle(Width / 2 - 100 * scaleFactor, Height / 2 - 100 * scaleFactor, 200 * scaleFactor, 200 * scaleFactor);
        dragBounds.setFill(Color.rgb(127, 255, 212, 0.25));
        dragBounds.setStroke(Color.rgb(127, 255, 212, 0.5));
        dragBounds.setVisible(false);
        group.getChildren().add(dragBounds);

        group.getChildren().add(componentGroup);
        setGraphic(group);
    }

    public Transfer_module_90_degree copyComponent() {
        Transfer_module_90_degree copy = new Transfer_module_90_degree(scaleFactor, preScaleWidth, preScaleHeight, Width, Height, H, V1, V2, P, title, control);
        copy.setLayoutX(getLayoutX() + getWidth() / 2 + 25); // Example: Adjust the layout for the copy
        copy.setLayoutY(getLayoutY() + getHeight() / 2 + 25);
        copy.setNewRotation(rotation);
        copy.updateMirrorText(mirror);
        return copy;
    }

    public void setNewRotation(int rotation) {
        this.rotation = (this.rotation + rotation) % 360;
        updateRotation();
    }

    public void resetComponent() {
        rotation = 0;
        updateRotation();
        updateMirrorText(1);
    }

    private void updateRotation() {
        bounds.setRotate(rotation);
        group.setRotate(rotation);
        dragBounds.setRotate(-rotation);
    }

    void drawBars(Group group, double middleY) {
        double currentX = 25 * scaleFactor;

        group.getChildren().add(drawRectangle(currentX, optimizedY(middleY, 30), 6, 30));
        currentX = currentX + 6 * scaleFactor;
        group.getChildren().add(drawRectangle(currentX, optimizedY(middleY, 42), 6, 42));
        currentX = currentX + 6 * scaleFactor;
        double barLength = preScaleWidth - currentX * (1 / scaleFactor) - 25 - 45;
        group.getChildren().add(drawRectangle(currentX, optimizedY(middleY, 45), barLength, 45));
        currentX = currentX + barLength * scaleFactor;
        group.getChildren().add(drawRectangle(currentX, optimizedY(middleY, 42), 6, 42));
        currentX = currentX + 6 * scaleFactor;
        group.getChildren().add(drawRectangle(currentX, optimizedY(middleY, 30), 6, 30));
        currentX = currentX + 6 * scaleFactor;
        group.getChildren().add(drawRoller(currentX, optimizedY(middleY, 33), 16, 33));
        currentX = currentX + 16 * scaleFactor;
        group.getChildren().add(drawRoller(currentX, optimizedY(middleY, 39), 6, 39));
        currentX = currentX + 6 * scaleFactor;
        group.getChildren().add(drawRoller(currentX, optimizedY(middleY, 36), 2, 36));
        currentX = currentX + 2 * scaleFactor;
        group.getChildren().add(drawRoller(currentX, optimizedY(middleY, 36), 2, 36));
        currentX = currentX + 2 * scaleFactor;
        group.getChildren().add(drawRoller(currentX, optimizedY(middleY, 28), 6, 28));

    }

    private Rectangle drawRectangle(double x1, double y1, double width, double height) {
        Rectangle rectangle = new Rectangle(x1, y1, width * scaleFactor, height * scaleFactor);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(0.7);
        rectangle.setFill(Color.TRANSPARENT);
        return rectangle;
    }

    private Rectangle drawRoller(double x1, double y1, double width, double height) {
        Rectangle rectangle = new Rectangle(x1, y1, width * scaleFactor, height * scaleFactor);
        rectangle.setStroke(Color.WHITE);
        rectangle.setStrokeWidth(0.7);
        // Create stops for the gradient (white and transparent)
        Stop[] stops = new Stop[]{
                new Stop(0, Color.WHITE),
                new Stop(0.1, Color.WHITE),
                new Stop(0.1, Color.TRANSPARENT),
                new Stop(0.2, Color.TRANSPARENT),
                new Stop(0.2, Color.WHITE),
                new Stop(0.3, Color.WHITE),
                new Stop(0.3, Color.TRANSPARENT),
                new Stop(0.4, Color.TRANSPARENT),
                new Stop(0.4, Color.WHITE),
                new Stop(0.5, Color.WHITE),
                new Stop(0.5, Color.TRANSPARENT),
                new Stop(0.6, Color.TRANSPARENT),
                new Stop(0.6, Color.WHITE),
                new Stop(0.7, Color.WHITE),
                new Stop(0.7, Color.TRANSPARENT),
                new Stop(0.8, Color.TRANSPARENT),
                new Stop(0.8, Color.WHITE),
                new Stop(0.9, Color.WHITE),
                new Stop(0.9, Color.TRANSPARENT),
                new Stop(1, Color.TRANSPARENT)
        };

        // Create a linear gradient using the stops
        LinearGradient linearGradient = new LinearGradient(0, 0, 1, 0, true, null, stops);
        rectangle.setFill(linearGradient);

        return rectangle;
    }

    private double optimizedY(double middleY, double height) {
        return middleY - height / 2 * scaleFactor;
    }

}
