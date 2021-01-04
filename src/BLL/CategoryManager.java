package BLL;

import BE.InputAlert;
import BE.Category;
import BE.Movie;
import DAL.DAO.DB.CategoryDBDAO;
import DAL.DAO.FILE.CategoryLocalDAO;
import DAL.DAO.CategoryDAOInterface;
import GUI.CONTROLLER.MainViewController;

import java.util.List;

public class CategoryManager {
    protected static CategoryDAOInterface categoryDAO;
    private static InputAlert inputAlert = new InputAlert();
    protected MainViewController mainController;


    //initializes the interface, if no connection to the database, it will try to use the local option.
    static {
        try {
            categoryDAO = new CategoryDBDAO();
        } catch (Exception e) {
            categoryDAO = new CategoryLocalDAO();
            inputAlert.showAlert("Couldn't establish connection to the database. Your changes will be done locally.");
        }
    }

    /**
     * Save categories locally instead of to database.
     */
    public void goLocal() {
        inputAlert.showAlert("Couldn't establish connection to the database. Your changes will be done locally.");
        categoryDAO = new CategoryLocalDAO();
    }

    /**
     * Set the value of CategoryDAO
     *
     * @param categoryDAO new value of CategoryDAO
     */
    public void setCategoryDAO(CategoryDAOInterface categoryDAO) {
        CategoryManager.categoryDAO = categoryDAO;
    }

    /**
     *x
     */
    public CategoryManager() {
        categoryDAO.setCategoryManager(this);
    }

    /**
     * Set the value of mainController
     *
     * @param mainController new value of mainController
     */
    public void setMainController(MainViewController mainController) {
        this.mainController = mainController;
    }

    /**
     * loads the categories, if it cannot connect to the database, it saves locally
     *
     * @return Categories
     */
    public List<Category> loadCategories() throws Exception {
        return categoryDAO.loadCategory();
    }

    /**
     * Sends information to create category
     *
     * @param name          the playlist name
     * @throws Exception    if something went wrong
     */
    public void createCategory(String name) throws Exception {
        categoryDAO.createCategory(name);
    }

    /**
     * Get the value of category name
     *
     * @param name          new value of name
     * @return              the value of name
     * @throws Exception    if something went wrong
     */
    public Category getCategory(String name) throws Exception {
        return categoryDAO.getCategory(name);
    }

    /**
     * Sends information to delete category
     *
     * @param category      the category
     * @throws Exception    if something went wrong.
     */
    public void deletePlaylist(Category category) throws Exception {
        categoryDAO.deleteCategory(category);
    }

    /**
     * @param category_id   the id of the category
     * @return              a list of movies
     * @throws Exception    if something went wrong
     */
    public List<Movie> loadMoviesOnCategory (int category_id) throws Exception {
        return categoryDAO.loadMoviesFromCategory(category_id);
    }

    /**
     * Sends information to add a movie to category
     *
     * @param category_id   the id of the category
     * @param movie_id       the id of the movie
     * @throws Exception    if something went wrong.
     */
    public void addMoviesToCategory(int category_id, int movie_id) throws Exception {
        categoryDAO.AddMovieToCategory(category_id, movie_id);
    }

    /**
     * Sends information to delete a movie from category.
     *
     * @param category_id   the id of the category
     * @param movie_id       the id of the movie
     * @throws Exception    if something went wrong.
     */
    public void deleteMovieFromCategory(int category_id, int movie_id) throws Exception {
        categoryDAO.deleteFromCategory(category_id, movie_id);
    }

    /**
     * Sends information to update category
     *
     * @param category      the category
     * @throws Exception    if something went wrong.
     */
    public void updateCategory(Category category) throws Exception {
        categoryDAO.updateCategory(category);
    }
}
