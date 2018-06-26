package neal.mitch.nsmaster.dataStructures;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Mitch on 2/6/2018
 */

public class AllData {

    private ArrayList<Category> categories;
    private ArrayList<Goal> goals;
    private ArrayList<Note> notes;
    private ArrayList<Task> tasks;
    private ArrayList<Task> inbox;

    public AllData() {
        categories = new ArrayList<>();
        goals = new ArrayList<>();
        notes = new ArrayList<>();
        tasks = new ArrayList<>();
        inbox = new ArrayList<>();

        initFakeData();//TODO move out of initializer
    }

    public void initFakeData() {
        int i=0;
        Random rand = new Random();
        for(i=0; i<8; i++) {
            Category cat = new Category();
            cat.setName("CAT");
            cat.setColor(rand.nextInt(16777215));
            categories.add(cat);
        }

        for(i=0; i<10; i++) {
            Goal goal = new Goal();
            goal.setName("GOAL");
            goals.add(goal);
        }

        for(i=0; i<10; i++) {
            Task task = new Task();
            task.setName("TASK");
            tasks.add(task);
        }
    }
}






















