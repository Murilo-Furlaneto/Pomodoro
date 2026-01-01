package br.com.furlaneto.murilo.pomodoroapp.ui.screen

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.furlaneto.murilo.pomodoroapp.viewmodel.PomodoroViewModel
import br.com.furlaneto.murilo.pomodoroapp.viewmodel.SessionType
import br.com.furlaneto.murilo.pomodoroapp.viewmodel.TimerState

@Composable
fun TimerScreen(
    pomodoroViewModel: PomodoroViewModel,
    onNavigateBack: () -> Unit
) {
    val timerState = pomodoroViewModel.timerState
    val remainingTime = pomodoroViewModel.remainingTimeInSeconds
    val currentSession = pomodoroViewModel.currentSession

    val totalTimeInMinutes = when (currentSession) {
        SessionType.Pomodoro -> pomodoroViewModel.pomodoroTime
        SessionType.ShortBreak -> pomodoroViewModel.shortBreakTime
        SessionType.LongBreak -> pomodoroViewModel.longBreakTime
    }
    val totalTimeInSeconds = totalTimeInMinutes * 60f

    val progress = if (totalTimeInSeconds > 0) remainingTime.toFloat() / totalTimeInSeconds else 0f

    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 1000, easing = LinearEasing),
        label = "ProgressAnimation"
    )

    val formattedTime = remember(remainingTime) {
        val minutes = remainingTime / 60
        val seconds = remainingTime % 60
        "%02d:%02d".format(minutes, seconds)
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.size(300.dp),
                    strokeWidth = 12.dp,
                    trackColor = MaterialTheme.colorScheme.surfaceVariant
                )
                CircularProgressIndicator(
                    progress = { animatedProgress },
                    modifier = Modifier.size(300.dp),
                    strokeWidth = 12.dp,
                )
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = formattedTime,
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = currentSession.name,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                when (timerState) {
                    is TimerState.Running -> {
                        Button(onClick = { pomodoroViewModel.pauseTimer() }) {
                            Icon(Icons.Default.Pause, contentDescription = "Pausar")
                        }
                    }
                    is TimerState.Paused -> {
                        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                            Button(onClick = { pomodoroViewModel.startTimer(isResuming = true) }) {
                                Icon(Icons.Default.PlayArrow, contentDescription = "Continuar")
                            }
                            Button(onClick = {
                                pomodoroViewModel.stopTimer()
                                onNavigateBack()
                            }) {
                                Icon(Icons.Default.Stop, contentDescription = "Parar")
                            }
                        }
                    }
                    is TimerState.Finished -> {
                        Button(onClick = { pomodoroViewModel.startNextSession() }) {
                            val nextSessionText = if (currentSession == SessionType.Pomodoro) "Iniciar Pausa" else "Iniciar Pomodoro"
                            Text(nextSessionText)
                        }
                    }
                    is TimerState.Stopped -> {
                        Button(onClick = onNavigateBack) {
                            Text("Voltar")
                        }
                    }
                }
            }
        }
    }
}
