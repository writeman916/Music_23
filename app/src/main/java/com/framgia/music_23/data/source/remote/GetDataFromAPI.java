package com.framgia.music_23.data.source.remote;

import android.os.AsyncTask;
import com.framgia.music_23.data.source.OnFetchDataListener;
import com.framgia.music_23.utils.Constants;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONException;

public class GetDataFromAPI extends AsyncTask<String, Void, String> {

    private OnFetchDataListener mOnFetchDataListener;

    public GetDataFromAPI(OnFetchDataListener onFetchDataListener) {
        mOnFetchDataListener = onFetchDataListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        String data = "";
        try {
            data = getJSON(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    @Override
    protected void onPostExecute(String stringJSON) {
        super.onPostExecute(stringJSON);
        if (stringJSON != null) mOnFetchDataListener.onSuccess(stringJSON);
    }

    private String getJSON(String stringURL) throws IOException, JSONException {
        HttpURLConnection httpURLConnection;
        URL url = new URL(stringURL);
        httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod(Constants.REQUEST_METHOD);
        httpURLConnection.setReadTimeout(Constants.READ_TIMEOUT);
        httpURLConnection.setConnectTimeout(Constants.CONNECT_TIMEOUT);
        httpURLConnection.setDoOutput(false);
        httpURLConnection.connect();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            stringBuilder.append(line);
        }
        bufferedReader.close();
        httpURLConnection.disconnect();
        return stringBuilder.toString();
    }
}
