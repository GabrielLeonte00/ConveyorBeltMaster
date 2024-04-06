package com.convofig.components;

import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.Arrays;

public class Gravity_roller_conveyor extends Label implements component_interface{

    private String H1;
    private String H2;
    private String P;
    private final double Width, Height;
    private final int preScaleWidth, preScaleHeight;
    private String title;
    private final String Component_name;
    private final String Type_code;
    private final String[] excelData;
    private int rotation = 0;
    private int mirror = 1;
    private Group group, textGroup;
    private Rectangle bounds;
    private Rectangle dragBounds;
    private final double scaleFactor;
    private Text titleText, textH1, textH2, textP;

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
        Component_name = "Gravity roller conveyor";
        Type_code = "G5601";
        excelData = new String[22];
        createComponent();
        populateData();
    }

    public String getDataForSave() {
        return rotation + "," + scaleFactor + "," + preScaleWidth + "," + preScaleHeight + "," + Width + "," + Height + "," + H1 + "," + H2 + "," + P + "," + title + "," + mirror;
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
        excelData[7] = H1;
        excelData[12] = P;
    }

    public String[] getDataForExcel() {
        return excelData;
    }

    public void modifyTitle(String newValue) {
        title = newValue;
        titleText.setText(newValue);
        populateData();
    }

    public void modifyH(int NO, String newValue) {
        if (NO == 1) {
            H1 = newValue;
            textH1.setText("H1 = " + newValue);
        } else {
            H2 = newValue;
            textH2.setText("H2 = " + newValue);
        }
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
        textH1.setScaleY(mirror);
        textH2.setScaleY(mirror);
        textP.setScaleY(mirror);
    }

    public void updateMirrorText(int mirror) {
        this.mirror = mirror;
        textGroup.setScaleX(mirror);
        titleText.setScaleY(mirror);
        textH1.setScaleY(mirror);
        textH2.setScaleY(mirror);
        textP.setScaleY(mirror);
    }

    public String getCurrentName() {
        return title;
    }

    public String getCurrentH(int NO) {
        if (NO == 1) {
            return H1;
        } else {
            return H2;
        }
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

        Line botLine = new Line(0, Height - 35 * scaleFactor, Width, Height - 35 * scaleFactor);
        botLine.setStroke(Color.WHITE);
        group.getChildren().add(botLine);

        group.getChildren().add(drawRectangle(10, 35, 40, preScaleHeight));
        group.getChildren().add(drawRectangle(70, 35, 40, preScaleHeight));

        textGroup = new Group();
        titleText = new Text(title);
        titleText.setFill(Color.MAGENTA);
        titleText.setStyle("-fx-font: 16 arial;");
        titleText.setX(140 * scaleFactor); // Adjust the X position based on your requirement
        titleText.setY(Height / 2 - 30 * scaleFactor); // Adjust the Y position based on your requirement
        textGroup.getChildren().add(titleText);

        textH1 = new Text("H1 = " + H1);
        textH1.setFill(Color.LIGHTGREEN);
        textH1.setStyle("-fx-font: 12 arial;");
        textH1.relocate(140 * scaleFactor, Height / 2 + 20 * scaleFactor);
        textGroup.getChildren().add(textH1);

        textH2 = new Text("H2 = " + H2);
        textH2.setFill(Color.LIGHTGREEN);
        textH2.setStyle("-fx-font: 12 arial;");
        textH2.relocate(140 * scaleFactor, Height / 2 + 55 * scaleFactor);
        textGroup.getChildren().add(textH2);

        textP = new Text("P = " + P);
        textP.setFill(Color.CYAN);
        textP.setStyle("-fx-font: 12 arial;");
        textP.relocate(140 * scaleFactor, Height / 2 + 90 * scaleFactor);
        textGroup.getChildren().add(textP);
        group.getChildren().add(textGroup);

        dragBounds = new Rectangle(Width / 2 - 100 * scaleFactor, Height / 2 - 100 * scaleFactor, 200 * scaleFactor, 200 * scaleFactor);
        dragBounds.setFill(Color.rgb(127, 255, 212, 0.25));
        dragBounds.setStroke(Color.rgb(127, 255, 212, 0.5));
        dragBounds.setVisible(false);
        group.getChildren().add(dragBounds);

        setGraphic(group);
    }

    public Gravity_roller_conveyor copyComponent() {
        Gravity_roller_conveyor copy = new Gravity_roller_conveyor(scaleFactor, preScaleWidth, preScaleHeight, Width, Height, H1, H2, P, title);
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
        double x = 140 * scaleFactor;
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
