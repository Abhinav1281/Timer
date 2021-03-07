package com.example.timer;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class main_frag extends Fragment {

    TextView yearPercent,monthPercent,weekPercent,lifePercent,dailyPercent
            ,Bdate,yleft,mleft,wleft,hleft,lleft;
    ConstraintLayout main;
    ProgressBar year,month,week,life,daily;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main
        ,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        yearPercent=getView().findViewById(R.id.yearPercent);
        year=getView().findViewById(R.id.yearBar);
        monthPercent=getView().findViewById(R.id.monthPercent);
        month=getView().findViewById(R.id.monthBar);
        weekPercent=getView().findViewById(R.id.weekPercent);
        week=getView().findViewById(R.id.weekBar);
        lifePercent=getView().findViewById(R.id.lifePercent);
        life=getView().findViewById(R.id.lifeBar);
        Bdate=getView().findViewById(R.id.dateB);
        dailyPercent=getView().findViewById(R.id.hourPercent);
        daily=getView().findViewById(R.id.hourBar);
        yleft=getView().findViewById(R.id.yearDay);
        mleft=getView().findViewById(R.id.monthDay);
        wleft=getView().findViewById(R.id.weekDay);
        hleft=getView().findViewById(R.id.hourDay);
        lleft=getView().findViewById(R.id.lifeDay);
        main=getView().findViewById(R.id.MainActLayout);



        SharedPreferences sharedPreferences=this.getActivity().getSharedPreferences("BirthDatePref", Context.MODE_PRIVATE);
        if(sharedPreferences.contains("Year")!=true)
        {
            getActivity().getSupportFragmentManager().beginTransaction().replace(
                    R.id.Fragment_container,new calendar_frag()
            ).commit();
        }
        Calendar calendar=Calendar.getInstance();

        //yearLeft
        int day=calendar.get(Calendar.DAY_OF_YEAR);
        double Percent=day*10000.0/365.0;
        String result = String.format("%.2f", day*100.0/365.0);
        year.setMax(10000);
        yearPercent.setText(result+"%");
        year.setProgress((int)Percent);
        yleft.setText((366-day)+" days left in this year");

        //monthLeft
        day=calendar.get(Calendar.DAY_OF_MONTH);
        int monthNum=calendar.get(Calendar.MONTH)+1;
        switch (monthNum)
        {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                Percent=day*10000.0/31.0;
                mleft.setText((31-day)+" days left in this month");
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                Percent=day*10000.0/30.0;
                mleft.setText((30-day)+" days left in this month");
                break;
            case 2:
                Percent=day*10000.0/28.0;
                mleft.setText((28-day)+" days left in this month");
                break;
            default:
                Log.e("monthError","month format wrong");
        }
        result = String.format("%.2f", Percent/100.0);
        month.setMax(10000);
        monthPercent.setText(result+"%");
        month.setProgress((int)Percent);


        //weekLeft
        day=calendar.get(Calendar.DAY_OF_WEEK);
        Percent=day*10000.0/7.0;
        result = String.format("%.2f", Percent/100.0);
        week.setMax(10000);
        weekPercent.setText(result+"%");
        week.setProgress((int)Percent);
        wleft.setText((7-day)+" days left in this week");

        //Birth
        int year=sharedPreferences.getInt("Year",2000);
        int LI=sharedPreferences.getInt("life",0);
        int finalyear=year+LI;

        int month=sharedPreferences.getInt("Month",2000);
        int date=sharedPreferences.getInt("Date",2000);

        String CurrentDate= calendar.get(Calendar.DATE)+"/"
                +calendar.get(Calendar.MONTH)+"/"
                +calendar.get(Calendar.YEAR);
        String DateOfD= date+"/"+month+"/"+finalyear;
        String DateOfB= date+"/"+month+"/"+year;
        Date dod = null;
        Date dob = null;
        Date current = null;
        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
        try {
            dod = dates.parse(DateOfD);
            dob = dates.parse(DateOfB);
            current= dates.parse(CurrentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long difference = Math.abs(dob.getTime() - current.getTime());
        long differenceDates = difference / (24 * 60 * 60 * 1000);
        long differenceMax = Math.abs(dob.getTime() - dod.getTime());
        long differenceDateMax = differenceMax / (24 * 60 * 60 * 1000);

        lleft.setText(differenceDateMax-differenceDates+" days of Lifetime left");

        double lresult=(differenceDates*100.0/differenceDateMax);
        life.setProgress((int) lresult);
        result = String.format("%.2f", lresult);
        lifePercent.setText(result+"%");

        Bdate.setText(date+"/"+(month+1)+"/"+year);

        //hours
        day=calendar.get(Calendar.HOUR_OF_DAY);
        int time=calendar.get(Calendar.MINUTE);
        Percent=((day*60.0)+time)*10000.0/(24.0*60.0);
        result = String.format("%.2f", Percent/100.0);
        daily.setMax(10000);
        dailyPercent.setText(result+"%");
        daily.setProgress((int)Percent);
        hleft.setText((23-day)+" hours "+(60-time)+" minutes left today");


    }
}
