package com.specikman.petbest.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.auth.FirebaseAuth
import com.specikman.petbest.R
import com.specikman.petbest.presentation.navigation.NavigationGoToMain
import com.specikman.petbest.presentation.navigation.NavigationRoot
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalPermissionsApi
@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val CHANNEL_ID = "ChannelID"
    private val CHANNEL_NAME = "ChannelName"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //config
        window.statusBarColor = ContextCompat.getColor(this, R.color.orange)

        val auth: FirebaseAuth = FirebaseAuth.getInstance()

        setContent {
            auth.currentUser?.let {
                NavigationGoToMain(context = this)
            } ?: NavigationRoot(context = this)
        }
    }
}



