package com.everest.moviedb.adapters

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.everest.moviedb.R
import com.everest.moviedb.ui.LatestMovieFragment
import com.everest.moviedb.ui.PopularMovieFragment


class PageAdapter(fragmentManager: FragmentManager, context: Context) :
    FragmentStatePagerAdapter(fragmentManager) {

    var pageTitles: Array<String> = context.resources.getStringArray(R.array.tab_titles)

    override fun getCount(): Int {
        return 2;
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> {
                LatestMovieFragment()
            }
            else -> {
                PopularMovieFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return pageTitles[position]
            }
            1 -> {
                return pageTitles[position]
            }
        }
        return super.getPageTitle(position)
    }
}