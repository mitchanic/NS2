package neal.mitch.nsmaster.dataStructures;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Mitch on 2/6/2018
 */

public class MyObj extends Object{

    protected int id;
    protected int number;
    protected String name;
    protected String longDescripton;
    protected String type;

    protected ArrayList<String> tags;
    protected int level;
    protected int maxLevel;

    protected Date dateCreated;
    protected Date dateEdited;
    protected Date dueDate;
    protected Date reminderDate;

    public MyObj() {
        level = 0;
        maxLevel = 1;
    }

    public boolean canAddChild() {
        if(level < maxLevel) {
            return true;
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumber(int num) {
        this.number = num;
    }

    public int getNumber() {
        return this.number;
    }
}
