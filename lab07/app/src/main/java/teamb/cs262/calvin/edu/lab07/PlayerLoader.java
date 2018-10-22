package teamb.cs262.calvin.edu.lab07;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;
import android.widget.Toast;


public class PlayerLoader extends AsyncTaskLoader<String> {
    private String mQueryString;
    private Context context;
    public PlayerLoader(@NonNull Context context, String string) {
        super(context);
        this.context = context;
        mQueryString = string;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        String result = NetworkUtils.getPlayerInfo(mQueryString);
        if (result == null) {
            Toast.makeText(context, "There was a network error. Reconnect and try again.", Toast.LENGTH_LONG);
            return "";
        }
        return result;
    }

}
