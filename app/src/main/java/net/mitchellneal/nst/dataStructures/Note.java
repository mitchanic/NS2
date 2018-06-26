package neal.mitch.nsmaster.dataStructures;

import neal.mitch.nsmaster.utils.I;

/**
 * Created by Mitch on 2/6/2018
 */

public class Note extends MyObj {

    private Category category;
    private Goal goal;

    public Note() {
        super();
        type = I.note;
        maxLevel = 0;
    }
}
