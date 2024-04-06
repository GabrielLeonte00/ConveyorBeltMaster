package com.convofig.components;

public interface component_interface {
    void mouseEnteredDragZone();
    void mouseExitedDragZone();
    void resetComponent();
    void setNewRotation(int rotation);
    component_interface copyComponent();
    void modifyTitle(String newValue);
    String getCurrentName();
    void mirrorText();
    String getDataForSave();
}
