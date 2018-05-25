package org.mira.companion.Adapters;

import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import org.mira.companion.Models.RPCData;
import org.mira.companion.R;
import org.mira.companion.Utils.MyDateUtils;

import java.util.List;


public class RPCAdapter extends BaseMultiItemQuickAdapter<RPCData, BaseViewHolder> {


//    private List<RPCData> mDataList;

    public RPCAdapter(List<RPCData> data)
    {
        super(data);
 //       this.mDataList = data;
            addItemType(RPCData.SENT, R.layout.rpc_sent_item_layout);
            addItemType(RPCData.RECEIVED, R.layout.rpc_received_item_layout);


    }

    @Override
    protected int getDefItemViewType(int position) {

        RPCData mRpcData = getItem(position);

     if(mRpcData.getFrom() == 1)
        {
            return RPCData.SENT;
        }
        else
        {
            return  RPCData.RECEIVED;
        }

    }

    @Override
    protected void convert(final BaseViewHolder helper, final RPCData data) {

        final CharSequence  relativeDate = MyDateUtils.getRelativeTimeSpanString(data.getTimestamp(),
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS, DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_YEAR, mContext);

                helper.setText(R.id.dataText, ""+data.getData())
                        .setText(R.id.dataTime, relativeDate);


                Log.e("CONVERTER", "Converting :"+data.toString());
    }

    @Override
    public boolean setOnItemLongClick(View v, int position) {
        return super.setOnItemLongClick(v, position);


    }


}
