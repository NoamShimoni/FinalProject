package com.finalProject.plateful.features.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.finalProject.plateful.databinding.FragmentEditProfileBinding
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textfield.TextInputEditText
import com.squareup.picasso.Picasso

class EditProfileFragment : Fragment() {
    private var binding: FragmentEditProfileBinding? = null

    private var usernameTextInput: TextInputEditText? = null
    private var avatarImageView: ShapeableImageView? = null
    var username: String? = null
    var avatarUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            username = it.getString(USERNAME)
            avatarUrl = it.getString(AVATAR_URL)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        usernameTextInput = binding?.usernameTextInput
        usernameTextInput?.setText(username)

        avatarImageView = binding?.avatarImageView
        if (!avatarUrl.isNullOrEmpty()) {
            avatarImageView?.imageTintList = null
            avatarImageView?.layoutParams?.width = ViewGroup.LayoutParams.MATCH_PARENT
            avatarImageView?.layoutParams?.height = ViewGroup.LayoutParams.MATCH_PARENT
            avatarImageView?.scaleType = android.widget.ImageView.ScaleType.CENTER_CROP

            Picasso.get().load(avatarUrl).into(avatarImageView)
        }

        return binding?.root
    }

    companion object {
        private const val USERNAME: String = "USERNAME_KEY"
        private const val AVATAR_URL: String = "AVATAR_URL_KEY"
    }
}