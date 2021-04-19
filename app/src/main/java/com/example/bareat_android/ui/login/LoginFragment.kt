package com.example.bareat_android.ui.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.bareat_android.R
import com.example.bareat_android.databinding.FragmentLoginBinding
import com.example.bareat_android.ui.base.BaseFragment


class LoginFragment : BaseFragment<FragmentLoginBinding>() {

    override fun initializeBinding(): FragmentLoginBinding {
        binding = FragmentLoginBinding.inflate(layoutInflater)
        return binding
    }

    override fun initView() {

    }

    override fun setToolbar() {

    }


}