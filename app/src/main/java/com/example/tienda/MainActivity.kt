package com.example.tienda

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import com.example.tienda.databinding.ActivityMainBinding
import com.example.tienda.adapter.SpinnerCategoriasAdapter
import com.example.tienda.model.Categoria

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var  adapterSpinnerCategoriasAdapter: SpinnerCategoriasAdapter
    private lateinit var listaCategorias:ArrayList<Categoria>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        instancias()//No mover de aquí. Si lo pones más abajo nunca se inicializa listaCategorias
        adapterSpinnerCategoriasAdapter = SpinnerCategoriasAdapter(this, listaCategorias)
        binding.spinnerCategorias.adapter = adapterSpinnerCategoriasAdapter

        /*
Crea la lista de categorías.
Conecta el Adapter al Spinner.
Maneja la selección con un OnItemSelectedListener.*/
    }

    private fun instancias(){

        listaCategorias= arrayListOf(
            Categoria("smartphones"),
            Categoria("laptops"),
            Categoria("fragrances"),
            Categoria("skincare")


        )
    }
}