package com.smartc.mohamed.senddata;

import android.os.AsyncTask;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Mido on 12/28/17.
 */

public class MessageSender extends AsyncTask<String,Void,Void> {

    Socket s ;
    PrintWriter pw;
    DataOutputStream dos;
    @Override
    protected Void doInBackground(String... voids) {

        String message = voids[0];
        try {
            s= new Socket("192.168.1.11",7800);
            pw = new PrintWriter(s.getOutputStream());
            pw.write(message);
            pw.flush();
            pw.close();
            s.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }
}
