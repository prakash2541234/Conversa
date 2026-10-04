package com.conversa.v1;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        TextView textView = new TextView(this);

        textView.setText(
                "Conversa V1\n\n" +
                "Direct UI Monitor\n\n" +
                "Enable 'Conversa UI Monitor' in Android Accessibility settings."
        );

        textView.setTextSize(20);
        textView.setPadding(40, 80, 40, 40);

        setContentView(textView);
    }
}
