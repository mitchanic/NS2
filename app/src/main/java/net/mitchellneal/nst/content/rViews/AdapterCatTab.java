package neal.mitch.nsmaster.content.rViews;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import neal.mitch.nsmaster.R;
import neal.mitch.nsmaster.content.listFrag.ListItem;
import neal.mitch.nsmaster.dataStructures.Category;

/**
 * Created by Mitchell Neal on 2/5/2018
 */

public class AdapterCatTab extends RecyclerView.Adapter<CatTabHolder>{

    protected ArrayList<Category> items;

    protected Context mContext;
    protected EventBus bus;

    protected Point containerDimens;

    public AdapterCatTab() {
        items = new ArrayList<>();
        bus = EventBus.getDefault();
    }

    public AdapterCatTab(ArrayList<Category> items, Context context, Point cDimens) {
        this();
        this.items = items;
        this.mContext = context;
        this.containerDimens = cDimens;
    }

    @Override
    public CatTabHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cat_tab, parent, false);
        CatTabHolder holder = new CatTabHolder(v, mContext, containerDimens);

        return holder;
    }


    /**
     * Main Functions
     * =============================================================================================
     */

    @Override
    public void onBindViewHolder(CatTabHolder holder, final int position) {
        final int pos = position;
        final Category cat = items.get(pos);

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

        holder.setObject(cat);
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

    public void add(Category item) {
        items.add(items.size(), item);
    }

    public void clear() {
        items.clear();
    }
}
