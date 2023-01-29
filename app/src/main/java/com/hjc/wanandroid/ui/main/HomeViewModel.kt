package com.hjc.wanandroid.ui.main

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.hjc.wanandroid.base.BaseViewModel
import com.hjc.wanandroid.base.IUiIntent
import com.hjc.wanandroid.base.LoadUiIntent
import com.hjc.wanandroid.model.respository.HomeRepository
import com.hjc.wanandroid.utils.getFlow
import kotlinx.coroutines.flow.*

/**
 *
 * @author jianchong.hu
 * @create at 2022 12.12
 * @description:
 **/
class HomeViewModel(private val homeRepo: HomeRepository) :
    BaseViewModel<HomeViewState, HomeViewIntent>() {

    override fun initUiState(): HomeViewState {
        return HomeViewState(BannerUiState.INIT, DetailUiState.INIT)
    }

    override fun handleIntent(intent: IUiIntent) {
        when (intent) {
            HomeViewIntent.GetBanner -> {
                requestDataWithFlow(
                    request = { homeRepo.requestWanData() },
                    successCallback = { data ->
                        sendUiState {
                            copy(
                                bannerUiState = BannerUiState.SUCCESS(
                                    data
                                )
                            )
                        }
                    },
                    failCallback = {})
            }
            is HomeViewIntent.GetDetail -> {
                requestDataWithFlow(showLoading = false,
                    request = { homeRepo.requestRankData(intent.page) },
                    successCallback = { data ->
                        sendUiState {
                            copy(
                                detailUiState = DetailUiState.SUCCESS(
                                    data
                                )
                            )
                        }
                    })
            }
            is HomeViewIntent.Combine -> {
                val request1 = getFlow { homeRepo.requestWanData() }
                val request2 = getFlow { homeRepo.requestRankData(intent.page) }
                //多个请求并行
                combine(request1, request2) { response1, response2 ->
                    //合并结果
                    mutableListOf(response1, response2)
                }.onStart {
                    Log.d("TEST", "loadUiStateFlow1111")
                    sendLoadUiIntent(LoadUiIntent.Loading(true))
                }
                    .catch {
                        sendLoadUiIntent(LoadUiIntent.Loading(false))
                        Log.d("TEST", "loadUiStateFlow2222")
                        //处理异常
                        it.printStackTrace()
                    }
                    .onEach {
                        sendLoadUiIntent(LoadUiIntent.Loading(false))
                        Log.d("TEST", "loadUiStateFlow3333")
                        //处理结果
                        println("it = $it")
                    }
                    .launchIn(viewModelScope)
                    .start()
            }
        }
    }
}

