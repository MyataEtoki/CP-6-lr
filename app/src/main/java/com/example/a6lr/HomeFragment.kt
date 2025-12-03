package com.example.a6lr

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Разворачиваем макет фрагмента на экран
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        // Находим TextView по новому ID
        val textView = view.findViewById<TextView>(R.id.home_text)
        textView.text = "Добро пожаловать в приложение про попугаев!"

        return view
    }
}