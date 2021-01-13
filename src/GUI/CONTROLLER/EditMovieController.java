package GUI.CONTROLLER;

import BE.Category;
import BE.Movie;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class EditMovieController extends Component implements Initializable {
    @FXML
    Label dialogTitleField;
    @FXML
    TextField filePathTextField;
    @FXML
    TextField titleTextField;
    @FXML
    ComboBox genreComboBox;
    @FXML
    TextField ratingTextField;
    @FXML
    Button saveButton;

    private MainViewController mainViewController;
    private Movie selectedMovie;
    private Movie modifiedMovie;
    private ObservableList<Category> categories;
    private String selectedCategory;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedCategory();
    }

    public void setDialogTitleField(String title){
        this.dialogTitleField.setText(title);
    }

    /**
     * Initialize the combo box to listen to when a new item is selected.
     */
    private void selectedCategory() {
        genreComboBox.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValue, newValue) -> {
            selectedCategory = (String) newValue;
            System.out.println("Selected category: " + selectedCategory);
        }));
    }

    /**
     * Assign the genre combo box to have the categories.
     *
     * @param categories The categories to add.
     */
    public void setGenreComboBox(ObservableList<Category> categories) {
        this.categories = categories;
        genreComboBox.getItems().clear();
        for(Category cat : categories)
            genreComboBox.getItems().add(cat.getCategoryName());
    }

    /**
     * Sets the main view controller
     *
     * @param mainViewController the main view controller
     */
    public void setMainController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    /**
     * Find the specified category name and return its id.
     *
     * @param categoryName The category name to find.
     * @return the category id
     */
    private int getCategoryIdFromName(String categoryName) {
        for (var category : categories) {
            if (category.getCategoryName() == categoryName) {
                return category.getCategoryId();
            }
        }
        return -1;
    }

    /**
     * Sets the selected song
     *
     * @param movie the selected song
     */
    public void setSelectedMovie(Movie movie) {
        if (movie != null) {
            selectedMovie = movie;
            titleTextField.setText(selectedMovie.getTitle());
            filePathTextField.setText(selectedMovie.getFilePath());
            genreComboBox.getSelectionModel().select(selectedMovie.getCategoryName());
        }
    }

    /**
     * Opens a window in which you can select files
     */
    public void browse() {
        try {
            //To allow variable usage in an anonymous lambda, we must create a new Object with the required variables.
            var ref = new Object() {
                int current_try = 0;
                int max_tries = 3;
            };

            Thread songEditorThread = null;
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Mp4 file", "mp4"));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                var fileName = selectedFile.getName();
                var fileNameNoExt = fileName.lastIndexOf('.') > 0 ? fileName.substring(0, fileName.lastIndexOf('.')) : fileName;
                var filePath = selectedFile.getAbsolutePath();

                modifiedMovie = new Movie();
                modifiedMovie.setFilePath(filePath);

                try {
                    songEditorThread = new Thread(() -> {
                        while (!selectedMovie.getIsInitialized()) {
                            try {
                                if (ref.current_try < ref.max_tries) {
                                    ref.current_try++;
                                    System.out.println("Waiting for media to initialize.");
                                    Thread.sleep(500);
                                } else break;
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                        // https://stackoverflow.com/questions/49343256/threads-in-javafx-not-on-fx-application-thread.
                        // Required for updating GUI stuff from another thread.
                        Platform.runLater(() -> {
                            try {
                                var title = !modifiedMovie.getTitle().isBlank() ? modifiedMovie.getTitle() : fileNameNoExt;
                                titleTextField.setText(title);
                                filePathTextField.setText(filePath);
                                System.out.println("Media initialized.");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    });
                    songEditorThread.start();
                } catch (
                        Exception e) {
                    if (songEditorThread != null)
                        songEditorThread.interrupt();
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the changes made to the selected song.
     */
    public void save() {
        try {
            if (selectedMovie != null) {
                selectedMovie.setTitle(titleTextField.getText());
                selectedMovie.setFilePath(filePathTextField.getText());
                selectedMovie.setCategoryId(getCategoryIdFromName(selectedCategory));
                var rating = Double.parseDouble(ratingTextField.getText().isEmpty() ? "0" : ratingTextField.getText().replaceAll(",", "."));
                selectedMovie.setRating(Double.toString(rating));
                mainViewController.getMovieManager().updateMovie(selectedMovie);
                mainViewController.addMovieToCategory(selectedMovie.getCategoryId(), selectedMovie.getId());
                close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Close the window.
     */
    public void close() {
        mainViewController.getWindowStage().close();
    }
}
