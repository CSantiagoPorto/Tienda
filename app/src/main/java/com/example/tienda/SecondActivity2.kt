package com.example.tienda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tienda.adapter.Recycler_tiendaAdapter
import com.example.tienda.databinding.ActivitySecond2Binding
import com.example.tienda.model.Producto

class SecondActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivitySecond2Binding
    private lateinit var carrito: ArrayList<Producto>
    private lateinit var adapter: Recycler_tiendaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivitySecond2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        // Recuperamos el carrito pasado desde MainActivity
        // Asegúrate de que Producto implementa Serializable
        carrito = intent.getSerializableExtra("carrito") as ArrayList<Producto>

        // Configuramos el RecyclerView

        adapter = Recycler_tiendaAdapter(carrito, object : Recycler_tiendaAdapter.OnProductoListener {
            override fun onProductoAdd(producto: Producto) {

            }
        })

        binding.recyclerCarrito.adapter = adapter
        binding.recyclerCarrito.layoutManager = LinearLayoutManager(this)

        // Calculamos el total de la compra y lo mostramos en txtTotal
        val total = carrito.sumOf { it.precio }
        binding.txtTotal.text = "Total: $total €"
    }
}
