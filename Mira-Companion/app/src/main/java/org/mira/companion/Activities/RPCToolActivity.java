package org.mira.companion.Activities;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import org.mira.companion.Adapters.RPCAdapter;
import org.mira.companion.Models.Device;
import org.mira.companion.Models.RPCData;
import org.mira.companion.R;
import org.mira.companion.Utils.RPCHelper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RPCToolActivity extends AppCompatActivity {


    @BindView(R.id.rpc_datas_list)
    RecyclerView rpcRecyclerView;

    @BindView(R.id.extraButton)
    ImageButton extraButton;

    @BindView(R.id.Command_EditText)
    EditText command_EditText;

    @BindView(R.id.sendButton)
    ImageButton sendButton;

    RPCAdapter mAdapter;
    List<RPCData> rpcDataList= new ArrayList<RPCData>();
    Device myPs4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rpctool);
        ButterKnife.bind(this);
        ButterKnife.setDebug(true);

        mAdapter = new RPCAdapter(rpcDataList);

        // TODO : Somebody needs to write this part
        myPs4 = new Device();


        rpcRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        rpcRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCommand();
            }
        });


    }




    public void sendCommand()
    {
        RPCData newRpcData = new RPCData();
        newRpcData.setData(command_EditText.getText().toString());
        newRpcData.setTimestamp(System.currentTimeMillis());

        // Dummy datas
        newRpcData.setStatusCode(200);
        newRpcData.setFrom(1);

        // Send the command
        if(RPCHelper.sendCommand(newRpcData, myPs4))
        {

        }

        rpcDataList.add(newRpcData);

        // Clear the edit text
        command_EditText.setText("");
        mAdapter.notifyDataSetChanged();
        // SIMULATION

        simulateResponse();


    }

    // Just for simulation :/
    public void simulateResponse()
    {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                // Dummy datas


                RPCData response = new RPCData();
                response.setData("Eskeeetiiit :p !");
                response.setTimestamp(System.currentTimeMillis());
                response.setStatusCode(200);
                response.setFrom(2);

                rpcDataList.add(response);
                mAdapter.notifyDataSetChanged();

            }
        }, 1000);
    }

}
