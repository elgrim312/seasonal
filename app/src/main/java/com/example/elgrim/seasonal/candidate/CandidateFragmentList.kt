package com.example.elgrim.seasonal.candidate

import android.content.SharedPreferences
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.beust.klaxon.Klaxon
import com.example.elgrim.seasonal.Constants
import com.example.elgrim.seasonal.R
import com.example.elgrim.seasonal.adapter.CandidateAdapterList
import com.example.elgrim.seasonal.http.APIController
import com.example.elgrim.seasonal.http.ServiceVolley
import com.example.elgrim.seasonal.model.Candidate
import com.example.elgrim.seasonal.model.CandidateParcelableList
import com.example.elgrim.seasonal.utils.PreferenceHelper
import com.example.elgrim.seasonal.utils.PreferenceHelper.get
import com.mikepenz.fastadapter.commons.adapters.FastItemAdapter
import com.mikepenz.fastadapter.listeners.OnClickListener
import kotlinx.android.synthetic.main.fragment_candidate_list.*


class CandidateFragmentList : Fragment() {

    private lateinit var prefs: SharedPreferences

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_candidate_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefs = PreferenceHelper.defaultPrefs(this.activity)
        val bundle = this.arguments
        if (bundle != null) {
            Log.d("BUNDLE", bundle.toString())
            val candidatesData = bundle.getParcelable<CandidateParcelableList>("CandidatesParcelableList")
            professional_recycler_list.layoutManager = LinearLayoutManager(context)
            val itemAdapter = FastItemAdapter<CandidateAdapterList>()
            itemAdapter.add(candidatesData.candidates?.map { CandidateAdapterList(it) })

            professional_recycler_list.adapter = itemAdapter

            itemAdapter.withOnClickListener({ _, _, item, _ ->
                val candidate = item.candidate
                val intent = Intent(this.context, CandidateDetail::class.java)
                intent.putExtra("candidate_EXTRA", candidate)
                startActivity(intent)
                true
            })
        }

    }

}
