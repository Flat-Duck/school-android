package ly.smarthive.school.activity;

import static ly.smarthive.school.Util.milliseconds;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.applikeysolutions.cosmocalendar.selection.MultipleSelectionManager;
import com.applikeysolutions.cosmocalendar.settings.lists.DisabledDaysCriteria;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import ly.smarthive.school.R;
//
//import sun.bob.mcalendarview.MCalendarView;
//import sun.bob.mcalendarview.vo.DateData;

public class TimeTableActivity extends AppCompatActivity {

   CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_table);

     //   calendarView = findViewById(R.id.calendarView);
//        calendarView.setOnClickListener(view -> {
//
//        });

//        MultipleSelectionManager ms = new MultipleSelectionManager(CalendarView
//
//                );

//        calendarView.setDisabledDayTextColor(Color.RED);
//     //   calendarView.setDisabledDaysCriteria();
//        Set<Long> disabledDaysSet = new HashSet<>();
//      //  disabledDaysSet.add(System.currentTimeMillis());
//
//        disabledDaysSet.add(milliseconds("2022-11-30T00:00:00.000000Z"));
//        disabledDaysSet.add(milliseconds("2022-12-01T00:00:00.000000Z"));
//        disabledDaysSet.add(milliseconds("2022-12-02T00:00:00.000000Z"));
//        disabledDaysSet.add(milliseconds("2022-11-01T00:00:00.000000Z"));
//        disabledDaysSet.add(milliseconds("2022-12-05T00:00:00.000000Z"));
//        disabledDaysSet.add(milliseconds("2022-12-061T00:00:00.000000Z"));
//        disabledDaysSet.add(milliseconds("2022-12-07T00:00:00.000000Z"));
//        disabledDaysSet.add(milliseconds("2022-12-08T00:00:00.000000Z"));
//        disabledDaysSet.add(milliseconds("2022-12-09T00:00:00.000000Z"));
//        //disabledDaysSet.add(x);
//        calendarView.setDisabledDays(disabledDaysSet);


        //calendarView.setSelectionManager(new MultipleSelectionManager(CalendarView.));
        //calendarView.Ev

  //      calendarView = findViewById(R.id.calendarView);
//
//        ArrayList<DateData> dates=new ArrayList<>();
//        dates.add(new DateData(2022,11,21));
//        dates.add(new DateData(2022,11,27));

        //for(int i=0;i<dates.size();i++) {
            //mark multiple dates with this code.
       //     calendarView.markDate(dates.get(i).getYear(),dates.get(i).getMonth(),dates.get(i).getDay());
        //}


    //    Log.d("marked dates:-","" + calendarView.getMarkedDates()); //get all marked dates.

    }
}