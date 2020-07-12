package com.ankit.smsanalyzer.presentation

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ankit.smsanalyzer.base.BaseViewModel
import com.ankit.smsanalyzer.domain.GetAnalizedDataUseCase
import com.ankit.smsanalyzer.domain.GetSmsListUseCase
import com.ankit.smsanalyzer.domain.model.AnalizedData
import com.github.mikephil.charting.data.PieEntry
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MainViewModel @ViewModelInject constructor(
    private val getSmsListUseCase: GetSmsListUseCase,
    private val getAnalizedDataUseCase: GetAnalizedDataUseCase
): BaseViewModel() {
    private val TAG = MainViewModel::class.java.simpleName
    private val _smsList =  MutableLiveData<SmsListState>()
    val smsList: LiveData<SmsListState> = _smsList

    private val _analizedData =  MutableLiveData<AnalizedData>()
    val analizedData: LiveData<AnalizedData> = _analizedData

    fun loadSmsList() {
        getSmsListUseCase.getSmsList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object : SingleObserver<List<SmsItem>>{
            override fun onSuccess(t: List<SmsItem>) {
                Log.d(TAG,"sms list success size: ${t.size}")
                _smsList.postValue(SmsListState.Success(t))
                getAnalizedData(t)
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.addAll(d)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
                _smsList.postValue(SmsListState.Failed(e.fillInStackTrace()))
            }

        })
    }
    fun getAnalizedData(items: List<SmsItem>){
        getAnalizedDataUseCase.getAnalizedData(items).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(object: SingleObserver<AnalizedData>{
            override fun onSuccess(t: AnalizedData) {
                Log.d(TAG,"analized data success ")
                _analizedData.postValue(t)
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }

        })
    }
}