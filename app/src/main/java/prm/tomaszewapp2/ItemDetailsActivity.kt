package prm.tomaszewapp2

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class ItemDetailsActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_item_details)

        val name=findViewById<TextView>(R.id.itemName)
        val radius=findViewById<TextView>(R.id.itemRadius)
        val type=findViewById<TextView>(R.id.itemType)

        if (intent.hasExtra("DatabaseShopItem")){
           val data:DatabaseShopItem=intent.getSerializableExtra("DatabaseShopItem")as DatabaseShopItem
            name.text=data.name
            radius.text=data.radius.toString()
            type.text=data.type
        }
    }
}