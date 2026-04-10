package com.example.monanatomie

data class ElementData(
    val drawable: Int,
    val text: Int
)

val CerveauData = listOf(
    ElementData(R.drawable.neurone,R.string.neurone),
    ElementData(R.drawable.emotion,R.string.emotion),
    ElementData(R.drawable.memoire,R.string.memoire),
    ElementData(R.drawable.sens,R.string.sens)
)

val CoeurPoumonsData = listOf(
    ElementData(R.drawable.coeur,R.string.coeur),
    ElementData(R.drawable.art_vei,R.string.art_vei),
    ElementData(R.drawable.globule,R.string.globules),
    ElementData(R.drawable.sang,R.string.sang),
    ElementData(R.drawable.poumon,R.string.poumon)
)

val OsMusclesPeauData = listOf(
    ElementData(R.drawable.os,R.string.os),
    ElementData(R.drawable.muscles,R.string.muscles),
    ElementData(R.drawable.peau,R.string.peau),
    ElementData(R.drawable.regul,R.string.regul),
    ElementData(R.drawable.main,R.string.main)
)

val DigestionData = listOf(
    ElementData(R.drawable.digestion,R.string.digestion),
    ElementData(R.drawable.nutrition,R.string.nutrition)
)

val AvatarList = listOf(
    R.drawable.avatar1,
    R.drawable.avatar2,
    R.drawable.avatar3,
    R.drawable.avatar4,
    R.drawable.avatar5,
    R.drawable.avatar6,
    R.drawable.avatar7,
    R.drawable.avatar8,
)

data class DetailItem(
    //val drawable: Int,
    val title: Int,
    val description: Int
)

val DetailList = listOf(
    DetailItem(R.string.neurone_titre,R.string.neurone_detail),
    DetailItem(R.string.emotion_titre,R.string.emotion_detail),
    DetailItem(R.string.memoire_titre,R.string.memoire_detail),
    DetailItem(R.string.sens_titre,R.string.sens_detail),
    DetailItem(R.string.coeur_titre,R.string.coeur_detail),
    DetailItem(R.string.art_vei_titre,R.string.art_vei_detail),
    DetailItem(R.string.globules_titre,R.string.globules_detail),
    DetailItem(R.string.sang_titre,R.string.sang_detail),
    DetailItem(R.string.poumon_titre,R.string.poumon_detail),
    DetailItem(R.string.os_titre,R.string.os_detail),
    DetailItem(R.string.muscles_titre,R.string.muscles_detail),
    DetailItem(R.string.peau_titre,R.string.peau_detail),
    DetailItem(R.string.regul_titre,R.string.regul_detail),
    DetailItem(R.string.main_titre,R.string.main_detail),
    DetailItem(R.string.digestion_titre,R.string.digestion_detail),
    DetailItem(R.string.nutrition_titre,R.string.nutrition_detail)
)