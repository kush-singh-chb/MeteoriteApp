package com.example.meteoritelandings

import com.example.meteoritelandings.models.Meteorite
import com.example.meteoritelandings.network.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import org.jetbrains.anko.doAsync

/**
 * Created by Kush Singh Chibber on 14-12-2020.
 */
object Data {

    private val nasaApiService by lazy { ApiService.create() }

    fun loadData(onSuccess: Consumer<List<Meteorite>>, onError: Consumer<Throwable>): Disposable {
        return nasaApiService
                .retrieveLandedMeteorites(
                        "yyhmUSd5r6OVuZK3GBZSyfSal",
                        "year >= '2011-01-01T00:00:00'",
                        "mass DESC")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        {
                            updateRealmDb(it)
                            onSuccess.accept(it)
                        },
                        onError::accept)
    }

    private fun updateRealmDb(meteorites: List<Meteorite>) {
        doAsync {
            Realm.getDefaultInstance().use { realm ->
                realm.beginTransaction()
                realm.insertOrUpdate(meteorites)
                realm.commitTransaction()
            }
        }
    }
}