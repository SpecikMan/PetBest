package com.specikman.petbest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
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



