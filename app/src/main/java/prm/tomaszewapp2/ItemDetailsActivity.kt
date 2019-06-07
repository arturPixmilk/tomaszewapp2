package prm.tomaszewapp2

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ItemDetailsActivity : AppCompatActivity() {
    val storage = FirebaseStorage.getInstance().reference
    val database = FirebaseDatabase.getInstance().reference
    val auth = FirebaseAuth.getInstance()
    lateinit var data:DatabaseShopItem
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_details)


        val name = findViewById<TextView>(R.id.itemName)
        val radius = findViewById<TextView>(R.id.itemRadius)
        val type = findViewById<TextView>(R.id.itemType)
        val image = findViewById<ImageView>(R.id.itemImage)

        val editButton = findViewById<Button>(R.id.editButton)
        val deleteButton: Button = findViewById<Button>(R.id.deleteButton)

        if (intent.hasExtra("DatabaseShopItem")) {
            data = intent.getSerializableExtra("DatabaseShopItem") as DatabaseShopItem
            name.text = data.name
            radius.text = data.radius.toString()
            type.text = data.type
            if (data.image) {
                var file: File = createTempFile("${data.name}", "jpg")
                var x=Uri.parse("${data.name}.jpg")
                storage.child("${auth.uid}").child("${data.name}.jpg").getFile(file)
                    .addOnSuccessListener { taskSnapshot ->
                        val bmp = BitmapFactory.decodeFile(file.absolutePath)
                        image.setImageBitmap(bmp)
                    }
            }
        }

        editButton.setOnClickListener {
            val intent=Intent(this,ItemEditActivity::class.java)
            intent.putExtra("DatabaseShopItem",data)
            startActivity(intent)
        }

        deleteButton.setOnClickListener {
            database.child("shops/${auth.uid}").child(data.name).removeValue()
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}