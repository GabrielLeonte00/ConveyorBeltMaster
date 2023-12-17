package com.convofig.controllers;

import com.convofig.conveyorbeltmaster.MainApplication;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;

public class MainView extends MainApplication {

    private int newMethodeUsedOnce = 0;
    private static final double ZOOM_FACTOR = 1.1;
    private static int zoomTimes = 10;
    private double initialWidth, initialHeight;
    private MenuItem zoomInMenu;
    private MenuItem zoomOutMenu;
    private final ObservableList<String> componentWidth = FXCollections.observableArrayList("417", "517", "617", "717", "817", "917");
    private final ObservableList<String> componentLength = FXCollections.observableArrayList("360", "390", "420", "450", "480", "510", "540", "570", "600", "630", "660", "690", "720", "750", "780", "810", "840", "870", "900", "930", "960", "990", "1020", "1050", "1080", "1110", "1140", "1170", "1200", "1230", "1260", "1290", "1320", "1350", "1380", "1410", "1440", "1470", "1500", "1530", "1560", "1590", "1620", "1650", "1680", "1710", "1740", "1770", "1800", "1830", "1860", "1890", "1920", "1950", "1980", "2010", "2040", "2070", "2100", "2130", "2160", "2190", "2220", "2250", "2280", "2310", "2340", "2370", "2400", "2430", "2460", "2490", "2520", "2550", "2580", "2610", "2640", "2670", "2700", "2730", "2760", "2790", "2820", "2850", "2880");
    private final ObservableList<String> componentRollerPitch = FXCollections.observableArrayList("60", "90", "120");
    private final ObservableList<String> componentPolyVeeSide = FXCollections.observableArrayList("Left", "Right");
    private final ObservableList<String> componentNoOfMDR = FXCollections.observableArrayList("1", "2", "3", "4");
    private double xOffset = 0;
    private double yOffset = 0;

    @FXML
    private ObservableList<String> componentType = FXCollections.observableArrayList("Motorized roller conveyor", "Gravity roller conveyor", "90 degree transfer module", "Merge conveyor", "Curve roller conveyor", "Skew roller conveyor", "Diverter");
    @FXML
    private ImageView mainImage;
    @FXML
    private Pane drawPane;
    @FXML
    private Pane mainPane;
    @FXML
    private MenuItem SaveMI, ExportMI, ClearMI;
    @FXML
    private RadioMenuItem ConfigurationRMI;
    @FXML
    private StackPane configurationMenu;
    @FXML
    private ComboBox<String> componentTypeComboBox, componentWidthComboBox, componentLengthComboBox, componentRollerPitchComboBox, componentPolyVeeSideComboBox, componentNoOfMDRComboBox;
    @FXML
    private MenuItem zoomInMI;
    @FXML
    private MenuItem zoomOutMI;
    @FXML
    private MenuItem zoomResetMI;
    @FXML
    private MenuItem resetPositionMI;
    @FXML
    private MenuItem increaseSizeMI;
    @FXML
    private Region componentRegion;

    @FXML
    public void initialize() {
        new ConfigurationView(mainPane, drawPane, componentRegion, componentTypeComboBox, componentWidthComboBox, componentLengthComboBox);
        componentTypeComboBox.setItems(componentType);
        componentTypeComboBox.getSelectionModel().select(0);
        componentWidthComboBox.setItems(componentWidth);
        componentWidthComboBox.getSelectionModel().select(0);
        componentLengthComboBox.setItems(componentLength);
        componentLengthComboBox.getSelectionModel().select(0);
        componentRollerPitchComboBox.setItems(componentRollerPitch);
        componentRollerPitchComboBox.getSelectionModel().select(0);
        componentPolyVeeSideComboBox.setItems(componentPolyVeeSide);
        componentPolyVeeSideComboBox.getSelectionModel().select(0);
        componentNoOfMDRComboBox.setItems(componentNoOfMDR);
        componentNoOfMDRComboBox.getSelectionModel().select(0);

        componentRegion.setPrefWidth((double) Integer.parseInt(componentLengthComboBox.getSelectionModel().getSelectedItem()) / 4);
        componentRegion.setPrefHeight((double) (Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70) / 4);

        addMovingDrawingPane();
        getOffsettForMove();
    }

    private void addContextMenuDrawPane() {
        ContextMenu drawMenu = new ContextMenu();
        zoomInMenu = new MenuItem("Zoom in");
        zoomInMenu.setOnAction(actionEvent -> zoomInMethod());
        drawMenu.getItems().add(zoomInMenu);
        zoomOutMenu = new MenuItem("Zoom out");
        zoomOutMenu.setOnAction(actionEvent -> zoomOutMethod());
        drawMenu.getItems().add(zoomOutMenu);
        MenuItem zoomResetMenu = new MenuItem("Reset zoom");
        zoomResetMenu.setOnAction(actionEvent -> zoomResetMethod());
        drawMenu.getItems().add(zoomResetMenu);

        drawPane.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.SECONDARY && e.getTarget() instanceof Pane) {
                drawMenu.show(drawPane, e.getScreenX(), e.getScreenY());
            } else {
                drawMenu.hide();
            }
        });
    }

    @FXML
    void resetPositionMethod() {
        double newLocationX = initialWidth - drawPane.getWidth();
        double newLocationY = initialHeight - drawPane.getHeight();

        // Assuming mainPane is the parent pane of drawPane
        double scaleFactorX = mainPane.getScaleX();
        double scaleFactorY = mainPane.getScaleY();
        // Adjust the relocation offsets based on the scale factor
        double offsetX = (newLocationX / 2 - 1200) * scaleFactorX;
        double offsetY = (newLocationY / 2 - 960) * scaleFactorY;

        drawPane.relocate(offsetX, offsetY);
    }

    @FXML
    void increaseSizeMethod() {
        drawPane.setPrefWidth(drawPane.getWidth() + 480);
        drawPane.setPrefHeight(drawPane.getHeight() + 480);
        drawPane.setLayoutX(drawPane.getLayoutX() - 240);
        drawPane.setLayoutY(drawPane.getLayoutY() - 240);
        for (javafx.scene.Node child : drawPane.getChildren()) {
            child.setTranslateX(child.getTranslateX() + 240);
            child.setTranslateY(child.getTranslateY() + 240);
        }
    }

    @FXML
    void addComponentMethod() {
        ConfigurationView.addComponent();
    }

    @FXML
    void quitMethod() {
        Platform.exit();
    }

    @FXML
    void newMethod() {
        initialize();
        if (newMethodeUsedOnce == 0) {
            Stage stage = maximizeStage();
            drawPane.getChildren().remove(mainImage);
            drawPane.setPrefWidth(4800);
            drawPane.setPrefHeight(3840);
            drawPane.setLayoutX(-1200);
            drawPane.setLayoutY(-960);
            initialWidth = 4800;
            initialHeight = 3840;
            SaveMI.setDisable(false);
            ExportMI.setDisable(false);
            ClearMI.setDisable(false);
            zoomInMI.setDisable(false);
            zoomOutMI.setDisable(false);
            zoomResetMI.setDisable(false);
            increaseSizeMI.setDisable(false);
            resetPositionMI.setDisable(false);
            ConfigurationRMI.setDisable(false);
            ConfigurationRMI.setSelected(true);
            configurationMenu.setVisible(true);
            componentRegion.setVisible(true);
            newMethodeUsedOnce = 1;
            addContextMenuDrawPane();
        }
    }

    @FXML
    void configurationMethod() {
        configurationMenu.setVisible(ConfigurationRMI.isSelected());
        componentRegion.setVisible(ConfigurationRMI.isSelected());
    }

    @FXML
    void alignMethod() {
        ConfigurationView.align = !ConfigurationView.align;
    }

    @FXML
    void zoomInMethod() {
        if (zoomOutMI.isDisable()) {
            zoomOutMI.setDisable(false);
            zoomOutMenu.setDisable(true);
        }
        zoomTimes = zoomTimes + 1;
        double x = mainPane.getWidth() * ZOOM_FACTOR;
        double y = mainPane.getHeight() * ZOOM_FACTOR;
        mainPane.getTransforms().add(new Scale(ZOOM_FACTOR, ZOOM_FACTOR));
        if (zoomTimes == 15) {
            zoomInMI.setDisable(true);
            zoomInMenu.setDisable(true);
        }
    }

    @FXML
    void zoomOutMethod() {
        if (zoomInMI.isDisable()) {
            zoomInMI.setDisable(false);
            zoomInMenu.setDisable(false);
        }
        zoomTimes--;
        double scaleFactor = 1.0 / ZOOM_FACTOR;
        double x = mainPane.getWidth() * scaleFactor;
        double y = mainPane.getHeight() * scaleFactor;
        mainPane.getTransforms().add(new Scale(scaleFactor, scaleFactor));
        if (zoomTimes == 5) {
            zoomOutMI.setDisable(true);
            zoomOutMenu.setDisable(true);
        }
    }

    @FXML
    void zoomResetMethod() {
        zoomInMI.setDisable(false);
        zoomInMenu.setDisable(false);
        zoomOutMI.setDisable(false);
        zoomOutMenu.setDisable(false);
        zoomTimes = 10;
        mainPane.getTransforms().clear();
    }

    void getOffsettForMove() {
        mainPane.setOnMousePressed(e -> {
            if (e.isMiddleButtonDown()) {
                xOffset = e.getSceneX() - drawPane.getLayoutX();
                yOffset = e.getSceneY() - drawPane.getLayoutY();
            }
        });
    }

    void addMovingDrawingPane() {
        drawPane.setOnMouseDragged(e -> {
            if (e.isMiddleButtonDown()) {
                double x = e.getSceneX() - xOffset;
                double y = e.getScreenY() - yOffset - 30;
                drawPane.setLayoutX(x);
                drawPane.setLayoutY(y);
            }
        });
    }
}
