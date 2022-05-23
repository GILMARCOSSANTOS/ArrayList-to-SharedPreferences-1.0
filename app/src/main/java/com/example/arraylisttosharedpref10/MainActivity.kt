package com.example.arraylisttosharedpref10

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var btSave: Button
    private lateinit var tvSize: TextView
    var arrayList: ArrayList<ModelClass>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        etName = findViewById(R.id.edtTxt_addItem_actvtMain)
        btSave = findViewById(R.id.btn_addItem_actvtMain)
        tvSize = findViewById(R.id.tv_size)

        loadData()

        btSave.setOnClickListener{
            saveData(etName.text.toString())
        }
    }

    private fun loadData() {
        val sharedPreferences = applicationContext.getSharedPreferences("DATA", MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("student_data", null)
        val type: Type = object : TypeToken<java.util.ArrayList<ModelClass?>?>() {}.getType()
        arrayList = gson.fromJson(json, type)

        if (arrayList == null) {
            arrayList = java.util.ArrayList()
            tvSize.text = "" + 0
        } else {
            for (i in arrayList!!.indices) {
                tvSize.text = """
            ${tvSize.text}
            ${arrayList!![i].name}
            
            """.trimIndent()
            }
        }
    }


    private fun saveData(name: String) {
        val sharedPreferences = applicationContext.getSharedPreferences("DATA", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        arrayList!!.add(ModelClass(name))
        val json: String = gson.toJson(arrayList)
        editor.putString("student_data", json)
        editor.apply()
        tvSize.text = "List Data \n"
        loadData()
    }
}