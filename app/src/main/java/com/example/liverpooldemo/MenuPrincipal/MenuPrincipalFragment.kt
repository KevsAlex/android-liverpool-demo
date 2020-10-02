package com.example.liverpooldemo.MenuPrincipal

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.liverpooldemo.MenuPrincipal.adapter.AdapterProductos
import com.example.liverpooldemo.R
import com.example.liverpooldemo.WebService.LiverpoolService
import com.example.liverpooldemo.WebService.LiverpoolService.NO_INTERNET
import com.example.liverpooldemo.WebService.WebResponseDelegate
import com.example.liverpooldemo.modelo.Producto
import com.example.liverpooldemo.utils.DialogLoader
import com.example.liverpooldemo.utils.MyUtilsJava
import kotlinx.android.synthetic.main.fragment_menu_principal.view.*
import me.everything.android.ui.overscroll.VerticalOverScrollBounceEffectDecorator
import me.everything.android.ui.overscroll.adapters.RecyclerViewOverScrollDecorAdapter
import org.json.JSONArray


/**
 * A simple [Fragment] subclass.
 * Use the [MenuPrincipalFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuPrincipalFragment : Fragment(), WebResponseDelegate {

    lateinit var adapter: AdapterProductos
    lateinit var webService: LiverpoolService
    var dialogLoader: DialogLoader? = null
    var productos = arrayListOf<Producto>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_menu_principal, container, false)

        webService = LiverpoolService(activity)

        webService.delegate = this
        dialogLoader = DialogLoader()
        dialogLoader?.isCancelable = false
        adapter = AdapterProductos(activity!!)
        view?.recyclerProductos?.layoutManager =
            GridLayoutManager(activity, 3, RecyclerView.VERTICAL, false)
        view?.recyclerProductos?.adapter = adapter

        VerticalOverScrollBounceEffectDecorator(
            RecyclerViewOverScrollDecorAdapter(
                view?.recyclerProductos
            ),
            1.5f,
            4f,
            2f
        );

        view?.editSearch?.addTextChangedListener(watcher())
        view?.imBuscar?.setOnClickListener {
            view?.editSearch.setText("")
        }

        return view
    }

    inner class watcher : TextWatcher {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun afterTextChanged(s: Editable?) {
            val textBuscar = s.toString().toLowerCase()
            Log.d(MyUtilsJava.APP_NAME, "texto a buscar: " + s.toString())

            if (textBuscar.isNotEmpty()) {
                var endpoint = LiverpoolService.Endpoint.SearchProducto
                val parametros = HashMap<String, Any>()
                parametros.put("force-plp", true)
                parametros.put("search-string", textBuscar)
                parametros.put("page-number", "1")
                parametros.put("number-of-items-per-page", "50")
                endpoint.parametros = parametros

                view?.imBuscar?.imageTintMode = null
                view?.imBuscar?.setImageResource(R.drawable.ic_close)
                var dialogLoader = DialogLoader()
                //dialogLoader.show(fragmentManager!!,"")
                webService.performRequest(endpoint, null, MenuPrincipalFragment::class.java)

            } else {
                adapter.productos?.clear()
                adapter.notifyDataSetChanged()
                view?.imBuscar?.setImageResource(R.drawable.ic_buscar)
            }
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {


        }
    }


    override fun didFInish(response: Any?, code: Any?) {

        if (code == LiverpoolService.Endpoint.SearchProducto) {
            var listaProductos = response as JSONArray
            //dialogLoader?.dismiss()
            productos.clear()
            for (n in 0 until listaProductos.length()) {
                val productoJson = listaProductos.getJSONObject(n)
                var producto = Producto()
                producto.nombre = productoJson.getString("productDisplayName")

                producto.precio = productoJson.getDouble("listPrice")
                producto.tinyPhoto = productoJson.getString("smImage")
                this.productos.add(producto)
                this.adapter.productos = productos
                this.adapter.notifyDataSetChanged()
            }
        }
    }

    override fun didFinishWithError(endpoint: Any?, response: Any?, code: Int) {
        when (code) {
            NO_INTERNET -> {
                Toast.makeText(activity, "No tienes internet", Toast.LENGTH_SHORT).show()
            }

            else -> {
                //val errorObject = response as GenericError
                Toast.makeText(activity, "Ocurrió un error en el servicio", Toast.LENGTH_SHORT)
                    .show()

            }
        }
        this.dialogLoader?.dismiss()
    }


}