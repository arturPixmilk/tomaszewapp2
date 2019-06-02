package prm.tomaszewapp2

import android.graphics.BitmapFactory
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.storage.FileDownloadTask
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item.view.*
import java.io.File

class MyAdapter(private val items: List<DatabaseShopItem>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    val storage=FirebaseStorage.getInstance().reference

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)



    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.itemName.text=items[position].name
        holder.view.itemType.text=items[position].type

        if (!items[position].imagePath.isEmpty()){
           var file:File= createTempFile("item.${items[position].name}.image","jpg")
            storage.getFile(file)
                .addOnSuccessListener { taskSnapshot ->
                    val bmp=BitmapFactory.decodeFile(file.absolutePath)
                    holder.view.itemImage.setImageBitmap(bmp)
                }

        }


    }

    override fun getItemCount() = items.size
}