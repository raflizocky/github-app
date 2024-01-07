package com.zocky.githubapp.ui.detail.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.zocky.githubapp.databinding.FragmentFollowBinding

class FollowFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FollowViewModel by lazy {
        ViewModelProvider(this)[FollowViewModel::class.java]
    }
    private val followAdapter = FollowAdapter()

    private val TAG = "FollowFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get username from arguments
        val username = arguments?.getString("username") ?: ""
        val isFollowersTab = arguments?.getBoolean("isFollowersTab") ?: true
        Log.d(TAG, "Username: $username")

        // initiate rv & adapter
        binding.rvFollow.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollow.adapter = followAdapter

        // call API to get followers/following list based on active tab
        if (isFollowersTab) {
            viewModel.getFollowers(username)
        } else {
            viewModel.getFollowing(username)
        }

        binding.progressBar.visibility = View.VISIBLE

        // observer for active followers/following tab
        if (isFollowersTab) {
            viewModel.followers.observe(viewLifecycleOwner) { followers ->
                followAdapter.submitList(followers)
                hideProgressBarIfBothRequestsDone()
            }
        } else {
            viewModel.following.observe(viewLifecycleOwner) { following ->
                followAdapter.submitList(following)
                hideProgressBarIfBothRequestsDone()
            }
        }
    }

    private fun hideProgressBarIfBothRequestsDone() {
        if (viewModel.followers.value != null && viewModel.following.value != null) {
            binding.progressBar.visibility = View.GONE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(username: String, isFollowersTab: Boolean): FollowFragment {
            return FollowFragment().apply {
                arguments = Bundle().apply {
                    putString("username", username)
                    putBoolean("isFollowersTab", isFollowersTab)
                }
            }
        }
    }
}
