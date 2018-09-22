 package jwk24.cs262.calvin.edu.lab02;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


 public class MainActivity extends AppCompatActivity {

     private int mCount = 0;
     private TextView mShowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mShowCount = (TextView) findViewById(R.id.show_count);
    }

     /**
      * The ToastButton calls this function when it is clicked
      * to display a message on screen
      * @param view View that calls this method when it is clicked
      */
     public void showToast(View view) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, R.string.toast_message, Toast.LENGTH_LONG);
        toast.show();
     }

     /**
      * The CountButton calls this function when it is clicked
      * to increment the toast counter
      * @param view View that calls this method when it is clicked
      */
     public void countUp(View view) {
        mCount++;
        if(mShowCount != null) {
            mShowCount.setText(Integer.toString(mCount));
        }
     }
 }
