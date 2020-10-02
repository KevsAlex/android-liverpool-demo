package com.example.liverpooldemo.MenuPrincipal.adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.example.liverpooldemo.R
import com.example.liverpooldemo.modelo.Producto
import com.example.liverpooldemo.utils.DidPerformEvent
import com.example.liverpooldemo.utils.MyUtilsJava
import java.io.File
import java.util.*

class AdapterProductos(private val context: Context) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    var productos: ArrayList<*>? = null
    var delegate: DidPerformEvent? = null

    companion object {
        val VIVIENDA = 0
    }

    init {
        productos = ArrayList<Any>()

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val viewProducto =
            LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false)
        return ProductoVH(viewProducto)
    }

    override fun getItemCount(): Int {
        return productos!!.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (productos != null) {
            val producto = productos!![position] as Producto
            val productoVH = holder as ProductoVH

            Log.d(MyUtilsJava.APP_NAME, producto.tinyPhoto)

            productoVH.textProducto.text = producto.nombre
            productoVH.textPrecio.text = "$${producto.precio}"

            Glide.with(context)
                .load(producto.tinyPhoto)
                .into(holder.imagenProducto)

        }
    }

    inner class ProductoVH(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var cardProducto: CardView = itemView.findViewById(R.id.cardProducto)
        internal var textProducto: TextView = itemView.findViewById(R.id.textNombreProducto)
        internal var imagenProducto: ImageView = itemView.findViewById(R.id.imagenProducto)
        internal var viewBackCategoria: FrameLayout = itemView.findViewById(R.id.viewBackCategoria)
        internal var textPlaceHolder: TextView = itemView.findViewById(R.id.textPlaceHolder)
        internal var textPrecio: TextView = itemView.findViewById(R.id.textPrecio)

    }


}