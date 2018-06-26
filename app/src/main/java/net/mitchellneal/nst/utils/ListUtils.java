package neal.mitch.nsmaster.utils;

import java.util.ArrayList;

import neal.mitch.nsmaster.dataStructures.MyObj;

/**
 * Created by Mitch on 2/6/2018
 */

public class ListUtils {

    public static void insertOrdered(Object list, MyObj obj) {
        if(list == null || obj == null) return;
        ArrayList<MyObj> temp = (ArrayList<MyObj>) list;

        for(int i=0; i<temp.size(); i++) {
            if(((MyObj) temp.get(i)).getNumber() > ((MyObj) obj).getNumber()) {
                temp.add(i, obj);
                return;
            }
        }
        temp.add(temp.size(), obj);
    }
}
