package br.com.furlaneto.murilo.pomodoroapp.viewmodel

sealed class TimerState {
    object Stopped : TimerState()

    object Running : TimerState()

    object Paused : TimerState()

    object Finished : TimerState()
}
