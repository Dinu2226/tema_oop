package main;

import actions.commands.Commands;
import actions.query.actors;
import actions.recommendations.all_users;
import actions.recommendations.premium;
import checker.Checkstyle;
import checker.Checker;
import common.Constants;
import fileio.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * Call the main checker and the coding style checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(Constants.TESTS_PATH);
        Path path = Paths.get(Constants.RESULT_PATH);
        if (!Files.exists(path)) {
            Files.createDirectories(path);
        }

        File outputDirectory = new File(Constants.RESULT_PATH);

        Checker checker = new Checker();
        checker.deleteFiles(outputDirectory.listFiles());

        for (File file : Objects.requireNonNull(directory.listFiles())) {

            String filepath = Constants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getAbsolutePath(), filepath);
            }
        }

        checker.iterateFiles(Constants.RESULT_PATH, Constants.REF_PATH, Constants.TESTS_PATH);
        Checkstyle test = new Checkstyle();
        test.testCheckstyle();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        InputLoader inputLoader = new InputLoader(filePath1);
        Input input = inputLoader.readData();

        Writer fileWriter = new Writer(filePath2);
        JSONArray arrayResult = new JSONArray();

        //TODO add here the entry point to your implementation

        List<ActionInputData> actions = input.getCommands();
        List<ActorInputData> actors = input.getActors();
        List<MovieInputData> movies = input.getMovies();
        List<SerialInputData> serials = input.getSerials();
        List<UserInputData> users = input.getUsers();

        for(ActionInputData action : actions) {
            if(action.getActionType().equals("command")) {
                Commands command = new Commands(users,movies,serials,action);
                if(action.getType().equals("favorite")) {

                    String message = command.favorite();
                    JSONObject outString = fileWriter.writeFile(action.getActionId(),"",message);
                    arrayResult.add(outString);

                } else if(action.getType().equals("view")) {

                    String message = command.view();
                    JSONObject outString = fileWriter.writeFile(action.getActionId(),"",message);
                    arrayResult.add(outString);

                 } else if(action.getType().equals("rating")) {

                    String message = command.rating();
                    JSONObject outString = fileWriter.writeFile(action.getActionId(),"",message);
                    arrayResult.add(outString);

                }
            }else if(action.getActionType().equals("query")) {

                actions.query.actors query = new actors(users, movies, serials, actors, action);

                if (action.getObjectType().equals("actors")) {
                    if(action.getCriteria().equals("average")) {
                        String message = query.average();
                        JSONObject outString = fileWriter.writeFile(action.getActionId(), "", message);
                        arrayResult.add(outString);
                    }
                }

            }else if(action.getActionType().equals("recommendation")) {

                all_users recommendation = new all_users(users, movies, serials, action);
                premium recommendationP = new premium(users, movies, serials, action);

                if(action.getType().equals("standard")) {
                    String message = recommendation.standard();
                    JSONObject outString = fileWriter.writeFile(action.getActionId(),"",message);
                    arrayResult.add(outString);
                } else if(action.getType().equals("best_unseen")) {
                    String message = recommendation.best_unseen(movies);
                    JSONObject outString = fileWriter.writeFile(action.getActionId(),"",message);
                    arrayResult.add(outString);
                } else if(action.getType().equals("favorite")) {
                    String message = recommendationP.favorite();
                    JSONObject outString = fileWriter.writeFile(action.getActionId(),"",message);
                    arrayResult.add(outString);
                }

            }

        }
        fileWriter.closeJSON(arrayResult);
    }
}
