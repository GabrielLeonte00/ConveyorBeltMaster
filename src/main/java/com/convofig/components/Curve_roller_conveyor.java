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

    private String H;
    private String V;
    private final String P;
    private final double Width;
    private final double preWidth;
    private String title;
    private final String Component_name;
    private final String Type_code;
    private final String angle;
    private final String[] excelData;
    private int rotation = 0;
    private int revert = 1;
    private Rectangle bounds;
    private Rectangle dragBounds;
    private Group group, textGroup;
    private final double scaleFactor;
    private Text titleText, textH, textV, textP;
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
        excelData[5] = (int) preWidth + "+70";
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
        return rotation + "," + scaleFactor + "," + preWidth + "," + Width + "," + H + "," + V + "," + title + "," + angle + "," + control + "," + revert;
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

    public void modifyTitle(String newValue) {
        title = newValue;
        titleText.setText(newValue);
    }

    public void modifyH(String newValue) {
        H = newValue;
        textH.setText("H = " + newValue);
    }

    public void modifyV(String newValue) {
        V = newValue;
        textV.setText("V = " + newValue);
    }

    public void mirrorText() {
        revert = -revert;
        textGroup.setScaleX(revert);
        titleText.setScaleY(revert);
        textH.setScaleY(revert);
        textV.setScaleY(revert);
        textP.setScaleY(revert);
    }

    public void updateMirrorText(int revert) {
        this.revert = revert;
        textGroup.setScaleX(revert);
        titleText.setScaleY(revert);
        textH.setScaleY(revert);
        textV.setScaleY(revert);
        textP.setScaleY(revert);
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

        textGroup = new Group();
        double textPosition = (Width - 790 * scaleFactor) / 2 + 30 * scaleFactor;
        titleText = new Text(title);
        titleText.setFill(Color.MAGENTA);
        titleText.setStyle("-fx-font: 16 arial;");
        titleText.setX(textPosition); // Adjust the X position based on your requirement
        titleText.setY(Width - 25 * scaleFactor); // Adjust the Y position based on your requirement
        textGroup.getChildren().add(titleText);

        textH = new Text("H = " + H);
        textH.setFill(Color.LIGHTGREEN);
        textH.setStyle("-fx-font: 12 arial;");
        textH.relocate(textPosition, Width - 25 * scaleFactor - (55 + 3 * 35) * scaleFactor);
        textGroup.getChildren().add(textH);

        textV = new Text("V = " + V);
        textV.setFill(Color.DEEPSKYBLUE);
        textV.setStyle("-fx-font: 12 arial;");
        textV.relocate(textPosition, Width - 25 * scaleFactor - (55 + 2 * 35) * scaleFactor);
        textGroup.getChildren().add(textV);

        textP = new Text("P = " + P);
        textP.setFill(Color.CYAN);
        textP.setStyle("-fx-font: 12 arial;");
        textP.relocate(textPosition, Width - 25 * scaleFactor - (55 + 35) * scaleFactor);
        textGroup.getChildren().add(textP);

        dragBounds = new Rectangle(Width / 2 - 100 * scaleFactor, Width / 2 - 100 * scaleFactor, 200 * scaleFactor, 200 * scaleFactor);
        dragBounds.setFill(Color.rgb(127, 255, 212, 0.25));
        dragBounds.setStroke(Color.rgb(127, 255, 212, 0.5));
        dragBounds.setVisible(false);

        group.getChildren().add(textGroup);
        group.getChildren().add(dragBounds);
        setGraphic(group);
    }

    public Curve_roller_conveyor copyComponent() {
        Curve_roller_conveyor copy = new Curve_roller_conveyor(scaleFactor, preWidth, Width, H, V, title, angle, control);
        copy.setLayoutX(getLayoutX() + getWidth() / 2 + 25); // Example: Adjust the layout for the copy
        copy.setLayoutY(getLayoutY() + getHeight() / 2 + 25);
        copy.setNewRotation(rotation);
        copy.updateMirrorText(revert);
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

}
