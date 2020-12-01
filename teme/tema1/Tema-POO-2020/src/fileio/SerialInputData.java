package fileio;

import entertainment.Season;

import java.util.ArrayList;

/**
 * Information about a tv show, retrieved from parsing the input test files
 * <p>
 * DO NOT MODIFY
 */
public final class SerialInputData extends ShowInput {
    /**
     * Number of seasons
     */
    private final int numberOfSeasons;
    /**
     * Season list
     */
    private final ArrayList<Season> seasons;
    private ArrayList<Double> ratings = new ArrayList<Double>(); //retine ratingurile


    public ArrayList<Double> getSerialRatings() {
        return ratings;
    }

    public void setSerialRatings(ArrayList<Double> ratings) {
        this.ratings = ratings;
    }

    //metoda ce-mi returneaza media ratingurilor
    public Double getMedSerialRating() {
        if(ratings.size() == 0) {
            return  0.0;
        }
        Double rating = 0.0;
        for(Double it : ratings) {
            rating += it;
        }

        return rating / ratings.size();
    }

    public SerialInputData(final String title, final ArrayList<String> cast,
                           final ArrayList<String> genres,
                           final int numberOfSeasons, final ArrayList<Season> seasons,
                           final int year) {
        super(title, year, cast, genres);
        this.numberOfSeasons = numberOfSeasons;
        this.seasons = seasons;
    }

    public int getNumberSeason() {
        return numberOfSeasons;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    @Override
    public String toString() {
        return "SerialInputData{" + " title= "
                + super.getTitle() + " " + " year= "
                + super.getYear() + " cast {"
                + super.getCast() + " }\n" + " genres {"
                + super.getGenres() + " }\n "
                + " numberSeason= " + numberOfSeasons
                + ", seasons=" + seasons + "\n\n" + '}';
    }
}
