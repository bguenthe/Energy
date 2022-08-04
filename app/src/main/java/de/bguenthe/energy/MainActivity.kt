package de.bguenthe.energy

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.DatePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import de.bguenthe.energy.ui.theme.EnergyTheme
import java.util.*

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnergyTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Column {
                        TopOfScreen()
                        DetailsList()
                    }
                }
            }
        }
    }
}

@Composable
fun TopOfScreen() {
    var value by rememberSaveable { mutableStateOf("") }
    var comment by rememberSaveable { mutableStateOf("") }

    InputSection(value, onValueChange = { value = it }, comment, onBezeichnungChange = { comment = it })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputSection(
    value: String, onValueChange: (String) -> Unit, bezeichnung: String, onBezeichnungChange: (String) -> Unit
) {
    Row(modifier = Modifier.padding(10.dp)) {
        // Fetching the Local Context
        val mContext = LocalContext.current

        // Declaring integer values
        // for year, month and day
        val mYear: Int
        val mMonth: Int
        val mDay: Int

        // Initializing a Calendar
        val mCalendar = Calendar.getInstance()

        // Fetching current year, month and day
        mYear = mCalendar.get(Calendar.YEAR)
        mMonth = mCalendar.get(Calendar.MONTH)
        mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

        mCalendar.time = Date()

        // Declaring a string value to
        // store date in string format
        val mDate = remember { mutableStateOf("") }

        // Declaring DatePickerDialog and setting
        // initial values as current values (present year, month and day)
        val mDatePickerDialog = DatePickerDialog(
            mContext,
            { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
                mDate.value = "$mDayOfMonth/${mMonth + 1}/$mYear"
            }, mYear, mMonth, mDay
        )

        // Creating a button that on
        // click displays/shows the DatePickerDialog
        Button(onClick = {
            mDatePickerDialog.show()
        }, colors = ButtonDefaults.buttonColors(containerColor = Color(0XFF0F9D58))) {
            Text(text = "Select Date", color = Color.White)
        }
        OutlinedTextField(
            value = bezeichnung,
            singleLine = true,
            onValueChange = onBezeichnungChange,
            label = { Text("Value") })
    }
}

@Composable
fun DetailsList() {
    LazyColumn(modifier = Modifier.padding(vertical = 2.dp)) {
        val list = listOf("1", "2")
        items(items = list) { cost ->
            Surface(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 1.dp)
            ) {
                Row(modifier = Modifier.padding(2.dp)) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            fontSize = 16.sp,
                            text = "Date: "
                        )
                    }
                    OutlinedButton(modifier = Modifier.padding(0.dp),
                        onClick = {
                        }
                    ) {
                        Text(fontSize = 16.sp, text = "D")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    EnergyTheme {
        Column {
            TopOfScreen()
            DetailsList()
        }
    }
}