package com.exampleui.coiffeur


/**
 * Created by atakanakar on 18.12.2021.
 * TransferChain
 */
data class CoiffeurServices(
    val name:String,
    val price: String,
    val category:String,
    val time: String,
    val description:String,
    val viewType: Int
)

data class Categories(
    val name: String
)