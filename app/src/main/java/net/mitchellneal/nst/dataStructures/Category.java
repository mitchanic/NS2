package neal.mitch.nsmaster.dataStructures;

import java.util.ArrayList;

import neal.mitch.nsmaster.utils.I;
import neal.mitch.nsmaster.utils.ListUtils;

/**
 * Created by Mitch on 2/6/2018
 */

public class Category extends MyObj {

    private int color;
    private ArrayList<Goal> goals;
    private ArrayList<Task> tasks;
    private ArrayList<Note> notes;

    public Category() {
        super();
        type = I.category;
        maxLevel = 0;
    }

    public int addTask(Task task) {
        if(!canAddChild()) {
            return 0;
        }
        task.setNumber(tasks.size());
        task.level = 0;
        ListUtils.insertOrdered(tasks, task);
        return 1;
    }

    public void setColor(int col) {
        this.color = col;
    }
}
