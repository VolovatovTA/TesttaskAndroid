package com.example.testtaskandroid.dagger

import android.app.Application
import com.example.testtaskandroid.MainActivity
import com.example.testtaskandroid.fragments.FragmentDetails
import com.example.testtaskandroid.fragments.ListOfCharactersFragment

import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {
    fun inject(details: FragmentDetails)
    fun inject(listFragment: ListOfCharactersFragment)
    fun inject(mainActivity: MainActivity)

}
class MyApplication: Application() {
    val appComponent: AppComponent = DaggerAppComponent.create()
}