package neal.mitch.nsmaster.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import neal.mitch.nsmaster.R;

/**
 * Created by Mitchell Neal on 2/2/2018.
 *
 *
 * This class sets up the Hamburger Menu
 **/

public class MenuItemAdapter extends BaseAdapter {
    Context mContext;
    ArrayList<String> items;

    public MenuItemAdapter(Context con, ArrayList<String> objs) {
        items = objs;
        mContext = con;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        if (items.size()>=position) {
            return items.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_drawer_list, parent, false);
        TextView tv = (TextView) v.findViewById(R.id.text);
        tv.setTextSize(25);
        tv.setPadding(15, 10, 10, 10);
        tv.setText(items.get(position));
        return v;
    }
}
