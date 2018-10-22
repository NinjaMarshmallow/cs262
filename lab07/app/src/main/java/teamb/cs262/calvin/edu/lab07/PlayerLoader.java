package teamb.cs262.calvin.edu.lab07;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;


public class PlayerLoader extends AsyncTaskLoader<String> {
    private String mQueryString;
    public PlayerLoader(@NonNull Context context, String string) {
        super(context);
        mQueryString = string;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public String loadInBackground() {
        return NetworkUtils.getPlayerInfo(mQueryString);
    }

}
