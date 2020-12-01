package actions.query;

import fileio.*;

import java.util.*;

public class Movies {

    private List<UserInputData> users;
    private List<MovieInputData> movies;
    private List<SerialInputData> serials;
    private List<ActorInputData> actors;
    private ActionInputData actions;
    private Map<String, Integer> userHistory;

    public Movies(List<UserInputData> users, List<MovieInputData> movies, List<SerialInputData> serials, List<ActorInputData> actors, ActionInputData actions) {
        this.users = users;
        this.movies = movies;
        this. serials = serials;
        this.actors = actors;
        this.actions = actions;
    }

    public String favorite(String sortType) {

        Map<String, Integer> movieFreq = new TreeMap<>(); //map pt frecventa
        ArrayList<String> favoriteMovies = new ArrayList<>();
        Integer currValue = 0;
        Map<String, Integer> sorted = new HashMap<>();
        List<List<String>> listFilter = new ArrayList<>();
        ArrayList<String> outString = new ArrayList<>();
        int year = 0;
        String genresList ;

        //initializez aparitia fiecarui film cu 0
        for(MovieInputData movieCheck : movies) {
            movieFreq.put(movieCheck.getTitle(), 0);
        }

        //retin numarul de aparitii in lista de favorite ale fiecarui user
        for(UserInputData checkUser : users) {

            favoriteMovies = checkUser.getFavoriteMovies();
            for(String movie : favoriteMovies) {
                if(movieFreq.get(movie) != null) {
                    currValue = movieFreq.get(movie);
                    currValue++;
                    movieFreq.replace(movie, currValue);
                }
            }
        }

        //sortez
        if(sortType == "desc") {
            movieFreq.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
        } else {
            movieFreq.entrySet()
                    .stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
                    .forEachOrdered(x -> sorted.put(x.getKey(), x.getValue()));
        }

        listFilter = actions.getFilters();

        if(listFilter.get(0).get(0) == null) {
            year = -1;
        } else {
            year = Integer.parseInt(listFilter.get(0).get(0)); //anul transformat in int
        }

        genresList = listFilter.get(1).get(0); //genul

        Integer N = actions.getNumber();

        for(String movieName : sorted.keySet()) {


            for(MovieInputData checkMovie : movies) {
                Integer valid = 0;
                if(checkMovie.getTitle().equals(movieName)){
                    //validez atat anul cat si genul ca sa stiu daca se potriveste criteriului
                    if(checkMovie.getGenres().contains(genresList) && year == checkMovie.getYear()) {
                        valid = 1;
                    }
                }
                if(valid == 1) {
                    outString.add(movieName);
                    break;
                }

            }
            //cand terminam de adaugat cele N elemente, ne oprim
            if (N == 0) {
                break;
            }
            N--;
        }

        return "Query result: " +outString.toString();
    }


}
