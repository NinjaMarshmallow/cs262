package teamb.cs262.calvin.edu.lab07;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<String> {
    RecyclerView recyclerView;
    private String[] players = {"item1", "item2", "item3", "item4","item5","item6"};
    private List<String> playersList;
    EditText mPlayerInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
        }
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerAdapter(this, players));
        playersList = new ArrayList<String>();
        mPlayerInput = (EditText) findViewById(R.id.inputPlayer);
    }

    public void searchPlayers(View view) {

        String queryString = mPlayerInput.getText().toString();

//        InputMethodManager inputManager = (InputMethodManager)
//                getSystemService(Context.INPUT_METHOD_SERVICE);
//        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
//                InputMethodManager.HIDE_NOT_ALWAYS);

        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle queryBundle = new Bundle();
            queryBundle.putString("queryString", queryString);
            getSupportLoaderManager().restartLoader(0, queryBundle,this);
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new PlayerLoader(this, bundle.getString("queryString"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        playersList.clear();
        try {
            JSONObject json = new JSONObject(s);
            if(json.isNull("items")) {
                handleSinglePlayer(json);
            } else {
                handleListOfPlayers(json);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        recyclerView.setAdapter(new RecyclerAdapter(this, playersList.toArray(new String[playersList.size()])));
    }

    @Override
    public void onLoaderReset(@NonNull Loader<String> loader) {

    }

    private void handleListOfPlayers(JSONObject json) {
        try {
            JSONArray itemsArray = json.getJSONArray("items");
            for(int i = 0; i<itemsArray.length(); i++){
                JSONObject item = itemsArray.getJSONObject(i);
                handleSinglePlayer(item);
            }
        } catch(JSONException ex) {
            ex.printStackTrace();
        }
    }

    private void handleSinglePlayer(JSONObject json) {
        String id = null;
        String email = null;
        String name = "no name";
        try {
            id = json.getString("id");
            email = json.getString("emailAddress");
            if(!json.isNull("name")) {
                name = json.getString("name");
            }
        } catch (JSONException exForList) {
            exForList.printStackTrace();

        }
        String result = id + ", " + name + ", " + email;
        playersList.add(result);
    }
}
