package com.eathealthyapp.is3261.eathealthyapp.fragments.sub_fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.eathealthyapp.is3261.eathealthyapp.Food
import com.eathealthyapp.is3261.eathealthyapp.R
import com.eathealthyapp.is3261.eathealthyapp.activities.ActivityFoodDetail


class FragmentFoodListItem : Fragment() {
    var name: String? = null
    var calories: Int? = null
    var protein: Int? = null
    var carbs: Int? = null
    var fat: Int? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_food_list_item, container, false)

        // Set on click listener for this fragment
        view.setOnClickListener {
            val intent = Intent(context, ActivityFoodDetail::class.java)
            startActivity(intent)
        }

        name = arguments?.getString("name")
        calories = arguments?.getInt("calories")
        protein = arguments?.getInt("protein")
        carbs = arguments?.getInt("carbs")
        fat = arguments?.getInt("fat")

        setNutrientInfo(name, calories, protein, carbs, fat)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNutrientInfo(name, calories, protein, carbs, fat)
    }

    fun setNutrientInfo(name: String?, calories: Int?, protein: Int?, carbs: Int?, fat: Int?) {
        val tvName = view?.findViewById<TextView>(R.id.tvFoodItemName)
        val tvCalories = view?.findViewById<TextView>(R.id.tvFoodItemCalories)
        val tvProtein = view?.findViewById<TextView>(R.id.tvFoodItemProtein)
        val tvCarbs = view?.findViewById<TextView>(R.id.tvFoodItemCarbs)
        val tvFat = view?.findViewById<TextView>(R.id.tvFoodItemFat)

        tvName?.text = name
        tvCalories?.text = calories.toString()
        tvProtein?.text = protein.toString()
        tvCarbs?.text = carbs.toString()
        tvFat?.text = fat.toString()
    }

    fun viewFoodDetailScreen(food: Food) {

    }
}