package com.nokopi.marketregistersystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nokopi.marketregistersystem.data.Product

class ProductCustomAdapter(
    private val itemClickListener: OnItemClickListener
): ListAdapter<Product, AddProductViewHolder>(DiffCallback) {

    private val context = NFCActivity.instance.applicationContext
    private val resources = context.resources

    interface OnItemClickListener {
        fun itemClick(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_product, parent, false)
        return AddProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.productName.text = resources.getString(R.string.purchase_product_name, product.productName)
        holder.productPrice.text = resources.getString(R.string.purchase_product_price, product.productPrice)
        holder.deleteBtn.setOnClickListener {
            itemClickListener.itemClick(product)
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Product>() {
            // Itemのnameが同じならtrue
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem.productName == newItem.productName
            }

            // Itemが同じならtrue
            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
                return oldItem == newItem
            }

        }
    }

}


class AddProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val productName: TextView = view.findViewById(R.id.productName)
    val productPrice: TextView = view.findViewById(R.id.productPrice)
    val deleteBtn: ImageButton = view.findViewById(R.id.productDeleteBtn)
}