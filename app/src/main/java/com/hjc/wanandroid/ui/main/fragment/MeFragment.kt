package com.hjc.wanandroid.ui.main.fragment

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.hjc.wanandroid.base.BaseBindingFragment
import com.hjc.wanandroid.databinding.FragmentMeBinding
import com.hjc.wanandroid.ui.flowdialog.TestActivity

class MeFragment : BaseBindingFragment<FragmentMeBinding>({
    FragmentMeBinding.inflate(it)
}) {

    override fun initView(view: View, savedInstanceState: Bundle?) {
        binding.button.setOnClickListener {
            startActivity(Intent(context, TestActivity::class.java))
        }
    }

    override fun initData(savedInstanceState: Bundle?) {
    }
}