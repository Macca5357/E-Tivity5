/* Student name: Gavin McCarthy
 * Student id: 19237766
 */
package com.service.threadsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    private DownloadImageTaskListener listener;

    public interface DownloadImageTaskListener {
        void onImageDownloaded(Bitmap bitmap);
    }

    public DownloadImageTask(DownloadImageTaskListener listener) {
        this.listener = listener;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String url = urls[0];
        Bitmap bitmap = null;
        try {
            InputStream in = new java.net.URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                // Create a Handler for the UI thread
                Handler handler = new Handler(Looper.getMainLooper());
                // Post a Runnable to the UI thread to update the UI
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (listener != null) {
                            listener.onImageDownloaded(result);
                        }
                    }
                });
            }
        }).start();
    }
}
