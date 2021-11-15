package com.example.testtaskandroid.fragments

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

class CharactersViewModel : ViewModel() {
    var countItems: Int = 0
    var currentPage: Int = 1
    var pageCount: Int? = 0
    var location: ResultLocation = ResultLocation(id = -1, name = "unknown", type = "unknown", dimension = "unknown", residents = emptyList())

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
        if (id != -1){
            _detailsForm.value = DetailsFormState(isInfoAboutLocationLoaded = false)

            retrofitService.getLocationById(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<ResultLocation> {
                    override fun onSubscribe(d: Disposable) {}

                    override fun onNext(t: ResultLocation) {
                        location = t
                    }

                    override fun onComplete() {
                        _detailsForm.value = DetailsFormState(isInfoAboutLocationLoaded = true)
                    }

                    override fun onError(e: Throwable) {}
                })
        }
        else{
            location = ResultLocation(id = -1, name = "unknown", type = "unknown", dimension = "unknown", residents = emptyList())
            _detailsForm.value = DetailsFormState(isInfoAboutLocationLoaded = true)

        }

    }


    fun loadFirstPage() {
        _listForm.value = ListFormState(
            isEmptyListCharacters = true
        )
        getCharactersPage(1)
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
    val isInfoAboutLocationLoaded: Boolean = false

)