package com.example.bareat_android.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentFinishRegisterBinding
import com.example.bareat_android.databinding.FragmentLoginBinding
import com.example.bareat_android.ui.base.BaseFragment

class FinishRegisterFragment : BaseFragment<FragmentFinishRegisterBinding>() {

    override fun initializeBinding(): FragmentFinishRegisterBinding {
        binding = FragmentFinishRegisterBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {

    }

    override fun setToolbar() {

    }

}