package actions.query;

import fileio.*;

import java.util.*;

public class Shows {

    private List<UserInputData> users;
    private List<MovieInputData> movies;
    private List<SerialInputData> serials;
    private List<ActorInputData> actors;
    private ActionInputData actions;
    private Map<String, Integer> userHistory;

    public Shows(List<UserInputData> users, List<MovieInputData> movies, List<SerialInputData> serials, List<ActorInputData> actors, ActionInputData actions) {
        this.users = users;
        this.movies = movies;
        this. serials = serials;
        this.actors = actors;
        this.actions = actions;
    }

    public String favorite(String sortType) {

        String outString = "Query result: [";
        Map<String, Integer> serialsFreq = new HashMap<>();
        ArrayList<String> favoriteSerials = new ArrayList<>();
        Integer currValue = 0;
        Map<String, Integer> sorted = new HashMap<>();
        List<List<String>> listFilter = new ArrayList<>();
        List<String> year = new ArrayList<>();
        List<String> genresList = new ArrayList<>();



        for(SerialInputData serialCheck : serials) {
            serialsFreq.put(serialCheck.getTitle(), 0);
        }

        for(UserInputData checkUser: users) {

            favoriteSerials = checkUser.getFavoriteMovies();
            for(String movie : favoriteSerials) {
                if(serialsFreq.get(movie) != null) {
                    currValue = serialsFreq.get(movie);
                    currValue++;
                    serialsFreq.replace(movie, currValue);
                }
            }
        }

        if(sortType == "desc") {
            serialsFreq.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
        } else {
            serialsFreq.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                    .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
        }

        listFilter = actions.getFilters();
        year = listFilter.get(0);
        genresList = listFilter.get(1);

        Integer N = actions.getNumber();

        for(String movieName : sorted.keySet()) {


            for(SerialInputData checkSerial : serials) {
                Integer valid = 0;
                if(checkSerial.getTitle().equals(movieName)){
                    if(!checkSerial.getGenres().contains(genresList)) {
                        valid = 1;
                    }
                    if(!year.equals(checkSerial.getYear())) {
                        valid = 1;
                    }
                }
                if(valid == 0) {
                    outString += movieName;
                    outString += ", ";
                    break;
                }

            }



            if(N == 0) {
                break;
            }
            N--;
        }

        //sterg ultimele 2 caractere adaugate in plus in outString
        outString.replaceFirst(".&","");
        outString.replaceFirst(".&","");

        outString += "]";
        return outString;
    }


}
