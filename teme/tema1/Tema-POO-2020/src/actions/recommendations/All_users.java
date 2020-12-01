package actions.recommendations;


import actions.commands.Commands;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import main.Main;

import java.util.*;

public class All_users {

    private List<UserInputData> users;
    private List<MovieInputData> movies;
    private List<SerialInputData> serials;
    private ActionInputData actions;
    private Map<String, Integer> userHistory;
    private ArrayList<String> favoriteMovies;

    public All_users(List<UserInputData> users, List<MovieInputData> movies, List<SerialInputData> serials, ActionInputData actions) {
        this.users = users;
        this.movies = movies;
        this. serials = serials;
        this.actions = actions;
    }

    public String standard() {

        String outString = "";
        UserInputData checkUser = users.get(1);

        for(UserInputData user : users){
            if(user.getUsername().equals(actions.getUsername())){
                checkUser = user;
                break;
            }
        }

        // parcurgem filmul si daca nu se afla in istoric il returnam
        userHistory = checkUser.getHistory();
        for(MovieInputData movie: movies) {
            if(!userHistory.containsKey(movie.getTitle())) {
                outString = "StandardRecommendation result: " + movie.getTitle();
            }
        }
        return outString;
    }

    public String best_unseen(List<MovieInputData> movieList) {

        String outString = "";
        String maxVideoR = "default";
        Double maxRating = 0.0;
        UserInputData checkUser = users.get(1);

        for(UserInputData user : users){
            if(user.getUsername().equals(actions.getUsername())){
                checkUser = user;
                break;
            }
        }

        userHistory = checkUser.getHistory();

        //parcurgem lista de filme si aflam filmul cu rating maxim, astfel, ne mai fiind nevoie sa folosim un map pe care sa il sortam ulterior
        for(MovieInputData checkMovie : movieList) {
            if(!userHistory.containsKey(checkMovie)){
                if(checkMovie.getMedRating() > maxRating) {
                    maxRating = checkMovie.getMedRating();
                    maxVideoR = String.copyValueOf(checkMovie.getTitle().toCharArray());
                }
            }
        }

        //verificam cazul in care maxRating, nu a fost modificat
        if(maxRating == 0.0) {
            for(MovieInputData checkMovie : movieList) {
                if(!userHistory.containsKey(checkMovie)){
                    return "BestRatedUnseenRecommendation result: " + checkMovie.getTitle();
                }
            }
        }

        outString = "BestRatedUnseenRecommendation result: " + maxVideoR;

        return  outString;

    }
}
