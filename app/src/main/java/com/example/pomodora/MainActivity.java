package com.example.pomodora;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    SeekBar seekbar;
    TextView timer;
    Boolean timerActive = false;
    Button start;
    long timeleft;
    MediaPlayer mediaPlayer;
    CountDownTimer countDownTimer = null;

    public void updateTimer(int timeLeft) {

        int minutes =  ( int ) timeLeft / 60;
        int hours = minutes / 60;
        int seconds = timeLeft - minutes * 60;

        if ( minutes > 59 ) {

            minutes = 60 - (120 - minutes);

        }


        String Stringminutes = Integer.toString(minutes);
        String Stringseconds = Integer.toString(seconds);

        if ( minutes <= 9 ) {

            Stringminutes = "0" + Stringminutes;
        }

        if ( seconds <= 9) {

            Stringseconds = "0" + Stringseconds;

        }

        timer.setText(Integer.toString(hours) + ":" + Stringminutes + ":" + Stringseconds);

    }

    public void controlTimer(View view) {

        if ( timerActive == false) {

            timerActive = true;
            seekbar.setEnabled(false);

            start.setText("Reset");

            countDownTimer = new CountDownTimer(seekbar.getProgress() * 1000 + 100, 1000) {

                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer((int) millisUntilFinished / 1000);
                    timeleft = millisUntilFinished;

                }

                @Override
                public void onFinish() {

                    timer.setText("0:00:00");
                    mediaPlayer = MediaPlayer.create(MainActivity.this, R.raw.alarm);
                    mediaPlayer.start();

                }
            }.start();

        } else {

            timerActive = false;
            seekbar.setEnabled(true);
            start.setText("GO !");
            timer.setText("0:25:00");
            seekbar.setProgress(1500);
            countDownTimer.cancel();
            if ( mediaPlayer != null ) {

                mediaPlayer.stop();
                mediaPlayer.release();

            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        timer = (TextView) findViewById(R.id.timer);
        seekbar = ( SeekBar ) findViewById(R.id.seekBar);
        seekbar.setMax(7200);
        seekbar.setProgress(1500);
        start = ( Button ) findViewById(R.id.start);

        seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

                updateTimer(i);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
}
