package com.finance.android.walletwise.ui.activity.transaction

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import com.finance.android.walletwise.ui.fragment.NormalTextField
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.finance.android.walletwise.R
import com.finance.android.walletwise.model.Category.CategoryUIState
import com.finance.android.walletwise.ui.fragment.NormalButton
import com.finance.android.walletwise.ui.viewmodel.transaction.ExpenseViewModel
import com.finance.android.walletwise.model.Transaction.TransactionUiState
import com.finance.android.walletwise.ui.AppViewModelProvider
import com.finance.android.walletwise.ui.activity.MessageItem
import com.finance.android.walletwise.ui.viewmodel.category.CategoryViewModel
import com.finance.android.walletwise.ui.viewmodel.TextExtractionViewModel


import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


@Composable
fun ScreeneAddExpense(viewModel: ExpenseViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory), navigateBack: () -> Unit){
    AddExpenseSreen(transactionUiState = viewModel.transactionUiState,navigateBack=navigateBack)
}

@Composable
fun AddExpenseSreen(transactionUiState: TransactionUiState,
                    expenseviewModel: ExpenseViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),

                    navigateBack: () -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val coroutineScope = rememberCoroutineScope()

    val tabTitles = listOf("Manual", "Scan", "Message")
    // Thêm biến onCloseClick

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.md_theme_background))
            .imePadding()
    ) {

        // First Column at the top
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter) // Aligns the column at the top center
                .padding(0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            // Row to hold the title and close button
            Row(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Title
                Text(
                    text = "Add Transaction",
                    fontSize = 22.sp,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .padding(end = 16.dp)
                )
                // Close Button
                IconButton(
                    onClick = { coroutineScope.launch {
                        navigateBack()
                    }},
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .size(30.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close Icon"
                    )
                }
            }
            // Tabs
            TabRow(
                modifier = Modifier
                    .background(colorResource(R.color.md_theme_background)),
                contentColor = colorResource(id = R.color.md_theme_onBackground),
                selectedTabIndex = selectedTabIndex,
                containerColor = colorResource(id = R.color.md_theme_background),
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = colorResource(id = R.color.md_theme_primary), // Thay màu ở đây
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        selectedContentColor = colorResource(id = R.color.md_theme_primary),
                        unselectedContentColor = colorResource(id = R.color.md_theme_onSurface),
                        text = { Text(title) }
                    )
                }
            }

            // Content
            when (selectedTabIndex) {
                0 -> TabContent1(transactionUiState = expenseviewModel.transactionUiState,navigateBack=navigateBack, coroutineScope = coroutineScope)
                1 -> TabContent2()
                2 -> TabContent3(transactionUiState = expenseviewModel.transactionUiState,navigateBack=navigateBack, coroutineScope = coroutineScope)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabContent1(transactionUiState: TransactionUiState,
                expenseViewModel: ExpenseViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                navigateBack: () -> Unit,
                coroutineScope: CoroutineScope
) {

    var selectedChipIndex by remember { mutableStateOf(0) }
    val chipTitles = listOf("EXPENSE", "INCOME")

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(0.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            EditableAmountField(
                value = transactionUiState.amount,
                transactionUiState = transactionUiState,
                onValueChange=expenseViewModel::updateUiState

            )
            Row(
                modifier = Modifier
                    .padding(top = 30.dp)
                    .fillMaxWidth()
//                horizontalArrangement = Arrangement.SpaceEvenly

            ) {
                chipTitles.forEachIndexed { index, title ->
                    InputChip(
                        selected =  selectedChipIndex == index,
                        onClick = { selectedChipIndex = index },
//                        border = InputChipDefaults.inputChipBorder(
//                            disabledBorderColor = colorResource(R.color.md_theme_onBackground),
//                            borderWidth = 0.dp
//                        ),
                        colors = InputChipDefaults.inputChipColors(
                            selectedContainerColor = if (selectedChipIndex == index) colorResource(R.color.md_theme_primaryFixed) else colorResource(R.color.md_theme_background),
                            labelColor = colorResource(R.color.md_theme_onBackground)
                        ),
                        label = {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center
                            ) {
                                Text(title,
                                    color = colorResource(R.color.md_theme_onBackground))
                            }
                        },
                        modifier = Modifier
                            .padding(0.dp)
                            .background(
                                shape = RectangleShape,
                                color = if (selectedChipIndex == index) colorResource(R.color.md_theme_primaryFixed) else colorResource(
                                    R.color.md_theme_background
                                )
                            )
                            .size(200.dp, 40.dp),  // Tùy chỉnh kích thước
                    )
                }
            }

            // Content
            when (selectedChipIndex) {

                0 -> InputChipContent1(transactionUiState = expenseViewModel.transactionUiState,navigateBack=navigateBack, coroutineScope = coroutineScope)
                1 -> InputChipContent2(transactionUiState = expenseViewModel.transactionUiState,navigateBack=navigateBack, coroutineScope = coroutineScope)
            }

        }
    }
}

@Composable
fun InputChipContent1(transactionUiState: TransactionUiState,
                      expenseViewModel: ExpenseViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                      categoryViewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                      navigateBack: () -> Unit,
                      coroutineScope: CoroutineScope
) {
    var expanded by remember { mutableStateOf(false) } // Khởi tạo là false để menu không mở ban đầu
    //  val menuItems = listOf("Item 1", "Item 2", "Item 3")
    val selectedItem = remember { mutableStateOf("") }
    var noteExpense by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            CategoryDropdown(transactionUiState = expenseViewModel.transactionUiState, categoryUiState = categoryViewModel.categoryUiState, onValueChange = expenseViewModel::updateUiState)

            datetimepicker(onValueChange=expenseViewModel::updateUiState, transactionUiState = transactionUiState)
            //Note
            DescriptionTextField(transactionUiState=transactionUiState,onValueChange=expenseViewModel::updateUiState)

        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            NormalButton(
                text = "Save",
                onClick = { coroutineScope.launch {
                    expenseViewModel.saveTransactionExpense()
                    navigateBack()
                }},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                backgroundColor = colorResource(R.color.md_theme_primary),
                contentColor = colorResource(R.color.md_theme_onPrimary)
            )
        }
    }
}


@Composable
fun InputChipContent2(
    transactionUiState: TransactionUiState,
    expenseViewModel: ExpenseViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
    categoryViewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    coroutineScope: CoroutineScope)
{
    var expanded by remember { mutableStateOf(false) } // Khởi tạo là false để menu không mở ban đầu
    //  val menuItems = listOf("Item 1", "Item 2", "Item 3")
    val selectedItem = remember { mutableStateOf("") }
    var noteExpense by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.TopCenter)
                .padding(top = 30.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {

            CategoryIncomeDropdown(transactionUiState = expenseViewModel.transactionUiState, onValueChange = expenseViewModel::updateUiState)


            datetimepicker(onValueChange=expenseViewModel::updateUiState, transactionUiState = transactionUiState)
            //Note
            DescriptionTextField(transactionUiState=transactionUiState,onValueChange=expenseViewModel::updateUiState)

        }
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            NormalButton(
                text = "Save",
                onClick = { coroutineScope.launch {
                    expenseViewModel.saveTransactionIncome()
                    navigateBack()
                }},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                backgroundColor = colorResource(R.color.md_theme_primary),
                contentColor = colorResource(R.color.md_theme_onPrimary)
            )
        }
    }
}


@Composable
fun TabContent2() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Spacer(modifier = Modifier.height(100.dp))

            Text(
                text = "Record your transaction easily\n" +
                        "with the receipt scanning feature",
                fontSize = 20.sp,
                modifier = Modifier.padding(bottom = 16.dp))
        }
        //Button
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            NormalButton(
                text = "Save",
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            )
        }
    }
}

@Composable
fun TabContent3(
    transactionUiState: TransactionUiState,
    expenseViewModel: ExpenseViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory),
    categoryViewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory),
    navigateBack: () -> Unit,
    coroutineScope: CoroutineScope
) {
    val viewModel: TextExtractionViewModel = viewModel()
    var input by remember { mutableStateOf(TextFieldValue("")) }
    val messages by viewModel.messages.observeAsState(listOf())

    Box(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .imePadding()
        ) {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                reverseLayout = true,
            ) {
                items(messages.reversed()) { message ->
                    MessageItem(message)
                }
            }

            Row(
                modifier = Modifier.padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    label = { org.commonmark.node.Text("Message Chatbot") },
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(onSend = {
                        if (input.text.isNotEmpty()) {
                            viewModel.extractText(input.text)
                            input = TextFieldValue("")
                        }
                    })
                )
                IconButton(onClick = {
                    if (input.text.isNotEmpty()) {
                        viewModel.extractText(input.text)
                        input = TextFieldValue("")
                        coroutineScope.launch {
                            delay(10000)
                            Log.d("TransactionInfo", "Amount extracted: ${viewModel.amountExtracted}")
                            Log.d("TransactionInfo", "Category extracted: ${viewModel.categoryExtracted}")
                            Log.d("TransactionInfo", "Date extracted: ${viewModel.dateExtracted}")
                            Log.d("TransactionInfo", "Note extracted: ${viewModel.noteExtracted}")

                            if (viewModel.amountExtracted.isNotEmpty() &&
                                viewModel.categoryExtracted.isNotEmpty() &&
                                viewModel.dateExtracted != null &&
                                viewModel.noteExtracted.isNotEmpty()) {

                                val categoryId = categoryViewModel.findCategoryIdByName(viewModel.categoryExtracted)
                                if (categoryId != null && categoryId > 0) {
                                    val updatedTransactionUiState = transactionUiState.copy(
                                        amount = viewModel.amountExtracted,
                                        idCategory = categoryId,
                                        date = viewModel.dateExtracted,
                                        description = viewModel.noteExtracted,
                                        time = LocalTime.now()
                                    )

                                    expenseViewModel.updateUiState(updatedTransactionUiState)
                                    Log.d("test transactionUiState", "update transactionUiState $updatedTransactionUiState")

                                    coroutineScope.launch {
                                        expenseViewModel.saveTransactionExpense()
                                        Log.d("SaveTransaction", "Transaction saved successfully")
                                        navigateBack()
                                        Log.d("Navigation", "Navigated back successfully")
                                    }
                                } else {
                                    Log.e("TransactionError", "Invalid category ID: $categoryId")
                                }
                            } else {
                                Log.e("TransactionInfo", "Extraction values are not valid or empty")
                            }
                        }
                    }
                }) {
                    Icon(
                        Icons.Default.Send,
                        contentDescription = "Send",
                        tint = colorResource(R.color.md_theme_primary)
                    )
                }
            }
        }
    }
}
@Composable
fun EditableAmountField(
    value: String,
    transactionUiState: TransactionUiState,
    onValueChange: (TransactionUiState) -> Unit,
//    placeholder:String
) {

    NormalTextField(
        value = value,
        onValueChange = {onValueChange(transactionUiState.copy(amount = it))  },
        label = "Amount",
        modifier = Modifier
            .padding(top = 14.dp)
            .fillMaxWidth(),
        singleLine = true
    )
}

val categoryOptions = listOf("INCOME","Shopping", "Food", "Entertainment", "Others")


@Composable
fun CategoryDropdown(
    transactionUiState: TransactionUiState,
    categoryUiState: CategoryUIState,
    categoryViewModel: CategoryViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory = AppViewModelProvider.Factory),
    onValueChange: (TransactionUiState) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        categoryViewModel.getAllCategories()
    }

    val categoryListState by categoryViewModel.expenseCategories.collectAsState()
    val selectedCategoryName = categoryListState.find { it.id == transactionUiState.idCategory }?.name ?: "Select a Category"

    Column {
        OutlinedTextField(
            value = selectedCategoryName,
            onValueChange = {},
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable { expanded = !expanded},
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expand category dropdown",
                    tint = Color(0xFF91919F)
                )
            },
            singleLine = true,
            readOnly = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth()
                .background(colorResource(R.color.md_theme_background))
        ) {
            categoryListState.forEach { category ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(transactionUiState.copy(idCategory = category.id))
                        expanded = false
                    },
                    text = {
                        Text(text = category.name, color = colorResource(R.color.md_theme_onBackground))
                    }
                )
            }
        }
    }
}

@Composable
fun DescriptionTextField(transactionUiState: TransactionUiState,
                         onValueChange: (TransactionUiState) -> Unit = {}) {
    NormalTextField(
        value = transactionUiState.description,
        onValueChange = { onValueChange(transactionUiState.copy(description=it))},
        label = "Description",
        modifier = Modifier
            .padding(top = 30.dp)
            .fillMaxWidth()
    )
}


@Composable
fun datetimepicker(transactionUiState: TransactionUiState, onValueChange: (TransactionUiState) -> Unit) {
    var pickedDate by remember { mutableStateOf(transactionUiState.date) }
    var pickedTime by remember { mutableStateOf(transactionUiState.time) }

    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("MMM dd yyyy").format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter.ofPattern("hh:mm a").format(pickedTime)
        }
    }

    val context = LocalContext.current
    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            val newDate = LocalDate.of(year, month + 1, dayOfMonth)
            pickedDate = newDate
            onValueChange(transactionUiState.copy(date = newDate))
        },
        pickedDate.year,
        pickedDate.monthValue - 1,
        pickedDate.dayOfMonth
    )

    val timePickerDialog = TimePickerDialog(
        context,
        { _, hourOfDay, minute ->
            val newTime = LocalTime.of(hourOfDay, minute)
            pickedTime = newTime
            onValueChange(transactionUiState.copy(time = newTime))
        },
        pickedTime.hour,
        pickedTime.minute,
        false // false for 12 hour time, true for 24 hour time
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 30.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF438CED),
                contentColor = colorResource(R.color.md_theme_onPrimary)
            ),
            shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp, bottomEnd = 5.dp, bottomStart = 5.dp),
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(0.dp)),
            onClick = { datePickerDialog.show() }
        ) {
            Text(text = formattedDate)
        }
        Spacer(modifier = Modifier.width(10.dp))
        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFF438CED),
                contentColor = colorResource(R.color.md_theme_onPrimary)
            ),
            shape = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp, bottomEnd = 5.dp, bottomStart = 5.dp),
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(0.dp)),
            onClick = { timePickerDialog.show() }
        ) {
            Text(text = formattedTime)
        }
    }
}

@Composable
fun CategoryIncomeDropdown(
    transactionUiState: TransactionUiState,
    onValueChange: (TransactionUiState) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val categoryList = listOf("INCOME")
    val selectedCategoryName = if (transactionUiState.idCategory != null) "INCOME" else "Select a Category"

    Column {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .clip(RoundedCornerShape(10.dp))
//                .background(Color.White)
//                .border(
//                    width = 1.5.dp,
//                    color = Color(0xFFF1F1FA),
//                    shape = RoundedCornerShape(10.dp)
//                )
//        ) {
//            Text(
//                text = selectedCategoryName,
//                modifier = Modifier
//                    .padding(12.dp)
//                    .fillMaxWidth(),
//                color = Color(0xFF91919F)
//            )
//            IconButton(
//                onClick = { expanded = !expanded },
//                modifier = Modifier.align(Alignment.CenterEnd)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.ArrowDropDown,
//                    contentDescription = "Expand category dropdown",
//                    tint = Color(0xFF91919F)
//                )
//            }
        OutlinedTextField(
            value = selectedCategoryName,
            onValueChange = {},
            shape = RoundedCornerShape(5.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            trailingIcon = {
                Icon(
                    modifier = Modifier
                        .clickable { expanded = !expanded},
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expand category dropdown",
                    tint = Color(0xFF91919F)
                )
            },
            singleLine = true,
            readOnly = true
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .background(Color(0xFFFCFCFC))
        ) {
            categoryList.forEach { category ->
                DropdownMenuItem(
                    onClick = {
                        onValueChange(transactionUiState.copy(idCategory = 1)) // Assuming "INCOME" has id 1
                        expanded = false
                    },
                    text = {
                        Text(text = category, color = Color.Black)
                    }
                )
            }
        }
    }
}