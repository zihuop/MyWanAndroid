package com.hjc.wanandroid.ui.main

import com.hjc.wanandroid.base.IUiIntent
import com.hjc.wanandroid.base.IUiState
import com.hjc.wanandroid.model.bean.Article
import com.hjc.wanandroid.model.bean.Banner

/**
 * @Author：张洪顺
 * @E-Mail: zhanghs@mfhcd.com
 * @Date：2023/1/29 16:09
 * @版权所有 © 现代金融控股（成都）有限公司
 * @描述：
 */
data class HomeViewState(val bannerUiState: BannerUiState, val detailUiState: DetailUiState) : IUiState

sealed class BannerUiState {
    object INIT : BannerUiState()
    data class SUCCESS(val models: List<Banner>) : BannerUiState()
}

sealed class DetailUiState {
    object INIT : DetailUiState()
    data class SUCCESS(val articles: Article) : DetailUiState()
}

sealed class HomeViewIntent : IUiIntent {
    object GetBanner : HomeViewIntent()
    data class GetDetail(val page: Int) : HomeViewIntent()
    // 合并请求测试
    data class Combine(val page: Int) : HomeViewIntent()
}