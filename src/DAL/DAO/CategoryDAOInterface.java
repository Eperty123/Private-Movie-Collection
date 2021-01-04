package DAL.DAO;

import BE.Category;
import BE.Movie;
import BLL.CategoryManager;
import java.util.List;

public interface CategoryDAOInterface {

    /**
     * Sets the categoryManager
     *
     * @param categoryManager the playlistManager
     */
    void setCategoryManager(CategoryManager categoryManager);

    /**
     * Loads the categories
     *
     * @return  A list of categories
     * @throws  Exception if something went wrong.
     */
    List<Category> loadCategory() throws Exception;

    /**
     * Creates a playlist
     *
     * @param   name the name of the playlist
     * @throws  Exception if something went wrong.
     */
    void createCategory(String name) throws Exception;

    /**
     * Gets a playlist
     *
     * @param   name the name of the playlist
     * @return  the requested playlist.
     * @throws  Exception if something went wrong.
     */

    Category getCategory(String name) throws Exception;

    /**
     * Deletes a playlist
     *
     * @param   category the playlist
     * @throws  Exception if something went wrong.
     */

    void deleteCategory(Category category) throws Exception;

    /**
     * Loads the songs on the playlist
     *
     * @param   category_id the id of the playlist
     * @return  A list of movies in the category
     * @throws  Exception if something went wrong.
     */
    List<Movie> loadMoviesFromCategory(int category_id) throws Exception;

    /**
     * Adds a move to the category
     *
     * @param   category_id the playlist you want to add a song to
     * @param   movie_id the song you want to add
     * @throws  Exception if something went wrong.
     */

    void AddMovieToCategory(int category_id,int movie_id) throws Exception;

    /**
     * Removes a song from the playlist
     *
     * @param   category_id the id of the playlist you want to remove the song from
     * @param   movie_id the id of the song you want to remove
     * @throws  Exception if something went wrong.
     */

    void deleteFromCategory(int category_id,int movie_id) throws Exception;

    /**
     * Updates a playlist to with new parameters
     *
     * @param   category    the modified playlist
     * @throws  Exception   if something went wrong.
     */
    void updateCategory(Category category) throws Exception;
}
