package neal.mitch.nsmaster.content.listFrag;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import neal.mitch.nsmaster.R;

/**
 * Created by Mitchell Neal on 2/5/2018
 */

public class AdapterListItem extends RecyclerView.Adapter<ListItemHolder>{

    protected ArrayList<ListItem> items;

    protected Context mContext;
    protected EventBus bus;

    protected Point containerDimens;
    protected Point itemDimens;

    public AdapterListItem() {
        items = new ArrayList<>();
        bus = EventBus.getDefault();
    }

    public AdapterListItem(ArrayList<ListItem> items, Context context, Point cDimens) {
        this();
        this.items = items;
        this.mContext = context;
        this.containerDimens = cDimens;
    }

    @Override
    public ListItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_item, parent, false);
        ListItemHolder holder = new ListItemHolder(v, mContext, containerDimens);

        return holder;
    }


    /**
     * Main Functions
     * =============================================================================================
     */

    @Override
    public void onBindViewHolder(ListItemHolder holder, final int position) {
        final int pos = position;
        final ListItem item = items.get(pos);

        View.OnClickListener mOnClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        };

        View.OnLongClickListener mOnLongClick = new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View view) {
                return false;
            }
        };

        holder.setObject(item);
        holder.setOnClick(mOnClick);
        holder.setOnLongClick(mOnLongClick);
        holder.init(mContext);
    }



    /**
     * Communication and Data Handling
     * =============================================================================================
     */



    /**
     * Utilities
     * =============================================================================================
     */

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView rView) {
        super.onDetachedFromRecyclerView(rView);
    }

    @Override
    public int getItemCount() {
        if (items != null) {
            return items.size();
        }
        return 0;
    }

    public void add(ListItem item) {
        items.add(items.size(), item);
    }

    public void clear() {
        items.clear();
    }
}
