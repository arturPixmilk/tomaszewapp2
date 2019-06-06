package prm.tomaszewapp2

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties
data class DatabaseShopItem (
    var name:String="",
    var type:String="",
    var radius:Int=0,
    var image:Boolean=false
):Serializable