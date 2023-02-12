package com.example.flicksterpart1
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler.JSON
import com.codepath.bestsellerlistapp.FlicksterMovieAdapter
import com.google.gson.Gson
import com.example.flicksterpart1.OnListFragmentInteractionListener
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONObject

private const val API_KEY = "a07e22bc18f5cb106bfe4cc1f83ad8ed"

class FlicksterMovieFragment : Fragment(), OnListFragmentInteractionListener {

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.flickster_movie_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.MovieRecyclerView) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1)
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val syncClient = AsyncHttpClient()
        val reqParams = RequestParams()
        reqParams["api_key"]= API_KEY

        syncClient[
                "https://api.themoviedb.org/3/movie/now_playing",
                reqParams,
                object: JsonHttpResponseHandler()
                // Using the client, perform the HTTP request

                {
                    /*
                     * The onSuccess function gets called when
                     * HTTP response status is "200 OK"
                     */
                    override fun onSuccess(
                        statusCode: Int,
                        headers: Headers,
                        json: JsonHttpResponseHandler.JSON
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        //TODO - Parse JSON into Models
                        val resultsJSON : JSONArray = json.jsonObject.get("results") as JSONArray
                        val moviesRawJSON : String = resultsJSON.toString()
                        Log.d("FlicksterMovieFragment",moviesRawJSON)
                        val gson = Gson()
                        val arrayMovieType = object : TypeToken<List<FlicksterMovie>>() {}.type
                        val models : List<FlicksterMovie> = gson.fromJson(moviesRawJSON,arrayMovieType)// Fix me!
                        recyclerView.adapter = FlicksterMovieAdapter(models, this@FlicksterMovieFragment)

                        // Look for this in Logcat:
                        Log.d("FlicksterMovieFragment", "response successful")
                    }

                    /*
                     * The onFailure function gets called when
                     * HTTP response status is "4XX" (eg. 401, 403, 404)
                     */
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        errorResponse: String,
                        t: Throwable?
                    ) {
                        // The wait for a response is over
                        progressBar.hide()

                        // If the error is not null, log it!
                        t?.message?.let {
                            Log.e("FlicksterMovieFragment", errorResponse)
                        }
                    }
                }]


    }

    /*
     * What happens when a particular book is clicked.
     */
    override fun onItemClick(item: FlicksterMovie) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

}