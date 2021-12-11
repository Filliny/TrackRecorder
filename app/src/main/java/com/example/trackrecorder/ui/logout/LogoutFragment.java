package com.example.trackrecorder.ui.logout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.trackrecorder.R;
import com.example.trackrecorder.databinding.FragmentLogoutBinding;
import com.example.trackrecorder.helpers.OnLogoutConfirm;
import com.example.trackrecorder.ui.MainActivityViewModel;


public class LogoutFragment extends Fragment implements OnLogoutConfirm {

    FragmentLogoutBinding binding;
    MainActivityViewModel mainViewModel;

    public LogoutFragment() {
        super(R.layout.fragment_logout);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_logout, container, false);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding = FragmentLogoutBinding.bind(view);

        new LogoutDialogFragment(this).show(getChildFragmentManager(), null);

        mainViewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);

    }

    @Override
    public void Confirm() {
        mainViewModel.logoutUser();
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_logoutFragment_to_nav_login);

    }

    @Override
    public void Cancel() {
        Navigation.findNavController(binding.getRoot()).navigate(R.id.action_logoutFragment_to_nav_home);
    }
}