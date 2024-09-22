package com.example.mt_me

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="response")
data class XmlResponse(
    @Element
    val body : myXmlBody
)

@Xml(name="body")
data class myXmlBody(
    @Element
    val items : myXmlItems
)

@Xml(name="items")
data class myXmlItems(
    @Element
    val item : MutableList<myXmlItem>
)

@Xml(name="item")
data class myXmlItem(
    @PropertyElement
    val mntnnm : String?,
    @PropertyElement
    val mntninfodscrt : String?,
    @PropertyElement
    val crcmrsghtnginfoetcdscrt : String?,
    @PropertyElement
    val hndfmsmtnmapimageseq : String?,

) {
    constructor() : this(null, null, null, null)
}