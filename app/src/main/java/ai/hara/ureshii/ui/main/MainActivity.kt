package ai.hara.ureshii.ui.main

import ai.hara.ureshii.service.SimpleMediaService
import ai.hara.ureshii.ui.Screen
import ai.hara.ureshii.ui.home.HomeViewModel
import ai.hara.ureshii.ui.login.LoginActivity
import ai.hara.ureshii.ui.player.PlayerViewModel
import ai.hara.ureshii.ui.theme.UreshiiTheme
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
    private val playerViewModel: PlayerViewModel by viewModels()

    private var isServiceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.loading.value
            }
        }
        setContent {
            if (!mainViewModel.isLoggedIn) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                UreshiiTheme {
                    val bottomNavController = rememberNavController()
                    val outNavController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomBar(navController = bottomNavController) }
                    ) { innerPadding ->
                        BottomNavHost(
                            bottomNavController,
                            mainViewModel,
                            homeViewModel,
                            innerPadding
                        )
                    }
                    if (mainViewModel.showPlayerView) {
                        navigateToScreen(outNavController, Screen.Player.route)
                    }
                    MainNavHost(outNavController, mainViewModel, playerViewModel)
                }
            }
        }
        actionBar?.hide()
        startService()
    }

    private fun navigateToScreen(bottomNavController: NavHostController, route: String) {
        bottomNavController.navigate(route) {
            popUpTo(bottomNavController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
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