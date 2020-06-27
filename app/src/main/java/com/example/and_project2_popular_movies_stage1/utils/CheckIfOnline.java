package com.example.and_project2_popular_movies_stage1.utils;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

// CheckIfOnline class based on https://stackoverflow.com/a/27312494
public class CheckIfOnline extends AsyncTask<Void, Void, Boolean> {
    private Consumer consumer;

    public CheckIfOnline(Consumer consumer) {
        this.consumer = consumer;
        execute();
    }

    // This is a public Google DNS server. In theory, it should never be offline.
    @Override
    protected Boolean doInBackground(Void... voids) {
        try {
            Socket sock = new Socket();
            sock.connect(new InetSocketAddress("8.8.8.8", 53), 1500);
            sock.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean internet) {
        consumer.accept(internet);
    }

    public interface Consumer {
        void accept(Boolean internet);
    }

}
