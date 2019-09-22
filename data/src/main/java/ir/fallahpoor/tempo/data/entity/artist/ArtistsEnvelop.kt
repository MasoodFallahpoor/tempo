package ir.fallahpoor.tempo.data.entity.artist

import com.google.gson.annotations.SerializedName

data class ArtistsEnvelop(
    @SerializedName("artists")
    val artists: List<ArtistEntity>
)