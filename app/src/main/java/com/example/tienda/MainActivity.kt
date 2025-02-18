package com.example.tienda

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.tienda.adapter.Recycler_tiendaAdapter
import com.example.tienda.adapter.SpinnerCategoriasAdapter
import com.example.tienda.databinding.ActivityMainBinding
import com.example.tienda.model.Categoria
import com.example.tienda.model.Producto
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapterSpinnerCategoriasAdapter: SpinnerCategoriasAdapter
    private lateinit var listaCategorias: ArrayList<Categoria>
    private lateinit var adapterRecycler: Recycler_tiendaAdapter
    private lateinit var listaProductos: ArrayList<Producto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        instancias() // No mover de aquí. Si lo pones más abajo nunca se inicializa listaCategorias

        // Configuro el listener del Spinner para filtrar los productos según la categoría seleccionada
        binding.spinnerCategorias.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val categoriaSeleccionada = listaCategorias[position].nombre
                actualizarListaProductos(categoriaSeleccionada)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        // Inicializamos el adaptador del Spinner
        adapterSpinnerCategoriasAdapter = SpinnerCategoriasAdapter(this, listaCategorias)
        binding.spinnerCategorias.adapter = adapterSpinnerCategoriasAdapter

        setContentView(binding.root)

        /*
        Crea la lista de categorías.
        Conecta el Adapter al Spinner.
        Maneja la selección con un OnItemSelectedListener.
        */
    }

    private fun instancias() {
        // Inicializamos las listas vacías
        listaCategorias = arrayListOf() // Inicializamos la lista vacía
        listaProductos = arrayListOf()

        // Configuramos el adaptador del Spinner
        adapterSpinnerCategoriasAdapter = SpinnerCategoriasAdapter(this, listaCategorias)
        binding.spinnerCategorias.adapter = adapterSpinnerCategoriasAdapter

        // Configuro el RecyclerView
        adapterRecycler = Recycler_tiendaAdapter(listaProductos)
        binding.recyclerProductos.adapter = adapterRecycler
        binding.recyclerProductos.layoutManager = LinearLayoutManager(this)

        // Cargamos las categorías y los productos desde la red
        cargarCategorias()
        cargarProductos()
    }

    private fun cargarCategorias() {
        val url = "https://dummyjson.com/products/categories"
        val request = JsonArrayRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                listaCategorias.clear()
                for (i in 0 until response.length()) {
                    val nombreCategoria = response.getString(i)
                    listaCategorias.add(Categoria(nombreCategoria))
                }
                adapterSpinnerCategoriasAdapter.notifyDataSetChanged()
            },
            { error ->
                Log.e("Volley", "Error al cargar categorías: $error")
            }
        )
        Volley.newRequestQueue(applicationContext).add(request)
    }

    private fun cargarProductos() {
        val url = "https://dummyjson.com/products"
        val peticion = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            { response ->
                val gson = Gson()
                // Parseamos el array "products" a un Array de Producto
                val productosArray = gson.fromJson(
                    response.getJSONArray("products").toString(),
                    Array<Producto>::class.java
                )
                listaProductos.clear()
                listaProductos.addAll(productosArray)
                adapterRecycler.notifyDataSetChanged()
            },
            { error ->
                Log.e("Volley", "Error al obtener productos: $error")
            }
        )
        Volley.newRequestQueue(applicationContext).add(peticion)
    }

    private fun actualizarListaProductos(categoria: String) {
        val productosFiltrados = listaProductos.filter { it.categoria == categoria }
        adapterRecycler.actualizarLista(productosFiltrados)
    }
}
