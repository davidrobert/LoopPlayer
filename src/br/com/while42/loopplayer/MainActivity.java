package br.com.while42.loopplayer;

import java.io.File;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {

	private MountSDCardReceiver mountSDCardReceiver = new MountSDCardReceiver();
	private VideoView videoView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		registerReceiver();

		videoView = (VideoView) findViewById(R.id.video);
	}

	@Override
	protected void onResume() {
		super.onResume();

		new AsyncTask<Object, Object, Object>() {

			@Override
			protected Object doInBackground(Object... params) {
				playVideo();
				return null;
			}

		};

	}

	public void playVideo(final String path) {

		final File video = new File(path);
		if (!video.isFile()) {
			String message = "Nenhum video encontrado!";
			Toast.makeText(this, message, Toast.LENGTH_LONG).show();
		}

		videoView.stopPlayback();

		View v = findViewById(R.id.main);
		v.setSystemUiVisibility(View.STATUS_BAR_HIDDEN);

		videoView.setVideoPath(path);
		videoView.setMediaController(new MediaController(this));
		videoView.requestFocus();
		videoView.setOnPreparedListener(new OnPreparedListener() {
			@Override
			public void onPrepared(MediaPlayer mp) {
				// Por algum motivo descnhecido isso funciona em um tablet e nao
				// funciona no AndroidPC
				mp.setLooping(true);
				Toast.makeText(MainActivity.this, ">> onPrepared <<",
						Toast.LENGTH_LONG).show();
			}
		});

		videoView.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				Toast.makeText(MainActivity.this, ">> onCompletion <<",
						Toast.LENGTH_LONG).show();
				// <- Assim funciona mas depois de muitas^muitas execucoes vai estourar a memoria
				playVideo(); 
			}
		});

		videoView.start();
	}

	private void registerReceiver() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_MEDIA_REMOVED);
		filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		filter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		filter.addAction(Intent.ACTION_MEDIA_BAD_REMOVAL);
		filter.addAction(Intent.ACTION_MEDIA_EJECT);
		filter.addDataScheme("file");
		this.registerReceiver(mountSDCardReceiver, filter);
	}

	// Somente para DEBUG
	private void playVideo() {
		Log.d("DEBUG", ">>>>>>>>>>>>>>>>"
				+ Environment.getExternalStorageDirectory().getPath());
		playVideo("/mnt/usbhost1/video.mp4");
	}
}
