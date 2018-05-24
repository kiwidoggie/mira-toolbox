package org.mira.companion.Adapters;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.mira.companion.Models.Downloadable;

import java.util.List;

/**
 * Created by @AstonBraham on 24/05/2018.
 */
public class DownloadablesAdapter extends BaseQuickAdapter<Downloadable, BaseViewHolder> {

    public DownloadablesAdapter(Activity activity, List<Downloadable> listOfDownloadables) {
        super(android.R.layout.simple_list_item_2, listOfDownloadables);
    }


    @Override
    protected void convert(final BaseViewHolder helper, final Downloadable item) {

        // TODO ---- Create a view and inflate it
        helper.setText(android.R.id.text1, item.getName())
                .setText(android.R.id.text2, item.getDescription());
    }


}