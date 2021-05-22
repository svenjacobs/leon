package com.svenjacobs.app.leon

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsClient
import androidx.browser.customtabs.CustomTabsServiceConnection
import com.svenjacobs.app.leon.ui.screens.main.MainScreen
import com.svenjacobs.app.leon.ui.screens.main.model.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        onIntent(intent)

        setContent { MainScreen(this, mainViewModel) }
    }

    override fun onStart() {
        super.onStart()
        warmupCustomTabsService()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        onIntent(intent)
    }

    // TODO: Pass all Intent extras
    private fun onIntent(intent: Intent?) {
        val text = when {
            intent?.action == Intent.ACTION_SEND && intent.type == MIME_TYPE ->
                intent.getStringExtra(Intent.EXTRA_TEXT)
            else -> null
        }

        mainViewModel.setText(text)
    }

    private fun warmupCustomTabsService() {
        val connection = object : CustomTabsServiceConnection() {
            override fun onCustomTabsServiceConnected(
                name: ComponentName,
                client: CustomTabsClient,
            ) {
                client.warmup(0)
            }

            override fun onServiceDisconnected(name: ComponentName?) {
            }
        }

        CustomTabsClient.bindCustomTabsService(this, CHROME_PACKAGE_NAME, connection)
    }

    private companion object {
        private const val MIME_TYPE = "text/plain"
        private const val CHROME_PACKAGE_NAME = "com.android.chrome"
    }
}
