package li.quwat.flickrfeed;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by a. z. quwatli on 8/22/2017.
 */

class GetFlickrJSONData implements OnDownloadComplete {
    private static final String TAG = "GetFlickrJSONData";

    private List<Photo> PhotoList = null;
    private String BaseURL;
    private String Language;
    private boolean MatchAll;

    private final OnDataAvailable callBack;

    public GetFlickrJSONData(String baseURL, String language, boolean matchAll, OnDataAvailable callBack) {
        Log.d(TAG, "GetFlickrJSONData: Called");
        BaseURL = baseURL;
        Language = language;
        MatchAll = matchAll;
        this.callBack = callBack;
    }

    void executeOnSameThread(String search) {
        Log.d(TAG, "executeOnSameThread: Starts");
        String destinationURI = createURI(search, Language, MatchAll);

        GetRawData getRawData = new GetRawData(this);
        getRawData.execute(destinationURI);
        Log.d(TAG, "executeOnSameThread: Ends");
    }

    private String createURI(String search, String lang, boolean matchAll) {
        Log.d(TAG, "createURI: Starts");

        return Uri.parse(BaseURL).buildUpon().appendQueryParameter("tags", search).appendQueryParameter("tagmode", matchAll ? "ALL" : "ANY")
                .appendQueryParameter("lang", lang).appendQueryParameter("format", "json").appendQueryParameter("nojsoncallback", "1").build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: status returned "+status);

        if (status == DownloadStatus.OK) {
            PhotoList = new ArrayList<>();

            try {
                JSONObject jsonData = new JSONObject(data);
                JSONArray itemsArray = jsonData.getJSONArray("items");

                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject jsonPhoto = itemsArray.getJSONObject(i);
                    String title = jsonPhoto.getString("title");
                    String author = jsonPhoto.getString("author");
                    String authorid = jsonPhoto.getString("author_id");
                    String tags = jsonPhoto.getString("tags");

                    //JSON object inside the main array object
                    JSONObject jsonMedia = jsonPhoto.getJSONObject("media");
                    String photoURL = jsonMedia.getString("m");

                    String link = photoURL.replaceFirst("_m.", "_b.");
                }
            }
        }

    }
}
