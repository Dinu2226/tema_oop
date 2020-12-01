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

        Map<String, Integer> serialsFreq = new HashMap<>(); //map pentru frecventa
        ArrayList<String> favoriteSerials = new ArrayList<>();
        Integer currValue = 0;
        Map<String, Integer> sorted = new HashMap<>();
        List<List<String>> listFilter = new ArrayList<>();
        ArrayList<String> outString = new ArrayList<>();
        int year = 0;
        String genresList ;


        //initializez aparitia fiecarui serial cu 0
        for(SerialInputData serialCheck : serials) {
            serialsFreq.put(serialCheck.getTitle(), 0);
        }

        //retin numarul de aparitii in lista de favorite ale fiecarui user
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

        //sortez
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

        if(listFilter.get(0).get(0) == null) {
            year = -1;
        } else {
            year = Integer.parseInt(listFilter.get(0).get(0)); //anul
        }

        genresList = listFilter.get(1).get(0); //genul

        Integer N = actions.getNumber();

        for(String movieName : sorted.keySet()) {


            for(SerialInputData checkSerial : serials) {
                Integer valid = 0;
                if(checkSerial.getTitle().equals(movieName)){
                    //validez atat anul cat si genul ca sa stiu daca se potriveste criteriului
                    if(checkSerial.getGenres().contains(genresList) && year == checkSerial.getYear()) {
                        valid = 1;
                    }
                }
                if(valid == 1) {
                    outString.add(movieName);
                    break;
                }

            }



            if(N == 0) { //ne oprim cand terminam de adaugat cele N elemente
                break;
            }
            N--;
        }


        return "Query result: " +outString.toString();
    }


}
