package br.com.furlaneto.murilo.pomodoroapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.RemoveCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import br.com.furlaneto.murilo.pomodoroapp.viewmodel.PomodoroViewModel

@Composable
fun InitialScreen(
    pomodoroViewModel: PomodoroViewModel,
    navigateToTimerScreen: () -> Unit
) {
    val pomodoroTime = pomodoroViewModel.pomodoroTime
    val shortBreakTime = pomodoroViewModel.shortBreakTime
    val longBreakTime = pomodoroViewModel.longBreakTime

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Pomodoro",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 48.dp)
            )

            ConfiguratorRow(
                title = "Pomodoro",
                value = "$pomodoroTime min",
                onDecrement = { pomodoroViewModel.decrementPomodoro() },
                onIncrement = { pomodoroViewModel.incrementPomodoro() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ConfiguratorRow(
                title = "Pausa Curta",
                value = "$shortBreakTime min",
                onDecrement = { pomodoroViewModel.decrementShortBreak() },
                onIncrement = { pomodoroViewModel.incrementShortBreak() }
            )

            Spacer(modifier = Modifier.height(16.dp))

            ConfiguratorRow(
                title = "Pausa Longa",
                value = "$longBreakTime min",
                onDecrement = { pomodoroViewModel.decrementLongBreak() },
                onIncrement = { pomodoroViewModel.incrementLongBreak() }
            )

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = navigateToTimerScreen,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Começar", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
private fun ConfiguratorRow(
    title: String,
    value: String,
    onDecrement: () -> Unit,
    onIncrement: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 4.dp,
        shadowElevation = 4.dp
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(value, style = MaterialTheme.typography.headlineMedium)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(onClick = onDecrement) {
                    Icon(Icons.Filled.RemoveCircle, contentDescription = "Diminuir")
                }
                IconButton(onClick = onIncrement) {
                    Icon(Icons.Filled.AddCircle, contentDescription = "Aumentar")
                }
            }
        }
    }
}
