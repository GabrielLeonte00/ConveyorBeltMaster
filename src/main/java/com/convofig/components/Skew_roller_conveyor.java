package com.convofig.components;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Arrays;
import java.util.Objects;

public class Skew_roller_conveyor extends Label {

    private String H;
    private String V;
    private String P;
    private String No_MDR;
    private String PolySide;
    private final double Width, Height;
    private int skewRotation;
    private int skewFactor;
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
    private double revertMove;
    private Text titleText, textH, textV, textP;
    private final String control;

    public Skew_roller_conveyor(double scaleFactor, int preScaleWidth, int preScaleHeight, double Width, double Height, String H, String V, String P, String title, String control, String PolySide) {
        super();
        this.scaleFactor = scaleFactor;
        this.H = H;
        this.V = V;
        this.P = P;
        this.title = title;
        this.Height = Height;
        this.Width = Width;
        this.control = control;
        this.PolySide = PolySide;
        this.preScaleHeight = preScaleHeight;
        this.preScaleWidth = preScaleWidth;
        Component_name = "Skew roller conveyor";
        Type_code = "G5304";
        excelData = new String[22];
        switch (preScaleHeight) {
            case 417:
                skewRotation = 15;
                skewFactor = 4;
                revertMove = 6;
                break;
            case 517:
                skewRotation = 13;
                skewFactor = 5;
                revertMove = 13;
                break;
            case 617:
                skewRotation = 12;
                skewFactor = 6;
                revertMove = 23;
                break;
            case 717:
                skewRotation = 11;
                skewFactor = 7;
                revertMove = 32;
                break;
            case 817:
                skewRotation = 9;
                skewFactor = 8;
                revertMove = 40;
                break;
            case 917:
                skewRotation = 7;
                skewFactor = 9;
                revertMove = 50;
                break;
        }
        switch (preScaleWidth) {
            case 1440:
                this.No_MDR = "2";
                break;
            case 1800:
            case 2160:
                this.No_MDR = "3";
                break;
            case 2880:
                this.No_MDR = "4";
                break;
        }
        createComponent();
        populateData();
    }

    public String getDataForSave() {
        return rotation + "," + scaleFactor + "," + preScaleWidth + "," + preScaleHeight + "," + Width + "," + Height + "," + H + "," + V + "," + P + "," + title + "," + control + "," + PolySide + "," + mirror;
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
        populateData();
    }

    public void modifyH(String newValue) {
        H = newValue;
        textH.setText("H = " + newValue);
        populateData();
    }

    public void modifyV(String newValue) {
        V = newValue;
        textV.setText("V = " + newValue);
        populateData();
    }

    public void modifyP(String newValue) {
        P = newValue;
        textP.setText("P = " + newValue);
        populateData();
    }

    public void mirrorText() {
        mirror = -mirror;
        textGroup.setScaleX(mirror);
        titleText.setScaleY(mirror);
        textH.setScaleY(mirror);
        textV.setScaleY(mirror);
        textP.setScaleY(mirror);
    }

    public void updateMirrorText(int mirror) {
        this.mirror = mirror;
        textGroup.setScaleX(mirror);
        titleText.setScaleY(mirror);
        textH.setScaleY(mirror);
        textV.setScaleY(mirror);
        textP.setScaleY(mirror);
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
        updateMirrorText(mirror);
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

        Line middleLine = new Line(0, Height / 2, Width, Height / 2);
        middleLine.setStroke(Color.RED);
        group.getChildren().add(middleLine);

        drawArrow(group, Height / 2);

        drawBars(group, 130, preScaleHeight, false);
        drawBars(group, preScaleWidth - 130, preScaleHeight, false);

        if (Objects.equals(PolySide, "Left")) {
            drawBars(group, 60, (double) preScaleHeight - 60 * skewFactor, true);
            drawBars(group, preScaleWidth - 60, (double) preScaleHeight - 60 * skewFactor, false);
        } else {
            drawBars(group, 60, (double) preScaleHeight - 60 * skewFactor, false);
            drawBars(group, preScaleWidth - 60, (double) preScaleHeight - 60 * skewFactor, true);
        }
        Line botLine = new Line(0, Height - 35 * scaleFactor, Width, Height - 35 * scaleFactor);
        botLine.setStroke(Color.WHITE);
        group.getChildren().add(botLine);

        textGroup = new Group();
        titleText = new Text(title);
        titleText.setFill(Color.MAGENTA);
        titleText.setStyle("-fx-font: 16 arial;");
        titleText.setX(240 * scaleFactor); // Adjust the X position based on your requirement
        titleText.setY(Height / 2 - 30 * scaleFactor); // Adjust the Y position based on your requirement
        textGroup.getChildren().add(titleText);

        textH = new Text("H = " + H);
        textH.setFill(Color.LIGHTGREEN);
        textH.setStyle("-fx-font: 12 arial;");
        textH.relocate(240 * scaleFactor, Height / 2 + 20 * scaleFactor);
        textGroup.getChildren().add(textH);

        textV = new Text("V = " + V);
        textV.setFill(Color.DEEPSKYBLUE);
        textV.setStyle("-fx-font: 12 arial;");
        textV.relocate(240 * scaleFactor, Height / 2 + 55 * scaleFactor);
        textGroup.getChildren().add(textV);

        textP = new Text("P = " + P);
        textP.setFill(Color.CYAN);
        textP.setStyle("-fx-font: 12 arial;");
        textP.relocate(240 * scaleFactor, Height / 2 + 90 * scaleFactor);
        textGroup.getChildren().add(textP);
        group.getChildren().add(textGroup);

        dragBounds = new Rectangle(Width / 2 - 100 * scaleFactor, Height / 2 - 100 * scaleFactor, 200 * scaleFactor, 200 * scaleFactor);
        dragBounds.setFill(Color.rgb(127, 255, 212, 0.25));
        dragBounds.setStroke(Color.rgb(127, 255, 212, 0.5));
        dragBounds.setVisible(false);
        group.getChildren().add(dragBounds);

        setGraphic(group);
    }

    public Skew_roller_conveyor copyComponent() {
        Skew_roller_conveyor copy = new Skew_roller_conveyor(scaleFactor, preScaleWidth, preScaleHeight, Width, Height, H, V, P, title, control, PolySide);
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

    private void drawArrow(Group group, double y) {
        double x = 240 * scaleFactor;
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

    private void drawBars(Group group, double middleX, double heightBar, boolean revert) {
        Group groupBars = new Group();
        if (Objects.equals(PolySide, "Right")) {
            groupBars.getChildren().add(drawRectangle(middleX - 15, 35, widthX(middleX, middleX - 15), 10)); //y = 35+10
            groupBars.getChildren().add(drawRectangle(middleX - 25, 45, widthX(middleX, middleX - 25), 40)); //y = 45+40
            groupBars.getChildren().add(drawRectangle(middleX - 30, 85, widthX(middleX, middleX - 30), heightBar - 80));
            double nextY = 85 + heightBar - 80;
            groupBars.getChildren().add((drawRectangle(middleX - 25, nextY, widthX(middleX, middleX - 25), 10)));
            groupBars.getChildren().add(drawRectangle(middleX - 20, nextY + 10, widthX(middleX, middleX - 20), 10)); //y = 70 + 10
            groupBars.setRotate(skewRotation);
        } else {
            groupBars.getChildren().add(drawRectangle(middleX - 20, 35, widthX(middleX, middleX - 20), 10)); //y = 35 + 10
            groupBars.getChildren().add((drawRectangle(middleX - 25, 45, widthX(middleX, middleX - 25), 10))); //y = 45 + 10
            groupBars.getChildren().add(drawRectangle(middleX - 30, 55, widthX(middleX, middleX - 30), heightBar - 80));
            double nextY = 55 + heightBar - 80;
            groupBars.getChildren().add(drawRectangle(middleX - 25, nextY, widthX(middleX, middleX - 25), 40));
            groupBars.getChildren().add(drawRectangle(middleX - 15, nextY + 40, widthX(middleX, middleX - 15), 10));
            groupBars.setRotate(-skewRotation);
        }

        if (revert) {
            groupBars.setLayoutY(Height / 2 + revertMove * scaleFactor);
        } else {
            groupBars.setTranslateY(7 * scaleFactor);
        }

        group.getChildren().add(groupBars);
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
