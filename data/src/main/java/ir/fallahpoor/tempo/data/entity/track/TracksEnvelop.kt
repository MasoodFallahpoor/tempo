package ir.fallahpoor.tempo.data.entity.track

import com.google.gson.annotations.SerializedName

data class TracksEnvelop(
    @SerializedName("tracks")
    val tracks: List<TrackEntity>
)