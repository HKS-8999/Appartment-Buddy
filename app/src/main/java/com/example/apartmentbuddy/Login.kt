package com.example.apartmentbuddy

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Login : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login2)
        val new_user_txt:TextView=findViewById(R.id.txtview_new_user)
      //  val new_usr_btn:Button=findViewById(R.id.btn_newuser)
        auth=Firebase.auth
        val loginButton:Button=findViewById(R.id.btn_login_app)
        loginButton.setOnClickListener{
            performLogin()
        }

    }

    private fun performLogin(){
        val email:EditText=findViewById(R.id.txt_email)
        val password:EditText=findViewById(R.id.txt_password)

        if(email.text.isEmpty()||password.text.isEmpty()){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return
        }
        val emailInput=email.text.toString()
        val passwordInput=password.text.toString()

        auth.signInWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                 //   val intent=Intent(this,AdvertisementActivity::class.java)
                    val intent=Intent(this,Home::class.java)
                    startActivity(intent)
                    Toast.makeText(baseContext,"Success",Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()

                }
            }
            .addOnFailureListener{
            Toast.makeText(this,"Error OCcured ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
        }

    }


}