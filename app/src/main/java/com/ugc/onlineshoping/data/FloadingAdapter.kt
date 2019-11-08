package com.ugc.onlineshoping.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ugc.onlineshoping.R
import com.ramotion.foldingcell.FoldingCell
import kotlinx.android.synthetic.main.cell_content_layout.view.*
import kotlinx.android.synthetic.main.cell_title_layout.view.*
import java.util.ArrayList
import java.util.HashSet

class FloadingAdapter(private var shoe_list: MutableList<ShowModel>, var listener: EventClick) :
    RecyclerView.Adapter<FloadingAdapter.FloadingVH>(), Filterable {
    private val unfoldedIndexes = HashSet<Int>()
    var qty :Int? = null
    var filerResult: List<ShowModel>
    init {
        this.filerResult = shoe_list
    }

    @SuppressLint("InflateParams")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FloadingVH {
        return FloadingVH(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell, null)
        )
    }

    override fun getItemCount(): Int {
        return filerResult.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FloadingVH, position: Int) {
        val dataModel= shoe_list.get(position)
        var cell: FoldingCell? = holder.itemView as FoldingCell
        with(holder.itemView){
            Glide.with(context)
                .load(dataModel.photo)
                .into(imagesThum)
            materials.text = dataModel.title
            var p: Double = dataModel.price
            price.text = p.toString()

            Glide.with(context)
                .load(dataModel.photo)
                .into(imagesThum1)
            materials1.text = dataModel.title
            price1.text = "₹ $p"
            desc.text = dataModel.description
            sizess.text = dataModel.size

            setOnClickListener {
                listener.onEventClickListener(shoe_list[position], position, holder.itemView)
            }
            add_cart.setOnClickListener {
                listener.onRequestItemClickListener(shoe_list[position],position)
            }
            holder.orders.setOnClickListener {
                listener.orderNow(shoe_list[position],position,qty!!)
            }
            val qty_array = arrayOf<String>("1","2","3","4","5")
            val adapters = ArrayAdapter(context!!, R.layout.qty_list,R.id.sp_qrt,qty_array)
            holder.qty_spinner.adapter = adapters
            holder.qty_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }

                override fun onItemSelected(parent: AdapterView<*>?,view: View?,position: Int, id: Long) {
                    val qtys = parent!!.getItemAtPosition(position).toString().trim()
                    qty = qtys.toInt()

                    holder.prices.text ="Price( "+ qty+" item)"
                    val qty_price : Double = qty!! * p
                    holder.item_price1.text = "₹"+qty_price.toString()

                    val d:Int = 49 * qty!!
                    holder.d_charge.text = "₹$d"
                    val amount: Double = qty_price + d
                    holder.total_price.text = amount.toString()
                    price1.text = "₹ $amount"
                }

            }
        }
    }
    override fun getFilter(): Filter {
        return object : Filter() {
            @SuppressLint("DefaultLocale")
            override fun performFiltering(charString: CharSequence?): FilterResults {
                val charSearch= charString.toString()
                if(charSearch.isEmpty()){
                    filerResult = shoe_list
                }else{
                    val resultList = ArrayList<ShowModel>()
                    for(row in shoe_list){
                        if(row.title.toLowerCase().contains(charSearch.toLowerCase()))
                            resultList.add(row)
                    }
                    filerResult = resultList
                }
                val filterResults = Filter.FilterResults()
                filterResults.values = filerResult
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                filerResult = filterResults!!.values as ArrayList<ShowModel>
                notifyDataSetChanged()
            }
        }
    }
    interface EventClick {
        fun onEventClickListener(event: ShowModel, position: Int, view: View)
        fun onRequestItemClickListener(event: ShowModel, position: Int)
        fun orderNow(event: ShowModel, position: Int,qty: Int)
    }
    fun registerToggle(position: Int) {
        if (unfoldedIndexes.contains(position))
            registerFold(position)
        else
            registerUnfold(position)
    }

    fun registerFold(position: Int) {
        unfoldedIndexes.remove(position)
    }

    fun registerUnfold(position: Int) {
        unfoldedIndexes.add(position)
    }
    class FloadingVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView : ImageView = itemView.findViewById(R.id.imagesThum)
        var titleText : TextView = itemView.findViewById(R.id.materials)
        var priceText : TextView = itemView.findViewById(R.id.price)
        var orders : Button = itemView.findViewById(R.id.orders)
        val qty_spinner = itemView.findViewById<Spinner>(R.id.qty_spinner)
        val prices = itemView.findViewById<TextView>(R.id.prices)
        val item_price1 = itemView.findViewById<TextView>(R.id.item_price1)
        val d_charge = itemView.findViewById<TextView>(R.id.d_charge)
        val total_price = itemView.findViewById<TextView>(R.id.total_price)
    }
}