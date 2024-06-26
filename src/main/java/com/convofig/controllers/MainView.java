package com.convofig.controllers;

import com.convofig.classes.exportExcel;
import com.convofig.components.*;
import com.convofig.conveyorbeltmaster.MainApplication;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.StageStyle;
import javafx.util.StringConverter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URISyntaxException;
import java.util.Objects;
import java.util.Optional;

import static com.convofig.controllers.ConfigurationView.addActionsToComponent;

public class MainView extends MainApplication {
    private int newMethodeUsedOnce = 0;
    private static final double ZOOM_FACTOR = 1.1;
    private static int zoomTimes = 50;
    private double initialWidth, initialHeight;
    private MenuItem zoomInMenu;
    private MenuItem zoomOutMenu;
    private final double scaleFactor = 0.5;
    private double xOffset = 0;
    private double yOffset = 0;
    private boolean validDrag = false;

    @FXML
    private ImageView mainImage;
    @FXML
    private Pane drawPane;
    @FXML
    private Pane mainPane;
    @FXML
    private MenuItem ExportDraftMI, ExportExcelMI, SaveMI;
    @FXML
    private RadioMenuItem ConfigurationRMI;
    @FXML
    private RadioMenuItem OutlineRMI;
    @FXML
    private StackPane configurationMenu;
    @FXML
    private ComboBox<String> componentTypeComboBox;
    @FXML
    private ComboBox<String> componentWidthComboBox;
    @FXML
    private ComboBox<String> componentLengthComboBox;
    @FXML
    private ComboBox<String> componentDiverterLengthComboBox;
    @FXML
    private ComboBox<String> componentDivertingAngleComboBox;
    @FXML
    private ComboBox<String> componentRollerPitchComboBox;
    @FXML
    private ComboBox<String> componentPolyVeeSideComboBox;
    @FXML
    private ComboBox<String> componentNoOfMDRComboBox;
    @FXML
    private ComboBox<String> componentAngleComboBox;
    @FXML
    private ComboBox<String> componentControlComboBox;
    @FXML
    private ComboBox<String> componentSkewLengthComboBox;
    @FXML
    private ComboBox<String> componentDivDirectionComboBox;
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
    private TextField textTitle;
    @FXML
    private TextField textSpeed1;
    @FXML
    private TextField textSpeed2;
    @FXML
    private TextField textHeight;
    @FXML
    private TextField textAuxHeight;
    @FXML
    private TextField textLength90Transfer;
    @FXML
    private Label labelLength;
    @FXML
    private Label labelPitch;
    @FXML
    private Label labelPolySide;
    @FXML
    private Label labelNoMDR;
    @FXML
    private Label labelSpeed;
    @FXML
    private Label labelHeight;
    @FXML
    private Label labelControl;
    @FXML
    private Label labelDivDirection;

    @FXML
    public void initialize() {
        new ConfigurationView(scaleFactor, mainPane, drawPane, componentRegion, componentTypeComboBox, componentWidthComboBox, componentLengthComboBox, componentSkewLengthComboBox, componentRollerPitchComboBox, componentPolyVeeSideComboBox, componentNoOfMDRComboBox, textTitle, textSpeed1, textSpeed2, textHeight, textAuxHeight, componentAngleComboBox, componentControlComboBox, componentDiverterLengthComboBox, componentDivertingAngleComboBox, componentDivDirectionComboBox);
        addMovingDrawingPane();
        getOffsetsForMove();
    }

    void initializeSelectors() {
        componentTypeComboBox.getItems().addAll("Motorized roller conveyor", "Gravity roller conveyor", "90 degree transfer module", "Merge conveyor", "Curve roller conveyor", "Skew roller conveyor", "Diverter");
        componentTypeComboBox.getSelectionModel().select(0);
        componentWidthComboBox.getItems().addAll("417", "517", "617", "717", "817", "917");
        componentWidthComboBox.getSelectionModel().select(0);

        // Creating a custom cell factory to display custom strings
        componentWidthComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item + "+70");
                }
            }
        });

        // Creating a custom string converter to handle conversion
        componentWidthComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(String object) {
                if (object == null) {
                    return null;
                } else {
                    return object + "+70";
                }
            }

            @Override
            public String fromString(String string) {
                // You might need to implement this method if needed
                return null;
            }
        });

        for (int i = 360; i <= 2880; i += 30) {
            componentLengthComboBox.getItems().add(String.valueOf(i));
        }
        componentLengthComboBox.getSelectionModel().select(0);

        for (int i = 30; i <= 45; i++) {
            componentDivertingAngleComboBox.getItems().add(String.valueOf(i));
        }
        componentDivertingAngleComboBox.getSelectionModel().select(0);

        componentDiverterLengthComboBox.getItems().addAll("500", "600", "720");
        componentDiverterLengthComboBox.getSelectionModel().select(0);
        componentSkewLengthComboBox.getItems().addAll("1440", "1800", "2160", "2880");
        componentSkewLengthComboBox.getSelectionModel().select(0);
        componentRollerPitchComboBox.getItems().addAll("60", "90", "120");
        componentRollerPitchComboBox.getSelectionModel().select(0);
        componentPolyVeeSideComboBox.getItems().addAll("Right", "Left");
        componentPolyVeeSideComboBox.getSelectionModel().select(0);
        componentDivDirectionComboBox.getItems().addAll("Right", "Left", "Right/Left");
        componentDivDirectionComboBox.getSelectionModel().select(2);
        componentNoOfMDRComboBox.getItems().addAll("1", "2", "3", "4");
        componentNoOfMDRComboBox.getSelectionModel().select(0);
        componentAngleComboBox.getItems().addAll("30", "45", "60", "90");
        componentAngleComboBox.getSelectionModel().select(3);
        componentControlComboBox.getItems().addAll("EQube AI", "EZQube", "Conveylinx-Eco", "Conveylinx AI2");
        componentControlComboBox.getSelectionModel().select(0);
        // Apply the style to the arrow button
        componentControlComboBox.lookup(".combo-box .arrow").setStyle("-fx-background-color: transparent;");
        componentControlComboBox.lookup(".combo-box .arrow-button").setStyle("-fx-padding: -0.25em -0.25em -0.25em -0.25em;");

        componentControlComboBox.setOnMouseEntered(e -> {
            componentControlComboBox.lookup(".combo-box .arrow").setStyle("-fx-background-color: black;");
            componentControlComboBox.lookup(".combo-box .arrow-button").setStyle("-fx-padding: 1em 1em 1em 1em;");
        });
        componentControlComboBox.setOnMouseExited(e -> {
            componentControlComboBox.lookup(".combo-box .arrow").setStyle("-fx-background-color: transparent;");
            componentControlComboBox.lookup(".combo-box .arrow-button").setStyle("-fx-padding: -0.25em -0.25em -0.25em -0.25em;");
        });

        textTitle.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches(".{0,7}") ? c : null));
        textSpeed1.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches(".{0,5}") ? c : null));
        textSpeed2.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches(".{0,5}") ? c : null));
        textHeight.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches(".{0,5}") ? c : null));
        textAuxHeight.setTextFormatter(new TextFormatter<>(c -> c.getControlNewText().matches(".{0,5}") ? c : null));

        textTitle.setText("A0010");
        textSpeed1.setText("30");
        textSpeed2.setText("0");
        textHeight.setText("700");
        textAuxHeight.setText("700");
        textLength90Transfer.setText("780");

        componentRegion.setPrefWidth((double) Integer.parseInt(componentLengthComboBox.getSelectionModel().getSelectedItem()) * scaleFactor);
        componentRegion.setPrefHeight((double) (Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70) * scaleFactor);
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
        MenuItem resetPosition = new MenuItem("Reset position");
        resetPosition.setOnAction(actionEvent -> resetPositionMethod());
        drawMenu.getItems().add(resetPosition);

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
        double scaleFactorX = mainPane.getLocalToSceneTransform().getMxx();
        double scaleFactorY = mainPane.getLocalToSceneTransform().getMyy();

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
        for (Node child : drawPane.getChildren()) {
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
        if (newMethodeUsedOnce == 0) {
            maximizeStage();
            drawPane.getChildren().remove(mainImage);
            drawPane.setPrefWidth(4800);
            drawPane.setPrefHeight(3840);
            drawPane.setLayoutX(-1200);
            drawPane.setLayoutY(-960);
            initialWidth = 4800;
            initialHeight = 3840;
            SaveMI.setDisable(false);
            ExportExcelMI.setDisable(false);
            ExportDraftMI.setDisable(false);
            zoomInMI.setDisable(false);
            zoomOutMI.setDisable(false);
            zoomResetMI.setDisable(false);
            increaseSizeMI.setDisable(false);
            resetPositionMI.setDisable(false);
            OutlineRMI.setDisable(false);
            ConfigurationRMI.setDisable(false);
            ConfigurationRMI.setSelected(true);
            configurationMenu.setVisible(true);
            OutlineRMI.setSelected(true);
            componentRegion.setVisible(true);
            newMethodeUsedOnce = 1;
            addContextMenuDrawPane();
            initializeSelectors();
            changeConfigurations();
            scrollZoom();
        } else {
            if (drawPane.getChildren().isEmpty()) {
                clearDrawPane();
                zoomResetMethod();
                resetPositionMethod();
            } else {
                boolean check = showWarningDialog();
                if (check) {
                    drawPane.getChildren().clear();
                    clearDrawPane();
                    zoomResetMethod();
                    resetPositionMethod();
                }
            }
        }
    }

    void clearDrawPane() {
        drawPane.getChildren().clear();
        drawPane.setPrefWidth(4800);
        drawPane.setPrefHeight(3840);
        drawPane.setLayoutX(-1200);
        drawPane.setLayoutY(-960);
        initialWidth = 4800;
        initialHeight = 3840;
    }

    @FXML
    void configurationMethod() {
        configurationMenu.setVisible(ConfigurationRMI.isSelected());
        componentRegion.setVisible(ConfigurationRMI.isSelected());
        OutlineRMI.setSelected(ConfigurationRMI.isSelected());
    }

    @FXML
    void outlineMethod() {
        componentRegion.setVisible(OutlineRMI.isSelected());
    }

    @FXML
    void alignMethod() {
        ConfigurationView.align = !ConfigurationView.align;
    }

    void scrollZoom() {
        mainPane.setOnScroll(e -> {
            double deltaY = e.getDeltaY();
            if (deltaY > 0 && zoomTimes < 60) {
                zoomInMethod();
            } else if (deltaY < 0 && zoomTimes > 40) {
                zoomOutMethod();
            }
        });
    }

    @FXML
    void zoomInMethod() {
        if (zoomOutMI.isDisable()) {
            zoomOutMI.setDisable(false);
            zoomOutMenu.setDisable(true);
        }
        zoomTimes = zoomTimes + 1;
        mainPane.getTransforms().add(new Scale(ZOOM_FACTOR, ZOOM_FACTOR));
        componentRegion.getTransforms().add(new Scale(ZOOM_FACTOR, ZOOM_FACTOR));

        if (zoomTimes == 60) {
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
        mainPane.getTransforms().add(new Scale(scaleFactor, scaleFactor));
        componentRegion.getTransforms().add(new Scale(scaleFactor, scaleFactor));

        if (zoomTimes == 40) {
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
        zoomTimes = 50;
        mainPane.getTransforms().clear();
        componentRegion.getTransforms().clear();
    }

    void getOffsetsForMove() {
        mainPane.setOnMousePressed(e -> {
            if (e.isPrimaryButtonDown() || e.isMiddleButtonDown()) {
                Node target = e.getPickResult().getIntersectedNode();

                validDrag = target.equals(drawPane);

                xOffset = e.getSceneX() - drawPane.getLayoutX();
                yOffset = e.getSceneY() - drawPane.getLayoutY();
            }
        });
    }

    void addMovingDrawingPane() {
        drawPane.setOnMouseDragged(e -> {
            if ((e.isPrimaryButtonDown() && validDrag) || e.isMiddleButtonDown()) {
                double x = e.getSceneX() - xOffset;
                double y = e.getScreenY() - yOffset;
                drawPane.setLayoutX(x);
                drawPane.setLayoutY(y);
            }
        });
        drawPane.setOnMouseReleased(e -> validDrag = false);
    }

    @FXML
    void saveMethod() throws URISyntaxException {
        if (drawPane.getChildren().isEmpty()) {
            emptyWarning();
        } else {
            String jarPath = MainView.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
            String jarDir = new File(jarPath).getParent();
            String savesFolderPath = jarDir + File.separator + "saves";
            File savesDirectory = new File(savesFolderPath);

            // Create a FileChooser
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save draft");
            if (savesDirectory.exists()) {
                fileChooser.setInitialDirectory(savesDirectory);
            }

            // Set the extension filter (optional)
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);

            // Show the Save File dialog
            File file = fileChooser.showSaveDialog(getStage());

            if (file != null) {
                String drawPaneDimensions = (drawPane.getWidth() - initialWidth) + "," + (drawPane.getHeight() - initialHeight);
                writeDataToFile1(file, drawPaneDimensions);

                drawPane.getChildren().forEach(component -> {
                    if(component instanceof component_interface)
                        writeDataToFile2(file, component.getClass().getSimpleName() + "," + component.getLayoutX() + "," + component.getLayoutY() + "," + ((component_interface) component).getDataForSave());
                });
            }
        }
    }

    void writeDataToFile1(File file, String data) {
        try (FileWriter writer = new FileWriter(file)) {
            // Append data to the file without overwriting existing content
            writer.write(data);

            // Write a newline character to start a new line
            writer.write("\n");
        } catch (IOException ignored) {
        }
    }

    static void writeDataToFile2(File file, String data) {
        try (FileWriter writer = new FileWriter(file, true)) {
            // Write data to the file, overwriting existing content
            writer.write(data);

            // Write a newline character to start a new line
            writer.write("\n");
        } catch (IOException ignored) {
        }
    }

    @FXML
    void loadMethod() throws URISyntaxException {
        String jarPath = MainView.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        String jarDir = new File(jarPath).getParent();
        String savesFolderPath = jarDir + File.separator + "saves";
        File savesDirectory = new File(savesFolderPath);

        // Create a FileChooser
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load draft");
        if (savesDirectory.exists()) {
            fileChooser.setInitialDirectory(savesDirectory);
        }
        // Set the extension filter to allow only text files (*.txt)
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show the Open File dialog
        File selectedFile = fileChooser.showOpenDialog(getStage());

        if (selectedFile != null) {
            if (drawPane.getChildren().isEmpty() || drawPane.getChildren().getFirst() instanceof ImageView) {
                drawPane.getChildren().clear();
                newMethod();
                processFile(selectedFile);
            } else {
                boolean check = showWarningDialog();
                if (check) {
                    drawPane.getChildren().clear();
                    newMethod();
                    processFile(selectedFile);
                }
            }
        }
    }

    private boolean showWarningDialog() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UTILITY);
        alert.setGraphic(null);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Your draft is not empty, do you want to continue?");

        // Set up Yes and No buttons
        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        // Show the dialog and wait for a result
        Optional<ButtonType> result = alert.showAndWait();

        // Handle the result
        return result.isPresent() && result.get() == buttonTypeYes;
    }

    private void emptyWarning() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.initStyle(StageStyle.UTILITY);
        alert.setGraphic(null);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Your draft is empty");
        alert.show();
    }

    void processFile(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            boolean OK = false;
            double addWidth = 1;
            double addHeight = 1;
            // Read each line from the file
            while ((line = reader.readLine()) != null) {
                // Process the current line
                String[] data = line.split(",");
                if (OK) {
                    addComponentFromLoadData(data);
                } else {
                    addWidth = Double.parseDouble(data[0]);
                    addHeight = Double.parseDouble(data[1]);
                    drawPane.setPrefWidth(initialWidth + addWidth);
                    drawPane.setPrefHeight(initialHeight + addHeight);
                    drawPane.setLayoutX(drawPane.getLayoutX() - addWidth / 2);
                    drawPane.setLayoutY(drawPane.getLayoutY() - addHeight / 2);
                    OK = true;
                }
            }
            for (Node child : drawPane.getChildren()) {
                child.setTranslateX(child.getTranslateX() + addWidth / 2);
                child.setTranslateY(child.getTranslateY() + addHeight / 2);
            }
        } catch (IOException ignored) {
        }
    }

    void addComponentFromLoadData(String[] data) {
        if (Objects.equals(data[0], "Curve_roller_conveyor")) {
            Curve_roller_conveyor newComponent = new Curve_roller_conveyor(Double.parseDouble(data[4]), Double.parseDouble(data[5]), Double.parseDouble(data[6]), data[7], data[8], data[9], data[10], data[11]);
            newComponent.setLayoutX(Double.parseDouble(data[1]));
            newComponent.setLayoutY(Double.parseDouble(data[2]));
            newComponent.setNewRotation(Integer.parseInt(data[3]));
            newComponent.updateMirrorText(Integer.parseInt(data[12]));
            newComponent.updateRevert(Integer.parseInt(data[13]));
            addActionsToComponent(newComponent, newComponent.getWidthForLoad(), newComponent.getWidthForLoad());
            drawPane.getChildren().add(newComponent);
        }
        if (Objects.equals(data[0], "Diverter")) {
            Diverter newComponent = new Diverter(Double.parseDouble(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]), data[9], data[10], data[11], data[12], data[13], data[14]);
            newComponent.setLayoutX(Double.parseDouble(data[1]));
            newComponent.setLayoutY(Double.parseDouble(data[2]));
            newComponent.setNewRotation(Integer.parseInt(data[3]));
            newComponent.updateMirrorText(Integer.parseInt(data[15]));
            addActionsToComponent(newComponent, newComponent.getWidthForLoad(), newComponent.getHeightLoad());
            drawPane.getChildren().add(newComponent);
        }
        if (Objects.equals(data[0], "Gravity_roller_conveyor")) {
            Gravity_roller_conveyor newComponent = new Gravity_roller_conveyor(Double.parseDouble(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]), data[9], data[10], data[11], data[12]);
            newComponent.setLayoutX(Double.parseDouble(data[1]));
            newComponent.setLayoutY(Double.parseDouble(data[2]));
            newComponent.setNewRotation(Integer.parseInt(data[3]));
            newComponent.updateMirrorText(Integer.parseInt(data[13]));
            addActionsToComponent(newComponent, newComponent.getWidthForLoad(), newComponent.getHeightLoad());
            drawPane.getChildren().add(newComponent);
        }
        if (Objects.equals(data[0], "Merge_conveyor")) {
            Merge_conveyor newComponent = new Merge_conveyor(Double.parseDouble(data[4]), Integer.parseInt(data[5]), Double.parseDouble(data[6]), data[7], data[8], data[9], data[10], data[11], data[12]);
            newComponent.setLayoutX(Double.parseDouble(data[1]));
            newComponent.setLayoutY(Double.parseDouble(data[2]));
            newComponent.setNewRotation(Integer.parseInt(data[3]));
            newComponent.updateMirrorText(Integer.parseInt(data[13]));
            addActionsToComponent(newComponent, newComponent.getWidthForLoad(), newComponent.getHeightLoad());
            drawPane.getChildren().add(newComponent);

        }
        if (Objects.equals(data[0], "Motorized_roller_conveyor")) {
            Motorized_roller_conveyor newComponent = new Motorized_roller_conveyor(Double.parseDouble(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]), data[9], data[10], data[11], data[12], data[13], data[14], data[15]);
            newComponent.setLayoutX(Double.parseDouble(data[1]));
            newComponent.setLayoutY(Double.parseDouble(data[2]));
            newComponent.setNewRotation(Integer.parseInt(data[3]));
            newComponent.updateMirrorText(Integer.parseInt(data[16]));
            addActionsToComponent(newComponent, newComponent.getWidthForLoad(), newComponent.getHeightLoad());
            drawPane.getChildren().add(newComponent);
        }
        if (Objects.equals(data[0], "Skew_roller_conveyor")) {
            Skew_roller_conveyor newComponent = new Skew_roller_conveyor(Double.parseDouble(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]), data[9], data[10], data[11], data[12], data[13], data[14]);
            newComponent.setLayoutX(Double.parseDouble(data[1]));
            newComponent.setLayoutY(Double.parseDouble(data[2]));
            newComponent.setNewRotation(Integer.parseInt(data[3]));
            newComponent.updateMirrorText(Integer.parseInt(data[15]));
            addActionsToComponent(newComponent, newComponent.getWidthForLoad(), newComponent.getHeightLoad());
            drawPane.getChildren().add(newComponent);

        }
        if (Objects.equals(data[0], "Transfer_module_90_degree")) {
            Transfer_module_90_degree newComponent = new Transfer_module_90_degree(Double.parseDouble(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]), Double.parseDouble(data[7]), Double.parseDouble(data[8]), data[9], data[10], data[11], data[12], data[13], data[14]);
            newComponent.setLayoutX(Double.parseDouble(data[1]));
            newComponent.setLayoutY(Double.parseDouble(data[2]));
            newComponent.setNewRotation(Integer.parseInt(data[3]));
            newComponent.updateMirrorText(Integer.parseInt(data[15]));
            addActionsToComponent(newComponent, newComponent.getWidthForLoad(), newComponent.getHeightLoad());
            drawPane.getChildren().add(newComponent);
        }
    }

    @FXML
    void exportMethod() throws URISyntaxException {
        if (drawPane.getChildren().isEmpty()) {
            emptyWarning();
        } else {
            double[] bounds = getExportLimits();
            Rectangle2D exportBounds = new Rectangle2D(bounds[0], bounds[1], bounds[2], bounds[3]);
            exportImage(exportBounds);
        }
    }

    double[] getExportLimits() {
        Node component = drawPane.getChildren().getFirst();
        double x1 = component.getLayoutX();
        double y1 = component.getLayoutY();
        double x2 = x1 + component.getBoundsInLocal().getWidth();
        double y2 = y1 + component.getBoundsInLocal().getHeight();

        if (drawPane.getChildren().size() > 1)
            for (int i = 1; i < drawPane.getChildren().size(); i++) {
                component = drawPane.getChildren().get(i);
                if (component.getLayoutX() < x1) x1 = component.getLayoutX();
                if (component.getLayoutY() < y1) y1 = component.getLayoutY();
                if (component.getLayoutX() + component.getBoundsInLocal().getWidth() > x2)
                    x2 = component.getLayoutX() + component.getBoundsInLocal().getWidth();
                if (component.getLayoutY() + component.getBoundsInLocal().getHeight() > y2)
                    y2 = component.getLayoutY() + component.getBoundsInLocal().getHeight();
            }
        double exportWidth = x2 - x1 + 50;
        double exportHeight = y2 - y1 + 50;
        x1 = x1 + drawPane.getLayoutX() - 25;
        y1 = y1 + drawPane.getLayoutY() - 25;
        return new double[]{x1, y1, exportWidth, exportHeight};
    }

    private void exportImage(Rectangle2D bounds) throws URISyntaxException {
        // Create a writable image
        WritableImage writableImage = new WritableImage((int) bounds.getWidth(), (int) bounds.getHeight());
        SnapshotParameters snapshotParameters = new SnapshotParameters();

        // Set the fill and transform of the snapshot parameters to capture the specified bounds
        snapshotParameters.setFill(Color.TRANSPARENT);
        snapshotParameters.setViewport(bounds);

        // Capture the snapshot of the specified bounds in the pane
        drawPane.snapshot(snapshotParameters, writableImage);

        // Set the initial directory inside the src folder
        String jarPath = MainView.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
        String jarDir = new File(jarPath).getParent();
        String exportsFolderPath = jarDir + File.separator + "exports" + File.separator + "drafts";
        File exportDirectory = new File(exportsFolderPath);

        // Choose a file to save the image
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save draft");
        if (exportDirectory.exists()) {
            fileChooser.setInitialDirectory(exportDirectory);
        }
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG Files", "*.png"));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            try {
                // Get the pixel data from the writable image
                BufferedImage bufferedImage = getBufferedImage(writableImage);

                // Save the BufferedImage to the chosen file
                ImageIO.write(bufferedImage, "png", file);
            } catch (IOException ignored) {
            }
        }
    }

    private static BufferedImage getBufferedImage(WritableImage writableImage) {
        PixelReader pixelReader = writableImage.getPixelReader();
        int width = (int) writableImage.getWidth();
        int height = (int) writableImage.getHeight();

        // Create a BufferedImage and copy pixel data
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                Color color = pixelReader.getColor(x, y);
                int argb = (int) (color.getOpacity() * 255) << 24 |
                        (int) (color.getRed() * 255) << 16 |
                        (int) (color.getGreen() * 255) << 8 |
                        (int) (color.getBlue() * 255);
                bufferedImage.setRGB(x, y, argb);
            }
        }
        return bufferedImage;
    }

    @FXML
    void extractExcelMethod() {
        if (drawPane.getChildren().isEmpty()) {
            emptyWarning();
        } else {
            exportExcel.startExport(drawPane);
        }
    }

    void changeConfigurations() {
        componentTypeComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            int selectedIndex = componentTypeComboBox.getItems().indexOf(newValue);
            switch (selectedIndex) {
                case 0:
                    //System.out.println("Motorized roller conveyor");
                    componentLengthComboBox.setVisible(true);
                    componentSkewLengthComboBox.setVisible(false);
                    labelLength.setText("Length (mm)");
                    labelLength.setVisible(true);
                    componentLengthComboBox.setVisible(true);
                    textLength90Transfer.setVisible(false);
                    componentAngleComboBox.setVisible(false);
                    componentRollerPitchComboBox.setVisible(true);
                    labelPitch.setVisible(true);
                    componentPolyVeeSideComboBox.setVisible(true);
                    labelPolySide.setVisible(true);
                    componentNoOfMDRComboBox.setVisible(true);
                    labelNoMDR.setVisible(true);
                    labelNoMDR.setText("No. of MDR");
                    textSpeed2.setVisible(false);
                    labelSpeed.setText("Speed (m/min)");
                    labelHeight.setText("Height (mm)");
                    textAuxHeight.setVisible(false);
                    textSpeed1.setVisible(true);
                    labelControl.setDisable(false);
                    componentControlComboBox.setDisable(false);
                    componentDivertingAngleComboBox.setVisible(false);
                    componentDiverterLengthComboBox.setVisible(false);
                    labelDivDirection.setVisible(false);
                    componentDivDirectionComboBox.setVisible(false);
                    componentRegion.setPrefWidth((Integer.parseInt(componentLengthComboBox.getSelectionModel().getSelectedItem())) * scaleFactor);
                    componentRegion.setPrefHeight((Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70) * scaleFactor);
                    break;

                case 1:
                    //System.out.println("Gravity roller conveyor");
                    componentLengthComboBox.setVisible(true);
                    componentSkewLengthComboBox.setVisible(false);
                    labelLength.setText("Length (mm)");
                    labelLength.setVisible(true);
                    componentLengthComboBox.setVisible(true);
                    textLength90Transfer.setVisible(false);
                    componentAngleComboBox.setVisible(false);
                    componentRollerPitchComboBox.setVisible(true);
                    labelPitch.setVisible(true);
                    componentPolyVeeSideComboBox.setVisible(false);
                    labelPolySide.setVisible(false);
                    componentNoOfMDRComboBox.setVisible(false);
                    labelNoMDR.setVisible(false);
                    labelNoMDR.setText("No. of MDR");
                    textSpeed2.setVisible(false);
                    labelSpeed.setText("Height1 (mm)");
                    labelHeight.setText("Height2 (mm)");
                    textAuxHeight.setVisible(true);
                    textSpeed1.setVisible(true);
                    labelControl.setDisable(true);
                    componentControlComboBox.setDisable(true);
                    componentDivertingAngleComboBox.setVisible(false);
                    componentDiverterLengthComboBox.setVisible(false);
                    labelDivDirection.setVisible(false);
                    componentDivDirectionComboBox.setVisible(false);
                    componentRegion.setPrefWidth((Integer.parseInt(componentLengthComboBox.getSelectionModel().getSelectedItem())) * scaleFactor);
                    componentRegion.setPrefHeight((Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70) * scaleFactor);
                    break;

                case 2:
                    //System.out.println("90 degree transfer module");
                    componentLengthComboBox.setVisible(false);
                    componentSkewLengthComboBox.setVisible(false);
                    labelLength.setText("Length (mm)");
                    labelLength.setVisible(true);
                    componentLengthComboBox.setVisible(true);
                    textLength90Transfer.setVisible(true);
                    componentAngleComboBox.setVisible(false);
                    componentRollerPitchComboBox.setVisible(false);
                    labelPitch.setVisible(false);
                    componentPolyVeeSideComboBox.setVisible(false);
                    labelPolySide.setVisible(false);
                    componentNoOfMDRComboBox.setVisible(false);
                    textSpeed2.setVisible(true);
                    textAuxHeight.setVisible(false);
                    textSpeed1.setVisible(true);
                    labelNoMDR.setVisible(true);
                    labelNoMDR.setText("B Speed (m/min)");
                    labelSpeed.setText("R Speed (m/min)");
                    labelHeight.setText("Height (mm)");
                    labelControl.setDisable(false);
                    componentControlComboBox.setDisable(false);
                    componentDivertingAngleComboBox.setVisible(false);
                    componentDiverterLengthComboBox.setVisible(false);
                    labelDivDirection.setVisible(false);
                    componentDivDirectionComboBox.setVisible(false);
                    componentRegion.setPrefWidth(780 * scaleFactor);
                    componentRegion.setPrefHeight((Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70) * scaleFactor);
                    break;

                case 3:
                    //System.out.println("Merge conveyor");
                    componentLengthComboBox.setVisible(true);
                    componentSkewLengthComboBox.setVisible(false);
                    labelLength.setText("Length (mm)");
                    labelLength.setVisible(false);
                    componentLengthComboBox.setVisible(false);
                    textLength90Transfer.setVisible(false);
                    componentAngleComboBox.setVisible(false);
                    componentRollerPitchComboBox.setVisible(true);
                    labelPitch.setVisible(true);
                    componentPolyVeeSideComboBox.setVisible(true);
                    labelPolySide.setVisible(true);
                    componentNoOfMDRComboBox.setVisible(false);
                    labelNoMDR.setVisible(false);
                    textSpeed2.setVisible(false);
                    labelSpeed.setText("Speed (m/min)");
                    labelHeight.setText("Height (mm)");
                    textAuxHeight.setVisible(false);
                    textSpeed1.setVisible(true);
                    labelControl.setDisable(false);
                    componentControlComboBox.setDisable(false);
                    componentDivertingAngleComboBox.setVisible(false);
                    componentDiverterLengthComboBox.setVisible(false);
                    labelDivDirection.setVisible(false);
                    componentDivDirectionComboBox.setVisible(false);
                    switch (componentWidthComboBox.getSelectionModel().getSelectedIndex()) {
                        case 0:
                            componentRegion.setPrefWidth(988 * scaleFactor);
                            break;
                        case 1:
                            componentRegion.setPrefWidth(1169 * scaleFactor);
                            break;
                        case 2:
                            componentRegion.setPrefWidth(1349 * scaleFactor);
                            break;
                        case 3:
                            componentRegion.setPrefWidth(1529 * scaleFactor);
                            break;
                        case 4:
                            componentRegion.setPrefWidth(1709 * scaleFactor);
                            break;
                        case 5:
                            componentRegion.setPrefWidth(1889 * scaleFactor);
                            break;
                    }
                    componentRegion.setPrefHeight((Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70) * scaleFactor);
                    break;

                case 4:
                    //System.out.println("Curve roller conveyor");
                    componentLengthComboBox.setVisible(false);
                    componentSkewLengthComboBox.setVisible(false);
                    labelLength.setText("Angle");
                    labelLength.setVisible(true);
                    textLength90Transfer.setVisible(false);
                    componentAngleComboBox.setVisible(true);
                    componentRollerPitchComboBox.setVisible(false);
                    labelPitch.setVisible(false);
                    componentPolyVeeSideComboBox.setVisible(false);
                    labelPolySide.setVisible(false);
                    componentNoOfMDRComboBox.setVisible(false);
                    labelNoMDR.setVisible(false);
                    textSpeed2.setVisible(false);
                    labelSpeed.setText("Speed (m/min)");
                    labelHeight.setText("Height (mm)");
                    textAuxHeight.setVisible(false);
                    textSpeed1.setVisible(true);
                    labelControl.setDisable(false);
                    componentControlComboBox.setDisable(false);
                    componentDivertingAngleComboBox.setVisible(false);
                    componentDiverterLengthComboBox.setVisible(false);
                    labelDivDirection.setVisible(false);
                    componentDivDirectionComboBox.setVisible(false);
                    componentRegion.setPrefWidth((Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70 + 790) * scaleFactor);
                    componentRegion.setPrefHeight((Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70 + 790) * scaleFactor);
                    break;

                case 5:
                    //System.out.println("Skew roller conveyor");
                    componentLengthComboBox.setVisible(false);
                    componentSkewLengthComboBox.setVisible(true);
                    labelLength.setText("Length (mm)");
                    labelLength.setVisible(true);
                    textLength90Transfer.setVisible(false);
                    componentAngleComboBox.setVisible(false);
                    componentRollerPitchComboBox.setVisible(true);
                    labelPitch.setVisible(true);
                    componentPolyVeeSideComboBox.setVisible(true);
                    labelPolySide.setVisible(true);
                    componentNoOfMDRComboBox.setVisible(false);
                    componentDivertingAngleComboBox.setVisible(false);
                    labelNoMDR.setVisible(false);
                    labelNoMDR.setText("No. of MDR");
                    textSpeed2.setVisible(false);
                    labelSpeed.setText("Speed (m/min)");
                    labelHeight.setText("Height (mm)");
                    textAuxHeight.setVisible(false);
                    textSpeed1.setVisible(true);
                    labelControl.setDisable(false);
                    componentControlComboBox.setDisable(false);
                    componentDiverterLengthComboBox.setVisible(false);
                    labelDivDirection.setVisible(false);
                    componentDivDirectionComboBox.setVisible(false);
                    componentRegion.setPrefWidth((Integer.parseInt(componentSkewLengthComboBox.getSelectionModel().getSelectedItem())) * scaleFactor);
                    componentRegion.setPrefHeight((Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70) * scaleFactor);
                    break;

                case 6:
                    //System.out.println("Diverter");
                    componentLengthComboBox.setVisible(false);
                    componentSkewLengthComboBox.setVisible(false);
                    componentDiverterLengthComboBox.setVisible(true);
                    labelLength.setText("Length (mm)");
                    labelLength.setVisible(true);
                    componentLengthComboBox.setVisible(true);
                    textLength90Transfer.setVisible(false);
                    componentAngleComboBox.setVisible(false);
                    componentRollerPitchComboBox.setVisible(true);
                    labelPitch.setVisible(true);
                    componentPolyVeeSideComboBox.setVisible(true);
                    labelPolySide.setVisible(true);
                    componentNoOfMDRComboBox.setVisible(false);
                    componentDivertingAngleComboBox.setVisible(true);
                    labelNoMDR.setVisible(true);
                    labelNoMDR.setText("Diverting angle");
                    textSpeed2.setVisible(false);
                    labelSpeed.setText("Speed (m/min)");
                    labelHeight.setText("Height (mm)");
                    textAuxHeight.setVisible(false);
                    textSpeed1.setVisible(true);
                    labelControl.setDisable(false);
                    labelDivDirection.setVisible(true);
                    componentDivDirectionComboBox.setVisible(true);
                    componentControlComboBox.setDisable(false);
                    componentRegion.setPrefWidth((Integer.parseInt(componentDiverterLengthComboBox.getSelectionModel().getSelectedItem())) * scaleFactor);
                    componentRegion.setPrefHeight((Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70 + 220) * scaleFactor);
                    break;
            }
        });
    }
}
