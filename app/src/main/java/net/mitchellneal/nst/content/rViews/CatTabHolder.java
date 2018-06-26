package neal.mitch.nsmaster.content.rViews;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;

import neal.mitch.nsmaster.R;
import neal.mitch.nsmaster.content.listFrag.ListItem;
import neal.mitch.nsmaster.dataStructures.Category;

/**
 * Created by Mitchell Neal on 2/5/2018
 */

public class CatTabHolder extends RecyclerView.ViewHolder {

    private Category item;
    public Context context;
    private String setupType;

    public RelativeLayout container;
    protected Point containerDimens;
    RelativeLayout.LayoutParams containerParams;

    protected View.OnClickListener onClick;
    protected View.OnLongClickListener onLongClick;


    /**
     * Constructors
     * =============================================================================================
     **/
    /* Basic constructor for a LightHolder.
     * Instantiates the container and sets its background to null */
    public CatTabHolder(View itemView) {
        super(itemView);

        initViewElements();
    }

    /* Calls the default constructor for a LightHolder, sets the containerDimens and itemDimens */
    public CatTabHolder(View itemView, Context context, Point containerDimens) {
        this(itemView);

        this.context = context;
        this.containerDimens = containerDimens;
    }

    /* Instantiates view elements.
     * Called by Constructors */
    private void initViewElements() {
        container = itemView.findViewById(R.id.container);
    }

    /**
     * INIT PRIMARY METHODS
     * =============================================================================================
     * These methods are called during Init(). All variables should have been set before Init()
     * is called.
     **/
    /* Initializes container params, item params, and icon. */
    public void init(Context context) {
        this.context = context;

        initContainer();
        //initItem();
        //TODO
    }

    /* Instantiates and initializes the container LayoutParams based on containerDimens.
     * Called by init() */
    private void initContainer() {
        if (container != null) {
            containerParams = (RelativeLayout.LayoutParams) container.getLayoutParams();
            containerParams.width = containerDimens.x;
            this.container.setLayoutParams(containerParams);
        }
    }



    /**
     * GENERAL SETTERS
     * =============================================================================================
     **/

    /* Sets the object for the holder */
    public void setObject(Object obj) {
        item = (ListItem) obj;
    }

    /* Sets the onClick listener for the holder, to be attached to holder views in other methods */
    public void setOnClick(View.OnClickListener onClick) {
        this.onClick = onClick;
    }

    /* Sets the onLongClick listener for the holder, to be attached to holder views in other methods */
    public void setOnLongClick(View.OnLongClickListener onLongClick) {
        this.onLongClick = onLongClick;
    }

    /**
     * INIT SECONDARY METHODS
     * =============================================================================================
     * These methods are called during methods in the INIT PRIMARY METHODS section.
     **/

}