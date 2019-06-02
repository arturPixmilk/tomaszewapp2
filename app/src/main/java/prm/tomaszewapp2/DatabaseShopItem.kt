package prm.tomaszewapp2

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class DatabaseShopItem (
    var name:String="",
    var type:String="",
    var imagePath:String=""
)