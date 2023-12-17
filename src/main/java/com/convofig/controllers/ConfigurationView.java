package com.convofig.controllers;

import com.convofig.components.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;

public class ConfigurationView extends MainView {
    private static Pane mainPane, drawPane;
    private static ComboBox<String> componentTypeComboBox, componentWidthComboBox, componentLengthComboBox;
    private static Region componentRegion;
    static Boolean align = false;
    static Boolean zone_confirm = false;

    ConfigurationView(Pane mainPane, Pane drawPane, Region componentRegion, ComboBox<String> componentTypeComboBox, ComboBox<String> componentWidthComboBox, ComboBox<String> componentLengthComboBox) {
        ConfigurationView.mainPane = mainPane;
        ConfigurationView.drawPane = drawPane;
        ConfigurationView.componentTypeComboBox = componentTypeComboBox;
        ConfigurationView.componentWidthComboBox = componentWidthComboBox;
        ConfigurationView.componentLengthComboBox = componentLengthComboBox;
        ConfigurationView.componentRegion = componentRegion;
        changeRegion();
    }

    void changeRegion() {
        componentWidthComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                componentRegion.setPrefHeight((double) (Integer.parseInt((String) componentWidthComboBox.getItems().get((Integer) t1)) + 70) / 4);
            }
        });
        componentLengthComboBox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                componentRegion.setPrefWidth((double) Integer.parseInt((String) componentLengthComboBox.getItems().get((Integer) t1)) / 4);
            }
        });
    }

    static void addComponent() {
        Node component = null;
        double width = (double) Integer.parseInt(componentLengthComboBox.getSelectionModel().getSelectedItem()) / 4;
        double height = (double) (Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70) / 4;
        switch (componentTypeComboBox.getSelectionModel().getSelectedIndex()) {
            case 0:
                //System.out.println("Motorized roller conveyor");
                component = new Motorized_roller_conveyor(width, height);
                break;

            case 1:
                //System.out.println("Gravity roller conveyor");
                component = new Gravity_roller_conveyor();
                break;

            case 2:
                //System.out.println("90 degree transfer module");
                component = new Transfer_module_90_degree();
                break;

            case 3:
                //System.out.println("Merge conveyor");
                component = new Merge_conveyor();
                break;

            case 4:
                //System.out.println("Curve roller conveyor");
                component = new Curve_roller_conveyor();
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
            if (adjustedLayoutX >= 0 && adjustedLayoutX + component.getBoundsInLocal().getWidth() <= drawPane.getWidth()
                    && adjustedLayoutY >= 0 && adjustedLayoutY + component.getBoundsInLocal().getHeight() <= drawPane.getHeight()) {
                component.setLayoutX(adjustedLayoutX);
                component.setLayoutY(adjustedLayoutY);
                addActionsToComponent(component);
                drawPane.getChildren().add(component);
            } else {
                showAlert();
                component = null;
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

    private static void addActionsToComponent(Node component) {
        double ALIGNMENT_THRESHOLD = 20;
        double GRAB_ZONE_SIZE = 20;

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
        copyComponentMenuItem.setOnAction(event -> copyComponent(component));
        contextMenu.getItems().add(copyComponentMenuItem);
        MenuItem removeComponentMenuItem = new MenuItem("Remove");
        removeComponentMenuItem.setOnAction(event -> removeComponent(component));
        contextMenu.getItems().add(removeComponentMenuItem);

        if (component instanceof Motorized_roller_conveyor) {
            component.setOnMouseEntered(event -> ((Motorized_roller_conveyor) component).showSquare());
            component.setOnMouseExited(event -> ((Motorized_roller_conveyor) component).hideSquare());
        }

        component.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                if (isMouseInGrabZone(e, component, GRAB_ZONE_SIZE))
                    zone_confirm = true;
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(component, e.getScreenX(), e.getScreenY());
            }
        });

        component.setOnMouseDragged(e -> {
            if (e.isPrimaryButtonDown()) {
                if (zone_confirm) {
                    double x = e.getX() + component.getLayoutX() - component.getBoundsInLocal().getWidth() / 2;
                    double y = e.getY() + component.getLayoutY() - component.getBoundsInLocal().getHeight() / 2;
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
            }
        });

        component.setOnMouseReleased(e -> {
            zone_confirm = false;
        });

    }

    static boolean isMouseInGrabZone(MouseEvent event, Node component, double GRAB_ZONE_SIZE) {
        double mouseX = event.getX();
        double mouseY = event.getY();

        // Get the center coordinates of the rotated component
        double centerX = component.getBoundsInLocal().getWidth() / 2;
        double centerY = component.getBoundsInLocal().getHeight() / 2;

        // Get the current rotation angle of the component
        double rotation = Math.toRadians(component.getRotate());

        // Calculate rotated mouse coordinates
        double rotatedMouseX = Math.cos(rotation) * (mouseX - centerX) + Math.sin(rotation) * (mouseY - centerY) + centerX;
        double rotatedMouseY = -Math.sin(rotation) * (mouseX - centerX) + Math.cos(rotation) * (mouseY - centerY) + centerY;

        // Check if the adjusted mouse coordinates are within the grab zone around the center
        return Math.abs(rotatedMouseX - centerX) < GRAB_ZONE_SIZE && Math.abs(rotatedMouseY - centerY) < GRAB_ZONE_SIZE;
    }



    private static void copyComponent(Node component) {
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
            addActionsToComponent(copy);
            drawPane.getChildren().add(copy);
        }
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
