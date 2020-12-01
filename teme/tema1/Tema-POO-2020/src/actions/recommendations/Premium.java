package actions.recommendations;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;

import java.util.*;

public class Premium {

    private List<UserInputData> users;
    private List<MovieInputData> movies;
    private List<SerialInputData> serials;
    private ActionInputData actions;
    private Map<String, Integer> userHistory;

    public Premium(List<UserInputData> users, List<MovieInputData> movies, List<SerialInputData> serials, ActionInputData actions) {
        this.users = users;
        this.movies = movies;
        this. serials = serials;
        this.actions = actions;
    }

    public String favorite() {

        String outString = "";
        Map<String, Integer> movieFreq = new HashMap<>();
        ArrayList<String> favoriteMovies = new ArrayList<>();
        Integer currValue = 0;
        Map<String, Integer> sorted = new HashMap<>();

        for(MovieInputData movieCheck : movies) {
            movieFreq.put(movieCheck.getTitle(), 0);
        }

        for(UserInputData checkUser: users) {

            favoriteMovies = checkUser.getFavoriteMovies();
            for(String movie : favoriteMovies) {
                if(movieFreq.get(movie) != null) {
                    currValue = movieFreq.get(movie);
                    currValue++;
                    movieFreq.replace(movie, currValue);
                }
            }
        }

        movieFreq.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));

        UserInputData checkUser = users.get(0);

        for(UserInputData user : users){
            if(user.getUsername().equals(actions.getUsername())){
                checkUser = user;
                break;
            }
        }

        userHistory = checkUser.getHistory();

        for(String movieName : sorted.keySet()) {
            if(!userHistory.containsKey(movieName)) {
                outString = "FavoriteRecommendation result: " + movieName;
                break;
            }
        }

        return outString;
    }


}
