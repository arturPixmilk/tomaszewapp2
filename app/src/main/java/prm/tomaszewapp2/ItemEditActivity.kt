package prm.tomaszewapp2

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class ItemEditActivity : AppCompatActivity() {
    val storage = FirebaseStorage.getInstance().reference
    val database = FirebaseDatabase.getInstance().reference
    val auth = FirebaseAuth.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_edit)

        val name = findViewById<TextInputEditText>(R.id.itemNameEdit)
        val radius = findViewById<TextInputEditText>(R.id.itemRadiusEdit)
        val type = findViewById<TextInputEditText>(R.id.itemTypeEdit)
        val image = findViewById<ImageView>(R.id.imageView)

        val saveButton=findViewById<Button>(R.id.saveButton)

        if (intent.hasExtra("DatabaseShopItem")) {
            val data: DatabaseShopItem = intent.getSerializableExtra("DatabaseShopItem") as DatabaseShopItem
            name.setText(data.name)
            radius.setText(data.radius.toString())
            type.setText(data.type)

            if (data.image) {
                var file: File = createTempFile("activity_item_details.${data.name}.image", "jpg")
                storage.getFile(file)
                    .addOnSuccessListener { taskSnapshot ->
                        val bmp = BitmapFactory.decodeFile(file.absolutePath)
                        image.setImageBitmap(bmp)
                    }
            }
        }

        saveButton.setOnClickListener {
            val item=DatabaseShopItem(name.text.toString(),type.text.toString(),radius.text as Int,image = false)
            database.child("shops/${auth.uid}").child(name.text.toString()).setValue(item)
            startActivity(Intent(this,MainActivity::class.java))

        }
    }
}