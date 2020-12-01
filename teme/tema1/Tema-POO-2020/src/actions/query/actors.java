package actions.query;

import fileio.*;

import java.util.*;

public class actors {

    private List<UserInputData> users;
    private List<MovieInputData> movies;
    private List<SerialInputData> serials;
    private List<ActorInputData> actors;
    private ActionInputData actions;

    public actors(List<UserInputData> users, List<MovieInputData> movies, List<SerialInputData> serials,List<ActorInputData> actors, ActionInputData actions) {
        this.users = users;
        this.movies = movies;
        this. serials = serials;
        this.actors = actors;
        this.actions = actions;
    }

    public String average() {

        String outString = "Query result: [";

        Map<String, Double> actorsR = new HashMap<>();
        ArrayList<String> filmography = new ArrayList<>();

        for(ActorInputData checkActor : actors) {

            Double rating =  0.0;
            Integer size = 0;
            filmography = checkActor.getFilmography();

            for(String movie : filmography) {
                for(MovieInputData checkMovie : movies) {
                    if(checkMovie.getTitle().equals(movie)) {
                        if(checkMovie.getMedRating() != 0.0) {
                            rating += checkMovie.getMedRating();
                            size++;
                            System.out.println(rating);
                        }
                    }
                }

                for(String serial : filmography) {
                    for(SerialInputData checkSerial : serials) {
                        if(checkSerial.getTitle().equals(serial)) {
                            if(checkSerial.getMedSerialRating() != 0.0) {
                                rating += checkSerial.getMedSerialRating();
                                size++;
                            }
                        }
                    }
                }
            }
            rating /= size;
            actorsR.put(checkActor.getName(), rating);
        }

        //sortez map-ul ce contine actorii si media ratingurilor

        Map<String, Double> sortActors = new HashMap<>();

        actorsR.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortActors.put(x.getKey(), x.getValue()));

        //iau primii N actori

        Integer N = actions.getNumber();
        for(String actorName : sortActors.keySet()) {
            N--;
            outString += actorName;
            if(N == 0) {
                break;
            }
            outString += ", ";
        }
        return outString + "]";
    }

    public String awards() {

        String outString = "Query result: ";
        List<List<String>> listFilter = new ArrayList<>();
        List<String> listAwards = new ArrayList<>();

        for(ActorInputData checkActor : actors) {
            listFilter = actions.getFilters();
            listAwards = listFilter.get(3);
            for(String award : listAwards) {

            }
        }
    }

}
