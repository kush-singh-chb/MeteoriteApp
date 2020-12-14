package com.example.meteoritelandings.adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.meteoritelandings.R
import com.example.meteoritelandings.Utils.Constants.SELECTED_METEORITE
import com.example.meteoritelandings.Utils.format
import com.example.meteoritelandings.Utils.inflate
import com.example.meteoritelandings.activities.MapActivity
import com.example.meteoritelandings.databinding.MeteoriteSingleColumnBinding
import com.example.meteoritelandings.models.Meteorite
import com.google.gson.Gson
import org.jetbrains.anko.startActivity

/**
 * Created by Kush Singh Chibber on 14-12-2020.
 */

class MeteoriteAdapter(_meteorites: List<Meteorite>) :
        RecyclerView.Adapter<MeteoriteAdapter.ConceptHolder>() {

    companion object {
        @JvmField
        val TAG: String = MeteoriteAdapter::class.java.simpleName
    }

    private var meteorites: List<Meteorite> = _meteorites

    override fun getItemCount() = meteorites.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConceptHolder =
            ConceptHolder(parent.inflate(R.layout.meteorite_single_column, false))

    override fun onBindViewHolder(holder: ConceptHolder, position: Int) =
            holder.bindConcept(meteorites[position])

    fun loadItems(otherMeteorites: List<Meteorite>) {
        meteorites = otherMeteorites
        Log.i(TAG, "current meteorites amount: ${meteorites.size}")
        notifyDataSetChanged()
    }

    class ConceptHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        private var meteorite: Meteorite? = null
        private val binding = MeteoriteSingleColumnBinding.bind(v)

        init {
            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            itemView.context.startActivity<MapActivity>(
                    SELECTED_METEORITE to Gson().toJson(meteorite))
        }

        fun bindConcept(meteorite: Meteorite) {
            this.meteorite = meteorite
            binding.meteoriteNameLabel.text = meteorite.name
            binding.meteoriteWeightView.text = meteorite.mass.format(2) // No i18n needed yet
        }
    }

}