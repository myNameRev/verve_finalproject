package com.example.verv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText

class ProfileFragment : Fragment() {

    private var isEditMode = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        val etName = view.findViewById<TextInputEditText>(R.id.et_profile_name)
        val etEmail = view.findViewById<TextInputEditText>(R.id.et_profile_email)
        val etAddress = view.findViewById<TextInputEditText>(R.id.et_profile_address)
        val etPhone = view.findViewById<TextInputEditText>(R.id.et_profile_phone)
        val etPassword = view.findViewById<TextInputEditText>(R.id.et_profile_password)
        val btnEdit = view.findViewById<Button>(R.id.btn_edit_profile)


        val currentUser = UserRepository.getUser(requireContext())
        currentUser?.let {
            etName.setText(it.name)
            etEmail.setText(it.email)
            etAddress.setText(it.location)
            etPhone.setText(it.phone)
            etPassword.setText(it.password)
        }

        btnEdit.setOnClickListener {
            if (!isEditMode) {
                isEditMode = true
                enableFields(listOf(etName, etEmail, etAddress, etPhone, etPassword), true)
                btnEdit.text = "Save Profile"
                btnEdit.setBackgroundColor(resources.getColor(R.color.verve_blue_dark))
            } else {
                val updatedUser = User(
                    name = etName.text.toString(),
                    email = etEmail.text.toString(),
                    location = etAddress.text.toString(),
                    phone = etPhone.text.toString(),
                    password = etPassword.text.toString()
                )

                UserRepository.saveUser(requireContext(), updatedUser)
                
                isEditMode = false
                enableFields(listOf(etName, etEmail, etAddress, etPhone, etPassword), false)
                btnEdit.text = "Edit Profile"
                btnEdit.setBackgroundColor(resources.getColor(R.color.verve_purple))
                
                Toast.makeText(context, "Profile updated successfully!", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun enableFields(fields: List<TextInputEditText>, isEnabled: Boolean) {
        for (field in fields) {
            field.isEnabled = isEnabled
        }
    }
}