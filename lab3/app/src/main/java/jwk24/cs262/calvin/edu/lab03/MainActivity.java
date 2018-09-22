package jwk24.cs262.calvin.edu.lab03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE =
            "edu.calvin.cs262.jwk24.extra.MESSAGE";
    public static final int TEXT_REQUEST = 1;
    private EditText mMessageEditText;
    private static final String LOG_TAG =
            MainActivity.class.getSimpleName();
    private TextView mReplyHeadTextView;
    private TextView mReplyTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMessageEditText = (EditText) findViewById(R.id.editText_main);
        mReplyHeadTextView = (TextView) findViewById(R.id.text_header_reply);
        mReplyTextView = (TextView) findViewById(R.id.text_message_reply);

    }

    public void launchSecondActivity(View view) {
        Log.d(LOG_TAG, "Button clicked!");
        String message = mMessageEditText.getText().toString();
        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivityForResult(intent, TEXT_REQUEST);
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (requestCode == TEXT_REQUEST) {
            if (resultCode == RESULT_OK) {
                String reply = data.getStringExtra(SecondActivity.EXTRA_REPLY);
                mReplyTextView.setText(reply);
                mReplyTextView.setVisibility(View.VISIBLE);
                mReplyHeadTextView.setVisibility(View.VISIBLE);
            }
        }
    }
}
