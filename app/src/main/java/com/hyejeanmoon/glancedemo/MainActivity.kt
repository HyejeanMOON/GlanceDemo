package com.hyejeanmoon.glancedemo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hyejeanmoon.glancedemo.ui.theme.GlanceDemoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val parameter = intent.getStringExtra(PARAMETER_KEY) ?: ""

        setContent {
            GlanceDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting(
                        parameter
                    )
                }
            }
        }
    }

    companion object {
        private const val PARAMETER_KEY = "parameters_keys"
    }
}

@Composable
fun Greeting(parameter: String = "") {
    Column {
        Text(text = "GlanceDemo")
        Text(text = "parameter: $parameter")
    }
}