package com.example.trackrecorder.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.View;

import com.example.trackrecorder.R;
import com.example.trackrecorder.databinding.FragmentLoginBinding;


public class LoginFragment extends Fragment {

    LoginFragmentViewModel viewModel;
    FragmentLoginBinding binding;

    public LoginFragment() {
      super(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentLoginBinding.bind(view);

        viewModel = new ViewModelProvider(this).get(LoginFragmentViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setUser(viewModel.user);




    }


}