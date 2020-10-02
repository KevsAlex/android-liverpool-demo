package com.example.liverpooldemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.liverpooldemo.MenuPrincipal.MenuPrincipalFragment

class MainActivity : AppCompatActivity() {

    lateinit var menuPrincipal: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        menuPrincipal = MenuPrincipalFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, menuPrincipal)
            .commit()
    }
}