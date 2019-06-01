package prm.tomaszewapp2

import android.content.Intent
import android.nfc.Tag
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.TextInputLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity:AppCompatActivity() {

    private val auth=FirebaseAuth.getInstance()

    override fun onStart() {
        super.onStart()
        var currentUser:FirebaseUser?=auth.currentUser
       if (currentUser!=null)
           startActivity(Intent(this, MainActivity::class.java))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginButton=findViewById<Button>(R.id.loginButton)
        val registerButton=findViewById<Button>(R.id.registerButton)

        val login=findViewById<TextInputLayout>(R.id.loginInput).editText?.text.toString()
        val password=findViewById<TextInputLayout>(R.id.passwordInput).editText?.text.toString()

        loginButton.setOnClickListener {
            auth.signInWithEmailAndPassword(login,password).addOnCompleteListener(this){
                if (it.isSuccessful){
                    Log.d("AUTH","signInWithEmail:success")
                    startActivity(Intent(this, MainActivity::class.java))
                }
                else{
                    Log.d("AUTH","signInWithEmail:failure")
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
                }

            }

        }

        registerButton.setOnClickListener {
            auth.createUserWithEmailAndPassword(login,password).addOnCompleteListener(this){
                if (it.isSuccessful){
                    Log.d("AUTH","createUserWithEmail:success")
                    startActivity(Intent(this, MainActivity::class.java))
                }
                else{
                    Log.d("AUTH","createUserWithEmail:failure")
                    Toast.makeText(this, "Authentication failed.",
                        Toast.LENGTH_SHORT).show();
                }
            }
        }

    }

}