package org.mira.companion.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import org.mira.companion.Models.Device;
import org.mira.companion.R;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by @monceeef on 25/05/2018.
 */

/**
 * A simple {@link Fragment} subclass.
 */
public class FileManager extends Fragment {

    //TODO:Check if connection is lost or connection is not made with the ps4
    //TODO:Make the connection with the ps4 server

    private Device mDevice;
    protected Socket clientSocket = null;
    protected static DataOutputStream outServer;
    protected static BufferedReader inServer;
    Button connectToPs4;
    String TAG = "FILEMANAGER";

    public FileManager() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_file_manager, container, false);
        mDevice = Device.getInstance();
        mDevice.setPort("23");
        mDevice.setIp("10.0.2.2");
        connectToPs4 = rootView.findViewById(R.id.connectToPS4);
        connectToPs4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        establishConnection();
                    }
                }).start();
            }
        });
        return rootView;
    }


    private void establishConnection()
    {
        try {
            InetSocketAddress socketAddr = new InetSocketAddress(mDevice.getIp(), Integer.parseInt(mDevice.getPort()));
            clientSocket = new Socket();

            //Connection timeout is 3 seconds , Should I make it more ?
            clientSocket.connect(socketAddr, 3000);

            outServer = new DataOutputStream(
                    clientSocket.getOutputStream());

            inServer = new BufferedReader(
                    new InputStreamReader(clientSocket.getInputStream()));
            //WILL IT ASK FOR AUTH ?
            //Maybe on ps4 connection ?

            //Starting here interaction will be with the ps4 server
            //TODO PS4 Interaction
        } catch (IOException e) {
            Log.d(TAG, e.toString());
        }
    }

}
