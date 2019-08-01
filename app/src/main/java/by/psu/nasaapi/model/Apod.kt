package by.psu.nasaapi.model

import android.os.Parcelable
import androidx.room.Entity
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(primaryKeys = ["date"])
data class Apod (
    val date: String,
    val title: String,
    val url: String,
    val explanation: String,
    @SerializedName("media_type")
    val mediaType: String?,
    @SerializedName("service_version")
    val serviceVersion: String?,
    val copyright: String?,
    @SerializedName("hdurl")
    val hdUrl: String?
): Parcelable