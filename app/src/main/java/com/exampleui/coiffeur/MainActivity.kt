package com.exampleui.coiffeur

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.exampleui.coiffeur.databinding.ActivityMainBinding
import com.iammert.tabscrollattacherlib.TabScrollAttacher

inline fun <T : ViewBinding> AppCompatActivity.viewBinding(
    crossinline bindingInflater: (LayoutInflater) -> T) =
    lazy(LazyThreadSafetyMode.NONE) {
        bindingInflater.invoke(layoutInflater)
    }

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val adapter = ServicesAdapter {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        }
        adapter.setItems(getAllListWithHeaders())
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.addItemDecoration(StickHeaderItemDecoration(adapter))

        TabLoader.loadTabs(binding.tabLayout, getCategories())

        val indexOffsets = getCategoryIndexOffsets(getCategories())
        val attacher = TabScrollAttacher(binding.tabLayout, binding.recyclerView, indexOffsets) {
            scrollSmoothly()
        }

        attacher.attach()
    }
    private fun getCategoryIndexOffsets(categories: List<String>): List<Int> {
        val indexOffsetList = arrayListOf<Int>()
        categories.forEach { categoryItem ->
            if (indexOffsetList.isEmpty()) {
                indexOffsetList.add(0)
            } else {
                indexOffsetList.add(indexOffsetList.last() + categories.size)
            }
        }
        return indexOffsetList
    }

    private fun getCategories(): List<String> {
        val array = ArrayList<String>()
        getAllServices().forEach {
            if (!array.contains(it.category)) {
                array.add(it.category)
            }
        }
        return array.sortedBy { it }
    }
    private fun getAllServices(): ArrayList<CoiffeurServices> {
        return arrayListOf(
            CoiffeurServices("Sac Kesimi","20 $","Sac","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Sac Bakimi","20 $","Sac","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Sac Boyama","20 $","Sac","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Sac Modelleme","210 $","Sac","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Sac Onarim","50 $","Sac","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Manikur 1","20 $","Manikur","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Manikur 2","20 $","Manikur","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Manikur 3","20 $","Manikur","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Pedikur 1","20 $","Pedikur","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Pedikur 2","20 $","Pedikur","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Pedikur 3","20 $","Pedikur","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Pedikur 4","20 $","Pedikur","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Makyaj 1","20 $","Makyaj","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Makyaj 2","20 $","Makyaj","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Makyaj 3","20 $","Makyaj","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Makyaj 4","20 $","Makyaj","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Makyaj 5","20 $","Makyaj","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Makyaj 6","20 $","Makyaj","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Makyaj 7","20 $","Makyaj","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Makyaj 8","20 $","Makyaj","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Fön 1","20 $","Fon","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Fön 2","20 $","Fon","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Fön 3","20 $","Fon","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Fön 4","20 $","Fon","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Ağda 1","20 $","Agda","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Ağda 2","20 $","Agda","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Ağda 3","20 $","Agda","2 hr 15 min",getString(R.string.lorem_ipsum_small),1),
            CoiffeurServices("Ağda 4","20 $","Agda","2 hr 15 min",getString(R.string.lorem_ipsum_small),1)
        )
    }

    private fun getAllListWithHeaders(): ArrayList<CoiffeurServices> {
        var categoryHeaderText = ""
        var coefficient = 0
        val resultList = ArrayList<CoiffeurServices>()
        val list :List<CoiffeurServices> =
            getAllServices().sortedBy { it.category }
        resultList.addAll(list)
        list.forEachIndexed { index, coiffeurServices ->
            if (categoryHeaderText != coiffeurServices.category) {
                categoryHeaderText = coiffeurServices.category
                resultList.add(index+coefficient,getHeaderItem(categoryHeaderText))
                coefficient += 1
            }
        }
        return resultList
    }

    private fun getHeaderItem(category : String) : CoiffeurServices =
        CoiffeurServices("","",category,"","",0)


}