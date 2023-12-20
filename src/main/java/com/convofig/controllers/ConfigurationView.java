package com.convofig.controllers;

import com.convofig.components.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;

import java.util.Optional;

public class ConfigurationView extends MainView {
    private static Pane mainPane, drawPane;
    private static ComboBox<String> componentTypeComboBox, componentWidthComboBox, componentLengthComboBox, componentRollerPitchComboBox, componentPolyVeeSideComboBox, componentNoOfMDRComboBox, componentAngleComboBox, componentControlComboBox;
    private static TextField textTitle, textSpeed1, textSpeed2, textHeight;
    private static Region componentRegion;
    private static double scaleFactor;
    static Boolean align = false;
    static Boolean zone_confirm = false;
    private static double off_setInitialX;
    private static double off_setInitialY;

    ConfigurationView(double scaleFactor, Pane mainPane, Pane drawPane, Region componentRegion, ComboBox<String> componentTypeComboBox, ComboBox<String> componentWidthComboBox, ComboBox<String> componentLengthComboBox, ComboBox<String> componentRollerPitchComboBox, ComboBox<String> componentPolyVeeSideComboBox, ComboBox<String> componentNoOfMDRComboBox, TextField textTitle, TextField textSpeed1, TextField textSpeed2, TextField textHeight, ComboBox<String> componentAngleComboBox, ComboBox<String> componentControlComboBox) {
        ConfigurationView.mainPane = mainPane;
        ConfigurationView.drawPane = drawPane;
        ConfigurationView.componentTypeComboBox = componentTypeComboBox;
        ConfigurationView.componentWidthComboBox = componentWidthComboBox;
        ConfigurationView.componentLengthComboBox = componentLengthComboBox;
        ConfigurationView.componentRegion = componentRegion;
        ConfigurationView.componentRollerPitchComboBox = componentRollerPitchComboBox;
        ConfigurationView.componentPolyVeeSideComboBox = componentPolyVeeSideComboBox;
        ConfigurationView.componentNoOfMDRComboBox = componentNoOfMDRComboBox;
        ConfigurationView.componentAngleComboBox = componentAngleComboBox;
        ConfigurationView.componentControlComboBox = componentControlComboBox;
        ConfigurationView.textTitle = textTitle;
        ConfigurationView.textSpeed1 = textSpeed1;
        ConfigurationView.textSpeed2 = textSpeed2;
        ConfigurationView.textHeight = textHeight;
        ConfigurationView.scaleFactor = scaleFactor;
        changeRegion();
    }

    void changeRegion() {
        componentWidthComboBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            componentRegion.setPrefHeight((double) (Integer.parseInt(componentWidthComboBox.getItems().get((Integer) t1)) + 70) * scaleFactor);
            if (componentTypeComboBox.getSelectionModel().getSelectedIndex() == 2) {
                componentRegion.setPrefHeight((double) (Integer.parseInt(componentWidthComboBox.getItems().get((Integer) t1)) + 70) * scaleFactor);
                componentRegion.setPrefWidth(780 * scaleFactor);
            }
            if (componentTypeComboBox.getSelectionModel().getSelectedIndex() == 4) {
                componentRegion.setPrefHeight((double) (Integer.parseInt(componentWidthComboBox.getItems().get((Integer) t1)) + 70 + 790) * scaleFactor);
                componentRegion.setPrefWidth((double) (Integer.parseInt(componentWidthComboBox.getItems().get((Integer) t1)) + 70 + 790) * scaleFactor);
            }
        });
        componentLengthComboBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> componentRegion.setPrefWidth((double) Integer.parseInt(componentLengthComboBox.getItems().get((Integer) t1)) * scaleFactor));
    }

    static void addComponent() {
        Node component = null;
        double width = (double) Integer.parseInt(componentLengthComboBox.getSelectionModel().getSelectedItem()) * scaleFactor;
        double height = (double) (Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70) * scaleFactor;
        int preScaleWidth = Integer.parseInt(componentLengthComboBox.getSelectionModel().getSelectedItem());
        int preScaleHeight = Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem());
        switch (componentTypeComboBox.getSelectionModel().getSelectedIndex()) {
            case 0:
                //System.out.println("Motorized roller conveyor");
                component = new Motorized_roller_conveyor(scaleFactor, preScaleWidth, preScaleHeight, width, height, componentPolyVeeSideComboBox.getSelectionModel().getSelectedItem(), componentNoOfMDRComboBox.getSelectionModel().getSelectedItem(), textHeight.getText(), textSpeed1.getText(), componentRollerPitchComboBox.getSelectionModel().getSelectedItem(), textTitle.getText(), componentControlComboBox.getSelectionModel().getSelectedItem());
                break;

            case 1:
                //System.out.println("Gravity roller conveyor");
                component = new Gravity_roller_conveyor();
                break;

            case 2:
                //System.out.println("90 degree transfer module");
                width = 780 * scaleFactor;
                preScaleWidth = 780;
                component = new Transfer_module_90_degree(scaleFactor, preScaleWidth, preScaleHeight, width, height, textHeight.getText(), textSpeed1.getText(), textSpeed2.getText(), componentRollerPitchComboBox.getSelectionModel().getSelectedItem(), textTitle.getText(), componentControlComboBox.getSelectionModel().getSelectedItem());
                break;

            case 3:
                //System.out.println("Merge conveyor");
                component = new Merge_conveyor();
                break;

            case 4:
                //System.out.println("Curve roller conveyor");
                height = (double) (Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70 + 790) * scaleFactor;
                width = (double) (Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70 + 790) * scaleFactor;
                component = new Curve_roller_conveyor(scaleFactor, preScaleHeight, height, textHeight.getText(), textSpeed1.getText(), textTitle.getText(), componentAngleComboBox.getSelectionModel().getSelectedItem(), componentControlComboBox.getSelectionModel().getSelectedItem());
                break;

            case 5:
                //System.out.println("Skew roller conveyor");
                component = new Skew_roller_conveyor();
                break;

            case 6:
                //System.out.println("Diverter");
                component = new Diverter();
                break;
        }
        if (component != null) {
            double scaleFactorX = mainPane.getLocalToSceneTransform().getMxx();
            double scaleFactorY = mainPane.getLocalToSceneTransform().getMyy();

            double adjustedLayoutX = 300 - drawPane.getLayoutX() * scaleFactorX;
            double adjustedLayoutY = 20 - drawPane.getLayoutY() * scaleFactorY;

            // Adjust the layout coordinates based on the scaling factors
            adjustedLayoutX /= scaleFactorX;
            adjustedLayoutY /= scaleFactorY;
            // Check if the adjusted layout coordinates are within the bounds of drawPane
            if (adjustedLayoutX >= 0 && adjustedLayoutX + component.getBoundsInLocal().getWidth() <= drawPane.getWidth() - width
                    && adjustedLayoutY >= 0 && adjustedLayoutY + component.getBoundsInLocal().getHeight() <= drawPane.getHeight() - height) {
                component.setLayoutX(adjustedLayoutX);
                component.setLayoutY(adjustedLayoutY);
                addActionsToComponent(component, width, height);
                drawPane.getChildren().add(component);
            } else {
                showAlert();
            }
        }
    }

    private static void showAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText("Component outside the drawing zone");
        alert.showAndWait();
    }

    static void addActionsToComponent(Node component, double width, double height) {
        double ALIGNMENT_THRESHOLD = 20;
        String currentName = null;

        // Rotation context menu
        ContextMenu contextMenu = new ContextMenu();
        MenuItem rotateMenuItem = new MenuItem("Rotate");
        rotateMenuItem.setOnAction(event -> showRotateDialog(component));
        contextMenu.getItems().add(rotateMenuItem);
        MenuItem clearRotateMenuItem = new MenuItem("Clear");
        clearRotateMenuItem.setOnAction(event -> clearRotation(component));
        contextMenu.getItems().add(clearRotateMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        MenuItem copyComponentMenuItem = new MenuItem("Copy");
        copyComponentMenuItem.setOnAction(event -> copyComponent(component, width, height));
        contextMenu.getItems().add(copyComponentMenuItem);
        MenuItem renameComponentMenuItem = new MenuItem("Rename");
        if (component instanceof Curve_roller_conveyor)
            currentName = ((Curve_roller_conveyor) component).getCurrentName();
        //if (component instanceof Diverter)
        //if (component instanceof Gravity_roller_conveyor)
        //if (component instanceof Merge_conveyor)
        if (component instanceof Motorized_roller_conveyor)
            currentName = ((Motorized_roller_conveyor) component).getCurrentName();
        //if (component instanceof Skew_roller_conveyor)
        if (component instanceof Transfer_module_90_degree)
            currentName = ((Transfer_module_90_degree) component).getCurrentName();
        String finalCurrentName = currentName;
        renameComponentMenuItem.setOnAction(event -> renameComponent(component, finalCurrentName));
        contextMenu.getItems().add(renameComponentMenuItem);
        MenuItem sideComponentMenuItem = new MenuItem("Change side");
        sideComponentMenuItem.setVisible(false);
        if(component instanceof  Motorized_roller_conveyor){
            sideComponentMenuItem.setVisible(true);
            sideComponentMenuItem.setOnAction(event -> ((Motorized_roller_conveyor) component).changeSide());
        }
        contextMenu.getItems().add(sideComponentMenuItem);
        MenuItem removeComponentMenuItem = new MenuItem("Remove");
        removeComponentMenuItem.setOnAction(event -> removeComponent(component));
        contextMenu.getItems().add(removeComponentMenuItem);

        component.setOnMouseEntered(e -> component.setStyle("-fx-border-color: blue ;\n" +
                "    -fx-border-width: 2 ; \n" +
                "    -fx-border-style: segments(2, 3, 3, 3)  line-cap round ; "));
        component.setOnMouseExited(e -> component.setStyle("-fx-border-style: none;"));

        component.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {

                double scale = drawPane.getLocalToSceneTransform().getMxx();
                off_setInitialX = (e.getSceneX() / scale) - component.getLayoutX();
                off_setInitialY = (e.getSceneY() / scale) - component.getLayoutY();
                if (component instanceof Curve_roller_conveyor) {
                    if (e.getX() > 0 && e.getX() <= height + 790 * scaleFactor && e.getY() > 0 && e.getY() <= height + 790 * scaleFactor)
                        zone_confirm = true;
                } else if (e.getX() > 0 && e.getX() <= width && e.getY() > 0 && e.getY() <= height)
                    zone_confirm = true;
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(component, e.getScreenX(), e.getScreenY());
            }
        });

        component.setOnMouseReleased(e -> zone_confirm = false);

        component.setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.PRIMARY && zone_confirm) {
                component.setStyle("-fx-border-style: none;");
                double scale = drawPane.getLocalToSceneTransform().getMxx();

                double x = e.getSceneX() / scale - off_setInitialX;
                double y = e.getSceneY() / scale - off_setInitialY;
                // Check for alignment with other components
                if (align) {
                    for (Node node : drawPane.getChildren()) {
                        if (node instanceof Curve_roller_conveyor || node instanceof Diverter || node instanceof Gravity_roller_conveyor || node instanceof Merge_conveyor || node instanceof Motorized_roller_conveyor || node instanceof Skew_roller_conveyor || node instanceof Transfer_module_90_degree) {
                            double nodeX = node.getLayoutX();
                            double nodeY = node.getLayoutY();

                            // Check alignment in both directions
                            if (Math.abs(x + component.getBoundsInLocal().getWidth() - nodeX) < ALIGNMENT_THRESHOLD) {
                                // Align horizontally
                                x = nodeX - component.getBoundsInLocal().getWidth();
                            } else if (Math.abs(x - (nodeX + node.getBoundsInLocal().getWidth())) < ALIGNMENT_THRESHOLD) {
                                // Align horizontally
                                x = nodeX + node.getBoundsInLocal().getWidth();
                            }

                            if (Math.abs(y + component.getBoundsInLocal().getHeight() - nodeY) < ALIGNMENT_THRESHOLD) {
                                // Align vertically
                                y = nodeY - component.getBoundsInLocal().getHeight();
                            } else if (Math.abs(y - (nodeY + node.getBoundsInLocal().getHeight())) < ALIGNMENT_THRESHOLD) {
                                // Align vertically
                                y = nodeY + node.getBoundsInLocal().getHeight();
                            }
                        }
                    }
                }
                if (x > 0 && x < drawPane.getWidth() - component.getBoundsInLocal().getWidth()
                        && y > 0
                        && y < drawPane.getHeight() - component.getBoundsInLocal().getHeight()) {
                    component.setLayoutX(x);
                    component.setLayoutY(y);
                }
            }
        });
    }

    private static void copyComponent(Node component, double width, double height) {
        Node copy = null;
        if (component instanceof Curve_roller_conveyor)
            copy = ((Curve_roller_conveyor) component).copyComponent();
        if (component instanceof Diverter)
            copy = ((Diverter) component).copyComponent();
        if (component instanceof Gravity_roller_conveyor)
            copy = ((Gravity_roller_conveyor) component).copyComponent();
        if (component instanceof Merge_conveyor)
            copy = ((Merge_conveyor) component).copyComponent();
        if (component instanceof Motorized_roller_conveyor)
            copy = ((Motorized_roller_conveyor) component).copyComponent();
        if (component instanceof Skew_roller_conveyor)
            copy = ((Skew_roller_conveyor) component).copyComponent();
        if (component instanceof Transfer_module_90_degree)
            copy = ((Transfer_module_90_degree) component).copyComponent();

        if (copy != null) {
            addActionsToComponent(copy, width, height);
            drawPane.getChildren().add(copy);
        }
    }

    private static void renameComponent(Node component, String currentName) {
        TextInputDialog dialog = new TextInputDialog(currentName);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle("Rename component");
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText("New name:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            // Assuming Node class has a 'setName' method
            if (component instanceof Curve_roller_conveyor)
                ((Curve_roller_conveyor) component).modifyTitle(result.get());
            //if (component instanceof Diverter)
            //if (component instanceof Gravity_roller_conveyor)
            //if (component instanceof Merge_conveyor)
            if (component instanceof Motorized_roller_conveyor)
                ((Motorized_roller_conveyor) component).modifyTitle(result.get());
            //if (component instanceof Skew_roller_conveyor)
            if (component instanceof Transfer_module_90_degree)
                ((Transfer_module_90_degree) component).modifyTitle(result.get());
        });
    }

    private static void removeComponent(Node component) {
        drawPane.getChildren().remove(component);
    }

    private static void clearRotation(Node component) {
        if (component instanceof Curve_roller_conveyor) {
            ((Curve_roller_conveyor) component).resetRotation();
        }
        if (component instanceof Diverter) {
            ((Diverter) component).resetRotation();
        }
        if (component instanceof Gravity_roller_conveyor) {
            ((Gravity_roller_conveyor) component).resetRotation();
        }
        if (component instanceof Merge_conveyor) {
            ((Merge_conveyor) component).resetRotation();
        }
        if (component instanceof Motorized_roller_conveyor) {
            ((Motorized_roller_conveyor) component).resetRotation();
        }
        if (component instanceof Skew_roller_conveyor) {
            ((Skew_roller_conveyor) component).resetRotation();
        }
        if (component instanceof Transfer_module_90_degree) {
            ((Transfer_module_90_degree) component).resetRotation();
        }
    }

    private static void showRotateDialog(Node component) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Rotate");
        dialog.setHeaderText(null); // Remove the header
        dialog.setContentText("Rotation angle:");
        dialog.getDialogPane().setGraphic(null);
        dialog.getDialogPane().setPrefWidth(200);
        // Customizing the content of the dialog
        dialog.showAndWait().ifPresent(angle -> handleRotationInput(component, angle));
    }

    private static void handleRotationInput(Node component, String angle) {
        try {
            int rotationAngle = Integer.parseInt(angle);
            if (rotationAngle >= -360 && rotationAngle <= 360) {
                if (component instanceof Curve_roller_conveyor) {
                    ((Curve_roller_conveyor) component).setNewRotation(rotationAngle);
                }
                if (component instanceof Diverter) {
                    ((Diverter) component).setNewRotation(rotationAngle);
                }
                if (component instanceof Gravity_roller_conveyor) {
                    ((Gravity_roller_conveyor) component).setNewRotation(rotationAngle);
                }
                if (component instanceof Merge_conveyor) {
                    ((Merge_conveyor) component).setNewRotation(rotationAngle);
                }
                if (component instanceof Motorized_roller_conveyor) {
                    ((Motorized_roller_conveyor) component).setNewRotation(rotationAngle);
                }
                if (component instanceof Skew_roller_conveyor) {
                    ((Skew_roller_conveyor) component).setNewRotation(rotationAngle);
                }
                if (component instanceof Transfer_module_90_degree) {
                    ((Transfer_module_90_degree) component).setNewRotation(rotationAngle);
                }
            } else {
                showWarning("Please enter a number between -360 and 360.");
            }
        } catch (NumberFormatException e) {
            showWarning("Please enter a valid number.");
        }
    }

    private static void showWarning(String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Invalid input");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
