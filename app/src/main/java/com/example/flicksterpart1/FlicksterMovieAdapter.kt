package com.codepath.bestsellerlistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.flicksterpart1.R.id
import com.example.flicksterpart1.FlicksterMovie
import com.example.flicksterpart1.OnListFragmentInteractionListener
import com.example.flicksterpart1.R

/**
 * [RecyclerView.Adapter] that can display a [BestSellerBook] and makes a call to the
 * specified [OnListFragmentInteractionListener].
 */
class FlicksterMovieAdapter(
    private val movies: List<FlicksterMovie>,
    private val mListener: OnListFragmentInteractionListener?
)
    : RecyclerView.Adapter<FlicksterMovieAdapter.BookViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.flickster_movie, parent, false)
        return BookViewHolder(view)
    }

    /**
     * This inner class lets us refer to all the different View elements
     * (Yes, the same ones as in the XML layout files!)
     */
    inner class BookViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: FlicksterMovie? = null
        val movieTitleView: TextView = mView.findViewById<View>(id.movieTitle) as TextView
        val movieOverviewView: TextView = mView.findViewById<View>(id.movieOverview) as TextView
        val moviePosterView: ImageView = mView.findViewById<View>(id.moviePoster) as ImageView

        override fun toString(): String {
            return movieTitleView.toString() + " '" + movieOverviewView.text + "'"
        }
    }

    /**
     * This lets us "bind" each Views in the ViewHolder to its' actual data!
     */
    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.movieTitleView.text = movie.title
        holder.movieOverviewView.text = movie.overview
        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
            .placeholder(R.drawable.slim_peter)
            .centerInside()
            .into(holder.moviePosterView)

/*
        holder.mBookButton.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(book.amazonUrl))
            //startActivity(it.context, browserIntent, null)
        }
*/
        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie)
            }
        }

    }
        /**
         * Remember: RecyclerView adapters require a getItemCount() method.
         */
        override fun getItemCount(): Int {
            return movies.size
        }
    }
