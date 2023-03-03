package com.example.quizzed.activities

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.quizzed.R
import com.example.quizzed.adapters.QuizAdapter
import com.example.quizzed.models.Quiz
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var adapter: QuizAdapter
    private  var quizList= mutableListOf<Quiz>()
    lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpViews()

    }


    fun setUpViews(){
        setUpFireStore()
        setUpDrawerLayout()
        setUpRecyclerView()
        setUpDatePicker()

    }

    private fun setUpDatePicker() {

        val btnDatePicker =findViewById<FloatingActionButton>(R.id.btnDatePicker)
        btnDatePicker.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker().build()
            datePicker.show(supportFragmentManager, "DatePicker")
            datePicker.addOnPositiveButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)
                val dateFormatter = SimpleDateFormat("dd-MM-yyyy")
                val date = dateFormatter.format(Date(it))

                Log.d("DATEPICKER", date)

                val intent = Intent(this, QuestionActivity::class.java)
                intent.putExtra("DATE", date)
                startActivity(intent)
            }
            datePicker.addOnNegativeButtonClickListener {
                Log.d("DATEPICKER", datePicker.headerText)

            }
            datePicker.addOnCancelListener {
                Log.d("DATEPICKER", "Date Picker Cancelled")
            }
        }

    }

    private fun setUpFireStore() {
        firestore= FirebaseFirestore.getInstance()
        val collectionReference=firestore.collection("quizzes")
        collectionReference.addSnapshotListener { value, error ->
            if (value == null || error != null){
                Toast.makeText(this,"Error Fetching data",Toast.LENGTH_SHORT).show()
                return@addSnapshotListener
            }
            Log.d("DATA", value.toObjects(Quiz::class.java).toString())
            quizList.clear()
            quizList.addAll(value.toObjects(Quiz::class.java))
            adapter.notifyDataSetChanged()

        }
    }

    private fun setUpRecyclerView() {
        val quizRecyclerView = findViewById<RecyclerView>(R.id.quizRecyclerView)
        adapter = QuizAdapter(this,quizList)
        quizRecyclerView.adapter =adapter
        //quizRecyclerView.layoutManager=LinearLayoutManager(this)
        quizRecyclerView.layoutManager=GridLayoutManager(this,2)

    }

    fun setUpDrawerLayout(){
        val appBar=findViewById<androidx.appcompat.widget.Toolbar>(R.id.topAppBar)
        val navigationView=findViewById<NavigationView>(R.id.navigationView)
        val mainDrawer=findViewById<DrawerLayout>(R.id.mainDrawer)
        setSupportActionBar(appBar)
        actionBarDrawerToggle =
            ActionBarDrawerToggle(this, mainDrawer, R.string.app_name, R.string.app_name)
        actionBarDrawerToggle.syncState()
        navigationView.setNavigationItemSelectedListener{

            when(it.itemId){

                R.id.nav_profile->{
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }
                R.id.nav_rateus -> Toast.makeText(this,"Rating Page Clicked ",Toast.LENGTH_SHORT).show()
                R.id.nav_about -> Toast.makeText(this,"About us...",Toast.LENGTH_SHORT).show()
            }

            mainDrawer.closeDrawers()
            true
        }
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}