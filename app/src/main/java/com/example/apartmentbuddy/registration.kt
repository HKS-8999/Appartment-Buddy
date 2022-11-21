package com.example.apartmentbuddy

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.apartmentbuddy.databinding.ActivityMainBinding
import com.example.apartmentbuddy.databinding.FragmentLogin2Binding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class registration : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private  lateinit var binding: ActivityMainBinding
    private lateinit var selectedRadioButton: RadioButton
    private lateinit var radioGroup: RadioGroup
    private lateinit var progressBar:ProgressBar
    private var db=Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     //   setContentView(R.layout.fragment_registration2)
        val new_user:Button=findViewById(R.id.btn_register)
        val email_address: EditText =findViewById(R.id.txt_email)
        val login_button_click:Button=findViewById(R.id.btn_login_app)
        new_user.setOnClickListener{
            Toast.makeText(applicationContext, "registration button is clicked", Toast.LENGTH_SHORT).show()
            //performSignup()
            //getUserInformation()

        }

    }

    private fun performSignup(){
        val email:EditText=findViewById(R.id.txt_email)
        val password:EditText=findViewById(R.id.txt_password)
        val name:EditText=findViewById(R.id.txt_username)
        val apartment_no:EditText=findViewById(R.id.txt_aprtnumber)
        val contact_no:EditText=findViewById(R.id.txt_contact_number)

        val inputEmail=email.text.toString()
        val inputPassword=password.text.toString()
        if(email.text.isEmpty()||password.text.isEmpty() || name.text.isEmpty()|| apartment_no.text.isEmpty() || contact_no.text.isEmpty()){
            Toast.makeText(this,"Please fill all fields",Toast.LENGTH_SHORT).show()
            return
        }

        if(password.text.length<6){
            Toast.makeText(this,"Password should be more than 6 characters",Toast.LENGTH_SHORT).show()
            return
        }
        radioGroup=findViewById(R.id.radioGroup)
        val selectedRadioButtonId: Int  = radioGroup.checkedRadioButtonId
        if (selectedRadioButtonId != -1) {
            selectedRadioButton = findViewById(selectedRadioButtonId)
            val string: String = selectedRadioButton.text.toString()
        } else {
            Toast.makeText(this,"Please select a role",Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(inputEmail, inputPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    getUserInformation()
                    // Sign in success, update UI with the signed-in user's information
                    val intent=Intent(this,Login::class.java)
                    startActivity(intent)
                    Toast.makeText(baseContext,"Success",Toast.LENGTH_SHORT).show()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(baseContext,"Authentication Failed",Toast.LENGTH_SHORT).show()
                }
            }
            .addOnFailureListener{
                Toast.makeText(this,"Error OCcured ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
            }
    }
    private fun getUserInformation(){
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = user.displayName
            val email = user.email
            val photoUrl = user.photoUrl

            // Check if user's email is verified
            val emailVerified = user.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            val uid = user.uid

            val name_fragment:EditText=findViewById(R.id.txt_username)
            val apartment_no:EditText=findViewById(R.id.txt_aprtnumber)
            val contact_no:EditText=findViewById(R.id.txt_contact_number)
            radioGroup =findViewById(R.id.radioGroup)
            val selectedRadioButtonId: Int  = radioGroup.checkedRadioButtonId

            if (selectedRadioButtonId != -1) {
                selectedRadioButton = findViewById(selectedRadioButtonId)
                val role: String = selectedRadioButton.text.toString()


                val name_input = name_fragment.text.toString()
                val apartmentno_input = apartment_no.text.toString()
                val contactno_input = contact_no.text.toString()
                val role_input = role

                val userMap = hashMapOf(
                    "name" to name_input,
                    "apartment" to apartmentno_input,
                    "contact" to contactno_input,
                    "role" to role_input,
                    "user_id" to uid
                )
                db.collection("users").document(uid).set(userMap)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener{
                        Toast.makeText(this,"Error OCcured ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
                    }

            }
        }
    }
}