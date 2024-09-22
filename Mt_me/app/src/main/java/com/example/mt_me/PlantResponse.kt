package com.example.mt_me

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="response")
data class PlantResponse(
    @Element
    val body : myPlantBody
)

@Xml(name="body")
data class myPlantBody(
    @Element
    val items : myPlantItems
)

@Xml(name="items")
data class myPlantItems(
    @Element
    val item : MutableList<myPlantItem>
)

@Xml(name="item")
data class myPlantItem(
    @PropertyElement
    val fskname : String?,
    @PropertyElement
    val fsguide : String?,
    @PropertyElement
    val fslifetime : String?

    ) {
    constructor() : this(null, null, null)
}