package prm.tomaszewapp2

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.recycler_item.view.*
import java.io.File
import java.io.Serializable

class MyAdapter(private val items: List<DatabaseShopItem>,private val context:Context) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    val storage=FirebaseStorage.getInstance().reference
    val auth=FirebaseAuth.getInstance()
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val image=view.imageView
        val name=view.nameTextView
        val type=view.typeTextView
        val radius=view.radiusTextView
        val cardView=view.cardView
    }

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_item, parent, false)

        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text=items[position].name
        holder.type.text=items[position].type
        holder.radius.text=items[position].radius.toString()


        if (items[position].image){
           var file:File= createTempFile("${items[position].name}","jpg")
            storage.child("${auth.uid}").child("${items[position].name}.jpg").getFile(file)
                .addOnSuccessListener { taskSnapshot ->
                    val bmp=BitmapFactory.decodeFile(file.absolutePath)
                    holder.image.setImageBitmap(bmp)
                }
        }

        holder.cardView.setOnClickListener {
            val intent=Intent(context,ItemDetailsActivity::class.java)
            //val bundle= Bundle().putSerializable("DatabaseShopItem",items[position] as Serializable)
            intent.putExtra("DatabaseShopItem",items[position] )
            context.startActivity(intent)

        }
    }

    override fun getItemCount() = items.size
}