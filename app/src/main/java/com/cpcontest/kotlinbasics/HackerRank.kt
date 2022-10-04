package com.cpcontest.kotlinbasics

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class HackerRank : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_codeforces, container, false)

        val apiInterface = ApiClient.getClient().create(ApiInteface::class.java)
        val call = apiInterface.getHackerrankContests()

        checkbox(view)

        call.enqueue(object : Callback<List<AllContestModel>> {
            override fun onResponse(
                call: Call<List<AllContestModel>>,
                response: Response<List<AllContestModel>>
            ) {
                val progressBar = view.findViewById<FrameLayout>(R.id.progressBar)
                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE
                    val recyclerview = view.findViewById<RecyclerView>(R.id.recyclerview)
                    val recyclerview2 = view.findViewById<RecyclerView>(R.id.recyclerview2)
                    val recyclerview3 = view.findViewById<RecyclerView>(R.id.recyclerview3)
                    recyclerview.layoutManager = LinearLayoutManager(requireContext())
                    recyclerview2.layoutManager = LinearLayoutManager(requireContext())
                    recyclerview3.layoutManager = LinearLayoutManager(requireContext())
                    recyclerview.isNestedScrollingEnabled = false
                    recyclerview2.isNestedScrollingEnabled = false
                    recyclerview2.isNestedScrollingEnabled = false
                    var mFiles = response.body()
                    if (mFiles != null) {
                        mFiles = mFiles.sortedWith(compareBy { it.start_time })
                    }
                    val text1 = view.findViewById<TextView>(R.id.text1)
                    val text2 = view.findViewById<TextView>(R.id.text2)
                    val text3 = view.findViewById<TextView>(R.id.text3)
                    val ongoing = mFiles?.let { getOngoing(it) }
                    val in24hrs = mFiles?.let { get24hrs(it) }
                    val later = mFiles?.let { getLater(it) }

                    if (ongoing == null || ongoing.isEmpty()) text1.text = "No ongoing contests"
                    if (in24hrs == null || in24hrs.isEmpty()) text2.text =
                        "No contests within 24 hrs"
                    if (later == null || later.isEmpty()) text3.text = "No future contests"

                    val adapter1 = ongoing?.let { context?.let { it1 -> AllContestAdapter(it, it1) } }
                    val adapter2 = in24hrs?.let { context?.let { it1 -> AllContestAdapter(it, it1) } }
                    val adapter3 = later?.let { context?.let { it1 -> AllContestAdapter(it, it1) } }
                    recyclerview.adapter = adapter1
                    recyclerview2.adapter = adapter2
                    recyclerview3.adapter = adapter3
                } else
                    progressBar.visibility = View.GONE            }

            override fun onFailure(call: Call<List<AllContestModel>>, t: Throwable) {
                Log.d("error :", t.message.toString())
            }

        })

        return view
    }

    private fun checkbox(view: View) {
        val recyclerview1 = view.findViewById<RecyclerView>(R.id.recyclerview)
        val recyclerview2 = view.findViewById<RecyclerView>(R.id.recyclerview2)
        val recyclerview3 = view.findViewById<RecyclerView>(R.id.recyclerview3)
        val checkBox1 = view.findViewById<CheckBox>(R.id.checkbox1)
        val checkBox2 = view.findViewById<CheckBox>(R.id.checkbox2)
        val checkBox3 = view.findViewById<CheckBox>(R.id.checkbox3)

        checkBox1.setOnCheckedChangeListener { compoundButton, b ->
            if (!b) recyclerview1.visibility = View.VISIBLE
            else recyclerview1.visibility = View.GONE
        }
        checkBox2.setOnCheckedChangeListener { compoundButton, b ->
            if (!b) recyclerview2.visibility = View.VISIBLE
            else recyclerview2.visibility = View.GONE
        }
        checkBox3.setOnCheckedChangeListener { compoundButton, b ->
            if (!b) recyclerview3.visibility = View.VISIBLE
            else recyclerview3.visibility = View.GONE
        }
    }

    fun getOngoing(list: List<AllContestModel>): List<AllContestModel> {
        val res: MutableList<AllContestModel> = mutableListOf()

        for (x in list) {
            if (x.status.lowercase(Locale.getDefault()).contains("coding"))
                res.add(x)
        }

        return res
    }

    fun get24hrs(list: List<AllContestModel>): List<AllContestModel> {
        val res: MutableList<AllContestModel> = mutableListOf()
        for (x in list) {
            if (x.in_24_hours.lowercase().contains("yes") &&
                !x.status.lowercase(Locale.getDefault()).contains("coding"))
                res.add(x)
        }

        return res
    }

    fun getLater(list: List<AllContestModel>): List<AllContestModel> {
        val res: MutableList<AllContestModel> = mutableListOf()
        for (x in list) {
            if (x.status.lowercase(Locale.getDefault()).contains("before") &&
                !x.in_24_hours.lowercase().contains("yes")
            )
                res.add(x)
        }

        return res
    }
}