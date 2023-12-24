package com.convofig.controllers;

import com.convofig.components.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import javafx.util.converter.IntegerStringConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ConfigurationView extends MainView {
    private static Pane mainPane, drawPane;
    private static ComboBox<String> componentTypeComboBox, componentWidthComboBox, componentLengthComboBox, componentSkewLengthComboBox, componentRollerPitchComboBox, componentPolyVeeSideComboBox, componentNoOfMDRComboBox, componentAngleComboBox, componentControlComboBox, componentDiverterLengthComboBox, componentDivertingAngleComboBox;
    private static TextField textTitle, textSpeed1, textSpeed2, textHeight, textAuxHeight;
    private static Region componentRegion;
    private static double scaleFactor;
    static Boolean align = false;
    static Boolean zone_confirm = false;
    private static double off_setInitialX;
    private static double off_setInitialY;

    ConfigurationView(double scaleFactor, Pane mainPane, Pane drawPane, Region componentRegion, ComboBox<String> componentTypeComboBox, ComboBox<String> componentWidthComboBox, ComboBox<String> componentLengthComboBox, ComboBox<String> componentSkewLengthComboBox, ComboBox<String> componentRollerPitchComboBox, ComboBox<String> componentPolyVeeSideComboBox, ComboBox<String> componentNoOfMDRComboBox, TextField textTitle, TextField textSpeed1, TextField textSpeed2, TextField textHeight, TextField textAuxHeight, ComboBox<String> componentAngleComboBox, ComboBox<String> componentControlComboBox, ComboBox<String> componentDiverterLengthComboBox, ComboBox<String> componentDivertingAngleComboBox) {
        ConfigurationView.mainPane = mainPane;
        ConfigurationView.drawPane = drawPane;
        ConfigurationView.componentTypeComboBox = componentTypeComboBox;
        ConfigurationView.componentWidthComboBox = componentWidthComboBox;
        ConfigurationView.componentLengthComboBox = componentLengthComboBox;
        ConfigurationView.componentSkewLengthComboBox = componentSkewLengthComboBox;
        ConfigurationView.componentRegion = componentRegion;
        ConfigurationView.componentRollerPitchComboBox = componentRollerPitchComboBox;
        ConfigurationView.componentPolyVeeSideComboBox = componentPolyVeeSideComboBox;
        ConfigurationView.componentNoOfMDRComboBox = componentNoOfMDRComboBox;
        ConfigurationView.componentAngleComboBox = componentAngleComboBox;
        ConfigurationView.componentControlComboBox = componentControlComboBox;
        ConfigurationView.componentDivertingAngleComboBox = componentDivertingAngleComboBox;
        ConfigurationView.componentDiverterLengthComboBox = componentDiverterLengthComboBox;
        ConfigurationView.textTitle = textTitle;
        ConfigurationView.textSpeed1 = textSpeed1;
        ConfigurationView.textSpeed2 = textSpeed2;
        ConfigurationView.textHeight = textHeight;
        ConfigurationView.textAuxHeight = textAuxHeight;
        ConfigurationView.scaleFactor = scaleFactor;
        changeRegion();
    }

    void changeRegion() {
        componentWidthComboBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> {
            componentRegion.setPrefHeight((double) (Integer.parseInt(componentWidthComboBox.getItems().get((Integer) t1)) + 70) * scaleFactor);
            if (componentTypeComboBox.getSelectionModel().getSelectedIndex() == 2) {
                componentRegion.setPrefWidth(780 * scaleFactor);
            }
            if (componentTypeComboBox.getSelectionModel().getSelectedIndex() == 3) {
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
            }
            if (componentTypeComboBox.getSelectionModel().getSelectedIndex() == 4) {
                componentRegion.setPrefHeight((double) (Integer.parseInt(componentWidthComboBox.getItems().get((Integer) t1)) + 70 + 790) * scaleFactor);
                componentRegion.setPrefWidth((double) (Integer.parseInt(componentWidthComboBox.getItems().get((Integer) t1)) + 70 + 790) * scaleFactor);
            }
            if (componentTypeComboBox.getSelectionModel().getSelectedIndex() == 6) {
                componentRegion.setPrefHeight((double) (Integer.parseInt(componentWidthComboBox.getItems().get((Integer) t1)) + 70 + 220) * scaleFactor);
            }
        });
        componentLengthComboBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> componentRegion.setPrefWidth((double) Integer.parseInt(componentLengthComboBox.getItems().get((Integer) t1)) * scaleFactor));
        componentSkewLengthComboBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, number, t1) -> componentRegion.setPrefWidth((double) Integer.parseInt(componentSkewLengthComboBox.getItems().get((Integer) t1)) * scaleFactor));
        componentDiverterLengthComboBox.setOnAction(e -> componentRegion.setPrefWidth(Double.parseDouble(componentDiverterLengthComboBox.getSelectionModel().getSelectedItem()) * scaleFactor));
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
                component = new Gravity_roller_conveyor(scaleFactor, preScaleWidth, preScaleHeight, width, height, textAuxHeight.getText(), textHeight.getText(), componentRollerPitchComboBox.getSelectionModel().getSelectedItem(), textTitle.getText());
                break;

            case 2:
                //System.out.println("90 degree transfer module");
                width = 780 * scaleFactor;
                preScaleWidth = 780;
                component = new Transfer_module_90_degree(scaleFactor, preScaleWidth, preScaleHeight, width, height, textHeight.getText(), textSpeed1.getText(), textSpeed2.getText(), componentRollerPitchComboBox.getSelectionModel().getSelectedItem(), textTitle.getText(), componentControlComboBox.getSelectionModel().getSelectedItem());
                break;

            case 3:
                //System.out.println("Merge conveyor");

                component = new Merge_conveyor(scaleFactor, preScaleHeight, height, componentPolyVeeSideComboBox.getSelectionModel().getSelectedItem(), textHeight.getText(), textSpeed1.getText(), componentRollerPitchComboBox.getSelectionModel().getSelectedItem(), textTitle.getText(), componentControlComboBox.getSelectionModel().getSelectedItem());
                break;

            case 4:
                //System.out.println("Curve roller conveyor");
                height = (double) (Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70 + 790) * scaleFactor;
                width = (double) (Integer.parseInt(componentWidthComboBox.getSelectionModel().getSelectedItem()) + 70 + 790) * scaleFactor;
                component = new Curve_roller_conveyor(scaleFactor, preScaleHeight, height, textHeight.getText(), textSpeed1.getText(), textTitle.getText(), componentAngleComboBox.getSelectionModel().getSelectedItem(), componentControlComboBox.getSelectionModel().getSelectedItem());
                break;

            case 5:
                //System.out.println("Skew roller conveyor");
                width = (double) Integer.parseInt(componentSkewLengthComboBox.getSelectionModel().getSelectedItem()) * scaleFactor;
                preScaleWidth = Integer.parseInt(componentSkewLengthComboBox.getSelectionModel().getSelectedItem());
                component = new Skew_roller_conveyor(scaleFactor, preScaleWidth, preScaleHeight, width, height, textHeight.getText(), textSpeed1.getText(), componentRollerPitchComboBox.getSelectionModel().getSelectedItem(), textTitle.getText(), componentControlComboBox.getSelectionModel().getSelectedItem(), componentPolyVeeSideComboBox.getSelectionModel().getSelectedItem());
                break;

            case 6:
                //System.out.println("Diverter");
                width = Integer.parseInt(componentDiverterLengthComboBox.getSelectionModel().getSelectedItem()) * scaleFactor;
                preScaleWidth = Integer.parseInt(componentDiverterLengthComboBox.getSelectionModel().getSelectedItem());
                component = new Diverter(scaleFactor, preScaleWidth, preScaleHeight, width, height, componentPolyVeeSideComboBox.getSelectionModel().getSelectedItem(), textHeight.getText(), textSpeed1.getText(), componentDivertingAngleComboBox.getSelectionModel().getSelectedItem(), textTitle.getText());
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

        ContextMenu contextMenu = addContextMenu(component, width, height);

        component.hoverProperty().addListener(e -> dragBoundsVisibleTrue(component));

        component.setOnMouseExited(e -> {
            dragBoundsVisibleFalse(component);
        });

        component.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                double scale = drawPane.getLocalToSceneTransform().getMxx();
                off_setInitialX = (e.getSceneX() / scale) - component.getLayoutX();
                off_setInitialY = (e.getSceneY() / scale) - component.getLayoutY();
                double middlePointX = component.getBoundsInLocal().getCenterX();
                double middlePointY = component.getBoundsInLocal().getCenterY();
                if (e.getX() > middlePointX - 100 * scaleFactor && e.getY() > middlePointY - 100 * scaleFactor && e.getX() < middlePointX + 100 * scaleFactor && e.getY() < middlePointY + 100 * scaleFactor)
                    zone_confirm = true;
            }
            if (e.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(component, e.getScreenX(), e.getScreenY());
            }
        });

        component.setOnMouseReleased(e -> {
            zone_confirm = false;
            dragBoundsVisibleTrue(component);
        });

        component.setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.PRIMARY && zone_confirm) {
                dragBoundsVisibleFalse(component);

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

    private static ContextMenu addContextMenu(Node component, double width, double height) {
        ContextMenu contextMenu = new ContextMenu();
        MenuItem rotateMenuItem = new MenuItem("Rotate");
        rotateMenuItem.setOnAction(event -> showRotateDialog(component));
        contextMenu.getItems().add(rotateMenuItem);
        MenuItem revertMenuItem = new MenuItem("Revert");
        contextMenu.getItems().add(revertMenuItem);
        MenuItem clearRotateMenuItem = new MenuItem("Clear");
        clearRotateMenuItem.setOnAction(event -> clearComponent(component));
        contextMenu.getItems().add(clearRotateMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        MenuItem titleComponentMenuItem = new MenuItem("Change title");
        contextMenu.getItems().add(titleComponentMenuItem);
        MenuItem changeValue1ComponentMenuItem = new MenuItem("Change H");
        contextMenu.getItems().add(changeValue1ComponentMenuItem);
        MenuItem changeValue2ComponentMenuItem = new MenuItem("Change V");
        contextMenu.getItems().add(changeValue2ComponentMenuItem);
        MenuItem changeValue3ComponentMenuItem = new MenuItem("Change P");
        contextMenu.getItems().add(changeValue3ComponentMenuItem);
        MenuItem sideComponentMenuItem = new MenuItem("Change side");
        sideComponentMenuItem.setVisible(false);
        contextMenu.getItems().add(sideComponentMenuItem);
        contextMenu.getItems().add(new SeparatorMenuItem());
        MenuItem copyComponentMenuItem = new MenuItem("Copy");
        copyComponentMenuItem.setOnAction(event -> copyComponent(component, width, height));
        contextMenu.getItems().add(copyComponentMenuItem);
        MenuItem removeComponentMenuItem = new MenuItem("Remove");
        removeComponentMenuItem.setOnAction(event -> removeComponent(component));
        contextMenu.getItems().add(removeComponentMenuItem);

        if (component instanceof Motorized_roller_conveyor) {
            sideComponentMenuItem.setVisible(true);
            sideComponentMenuItem.setOnAction(event -> ((Motorized_roller_conveyor) component).changeSide());
            revertMenuItem.setOnAction(event -> ((Motorized_roller_conveyor) component).revertComponent());
            titleComponentMenuItem.setOnAction(event -> ((Motorized_roller_conveyor) component).modifyTitle(changeComponentValue(7, "Change title", ((Motorized_roller_conveyor) component).getCurrentName())));
            changeValue1ComponentMenuItem.setOnAction(event -> ((Motorized_roller_conveyor) component).modifyH(changeComponentValue(5, "Change H", ((Motorized_roller_conveyor) component).getCurrentH())));
            changeValue2ComponentMenuItem.setOnAction(event -> ((Motorized_roller_conveyor) component).modifyV(changeComponentValue(5, "Change V", ((Motorized_roller_conveyor) component).getCurrentV())));
            changeValue3ComponentMenuItem.setOnAction(event -> ((Motorized_roller_conveyor) component).modifyP(changeComponentPitch(1, "Change P", ((Motorized_roller_conveyor) component).getCurrentP())));
        }
        if (component instanceof Curve_roller_conveyor) {
            revertMenuItem.setOnAction(event -> ((Curve_roller_conveyor) component).revertComponent());
            titleComponentMenuItem.setOnAction(event -> ((Curve_roller_conveyor) component).modifyTitle(changeComponentValue(7, "Change title", ((Curve_roller_conveyor) component).getCurrentName())));
            changeValue1ComponentMenuItem.setOnAction(event -> ((Curve_roller_conveyor) component).modifyH(changeComponentValue(5, "Change H", ((Curve_roller_conveyor) component).getCurrentH())));
            changeValue2ComponentMenuItem.setOnAction(event -> ((Curve_roller_conveyor) component).modifyV(changeComponentValue(5, "Change V", ((Curve_roller_conveyor) component).getCurrentV())));
            changeValue3ComponentMenuItem.setVisible(false);
        }
        if (component instanceof Diverter) {
            sideComponentMenuItem.setVisible(true);
            sideComponentMenuItem.setOnAction(event -> ((Diverter) component).changeSide());
            revertMenuItem.setOnAction(event -> ((Diverter) component).revertComponent());
            titleComponentMenuItem.setOnAction(event -> ((Diverter) component).modifyTitle(changeComponentValue(7, "Change title", ((Diverter) component).getCurrentName())));
            changeValue1ComponentMenuItem.setOnAction(event -> ((Diverter) component).modifyH(changeComponentValue(5, "Change H", ((Diverter) component).getCurrentH())));
            changeValue2ComponentMenuItem.setOnAction(event -> ((Diverter) component).modifyV(changeComponentValue(5, "Change V", ((Diverter) component).getCurrentV())));
            changeValue3ComponentMenuItem.setText("Change angle");
            changeValue3ComponentMenuItem.setOnAction(event -> ((Diverter) component).modifyDiverting_Angle(changeComponentPitch(2, "Change P", ((Diverter) component).getCurrentDiverting_Angle())));
        }
        if (component instanceof Gravity_roller_conveyor) {
            revertMenuItem.setOnAction(event -> ((Gravity_roller_conveyor) component).revertComponent());
            titleComponentMenuItem.setOnAction(event -> ((Gravity_roller_conveyor) component).modifyTitle(changeComponentValue(7, "Change title", ((Gravity_roller_conveyor) component).getCurrentName())));
            changeValue1ComponentMenuItem.setText("Change H1");
            changeValue1ComponentMenuItem.setOnAction(event -> ((Gravity_roller_conveyor) component).modifyH(1, changeComponentValue(5, "Change H1", ((Gravity_roller_conveyor) component).getCurrentH(1))));
            changeValue2ComponentMenuItem.setText("Change H2");
            changeValue2ComponentMenuItem.setOnAction(event -> ((Gravity_roller_conveyor) component).modifyH(2, changeComponentValue(5, "Change H2", ((Gravity_roller_conveyor) component).getCurrentH(2))));
            changeValue3ComponentMenuItem.setOnAction(event -> ((Gravity_roller_conveyor) component).modifyP(changeComponentPitch(1, "Change P", ((Gravity_roller_conveyor) component).getCurrentP())));
        }
        if (component instanceof Merge_conveyor) {
            sideComponentMenuItem.setVisible(true);
            sideComponentMenuItem.setOnAction(event -> ((Merge_conveyor) component).changeSide());
            revertMenuItem.setOnAction(event -> ((Merge_conveyor) component).revertComponent());
            titleComponentMenuItem.setOnAction(event -> ((Merge_conveyor) component).modifyTitle(changeComponentValue(7, "Change title", ((Merge_conveyor) component).getCurrentName())));
            changeValue1ComponentMenuItem.setOnAction(event -> ((Merge_conveyor) component).modifyH(changeComponentValue(5, "Change H", ((Merge_conveyor) component).getCurrentH())));
            changeValue2ComponentMenuItem.setOnAction(event -> ((Merge_conveyor) component).modifyV(changeComponentValue(5, "Change V", ((Merge_conveyor) component).getCurrentV())));
            changeValue3ComponentMenuItem.setOnAction(event -> ((Merge_conveyor) component).modifyP(changeComponentPitch(1, "Change P", ((Merge_conveyor) component).getCurrentP())));
        }
        if (component instanceof Skew_roller_conveyor) {
            sideComponentMenuItem.setVisible(true);
            sideComponentMenuItem.setOnAction(event -> ((Skew_roller_conveyor) component).changeSide());
            revertMenuItem.setOnAction(event -> ((Skew_roller_conveyor) component).revertComponent());
            titleComponentMenuItem.setOnAction(event -> ((Skew_roller_conveyor) component).modifyTitle(changeComponentValue(7, "Change title", ((Skew_roller_conveyor) component).getCurrentName())));
            changeValue1ComponentMenuItem.setOnAction(event -> ((Skew_roller_conveyor) component).modifyH(changeComponentValue(5, "Change H", ((Skew_roller_conveyor) component).getCurrentH())));
            changeValue2ComponentMenuItem.setOnAction(event -> ((Skew_roller_conveyor) component).modifyV(changeComponentValue(5, "Change V", ((Skew_roller_conveyor) component).getCurrentV())));
            changeValue3ComponentMenuItem.setOnAction(event -> ((Skew_roller_conveyor) component).modifyP(changeComponentPitch(1, "Change P", ((Skew_roller_conveyor) component).getCurrentP())));
        }
        if (component instanceof Transfer_module_90_degree) {
            revertMenuItem.setOnAction(event -> ((Transfer_module_90_degree) component).revertComponent());
            titleComponentMenuItem.setOnAction(event -> ((Transfer_module_90_degree) component).modifyTitle(changeComponentValue(7, "Change title", ((Transfer_module_90_degree) component).getCurrentName())));
            changeValue1ComponentMenuItem.setOnAction(event -> ((Transfer_module_90_degree) component).modifyH(changeComponentValue(5, "Change H", ((Transfer_module_90_degree) component).getCurrentH())));
            changeValue2ComponentMenuItem.setText("Change V1");
            changeValue2ComponentMenuItem.setOnAction(event -> ((Transfer_module_90_degree) component).modifyV(1, changeComponentValue(5, "Change V1", ((Transfer_module_90_degree) component).getCurrentV(1))));
            changeValue3ComponentMenuItem.setText("Change V2");
            changeValue3ComponentMenuItem.setOnAction(event -> ((Transfer_module_90_degree) component).modifyV(2, changeComponentValue(5, "Change V2", ((Transfer_module_90_degree) component).getCurrentV(2))));
        }

        return contextMenu;
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

    private static String changeComponentValue(int maxCharacters, String title, String currentValue) {

        TextInputDialog dialog = new TextInputDialog(currentValue);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText("New value:");

        dialog.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > maxCharacters) {
                dialog.getEditor().setText(oldValue);
            }
        });

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return currentValue;
    }

    private static String changeComponentPitch(int choiceCase, String title, String currentValue) {
        List<String> choices = Arrays.asList("60", "90", "120");
        if (choiceCase == 2) {
            choices = Arrays.asList("30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45");
        }
        ChoiceDialog<String> dialog = new ChoiceDialog<>(currentValue, choices);
        dialog.initStyle(StageStyle.UTILITY);
        dialog.setTitle(title);
        dialog.setHeaderText(null);
        dialog.setGraphic(null);
        dialog.setContentText("Select new value:");
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            return result.get();
        }
        return currentValue;
    }

    private static void dragBoundsVisibleTrue(Node component) {
        if (component instanceof Motorized_roller_conveyor)
            ((Motorized_roller_conveyor) component).mouseEnteredDragZone();
        if (component instanceof Curve_roller_conveyor)
            ((Curve_roller_conveyor) component).mouseEnteredDragZone();
        if (component instanceof Diverter)
            ((Diverter) component).mouseEnteredDragZone();
        if (component instanceof Gravity_roller_conveyor)
            ((Gravity_roller_conveyor) component).mouseEnteredDragZone();
        if (component instanceof Merge_conveyor)
            ((Merge_conveyor) component).mouseEnteredDragZone();
        if (component instanceof Skew_roller_conveyor)
            ((Skew_roller_conveyor) component).mouseEnteredDragZone();
        if (component instanceof Transfer_module_90_degree)
            ((Transfer_module_90_degree) component).mouseEnteredDragZone();
    }

    private static void dragBoundsVisibleFalse(Node component) {
        if (component instanceof Motorized_roller_conveyor)
            ((Motorized_roller_conveyor) component).mouseExitedDragZone();
        if (component instanceof Curve_roller_conveyor)
            ((Curve_roller_conveyor) component).mouseExitedDragZone();
        if (component instanceof Diverter)
            ((Diverter) component).mouseExitedDragZone();
        if (component instanceof Gravity_roller_conveyor)
            ((Gravity_roller_conveyor) component).mouseExitedDragZone();
        if (component instanceof Merge_conveyor)
            ((Merge_conveyor) component).mouseExitedDragZone();
        if (component instanceof Skew_roller_conveyor)
            ((Skew_roller_conveyor) component).mouseExitedDragZone();
        if (component instanceof Transfer_module_90_degree)
            ((Transfer_module_90_degree) component).mouseExitedDragZone();
    }

    private static void removeComponent(Node component) {
        drawPane.getChildren().remove(component);
    }

    private static void clearComponent(Node component) {
        if (component instanceof Curve_roller_conveyor) {
            ((Curve_roller_conveyor) component).resetComponent();
        }
        if (component instanceof Diverter) {
            ((Diverter) component).resetComponent();
        }
        if (component instanceof Gravity_roller_conveyor) {
            ((Gravity_roller_conveyor) component).resetRotation();
        }
        if (component instanceof Merge_conveyor) {
            ((Merge_conveyor) component).resetRotation();
        }
        if (component instanceof Motorized_roller_conveyor) {
            ((Motorized_roller_conveyor) component).resetComponent();
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
