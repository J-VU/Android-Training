package example.supervoo.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {

    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;
    private WeakReference<TextView> mDescription;

    public FetchBook(TextView titleText, TextView authorText, TextView description) {
        this.mTitleText = new WeakReference<>(titleText);
        this.mAuthorText = new WeakReference<>(authorText);
        this.mDescription = new WeakReference<>(description);
    }


    @Override
    protected String doInBackground(String... strings) {

        return NetworkUtils.getBookInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");

            int i = 0;
            String title = null;
            String authors = null;
            String description = null;

            while (i < itemsArray.length() && (authors == null && title == null && description == null)) {
                // Get the current item information.
                JSONObject book = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");

                // Try to get the author and title from the current item,
                // catch if either field is empty and move on.
                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                    description = volumeInfo.getString("description");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                // Move to the next item.
                i++;
            }

            if (title != null && authors != null && description != null) {
                mTitleText.get().setText(title);
                mAuthorText.get().setText(authors);
                mDescription.get().setText(description);
            } else {
                mTitleText.get().setText("No book found!");
                mAuthorText.get().setText(R.string.not_available);
                mDescription.get().setText(R.string.not_available);
            }


        } catch (JSONException e) {
            // If onPostExecute does not receive a proper JSON string,
            // update the UI to show failed results.
            mTitleText.get().setText("Network Failed");
            mAuthorText.get().setText(R.string.not_available);
            mDescription.get().setText(R.string.not_available);
            e.printStackTrace();
        }


    }
}



