package com.example.todolist.TodoList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.todolist.R
import com.example.todolist.Util.toDateString
import com.example.todolist.data.local.TodoDatabase
import com.example.todolist.data.local.TodoRepository
import com.example.todolist.model.Todo
import com.example.todolist.model.TodoState
import com.example.todolist.ui.components.CollapsedPeekContent
import com.example.todolist.ui.components.FilterComponent
import com.example.todolist.ui.components.TodoAddBottomSheet
import com.example.todolist.ui.components.TodoItem
import com.example.todolist.ui.theme.MyAndroidPlaygroundTheme
import com.example.todolist.ui.theme.bg
import com.example.todolist.ui.theme.bottomSheet_bg
import com.example.todolist.ui.theme.sub_title_color
import com.example.todolist.ui.theme.title_color
import kotlinx.coroutines.launch
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current
    val repository = remember {
        val db = Room.databaseBuilder(context.applicationContext, TodoDatabase::class.java, "todo.db").build()
        TodoRepository(db.todoDao())
    }
    val viewModel: TodoListViewModel = viewModel(factory = TodoListViewModelFactory(repository))

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val localDate = LocalDate.now();
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val scaffoldState = rememberBottomSheetScaffoldState()

    var editingTodo by remember { mutableStateOf<Todo?>(null) }

    BottomSheetScaffold (
        snackbarHost = { SnackbarHost(snackbarHostState) },
        scaffoldState = scaffoldState,
        sheetContainerColor = bottomSheet_bg,
        sheetPeekHeight = 80.dp,    //collapsed시 높이
        sheetDragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(40.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(Color.White)
            )
        },
        sheetContent = {

            CollapsedPeekContent()
            TodoAddBottomSheet(
                editingTodo = editingTodo,
                onSubmit = { title, dueDateTime, priority ->
                    editingTodo?.let { viewModel.updateTodo(it.id, title, dueDateTime, priority) }
                        ?: viewModel.addTodo(title, dueDateTime, priority)
                    editingTodo = null
                    scope.launch { scaffoldState.bottomSheetState.partialExpand() }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .background(bg)
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
        ) {

            Column(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                Text(
                    text = localDate.toDateString(),
                    color = sub_title_color.copy(alpha = 0.6f),
                    )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = stringResource(R.string.todolist_title),
                    color = title_color,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    modifier = modifier.padding(horizontal = 0.dp , vertical = 24.dp)
                ) {
                    TodoState.entries.forEach { filter ->

                        val isSelected = filter == uiState.selectedFilter

                        FilterComponent(
                            filter.label,
                            isSelected,
                            {viewModel.onFilterSelected(filter)}
                        )
                        Spacer(modifier = modifier.width(16.dp))
                    }
                }

                LazyColumn(modifier = modifier) {
                    items(
                        items = uiState.filteredTodos , key = {it.id}
                    ){ todo ->
                        TodoItem(
                            title = todo.title,
                            priority = todo.priority,
                            dueDateTime = todo.dueDateTime,
                            isDone = todo.isDone,
                            onCheckedChange = {isDone -> viewModel.onTodoCheckedChange(todo.id, isDone)},
                            onDelete = {
                                viewModel.onDelete(todo.id)
                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message = "'${todo.title}' 삭제됨",
                                        actionLabel = "실행취소"
                                    )
                                    if (result == SnackbarResult.ActionPerformed) {
                                        viewModel.undoDelete()
                                    }
                                }
                            },
                            onClick = {
                                editingTodo = todo
                                scope.launch { scaffoldState.bottomSheetState.expand() }
                            }
                        )
                    }
                }
            }

        }
    }

}

@Preview(name = "Phone", device = "spec:width=360dp,height=800dp")
@Preview(name = "Phone_flip", device = "spec:width=360dp,height=880dp")
@Preview(name = "Tablet", device = "spec:width=1280dp,height=800dp")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
@Composable
fun TodoListScreenPreview() {

    MyAndroidPlaygroundTheme() {
        TodoListScreen()
    }
}