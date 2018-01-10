package com.smartc.mohamed.senddata;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    EditText edMessage;
    Button btnSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edMessage = (EditText) findViewById(R.id.ed_message);
        btnSend = (Button ) findViewById(R.id.btn_send);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageSender messageSender = new MessageSender();
                messageSender.execute(edMessage.getText().toString());
            }
        });

        Thread myThread = new Thread(new MyServerThread(7100));
        myThread.start();

    }


    class MyServerThread implements Runnable {


        Socket s;
        ServerSocket ss;
        InputStreamReader isr;
        BufferedReader br;
        Handler handler = new Handler();
        String message;
        int port;

        public MyServerThread(int port) {
            this.port = port;
        }

        @Override
        public void run() {
            try{

                ss = new ServerSocket(port);
                while(true){
                    s = ss.accept();
                    isr = new InputStreamReader(s.getInputStream());
                    br  = new BufferedReader(isr);
                    message = br.readLine();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
                        }
                    });


                }


            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}
