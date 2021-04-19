package com.example.bareat_android.ui.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentLoginBinding
import com.example.bareat_android.databinding.FragmentRegisterBinding
import com.example.bareat_android.ui.base.BaseFragment

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override fun initializeBinding(): FragmentRegisterBinding {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {

    }

    override fun setToolbar() {

    }

}