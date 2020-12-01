package actions.commands;

import fileio.ActionInputData;
import fileio.MovieInputData;
import fileio.SerialInputData;
import fileio.UserInputData;
import main.Main;

import java.util.*;

public class Commands {

    private List<UserInputData> users;
    private List<MovieInputData> movies;
    private List<SerialInputData> serials;
    private ActionInputData actions;
    private Map<String, Integer> userHistory;
    private ArrayList<String>  favoriteMovies;
    private Integer value = 0;

    public Commands(List<UserInputData> users, List<MovieInputData> movies, List<SerialInputData> serials, ActionInputData actions) {
        this.users = users;
        this.movies = movies;
        this.serials = serials;
        this.actions = actions;
    }


    public String favorite() {

        String outString = "";
        for(UserInputData user : users) {
            if(user.getUsername().equals(actions.getUsername())) {
                userHistory = user.getHistory();
                int verify = 0;
                if(userHistory.containsKey(actions.getTitle())) {
                    verify = 1;
                }
                if(verify == 0) {
                    outString = "error -> " + actions.getTitle() + " is not seen";
                    break;
                }
                if(verify == 1) {
                    favoriteMovies = user.getFavoriteMovies();
                    if(favoriteMovies.contains(actions.getTitle())) {
                        outString = "error -> " +actions.getTitle() + " is already in favourite list";
                    }else {
                        favoriteMovies.add(actions.getTitle());
                        outString = "success -> " + actions.getTitle() + " was added as favourite";
                    }
                }
            }
        }
        return outString;
    }

    public String view() {

        String outString = "";
        for(UserInputData user : users) {
            if(user.getUsername().equals(actions.getUsername())) {
                userHistory = user.getHistory();
                if(userHistory.containsKey(actions.getTitle())) {
                    value = userHistory.get(actions.getTitle());
                    value++;
                    userHistory.replace(actions.getTitle(), value);
                    outString = "success -> " + actions.getTitle() + " was viewed with total views of " + value;
                }
                else {
                    userHistory.put(actions.getTitle(), 1);
                    outString = "success -> " + actions.getTitle() + " was viewed with total views of " + 1;
                }
            }
        }
        return outString;
    }

    public String rating() {

        String outString = "";
        double raiting = actions.getGrade();

        UserInputData checkUser = users.get(0);

        for(UserInputData user : users){
            if(user.getUsername().equals(actions.getUsername())){
                checkUser = user;
                break;
            }
        }

        userHistory = checkUser.getHistory();

        if(userHistory.containsKey(actions.getTitle())) {
            outString = "success -> " + actions.getTitle() + " was rated with " + raiting + " by " + actions.getUsername();

            ArrayList<Double> newRatings;
            ArrayList<Double> newSerialRatings;


            for(MovieInputData checkMovies : movies) {
                if(actions.getTitle().equals(checkMovies)) {
                    newRatings = checkMovies.getRatings();
                    newRatings.add(actions.getGrade());
                    checkMovies.setRatings(newRatings);
                }
            }
            for(SerialInputData checkSerial : serials) {
                if(actions.getTitle().equals(checkSerial)) {
                    newSerialRatings = checkSerial.getSerialRatings();
                    newSerialRatings.add(actions.getGrade());
                    checkSerial.setSerialRatings(newSerialRatings);
                }
            }

        } else {
            outString = "error -> " + actions.getTitle() + " is not seen";
        }

        return outString;
    }


}