package com.example.tentangsaya

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tentangsaya.databinding.ActivityMainBinding
import com.example.tentangsaya.NotesAppActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setup()
    }

    private fun setup() {
        binding.textView3.setOnClickListener {
            val builder = AlertDialog.Builder (this)
            builder.setTitle("About Me")
            builder.setMessage("Tentang Saya: Saya adalah siswi SMKN 24 Jakarta dengan jurusan Rekayasa Perangkat Lunak dengan passion desain. Dan berkepribadian INFJ. Sangat suka merawat kucing, memasak, dan melukis.")
            builder.setPositiveButton("OK"){dialog, _ ->
                dialog.dismiss()
            }

            val dialog = builder.create()
            dialog.show()
            Toast.makeText(applicationContext, "About me", Toast.LENGTH_LONG).show()
        }

        binding.btnKeluar.setOnClickListener {
            finishAffinity()
        }

        binding.buttonProject1.setOnClickListener {
            val intent = Intent(this, CalculatorActivity::class.java)
            startActivity(intent)
        }

        binding.btnNotes.setOnClickListener {
            val intent = Intent(this, NotesAppActivity::class.java)
            startActivity(intent)
        }
    }
}