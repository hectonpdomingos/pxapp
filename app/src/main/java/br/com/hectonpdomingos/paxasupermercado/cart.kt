package br.com.hectonpdomingos.paxasupermercado

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class cart : AppCompatActivity() {


    var quantity = arrayListOf<Int>()
    var price = arrayListOf<Double>()
    var totalPrice: Double? = null
    var myListProducts = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)


        val choseProducts:ArrayList<String> = intent.getStringArrayListExtra("Prod")
         for (item in choseProducts){
             myListProducts.add(item.toString())
         }


        val listaProdutos = findViewById(R.id.myCart) as ListView
        val cartAdapter = ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, myListProducts)
        // Assign adapter to ListView
        listaProdutos.setAdapter(cartAdapter)

}

}