package com.qt46.easynotebook.notes

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import epicarchitect.calendar.compose.pager.EpicCalendarPager


@Composable
@Preview
fun CalendarUI(){

//
//

    val context = LocalContext.current
    EpicCalendarPager {
        Text(text = it.dayOfMonth.toString(), modifier = Modifier.clickable {
            Toast.makeText(context,it.dayOfMonth.toString(),Toast.LENGTH_SHORT).show()
        })
    }

//
//    VerticalCalendar(
//        modifier = Modifier.fillMaxHeight(.5f),
//        state = state,
//        dayContent = { Day(it) }, monthHeader = {
//            Text(text = it.yearMonth.toString())
//        }
//    )
//    HorizontalCalendar(modifier = Modifier.fillMaxHeight(),
//        state = state,
////        userScrollEnabled = false,
//        dayContent = { Day(it) })
//    AndroidView(modifier = Modifier.fillMaxSize(),factory = {
//
//        CalendarView(it).apply {
//            this.setOnCalendarSelectListener(object :CalendarView.OnCalendarSelectListener{
//                override fun onCalendarOutOfRange(calendar: Calendar?) {
//                    TODO("Not yet implemented")
//                }
//
//                override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
//                   Toast.makeText(it,calendar?.day.toString(),Toast.LENGTH_SHORT).show()
//                }
//
//            })
//
////            this.setMonthView(CustomMonthView::class.java)
//        }
//    }, update = {
//
//    })
}