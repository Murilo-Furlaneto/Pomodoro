package br.com.furlaneto.murilo.pomodoroapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.com.furlaneto.murilo.pomodoroapp.ui.screen.InitialScreen
import br.com.furlaneto.murilo.pomodoroapp.viewmodel.PomodoroViewModel
import br.com.furlaneto.murilo.pomodoroapp.ui.screen.TimerScreen

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val pomodoroViewModel: PomodoroViewModel = viewModel()

    NavHost(navController = navController, startDestination = "initial_screen") {
        composable("initial_screen") {
            InitialScreen(
                pomodoroViewModel = pomodoroViewModel,
                navigateToTimerScreen = {
                    pomodoroViewModel.startTimer()
                    navController.navigate("timer_screen")
                }
            )
        }
        composable("timer_screen") {
            TimerScreen(
                pomodoroViewModel = pomodoroViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
