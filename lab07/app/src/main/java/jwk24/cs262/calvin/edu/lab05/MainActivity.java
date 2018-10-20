package jwk24.cs262.calvin.edu.lab05;

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
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import jwk24.cs262.calvin.edu.lab05.R;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<String> {

    private EditText mBookInput;
    private String[] players = new String[]{R.string.check_network_text};
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         mBookInput = findViewById(R.id.bookInput);
        if(getSupportLoaderManager().getLoader(0)!=null){
            getSupportLoaderManager().initLoader(0,null,this);
        }
        players = new String[];
        mRecyclerView = (RecyclerView) findViewById(R.id.player_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new RecyclerAdapter(players);
        mRecyclerView.setAdapter(mAdapter);

    }

    public void searchBooks(View view) {

        String queryString = mBookInput.getText().toString();

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

        else {
            players = new String[]{R.string.check_network_text};
        }
    }

    @NonNull
    @Override
    public Loader<String> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new BookLoader(this, bundle.getString("queryString"));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<String> loader, String s) {
        System.out.println(s);
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
        System.out.println(players);
        for(String player : players){
            System.out.println(player);
        }
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
        players.add(result);
    }
}

/*
 private static final String MONOPOLY_PLAYERS_URL = "https://calvincs262-monopoly.appspot.com/monopoly/v1/players";
    private static final String MONOPOLY_PLAYER_ID_URL = "https://calvincs262-monopoly.appspot.com/monopoly/v1/player/";

     if(queryString == "") {
            Uri builtURI = Uri.parse(MONOPOLY_PLAYERS_URL).buildUpon().build();
        }
 */
