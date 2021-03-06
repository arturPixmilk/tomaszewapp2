package prm.tomaszewapp2

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    val auth = FirebaseAuth.getInstance()
    val database = FirebaseDatabase.getInstance()
    val databaseRef = database.reference
    var items: List<DatabaseShopItem> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        recyclerView = findViewById(R.id.recyclerView)
        viewManager = LinearLayoutManager(this)
        viewAdapter = MyAdapter(items, this)

        recyclerView.layoutManager = viewManager

        databaseRef.child("shops/${auth.uid}").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                items = dataSnapshot.children.mapNotNull { it.getValue(DatabaseShopItem::class.java) }

                Log.d("DATA", "Loaded ${items.size} items")

                recyclerView.apply {
                    layoutManager = LinearLayoutManager(this@MainActivity)
                    adapter = MyAdapter(items, this@MainActivity)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("DATA", "Failed to read value.", error.toException())
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater.inflate(R.menu.app_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.newItem -> {
                startActivity(Intent(this, ItemEditActivity::class.java))
            }
            R.id.logout -> {
                auth.signOut()
                startActivity(Intent(this, LoginActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)

    }


}


