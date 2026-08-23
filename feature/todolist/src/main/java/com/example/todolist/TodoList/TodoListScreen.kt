package com.example.todolist.TodoList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todolist.R
import com.example.todolist.Util.toDateString
import com.example.todolist.model.TodoState
import com.example.todolist.ui.components.FilterComponent
import com.example.todolist.ui.components.TodoItem
import com.example.todolist.ui.theme.MyAndroidPlaygroundTheme
import com.example.todolist.ui.theme.bg
import com.example.todolist.ui.theme.sub_title_color
import com.example.todolist.ui.theme.title_color
import kotlinx.coroutines.launch
import java.time.LocalDate

@Composable
fun TodoListScreen(
    modifier: Modifier = Modifier,
    viewModel: TodoListViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val localDate = LocalDate.now();
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(innerPadding)
                .background(bg)
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