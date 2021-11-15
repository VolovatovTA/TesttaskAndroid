package com.example.testtaskandroid.fragments

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.testtaskandroid.data.CharacterResponse
import com.example.testtaskandroid.data.EpisodeName
import com.example.testtaskandroid.data.ResultLocation
import com.example.testtaskandroid.recycler_view.PlaceholderContent
import com.example.testtaskandroid.retrofit.JSonPlaceHolderAPI
import com.example.testtaskandroid.retrofit.NetworkService
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CharactersViewModel : ViewModel() {
    var countItems: Int = 0
    var currentPage: Int = 1
    var pageCount: Int? = 0
    lateinit var location: ResultLocation

    private val _listForm = MutableLiveData<ListFormState>()
    val listFormState: LiveData<ListFormState> = _listForm

    private val _detailsForm = MutableLiveData<DetailsFormState>()
    val detailsFormState: LiveData<DetailsFormState> = _detailsForm

    private val retrofitService: JSonPlaceHolderAPI = NetworkService.jSonPlaceHolderAPI

    private fun getCharactersPage(page: Int): Observable<CharacterResponse> {
        return retrofitService.getCharactersPage(page)
    }


    private fun getEpisodeName(ep: Int): Observable<EpisodeName> {
        return retrofitService.getEpisodeName(ep)
    }


    fun getLocationById(id: Int) {
        _detailsForm.value = DetailsFormState(isAddingInfoAboutLocation = false)

        retrofitService.getLocationById(id).enqueue(object : Callback<ResultLocation> {
            override fun onResponse(
                call: Call<ResultLocation>,
                response: Response<ResultLocation>
            ) {

                if (response.code() == 200) {
                    location = response.body()!!
                    _detailsForm.value = DetailsFormState(isAddingInfoAboutLocation = true)
                }
            }

            override fun onFailure(call: Call<ResultLocation>, t: Throwable) {
            }

        })
    }


    fun loadFirstPage() {
        _listForm.value = ListFormState(
            isEmptyListCharacters = true
        )
        getCharactersPage(currentPage)
            .flatMap { response ->
                pageCount = response.infoPOJO.pages
                countItems = response.results.size
                Observable.fromIterable(response.results)
            }
            .flatMap({
                getEpisodeName(it.episode[0].split("episode/")[1].toInt())
            },
                { results, name ->
                    PlaceholderContent.addItem(
                        PlaceholderContent.PlaceholderItem(
                            results,
                            name.name
                        )
                    )

                })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Unit> {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: Unit) {}

                override fun onComplete() {
                    _listForm.value = ListFormState(allIsGood = true)
                }

                override fun onError(e: Throwable) {}

            })
    }


    fun appendCharacters() {
        currentPage++
        Log.d("Timofey", currentPage.toString())
        _listForm.value = ListFormState(isAddingCharacters = true)

        getCharactersPage(currentPage)
            .flatMap { response ->
                Observable.fromIterable(response.results)
            }
            .flatMap({
                getEpisodeName(it.episode[0].split("episode/")[1].toInt())
            },
                { results, name ->
                    PlaceholderContent.addItem(
                        PlaceholderContent.PlaceholderItem(
                            results,
                            name.name
                        )
                    )
                })
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Unit> {
                override fun onSubscribe(d: Disposable) {}

                override fun onNext(t: Unit) {}

                override fun onComplete() {
                    countItems = PlaceholderContent.ITEMS.size
                    _listForm.value = ListFormState(isCharactersAdd = true)
                }

                override fun onError(e: Throwable) {}

            })
    }

    data class ListFormState(
        val allIsGood: Boolean = false,
        val isEmptyListCharacters: Boolean = false,
        val isAddingCharacters: Boolean = false,
        val isCharactersAdd: Boolean = false
    )
}

class DetailsFormState(
    val isAddingInfoAboutLocation: Boolean = false

)