package com.example.mygeoapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import com.example.mygeoapp.databinding.ActivityMainBinding
import com.example.mygeoapp.ui.main.MainFragment
import com.example.mygeoapp.ui.main.MainViewModel
import com.example.mygeoapp.ui.main.MapFragment
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var binding: ActivityMainBinding
        lateinit var firebaseAuth: FirebaseAuth

        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_main)
        val viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        firebaseAuth = FirebaseAuth.getInstance()
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }
    internal fun onOpenMap(){
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, MapFragment())
            .commitNow()
    }
}