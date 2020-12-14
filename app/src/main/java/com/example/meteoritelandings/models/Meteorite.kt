package com.example.meteoritelandings.models

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass
import java.util.*

/**
 * Created by Kush Singh Chibber on 14-12-2020.
 */
@RealmClass
open class Meteorite(
        @PrimaryKey
        open var id: Long = 0,
        open var name: String = "",
        open var nametype: String = "",
        open var recclass: String = "",
        open var mass: Double = 0.0,
        open var fall: String = "",
        open var year: Date = Date(0L),
        open var reclat: Double = 0.0,
        open var reclong: Double = 0.0
) : RealmObject() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Meteorite

        if (name != other.name) return false
        if (id != other.id) return false
        if (nametype != other.nametype) return false
        if (recclass != other.recclass) return false
        if (mass != other.mass) return false
        if (fall != other.fall) return false
        if (year != other.year) return false
        if (reclat != other.reclat) return false
        if (reclong != other.reclong) return false

        return true
    }

    override fun hashCode(): Int {
        var name_hash = name.hashCode()
        name_hash = 31 * name_hash + id.hashCode()
        name_hash = 31 * name_hash + nametype.hashCode()
        name_hash = 31 * name_hash + recclass.hashCode()
        name_hash = 31 * name_hash + mass.hashCode()
        name_hash = 31 * name_hash + fall.hashCode()
        name_hash = 31 * name_hash + year.hashCode()
        name_hash = 31 * name_hash + reclat.hashCode()
        name_hash = 31 * name_hash + reclong.hashCode()
        return name_hash
    }

    override fun toString(): String =
            "Meteorite(name='$name', id=$id, nametype='$nametype', recclass='$recclass', " +
                    "mass=$mass, fall='$fall', year=$year, reclat=$reclat, reclong=$reclong)"
}
