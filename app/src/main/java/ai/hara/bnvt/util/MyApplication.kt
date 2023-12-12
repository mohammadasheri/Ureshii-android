//package ai.hara.bnvt.util
//
//import ai.hara.bnvt.injection.component.AppComponent
//import ai.hara.bnvt.injection.component.DaggerAppComponent
//import ai.hara.bnvt.injection.module.ApiServiceModule
//import ai.hara.bnvt.injection.module.AppModule
//import ai.hara.bnvt.injection.module.RepositoryModule
//import ai.hara.bnvt.injection.module.RoomModule
//import ai.hara.bnvt.ui.RegisterActivity
//import android.app.Application
//import android.content.Intent
//import android.content.res.Configuration
//import androidx.appcompat.app.AppCompatDelegate
//import java.util.Locale
//
//
//class MyApplication : Application() {
//
//    var netComponent: AppComponent? = null
//
//    override fun onCreate() {
//        super.onCreate()
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//
////        Log.i("APPID", BuildConfig.APPLICATION_ID)
//        appcontext = this
//
//        netComponent = DaggerAppComponent.builder()
//            .appModule(AppModule())
//            .repositoryModule(RepositoryModule())
//            .apiServiceModule(ApiServiceModule())
//            .roomModule(RoomModule())
//            .build()
//
//        val locale = Locale("fa")
//        Locale.setDefault(locale)
//        val config: Configuration = baseContext.resources.configuration
//        config.setLocale(locale)
//        createConfigurationContext(config)
////        val appMetricaConfig = YandexMetricaConfig.newConfigBuilder("cc91891c-a7d7-40a9-9319-074461e4942d")
////                .withNativeCrashReporting(false)
////                .withLocationTracking(false)
////                .withAppVersion(BuildConfig.VERSION_NAME).build()
////        YandexMetrica.activate(applicationContext, appMetricaConfig)
////        YandexMetrica.enableActivityAutoTracking(this)
//
//    }
//
//    fun login() {
//
//        val intent = Intent(applicationContext, RegisterActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(intent)
//    }
//
//    companion object {
//        lateinit var appcontext: MyApplication
//    }
//}