package prm.tomaszewapp2

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.TextInputEditText
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import android.widget.ImageView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.io.File

class ItemEditActivity : AppCompatActivity() {
    private val storage = FirebaseStorage.getInstance().reference
    private val database = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    private val REQUEST_IMAGE_CAPTURE = 1
    private lateinit var image: ImageView
    private lateinit var name:TextInputEditText
    private var itemData=DatabaseShopItem()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_edit)

        name = findViewById<TextInputEditText>(R.id.itemNameEdit)
        val radius = findViewById<TextInputEditText>(R.id.itemRadiusEdit)
        val type = findViewById<TextInputEditText>(R.id.itemTypeEdit)
        image = findViewById<ImageView>(R.id.itemImage)

        val saveButton = findViewById<Button>(R.id.saveButton)
        val photoButton = findViewById<Button>(R.id.takePhoto)

        if (intent.hasExtra("DatabaseShopItem")) {
            itemData = intent.getSerializableExtra("DatabaseShopItem") as DatabaseShopItem
            name.setText(itemData.name)
            radius.setText(itemData.radius.toString())
            type.setText(itemData.type)

            if (itemData.image) {
                var file: File = createTempFile("activity_item_details.${itemData.name}.image", "jpg")
                storage.getFile(file)
                    .addOnSuccessListener { taskSnapshot ->
                        val bmp = BitmapFactory.decodeFile(file.absolutePath)
                        image.setImageBitmap(bmp)
                    }
            }
        }


        saveButton.setOnClickListener {
            itemData.name = name.text.toString()
            itemData.type = type.text.toString()
            itemData.radius = radius.text.toString().toInt()


            database.child("shops/${auth.uid}").child(itemData.name).setValue(itemData)
            startActivity(Intent(this, MainActivity::class.java))


        }

        photoButton.setOnClickListener {
            Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
                takePictureIntent.resolveActivity(packageManager)?.also {
                    startActivityForResult(takePictureIntent, 1)
                }

            }

        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            image.setImageBitmap(imageBitmap)
            itemData.image = true;
            val stream=ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG,100,stream)
            var storageImgRef=storage.child("${auth.uid}").child("${name.text.toString()}.jpg")
            storageImgRef.putBytes(stream.toByteArray())

        }
    }

}