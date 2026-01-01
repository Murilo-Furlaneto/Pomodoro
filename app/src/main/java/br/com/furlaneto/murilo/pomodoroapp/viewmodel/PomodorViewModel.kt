package br.com.furlaneto.murilo.pomodoroapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

enum class SessionType {
    Pomodoro,
    ShortBreak,
    LongBreak
}

class PomodoroViewModel : ViewModel() {
    var pomodoroTime by mutableStateOf(25)
    var shortBreakTime by mutableStateOf(5)
    var longBreakTime by mutableStateOf(15)

    var timerState by mutableStateOf<TimerState>(TimerState.Stopped)
        private set

    var currentSession by mutableStateOf(SessionType.Pomodoro)
        private set

    var remainingTimeInSeconds by mutableStateOf(0)
        private set

    private var pomodoroCycleCount by mutableStateOf(0)
    private var timerJob: Job? = null

    fun incrementPomodoro() { pomodoroTime++ }
    fun decrementPomodoro() { if (pomodoroTime > 1) pomodoroTime-- }
    fun incrementShortBreak() { shortBreakTime++ }
    fun decrementShortBreak() { if (shortBreakTime > 1) shortBreakTime-- }
    fun incrementLongBreak() { longBreakTime++ }
    fun decrementLongBreak() { if (longBreakTime > 1) longBreakTime-- }

    fun startTimer(isResuming: Boolean = false) {
        if (timerState == TimerState.Running) return

        if (!isResuming) {
            val durationInMinutes = when (currentSession) {
                SessionType.Pomodoro -> pomodoroTime
                SessionType.ShortBreak -> shortBreakTime
                SessionType.LongBreak -> longBreakTime
            }
            remainingTimeInSeconds = durationInMinutes * 60
        }

        timerState = TimerState.Running
        timerJob = viewModelScope.launch {
            while (remainingTimeInSeconds > 0 && timerState == TimerState.Running) {
                delay(1000)
                remainingTimeInSeconds--
            }
            if (remainingTimeInSeconds == 0) {
                handleSessionFinish()
            }
        }
    }

    fun pauseTimer() {
        if (timerState == TimerState.Running) {
            timerState = TimerState.Paused
            timerJob?.cancel()
        }
    }

    fun stopTimer() {
        timerState = TimerState.Stopped
        timerJob?.cancel()
        remainingTimeInSeconds = 0
        currentSession = SessionType.Pomodoro
        pomodoroCycleCount = 0
    }

    private fun handleSessionFinish() {
        if (currentSession == SessionType.Pomodoro) {
            pomodoroCycleCount++
        }
        timerState = TimerState.Finished
    }

    fun startNextSession() {
        currentSession = when (currentSession) {
            SessionType.Pomodoro -> {
                if (pomodoroCycleCount % 4 == 0) SessionType.LongBreak else SessionType.ShortBreak
            }
            SessionType.ShortBreak, SessionType.LongBreak -> SessionType.Pomodoro
        }
        startTimer(isResuming = false)
    }
}
