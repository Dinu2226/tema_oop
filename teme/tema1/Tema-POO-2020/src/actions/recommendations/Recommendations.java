package actions.recommendations;


import actions.commands.Commands;
import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import main.Main;

import java.util.*;

public class Recommendations {

    private List<UserInputData> users;
    private List<MovieInputData> movies;
    private List<SerialInputData> serials;
    private ActionInputData actions;
    private Map<String, Integer> userHistory;
    private ArrayList<String> favoriteMovies;

    public Recommendations (List<UserInputData> users, List<MovieInputData> movies, List<SerialInputData> serials, ActionInputData actions) {
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

        for(MovieInputData checkMovie : movieList) {
            if(!userHistory.containsKey(checkMovie)){
                if(checkMovie.getMedRating() > maxRating) {
                    maxRating = checkMovie.getMedRating();
                    maxVideoR = String.copyValueOf(checkMovie.getTitle().toCharArray());
                }
            }
        }

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
