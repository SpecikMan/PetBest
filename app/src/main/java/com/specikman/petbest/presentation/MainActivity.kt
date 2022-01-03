package com.specikman.petbest.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.content.ContextCompat
import com.specikman.petbest.R
import com.specikman.petbest.presentation.navigation.NavigationRoot
import com.specikman.petbest.presentation.ui.theme.Orange
import dagger.hilt.android.AndroidEntryPoint


@ExperimentalAnimationApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //config
        window.statusBarColor = ContextCompat.getColor(this, R.color.orange)

        setContent {
            NavigationRoot(context = this)
        }
    }


}



