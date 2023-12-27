package ai.hara.ureshii.ui.main

import ai.hara.ureshii.R
import ai.hara.ureshii.databinding.ActivityMainBinding
import ai.hara.ureshii.service.PlayerEvent
import ai.hara.ureshii.service.SimpleMediaService
import ai.hara.ureshii.ui.login.LoginActivity
import ai.hara.ureshii.ui.player.PlayerFragment
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.media3.common.MediaItem
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var isServiceRunning = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.loading.value
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navView: BottomNavigationView = binding.navView
        viewModel.onUIEvent(PlayerEvent.PlayPause)
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        navView.setupWithNavController(navController)
        startService()

        viewModel.isLoggedOut.observe(this){isLoggedOut->
            if (isLoggedOut){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        viewModel.tempSelectedSongLiveData.observe(this){
            val fragmentA: Fragment? = supportFragmentManager.findFragmentByTag("playerFragment")
            if (fragmentA == null){
                val fa: FragmentTransaction = supportFragmentManager.beginTransaction()
                fa.replace(R.id.activity_main_container, PlayerFragment.newInstance(), "playerFragment").addToBackStack(null)
                    .commit()
            }
            updateUI(it)
        }
    }

    private fun updateUI(mediaItem: MediaItem) {
        binding.activityMainPlayback.visibility = View.VISIBLE

//        GlideApp.with(this)
//            .load(metadata.albumArtUri)
//            .placeholder(R.drawable.baseline_music_note_24)
//            .centerCrop()
//            .error(R.drawable.baseline_music_note_24)
//            .into(binding.activityMainPlaybackImage)
        binding.activityMainPlaybackTitle.text = mediaItem.mediaMetadata.displayTitle
        binding.activityMainPlaybackArtist.text = mediaItem.mediaMetadata.artist
        if (viewModel.isPlaying){
            binding.activityMainPlaybackPlay.setImageResource(androidx.media3.ui.R.drawable.exo_icon_pause)
        }else{
            binding.activityMainPlaybackPlay.setImageResource(R.drawable.baseline_play_arrow_24)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this, SimpleMediaService::class.java))
        isServiceRunning = false
    }

    private fun startService() {
        if (!isServiceRunning) {
            val intent = Intent(this, SimpleMediaService::class.java)
            startForegroundService(intent)
            isServiceRunning = true
        }
    }
}