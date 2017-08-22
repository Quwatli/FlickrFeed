package li.quwat.flickrfeed;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

enum DownloadStatus {IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK}

/**
 * Created by a. z. quwatli on 8/22/2017.
 */

class GetRawData extends AsyncTask<String, Void, String> {

    private static final String TAG = "GetRawData";

    //the interface OnDownloadComplete has the purpose of making sure the GetRawData downloading class gets the correct object that has an OnDownloadComplete method
    //the importance of this move is to decouple the class, ie object, doing the downloading from the main activity and the parsing/processing objects

    private DownloadStatus downloadStatus;

    private final OnDownloadComplete callBack;


    public  GetRawData(OnDownloadComplete callback) {
        this.downloadStatus = DownloadStatus.IDLE;
        callBack = callback;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: parameter is "+s);

        if (callBack != null) {
            callBack.onDownloadComplete(s, downloadStatus);
        }
        Log.d(TAG, "onPostExecute: ends");

        //Although the super method does not do anything and is actually empty with simple code to suppress the unused annotation, I'm going to comment it out
        //as there is a possibility that it throws some exception at certain situations as I found in my readings
//        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;
        if (params == null) {
            downloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }

        try {
            downloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: The response code: "+response);
            StringBuilder result = new StringBuilder();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String line;

            //Using the null check first is a good practice to point the reader/reviewer into paying attention into the actual line processing so the code is better received
            while (null!=(line=reader.readLine())) {

                //Alternatively, we could use a for loop to restrict the line variable into the loop, making the code more robust
                //as follows
                //for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                //each time the for assigns the line variable the line it is reading

                result.append(line).append("\n");
            }

            downloadStatus = DownloadStatus.OK;
            return result.toString();

        } catch (MalformedURLException e){
            Log.e(TAG, "doInBackground: Invalid URL "+e.getMessage() );
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IOEXceptio reading data "+e.getMessage() );
        } catch(SecurityException e) {
            Log.e(TAG, "doInBackground: Security exception, needs permission "+e.getMessage() );
        } finally {

            //Finally block is important as it executes regardless if an exceptio is thrown or not, thus closing the stream here is a good place to do so
            if (connection!=null) {
                connection.disconnect();
            }
            if (reader!=null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error closing stream "+e.getMessage() );
                }
            }
        }

        downloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
}
