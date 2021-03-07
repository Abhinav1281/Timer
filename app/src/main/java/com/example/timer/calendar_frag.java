package com.example.timer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

public class calendar_frag extends Fragment {
    DatePicker datePicker;
    EditText expect;
    Button submit;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_birth_day
        ,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        datePicker=getView().findViewById(R.id.BDate);
        expect=getView().findViewById(R.id.expectancy);
        submit=getView().findViewById(R.id.submit);



        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String life=expect.getText().toString();
                if(life.equals(""))
                {
                    Toast.makeText(getContext(),"Life Expectancy cannot be empty",Toast.LENGTH_SHORT)
                            .show();
                }
                else {
                    int lI = Integer.parseInt(life);
                    SharedPreferences sharedPreferences =getActivity().getSharedPreferences("BirthDatePref", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("life", lI);
                    editor.putInt("Year", datePicker.getYear());
                    editor.putInt("Month", datePicker.getMonth());
                    editor.putInt("Date", datePicker.getDayOfMonth());
                    editor.commit();
                    //editor.apply();
                    getActivity().getSupportFragmentManager().beginTransaction().replace(
                            R.id.Fragment_container,new main_frag()).commit();
                    
                }

            }
        });
    }
}
