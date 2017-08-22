package li.quwat.flickrfeed;

import java.util.List;

/**
 * Created by a. z. quwatli on 8/22/2017.
 */

interface OnDataAvailable {
    void onDataAvailable(List<Photo> data, DownloadStatus status);
}
