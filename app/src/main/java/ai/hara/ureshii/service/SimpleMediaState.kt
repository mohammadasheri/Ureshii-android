package ai.hara.ureshii.service

sealed class SimpleMediaState {
    data class Ready(val duration: Long) : SimpleMediaState()
    data class Progress(val progress: Long) : SimpleMediaState()
    data class Buffering(val progress: Long) : SimpleMediaState()
    data class Playing(val isPlaying: Boolean) : SimpleMediaState()
    data class TrackChange(val isChanged: Boolean) : SimpleMediaState()
}