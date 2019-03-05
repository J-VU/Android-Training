package example.supervoo.whowroteit;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

public class BookLoader extends AsyncTaskLoader<String> {

    private String mQueryString;

     BookLoader(@NonNull Context context, String queryString) {
        super(context);
        mQueryString=queryString;


    }


    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    // Async Task
    @Nullable
    @Override
    public String loadInBackground() {

        return NetworkUtils.getBookInfo(mQueryString);
    }
}
