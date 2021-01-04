package GUI.CONTROLLER;

import BE.Category;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddCategoryController {
    @FXML
    private Label labelField;
    @FXML
    private TextField titleField;
    @FXML
    private Label dialogTitle;
    private MainViewController mainMainViewController;
    private int mode;

    /**
     * Sets the main view controller
     * @param mainViewController the controller
     */
    public void setMainController(MainViewController mainViewController) {
        this.mainMainViewController = mainViewController;
    }

    /**
     * Gets the mode
     * @param i the mode
     */
    public void setMode(int i) {
        this.mode = i;
    }

    /**
     * Sets the labelFiled text
     * @param labelText the new text
     */
    public void setLabelField(String labelText) {
        this.labelField.setText(labelText);
    }

    /**
     * Sets the dialog title text
     * @param dialogTitle the new text
     */
    public void setDialogTitle(String dialogTitle) {
        this.dialogTitle.setText(dialogTitle);
    }

    /**
     * Sets the title field text
     * @param titleFieldText the new text
     */
    public void setTitleField(String titleFieldText) {
        this.titleField.setText(titleFieldText);
    }

    /**
     * Confirms the input is correct, does the action and closes the window. Does not close if the input is invalid.
     */
    public void confirmButton() {
        if (titleField != null && !titleField.getText().isEmpty()) {
            switch (mode) {
                case (1) -> {
                    mainMainViewController.addPlaylist(new Category(titleField.getText()));
                }
                case (2) -> {
                    mainMainViewController.editPlaylist(titleField.getText());
                }
            }
            mainMainViewController.getWindowStage().close();
        }
    }

    /**
     * Closes the window
     */
    public void cancelButton() {
        mainMainViewController.getWindowStage().close();
    }
}
