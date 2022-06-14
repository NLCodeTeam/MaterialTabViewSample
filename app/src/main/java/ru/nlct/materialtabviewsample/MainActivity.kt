package ru.nlct.materialtabviewsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import ru.nlct.materialtabview.MaterialTabView

class MainActivity : AppCompatActivity() {
    private lateinit var tab: MaterialTabView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tab = findViewById(R.id.materialTabView)
       // tab.setActiveTab(MaterialTabView.TAB.FIRST)
        tab.setOnMaterialTabViewClickListener {
            tabId -> onTabClick(tabId)
        }
    }

    private fun onTabClick(tabId: MaterialTabView.TAB) {
        Log.d("!!!", tabId.name)
    }
}