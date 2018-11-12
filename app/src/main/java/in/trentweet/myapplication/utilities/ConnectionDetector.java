package in.trentweet.myapplication.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Class used to detect the internet connection throughout the application.
 */
public class ConnectionDetector {
    private Context _context;


    public ConnectionDetector(Context context) {
        this._context = context;
    }


    /**
     * Checking for all possible internet providers
     **/

    public boolean isConnectedToInternet() {

        ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null)
                if (info.getState() == NetworkInfo.State.CONNECTED || info.getState() == NetworkInfo.State.CONNECTING) {
                    return info.isAvailable();
                }
        }
        return false;
    }


}
