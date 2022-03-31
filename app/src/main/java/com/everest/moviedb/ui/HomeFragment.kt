package com.everest.moviedb.ui

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.everest.moviedb.R
import com.everest.moviedb.adapters.PageAdapter
import com.everest.moviedb.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    private lateinit var _binding: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        setHasOptionsMenu(true)

        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = _binding.viewPager
        viewPager.adapter = PageAdapter(childFragmentManager, requireContext())

        val tabLayout = _binding.tabLayout
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search ->
                loadActivity()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun loadActivity() {
        val intent = Intent(requireActivity(), SearchActivity::class.java)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)
    }
}