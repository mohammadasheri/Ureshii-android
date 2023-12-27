package ai.hara.ureshii.service

sealed class PlayerEvent {
    object PlayPause : PlayerEvent()
    object Backward : PlayerEvent()
    object Forward : PlayerEvent()
    object Shuffle : PlayerEvent()
    object Stop : PlayerEvent()
    data class Repeat(val repeatMode: Int) : PlayerEvent()
    data class UpdateProgress(val newProgress: Float) : PlayerEvent()
}