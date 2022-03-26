package com.everest.moviedb.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.everest.moviedb.ui.LatestMovieFragment
import com.everest.moviedb.ui.PopularMovieFragment

class PageAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(fragmentManager) {
    override fun getCount(): Int {
        return 2;
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                PopularMovieFragment()
            }
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
                return "Popular"
            }
            1 -> {
                return "Current Year"
            }
        }
        return super.getPageTitle(position)
    }
}