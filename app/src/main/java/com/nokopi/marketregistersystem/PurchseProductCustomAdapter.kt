package com.nokopi.marketregistersystem

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.nokopi.marketregistersystem.data.Product


class PurchaseProductCustomAdapter(
    private val itemClickListener: OnItemClickListener
): ListAdapter<Product, PurchaseProductViewHolder>(DiffCallback) {

    private val context = NFCActivity.instance.applicationContext
    private val resources = context.resources

    interface OnItemClickListener {
        fun itemClick(product: Product)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PurchaseProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_purchase_product, parent, false)
        return PurchaseProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: PurchaseProductViewHolder, position: Int) {
        val product = getItem(position)
        holder.purchaseProductName.text = resources.getString(R.string.purchase_product_name, product.productName)
        holder.purchaseProductPrice.text = resources.getString(R.string.purchase_product_price, product.productPrice)
        holder.purchaseLinearLayout.setOnClickListener {
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


class PurchaseProductViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val purchaseProductName: TextView = view.findViewById(R.id.purchaseProductName)
    val purchaseProductPrice: TextView = view.findViewById(R.id.purchaseProductPrice)
    val purchaseLinearLayout: LinearLayout = view.findViewById(R.id.linearLayout)
}