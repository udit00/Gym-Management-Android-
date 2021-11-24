package com.example.gymmanagement

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import java.lang.invoke.ConstantCallSite
import java.lang.reflect.Member
import kotlin.coroutines.coroutineContext

class MemberListAdapter(private val listener: MemberItemClicked):  RecyclerView.Adapter<MemberViewHolder>(){
    var memberList = ArrayList<com.example.gymmanagement.Data.Member>()
    lateinit var context : Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_member, parent, false)
        val memberViewHolder = MemberViewHolder(view)
        view.setOnClickListener{
            listener.onItemClicked(memberList[memberViewHolder.adapterPosition])
        }
        return memberViewHolder
    }
    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        val currentItem = memberList[position]
        holder.memberRollNo.text = currentItem.id.toString()
        holder.memberName.text = currentItem.name
        holder.memberPhoneNumber.text = currentItem.phoneNumber.toString()
        if(position%2==0){
            holder.parentLayout.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.even
                )
            )
        }else{
            holder.parentLayout.setBackgroundColor(
                ContextCompat.getColor(
                    context,
                    R.color.odd
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return memberList.size
    }
    fun setData(member: List<com.example.gymmanagement.Data.Member>){
        memberList.clear()
        memberList.addAll(member)
        notifyDataSetChanged()
    }

}
class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
    val parentLayout: ConstraintLayout = itemView.findViewById(R.id.item_row_constraint_layout)
    val memberRollNo: TextView = itemView.findViewById(R.id.tv_member_roll_no)
    val memberName: TextView = itemView.findViewById(R.id.tv_member_name)
    val memberPhoneNumber: TextView = itemView.findViewById(R.id.tv_phone_number)
}
interface MemberItemClicked{
    fun onItemClicked(member: com.example.gymmanagement.Data.Member)
}