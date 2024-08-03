package ai.hara.ureshii.ui.main

import ai.hara.ureshii.service.SimpleMediaService
import ai.hara.ureshii.ui.Screen
import ai.hara.ureshii.ui.login.LoginActivity
import ai.hara.ureshii.ui.theme.UreshiiTheme
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()
    private var isServiceRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                mainViewModel.loading.value
            }
        }
        setContent {
            onBackPressedDispatcher.addCallback(this, onBackPressedCallback)
            val bottomNavController = rememberNavController()
            val outNavController = rememberNavController()
            val homeNavController = rememberNavController()

            if (mainViewModel.showPlayerView) {
                mainViewModel.navigateToScreen(outNavController, Screen.Player.route)
            }
            if (!mainViewModel.isLoggedIn) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
                UreshiiTheme {
                    Scaffold(
                        bottomBar = {
                            BottomBar(
                                navController = bottomNavController,
                                mainViewModel
                            )
                        }
                    ) { innerPadding ->
                        BottomNavHost(
                            bottomNavController,
                            mainViewModel,
                            innerPadding
                        )
                    }
                    MainNavHost(outNavController, mainViewModel)
                }
            }
        }
        actionBar?.hide()
        startService()
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

    private val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // Your business logic to handle the back pressed event
            mainViewModel.navStack.let { stack ->
                if (stack.isEmpty()) {
                    finish()
                }else{
                    val controller = stack.pop()
                    controller?.popBackStack() ?: finish()
                }
            }
        }
    }
}