package neal.mitch.nsmaster.dataStructures;

import java.util.ArrayList;

import neal.mitch.nsmaster.utils.I;

/**
 * Created by Mitch on 2/6/2018
 */

public class Task extends MyObj {

    private Category category;
    private Goal goal;
    private Task parentTask;
    private ArrayList<Task> subtasks;

    public Task() {
        super();
        type = I.task;
        maxLevel = 0;
        subtasks = new ArrayList<>();
    }

    public int addSubtask(Task sub) {
        if(!canAddChild()) {
            return 0;
        }
        sub.setNumber(subtasks.size());
        sub.setParentTask(this);
        sub.level = this.level + 1;
        subtasks.add(subtasks.size(), sub);
        return 1;
    }

    public void setParentTask(Task parent) {
        parentTask = parent;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
