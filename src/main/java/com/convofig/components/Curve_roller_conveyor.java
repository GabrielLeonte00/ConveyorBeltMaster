package com.convofig.components;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Objects;

public class Curve_roller_conveyor extends Label {

    private final String H, V, P;
    private final double Width;
    private final double preWidth;
    private String title;
    private final String Component_name;
    private final String Type_code;
    private final String angle;
    private final String[] excelData;
    private int rotation = 0;
    private Rectangle bounds;
    private Rectangle dragBounds;
    private Group group;
    private final double scaleFactor;
    private Text titleText;
    private final String control;

    public Curve_roller_conveyor(double scaleFactor, double w, double pW, String H, String V, String title, String angle, String control) {
        super();
        this.scaleFactor = scaleFactor;
        this.H = H;
        this.V = V;
        this.title = title;
        this.preWidth = w;
        this.Width = pW;
        this.control = control;
        Component_name = "Curve roller conveyor";
        this.angle = angle;
        Type_code = "G5303A";
        P = "72";
        excelData = new String[22];
        Arrays.fill(excelData, "");
        createComponent();
        populateData();
    }

    void populateData() {
        excelData[0] = title;
        excelData[1] = Type_code;
        excelData[2] = Component_name;
        excelData[3] = "1";
        excelData[4] = "0";
        excelData[5] = Integer.toString((int) preWidth);
        excelData[6] = V;
        excelData[7] = H;
        excelData[10] = angle;
        excelData[12] = P;
        excelData[13] = control;
        if (Objects.equals(control, "EQube AI") || Objects.equals(control, "EZQube"))
            excelData[14] = "2";
        else if (Objects.equals(control, "Conveylinx-Eco") || Objects.equals(control, "Conveylinx AI2"))
            excelData[14] = "1";
        excelData[15] = "2";
    }

    public String getDataForSave() {
        return rotation + "," + scaleFactor + "," + preWidth + "," + Width + "," + H + "," + V + "," + title + "," + angle + "," + control;
    }

    public double getWidthForLoad() {
        return Width;
    }

    public void mouseEnteredDragZone() {
        dragBounds.setVisible(true);
    }

    public void mouseExitedDragZone() {
        dragBounds.setVisible(false);
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

        group = new Group();
        double doubleAngle = Double.parseDouble(angle);

        bounds = new Rectangle(Width, Width);
        bounds.fillProperty().set(null);
        bounds.setStroke(Color.TRANSPARENT);
        group.getChildren().add(bounds);

        Arc outerArc = new Arc();
        outerArc.setCenterX(Width);
        outerArc.setCenterY(Width);
        outerArc.setRadiusX(Width);
        outerArc.setRadiusY(Width);
        outerArc.setStartAngle(180);
        outerArc.fillProperty().set(null);
        outerArc.setStroke(Color.WHITE);
        outerArc.setLength(-doubleAngle);
        group.getChildren().add(outerArc);

        Arc outerArc2 = new Arc();
        outerArc2.setCenterX(Width);
        outerArc2.setCenterY(Width);
        outerArc2.setRadiusX(Width - 35 * scaleFactor);
        outerArc2.setRadiusY(Width - 35 * scaleFactor);
        outerArc2.setStartAngle(180);
        outerArc2.fillProperty().set(null);
        outerArc2.setStroke(Color.WHITE);
        outerArc2.setLength(-doubleAngle);
        group.getChildren().add(outerArc2);

        Arc inerArc = new Arc();
        inerArc.setCenterX(Width);
        inerArc.setCenterY(Width);
        inerArc.setRadiusX(790 * scaleFactor);
        inerArc.setRadiusY(790 * scaleFactor);
        inerArc.setStartAngle(180);
        inerArc.fillProperty().set(null);
        inerArc.setStroke(Color.WHITE);
        inerArc.setLength(-doubleAngle);
        group.getChildren().add(inerArc);

        Arc inerArc2 = new Arc();
        inerArc2.setCenterX(Width);
        inerArc2.setCenterY(Width);
        inerArc2.setRadiusX((790 + 35) * scaleFactor);
        inerArc2.setRadiusY((790 + 35) * scaleFactor);
        inerArc2.setStartAngle(180);
        inerArc2.fillProperty().set(null);
        inerArc2.setStroke(Color.WHITE);
        inerArc2.setLength(-doubleAngle);
        group.getChildren().add(inerArc2);

        Arc middleArc = new Arc();
        middleArc.setCenterX(Width);
        middleArc.setCenterY(Width);
        middleArc.setRadiusX(Width - (Width - 790 * scaleFactor) / 2);
        middleArc.setRadiusY(Width - (Width - 790 * scaleFactor) / 2);
        middleArc.setStartAngle(180);
        middleArc.fillProperty().set(null);
        middleArc.setStroke(Color.RED);
        middleArc.setLength(-doubleAngle);
        group.getChildren().add(middleArc);

        Line baseLine = new Line(0, Width, Width - 790 * scaleFactor, Width);
        baseLine.setStroke(Color.WHITE);
        group.getChildren().add(baseLine);

        double radianAngle = Math.toRadians(doubleAngle);
        int x1 = (int) (Width - Width * Math.cos(radianAngle));
        int y1 = (int) (Width - Width * Math.sin(radianAngle));
        int x2 = (int) (Width - 790 * scaleFactor * Math.cos(radianAngle));
        int y2 = (int) (Width - 790 * scaleFactor * Math.sin(radianAngle));
        Line topLine = new Line(x1, y1, x2, y2);
        topLine.setStroke(Color.WHITE);
        group.getChildren().add(topLine);

        double textPosition = (Width - 790 * scaleFactor) / 2 + 30 * scaleFactor;
        titleText = new Text(title);
        titleText.setFill(Color.MAGENTA);
        titleText.setStyle("-fx-font: 16 arial;");
        titleText.setX(textPosition); // Adjust the X position based on your requirement
        titleText.setY(Width - 25 * scaleFactor); // Adjust the Y position based on your requirement
        group.getChildren().add(titleText);

        Text textH = new Text("H = " + H);
        textH.setFill(Color.LIGHTGREEN);
        textH.setStyle("-fx-font: 10 arial;");
        textH.relocate(textPosition, Width - 25 * scaleFactor - (45 + 3 * 35) * scaleFactor);
        group.getChildren().add(textH);

        Text textV = new Text("V = " + V);
        textV.setFill(Color.BLUE);
        textV.setStyle("-fx-font: 10 arial;");
        textV.relocate(textPosition, Width - 25 * scaleFactor - (45 + 2 * 35) * scaleFactor);
        group.getChildren().add(textV);

        Text textP = new Text("P = " + P);
        textP.setFill(Color.CYAN);
        textP.setStyle("-fx-font: 10 arial;");
        textP.relocate(textPosition, Width - 25 * scaleFactor - (45 + 35) * scaleFactor);
        group.getChildren().add(textP);

        dragBounds = new Rectangle(Width / 2 - 100 * scaleFactor, Width / 2 - 100 * scaleFactor, 200 * scaleFactor, 200 * scaleFactor);
        dragBounds.setFill(Color.rgb(127, 255, 212, 0.25));
        dragBounds.setStroke(Color.rgb(127, 255, 212, 0.5));
        dragBounds.setVisible(false);
        group.getChildren().add(dragBounds);

        setGraphic(group);
    }

    public Curve_roller_conveyor copyComponent() {
        Curve_roller_conveyor copy = new Curve_roller_conveyor(scaleFactor, preWidth, Width, H, V, title, angle, control);
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
