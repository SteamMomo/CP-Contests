package com.cpcontest.kotlinbasics

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class AllContestAdapter internal constructor(
    private val mFiles: List<AllContestModel>, private val context: Context
) :
    RecyclerView.Adapter<AllContestAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.all_contest_item, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cardView.setOnClickListener {
            searchIntent(mFiles[position].url)
        }

        val site = mFiles[position].site
        if (site == null) holder.site.visibility = View.GONE
        holder.site.text = mFiles[position].site
        holder.contestName.text = mFiles[position].name

        val type = mFiles[position].type_
        if (type != null) {
            holder.type_container.visibility = View.VISIBLE
            holder.type.text = type
        }
// nothing
        val inputFormat2 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss z")
        inputFormat2.timeZone = TimeZone.getTimeZone("UTC")
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("hh:mm aa")
        val outputDate = SimpleDateFormat("dd MMMM, yyyy '('EEEE')'")

        val date1: Date = try {
            inputFormat.parse(mFiles[position].start_time) as Date
        } catch (e: Exception) {
            inputFormat2.parse(mFiles[position].start_time) as Date
        }

        val date0: String = outputDate.format(date1)
        val start_time: String = outputFormat.format(date1)

        holder.datee.text = date0
        holder.start.text = start_time

        val totalSeconds = mFiles[position].duration
        val seconds: Long = totalSeconds % 60
        val minutes: Long = totalSeconds % 3600 / 60
        val hours: Long = totalSeconds % 86400 / 3600
        val days: Long = totalSeconds / 86400
        val one: Long = 1
        var dur = ""
        if (days >= 1)
            dur += if (days == one) "1 day "
            else "$days days "
        if (hours >= 1)
            dur += if (hours == one) "1 hr "
            else "$hours hrs "
        if (minutes >= 1)
            dur += if (minutes == one) "1 min "
            else "$minutes mins "
        if (seconds >= 1)
            dur += if (seconds == one) "1 sec "
            else "$seconds secs "

        holder.duration.text = dur
    }

    private fun searchIntent(url: String) {
        val alertDialog = AlertDialog.Builder(context)
        alertDialog.setMessage("Do you want to visit -\n$url")
        alertDialog.setPositiveButton("Yes"){ _, _ ->
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(url)
            context.startActivity(intent)
        }
        alertDialog.setNegativeButton("No"){ _, _ ->}
        alertDialog.show()
    }

    override fun getItemCount(): Int {
        return mFiles.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val site: TextView = itemView.findViewById(R.id.all_site)
        val start: TextView = itemView.findViewById(R.id.all_start)
        val datee: TextView = itemView.findViewById(R.id.all_date)
        val duration: TextView = itemView.findViewById(R.id.all_duration)
        val contestName: TextView = itemView.findViewById(R.id.all_name)
        val type: TextView = itemView.findViewById(R.id.all_type)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val type_container: LinearLayout = itemView.findViewById(R.id.type_container)
    }
}