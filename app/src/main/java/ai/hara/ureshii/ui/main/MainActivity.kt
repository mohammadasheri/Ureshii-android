package ai.hara.ureshii.ui.main

import ai.hara.ureshii.R
import ai.hara.ureshii.service.SimpleMediaService
import ai.hara.ureshii.ui.Screen
import ai.hara.ureshii.ui.home.HomeScreen
import ai.hara.ureshii.ui.home.HomeViewModel
import ai.hara.ureshii.ui.items
import ai.hara.ureshii.ui.library.LibraryScreen
import ai.hara.ureshii.ui.login.LoginActivity
import ai.hara.ureshii.ui.player.PlayerScreen
import ai.hara.ureshii.ui.search.SearchScreen
import ai.hara.ureshii.ui.theme.UreshiiTheme
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()

    private var isServiceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.loading.value
            }
        }
        setContent {
            if (!mainViewModel.isLoggedIn){
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            UreshiiTheme {
                val navController = rememberNavController()
                Scaffold(
                    topBar = {},
                    bottomBar = {
                        BottomNavigation {
                            val navBackStackEntry = navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry.value?.destination
                            items.forEach { screen ->
                                BottomNavigationItem(
                                    selectedContentColor = colorResource(R.color.white),
                                    unselectedContentColor = colorResource(R.color.white),
//                                    modifier = Modifier.background(colorResource(R.color.navigation)),
                                    icon = {
                                        Icon(
                                            screen.icon,
                                            contentDescription = null
                                        )
                                    },
                                    label = { Text(stringResource(screen.resourceId)) },
                                    selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                                    onClick = {
                                        navController.navigate(screen.route) {
                                            // Pop up to the start destination of the graph to
                                            // avoid building up a large stack of destinations
                                            // on the back stack as users select items
                                            popUpTo(navController.graph.findStartDestination().id) {
                                                saveState = true
                                            }
                                            // Avoid multiple copies of the same destination when
                                            // reselecting the same item
                                            launchSingleTop = true
                                            // Restore state when reselecting a previously selected item
                                            restoreState = true
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController,
                        startDestination = Screen.Home.route,
                        Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(
                                navController,
                                mainViewModel,
                                homeViewModel
                            )
                        }
                        composable(Screen.Search.route) { SearchScreen(navController) }
                        composable(Screen.Library.route) { LibraryScreen(navController) }
                    }
                }
            }
            if (mainViewModel.showPlayerView){
                PlayerScreen()
            }
        }
        actionBar?.hide()
//
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//        val navView: BottomNavigationView = binding.navView
//        viewModel.onUIEvent(PlayerEvent.PlayPause)
//        val navController = findNavController(R.id.nav_host_fragment_activity_main)
//        navView.setupWithNavController(navController)
//        startService()
//
//        viewModel.isLoggedOut.observe(this){isLoggedOut->
//            if (isLoggedOut){

//            }
//        }
//
//        viewModel.tempSelectedSongLiveData.observe(this){
//            val fragmentA: Fragment? = supportFragmentManager.findFragmentByTag("playerFragment")
//            if (fragmentA == null){
//                val fa: FragmentTransaction = supportFragmentManager.beginTransaction()
//                fa.replace(R.id.activity_main_container, PlayerFragment.newInstance(), "playerFragment").addToBackStack(null)
//                    .commit()
//            }
//            updateUI(it)
//        }
    }
//
//    private fun updateUI(mediaItem: MediaItem) {
//        binding.activityMainPlayback.visibility = View.VISIBLE
//
////        GlideApp.with(this)
////            .load(metadata.albumArtUri)
////            .placeholder(R.drawable.baseline_music_note_24)
////            .centerCrop()
////            .error(R.drawable.baseline_music_note_24)
////            .into(binding.activityMainPlaybackImage)
//        binding.activityMainPlaybackTitle.text = mediaItem.mediaMetadata.displayTitle
//        binding.activityMainPlaybackArtist.text = mediaItem.mediaMetadata.artist
//        if (viewModel.isPlaying) {
//            binding.activityMainPlaybackPlay.setImageResource(androidx.media3.ui.R.drawable.exo_icon_pause)
//        } else {
//            binding.activityMainPlaybackPlay.setImageResource(R.drawable.baseline_play_arrow_24)
//        }
//    }

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