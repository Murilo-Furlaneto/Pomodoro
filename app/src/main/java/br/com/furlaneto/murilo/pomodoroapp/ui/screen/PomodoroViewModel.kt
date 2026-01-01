package br.com.furlaneto.murilo.pomodoroapp.ui.screen

import androidx.lifecycle.ViewModel
import br.com.furlaneto.murilo.pomodoroapp.viewmodel.TimerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class PomodoroViewModel : ViewModel() {

    // Timer State
    private val _timerState = MutableStateFlow<TimerState>(TimerState.Stopped)
    val timerState = _timerState.asStateFlow()

    // Countdown Time
    private val _remainingTime = MutableStateFlow(0L)
    val remainingTime = _remainingTime.asStateFlow()

    private val _totalTime = MutableStateFlow(0L)
    val totalTime = _totalTime.asStateFlow()

    // Pomodoro Time (in minutes)
    private val _pomodoroTime = MutableStateFlow(25)
    val pomodoroTime = _pomodoroTime.asStateFlow()

    // Short Break Time (in minutes)
    private val _shortBreakTime = MutableStateFlow(5)
    val shortBreakTime = _shortBreakTime.asStateFlow()

    // Long Break Time (in minutes)
    private val _longBreakTime = MutableStateFlow(15)
    val longBreakTime = _longBreakTime.asStateFlow()

    // --- Timer Control Functions ---
    fun startTimer() {
        _timerState.value = TimerState.Running
        val timeInSeconds = _pomodoroTime.value * 60L
        _totalTime.value = timeInSeconds
        _remainingTime.value = timeInSeconds
    }

    fun pauseTimer() {
        _timerState.value = TimerState.Paused
    }

    fun resumeTimer() {
        _timerState.value = TimerState.Running
    }

    fun stopTimer() {
        _timerState.value = TimerState.Stopped
        _remainingTime.value = 0
        _totalTime.value = 0
    }

    fun tick() {
        if (_remainingTime.value > 0) {
            _remainingTime.value--
        } else {
            _timerState.value = TimerState.Finished
        }
    }

    // --- Functions for Pomodoro ---
    fun incrementPomodoro() {
        _pomodoroTime.value++
    }

    fun decrementPomodoro() {
        if (_pomodoroTime.value > 1) {
            _pomodoroTime.value--
        }
    }

    // --- Functions for Short Break ---
    fun incrementShortBreak() {
        _shortBreakTime.value++
    }

    fun decrementShortBreak() {
        if (_shortBreakTime.value > 1) {
            _shortBreakTime.value--
        }
    }

    // --- Functions for Long Break ---
    fun incrementLongBreak() {
        _longBreakTime.value++
    }

    fun decrementLongBreak() {
        if (_longBreakTime.value > 1) {
            _longBreakTime.value--
        }
    }
}
