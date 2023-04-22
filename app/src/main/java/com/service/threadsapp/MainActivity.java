/* Student name: Gavin McCarthy
 * Student id: 19237766
 */
package com.service.threadsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DownloadImageTask.DownloadImageTaskListener {
    private ImageView imageView;
    private Button downloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView);
        downloadButton = findViewById(R.id.downloadButton);
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageView.setImageDrawable(getDrawable(R.drawable.placeholder));
                startDownload();
            }
        });
    }

    public void startDownload() {
        downloadButton.setEnabled(false);
        Toast.makeText(this, getResources().getString(R.string.toastDownloadImage), Toast.LENGTH_SHORT).show();

        DownloadImageTask downloadImageTask = new DownloadImageTask(this);
        downloadImageTask.execute(getResources().getString(R.string.imageURL));
    }

    @Override
    public void onImageDownloaded(Bitmap bitmap) {
        downloadButton.setEnabled(true);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            Toast.makeText(this, getResources().getString(R.string.toastSuccess), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, getResources().getString(R.string.toastError), Toast.LENGTH_SHORT).show();
        }
    }
}
