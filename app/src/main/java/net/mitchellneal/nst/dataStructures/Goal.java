package neal.mitch.nsmaster.dataStructures;

import java.util.ArrayList;

import neal.mitch.nsmaster.utils.I;

/**
 * Created by Mitch on 2/6/2018
 */

public class Goal extends MyObj {

    private Category category;
    private ArrayList<Task> tasks;
    private ArrayList<Note> notes;

    public Goal() {
        super();
        type = I.goal;
        maxLevel = 1;
    }
}
