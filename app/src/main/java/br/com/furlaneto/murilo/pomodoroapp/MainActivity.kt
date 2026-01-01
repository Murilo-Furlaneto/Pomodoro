package br.com.furlaneto.murilo.pomodoroapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import br.com.furlaneto.murilo.pomodoroapp.navigation.AppNavigation
import br.com.furlaneto.murilo.pomodoroapp.ui.theme.PomodoroAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PomodoroAppTheme {
                AppNavigation()
            }
        }
    }
}
