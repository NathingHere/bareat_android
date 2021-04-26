package com.example.bareat_android.ui.book

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentBookBinding
import com.example.bareat_android.ui.base.BaseBottomSheet

class BookFragment : BaseBottomSheet<FragmentBookBinding>() {

    override fun initializeBinding(): FragmentBookBinding {
        binding = FragmentBookBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {

    }

}