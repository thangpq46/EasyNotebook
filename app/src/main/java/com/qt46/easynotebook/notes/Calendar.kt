package com.qt46.easynotebook.notes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.qt46.easynotebook.R
import com.qt46.easynotebook.data.Note
import com.qt46.easynotebook.data.NoteCategory
import com.qt46.easynotebook.data.Screen
import com.qt46.easynotebook.data.local.relations.NoteWithNoteCategory
import com.qt46.easynotebook.data.local.relations.NoteWithNoteItem
import epicarchitect.calendar.compose.basis.EpicMonth
import epicarchitect.calendar.compose.basis.config.BasisEpicCalendarConfig
import epicarchitect.calendar.compose.basis.config.ImmutableBasisEpicCalendarConfig
import epicarchitect.calendar.compose.basis.daysOfWeekSortedBy
import epicarchitect.calendar.compose.basis.firstDayOfWeek
import epicarchitect.calendar.compose.pager.EpicCalendarPager
import epicarchitect.calendar.compose.pager.config.EpicCalendarPagerConfig
import epicarchitect.calendar.compose.pager.state.rememberEpicCalendarPagerState
import kotlinx.coroutines.launch
import java.time.LocalDate


@OptIn(ExperimentalMaterial3Api::class)
@Composable

fun CalendarUI(viewModel: NotesViewModel= androidx.lifecycle.viewmodel.compose.viewModel(),selectTypeText: () -> Unit,selectTypeCheckList: () -> Unit,onNoteClick:(NoteWithNoteItem)->Unit) {
    var selectedDay by remember {
        mutableStateOf(LocalDate.now().toString())
    }
    val noteGroupByDate by viewModel.noteGroupByDate.collectAsState()
    val state = rememberEpicCalendarPagerState(
        config = object : EpicCalendarPagerConfig {
            override val basisConfig: BasisEpicCalendarConfig
                get() = ImmutableBasisEpicCalendarConfig(
                    rowsSpacerHeight = 4.dp,
                    dayOfWeekViewHeight = 50.dp,
                    dayOfMonthViewHeight = 50.dp,
                    columnWidth = 50.dp,
                    dayOfWeekShape = RoundedCornerShape(0.dp),
                    dayOfMonthShape = RoundedCornerShape(0.dp),
                    contentPadding = PaddingValues(0.dp),
                    contentColor = Color.Unspecified,
                    displayDaysOfAdjacentMonths = true,
                    displayDaysOfWeek = true,
                    daysOfWeek = daysOfWeekSortedBy(firstDayOfWeek())
                )

        }
    )
    var showDialogSelectNoteType by remember {
        mutableStateOf(false)
    }
    if (showDialogSelectNoteType) {
        DialogSelectNoteType(onClickFirstBtn = {
            selectTypeText()
            viewModel.setCalendarDate(selectedDay)
            showDialogSelectNoteType = false
        }, {
            selectTypeCheckList()
            showDialogSelectNoteType = false
        }, {
            showDialogSelectNoteType = false
        })
    }
    val currentDay = LocalDate.now()
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier) {
        TopAppBar(title = { }, navigationIcon = {

            Text(
                text = "${state.currentMonth.month} ${state.currentMonth.year}",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }, actions = {

            IconButton(onClick = {
                coroutineScope.launch {
                    state.scrollToMonth(
                        EpicMonth(currentDay.year, currentDay.month)
                    )
                }

            }) {
                Icon(painterResource(id = R.drawable.ic_remider_time), null)
            }
        }

        )
        HorizontalDivider()
        EpicCalendarPager(state = state) { date ->
            OutlinedButton(
                onClick = {
                    selectedDay = date.toString()
                },
                modifier = Modifier.fillMaxSize(),
                shape = RoundedCornerShape(2.dp),
                contentPadding = PaddingValues(0.dp),
                border = if (selectedDay == date.toString()) BorderStroke(
                    2.dp,
                    MaterialTheme.colorScheme.primary
                ) else BorderStroke(1.dp, MaterialTheme.colorScheme.primaryContainer)
            ) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Bottom
                    ) {
                        for (item in noteGroupByDate[date.toString()].orEmpty()) {
                            HorizontalDivider(thickness = 5.dp, color = Color(item.noteCategory.color))
                            Spacer(modifier = Modifier.height(1.dp))
                        }

                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = date.dayOfMonth.toString())
                    }
                }

            }

        }
        Spacer(modifier = Modifier.height(4.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(4.dp))
        Row {
            Spacer(modifier = Modifier.width(14.dp))
            Text(
                text = getDateByText(selectedDay),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.SemiBold
            )
        }

        LazyColumn(Modifier.padding(horizontal = 9.dp)) {
            noteGroupByDate[selectedDay]?.let {
                items(items = it) {item->
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.fillMaxSize().clickable {

                        onNoteClick(item)
                    }) {
                        Checkbox(checked = item.note.maskAsComplete, onCheckedChange = {
                            viewModel.updateNoteMask(item,it)
                        })
                        Text(text = item.note.heading, fontWeight = FontWeight.SemiBold,textDecoration = if (item.note.maskAsComplete) TextDecoration.LineThrough else null)
                    }
                }
            }

        }
    }
    Column(
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Bottom,
        modifier = Modifier.padding(16.dp)
    ) {
        FloatingActionButton(
            onClick = { showDialogSelectNoteType = true }, shape = CircleShape
        ) {
            Icon(Icons.Filled.Add, "Extended floating action button.")
        }
    }
}

fun getDateByText(text:String):String{
    val date = LocalDate.parse(text)
    return date.dayOfMonth.toString()+ " "+ date.month.name
}