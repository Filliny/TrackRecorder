package com.example.trackrecorder.ui.history;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.trackrecorder.R;
import com.example.trackrecorder.databinding.FragmentHistoryBinding;

import java.time.DateTimeException;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class HistoryFragment extends Fragment implements DatePickerDialog.OnDateSetListener {


    FragmentHistoryBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    public HistoryFragment() {
        super(R.layout.fragment_history);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        binding = FragmentHistoryBinding.bind(view);

    }


    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        Toast.makeText(getContext(),i+" "+i1+" "+i2,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.history_top_menu,menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Date date = Calendar.getInstance().getTime();

        if(item.getItemId() == R.id.calendarOpen){
            DatePickerDialog dialog = new DatePickerDialog(getContext(),this,date.getYear()+1900,date.getMonth(),date.getDate());
            dialog.show();
            return true;
        }
        return false;
    }
}