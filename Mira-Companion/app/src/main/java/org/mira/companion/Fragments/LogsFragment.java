package org.mira.companion.Fragments;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.mira.companion.R;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by @valentinbreiz on 24/05/2018.
 */
public class LogsFragment extends Fragment {


    public LogsFragment() {
        // Required empty public constructor
    }

    private  TextView Logs;

    private ReadLogs readlogs;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View RootView = inflater.inflate(R.layout.fragment_logs, container, false);

        Logs  = (TextView) RootView.findViewById(R.id.Logs_Textview);

        ip = "192.168.1.17";
        port = 9998;

        params = new ConnectionConfig(ip, port);

        Logs.setText("Logs from " + ip + ":" + Integer.toString(port));

        readlogs = new ReadLogs();
        readlogs.execute(params);

        return inflater.inflate(R.layout.fragment_logs, container, false);
    }

    private static class ConnectionConfig {
        String ip;
        int port;

        ConnectionConfig(String ip, int port) {
            this.ip = ip;
            this.port = port;
        }
    }

    private Socket socket;
    private ConnectionConfig params;

    private String ip;
    private int port;

    private DataInputStream dIn;

    private class ReadLogs extends AsyncTask<ConnectionConfig, Integer, Void>
    {
        @Override
        protected Void doInBackground(ConnectionConfig... params)
        {
            try
            {
                String ip = params[0].ip;
                int port = params[0].port;
                socket = new Socket(ip, port);


                StringBuffer inputLine = new StringBuffer();
                String response;

                while ((response = dIn.readLine()) != null)
                {
                    inputLine.append(response);
                    Logs.setText(Logs.getText().toString() + "\n" + response);
                }
            }
            catch (UnknownHostException e1)
            {

            }
            catch (IOException e1)
            {

            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(Void voidd) {

        }
    }

}
