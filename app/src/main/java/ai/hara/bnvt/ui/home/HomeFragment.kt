package ai.hara.bnvt.ui.home

import ai.hara.bnvt.data.model.Song
import ai.hara.bnvt.ui.main.MainViewModel
import ai.hara.bnvt.util.enums.Status
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bnvt.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        homeViewModel.getSongs().observe(viewLifecycleOwner) { response ->
            when (response.status) {
                Status.ERROR -> mainViewModel.isLoggedOut.value = true
                Status.SUCCESS -> playSongs(response.data)
                else -> {}
            }
        }
        return root
    }

    private fun playSongs(songs: List<Song>?) {

        Log.i("Mohammad", "Song list received:" + (songs?.size ?: 0))
        songs?.let {
            binding.textHome.text = it.size.toString()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}