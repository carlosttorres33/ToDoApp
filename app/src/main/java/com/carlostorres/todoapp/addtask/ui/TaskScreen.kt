package com.carlostorres.todoapp.addtask.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.carlostorres.todoapp.addtask.ui.model.TaskModdel

@Composable
fun TaskScreen(taskViewModel: TaskViewModel) {

    val showDialog: Boolean by taskViewModel.showDialog.observeAsState(initial = false)
    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<TasksUIState>(
        initialValue = TasksUIState.Loading,
        key1 = lifecycle,
        key2 = taskViewModel
    ){
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED){
            taskViewModel.uiState.collect{value = it}
        }
    }

    when(uiState){
        is TasksUIState.Error -> {

        }
        TasksUIState.Loading -> {
            CircularProgressIndicator()
        }
        is TasksUIState.Success -> {
            Box(modifier = Modifier.fillMaxSize()) {
                AddTaskDialog(
                    show = showDialog,
                    onDismiss = { taskViewModel.dialogClose() },
                    onTaskAdded = { taskViewModel.onTaskCreated(it) }
                )
                FabDialog(Modifier.align(Alignment.BottomEnd), taskViewModel)
                TasksList((uiState as TasksUIState.Success).tasks, taskViewModel)
            }
        }
    }

}

@Composable
fun TasksList(tasks: List<TaskModdel>, taskViewModel: TaskViewModel) {

    LazyColumn {

        items(tasks, key = { it.id }) { task ->
            ItemTask(
                taskModel = task,
                taskViewModel = taskViewModel
            )
        }

    }
}

@Composable
fun ItemTask(taskModel: TaskModdel, taskViewModel: TaskViewModel) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onLongPress = {
                        taskViewModel.onItemRemove(taskModel)
                    }
                )
            },
        elevation = CardDefaults.cardElevation(8.dp)
    ) {

        Row(
            Modifier
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            if (taskModel.selected){
                Text(
                    text = taskModel.task,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    textDecoration = TextDecoration.LineThrough
                )
            }else{
                Text(
                    text = taskModel.task,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                )

            }
            Checkbox(
                checked = taskModel.selected,
                onCheckedChange = {
                    taskViewModel.onCheckBoxSelected(taskModel)
                }
            )
        }

    }
}

@Composable
fun FabDialog(modifier: Modifier, taskViewModel: TaskViewModel) {
    FloatingActionButton(
        onClick = {
            taskViewModel.onShowDialogClick()
        },
        modifier = modifier.padding(16.dp)
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Add")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskDialog(show: Boolean, onDismiss: () -> Unit, onTaskAdded: (String) -> Unit) {

    var myTask by remember { mutableStateOf("") }


    if (show) {
        Dialog(
            onDismissRequest = { onDismiss() }
        ) {

            Column(
                Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = "Añade tu tarea",
                    fontSize = 18.sp,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally),
                    fontWeight = FontWeight.Bold
                )
                Spacer(
                    modifier = Modifier.size(16.dp)
                )
                TextField(
                    value = myTask,
                    onValueChange = {
                        myTask = it
                    },
                    maxLines = 1
                )
                Spacer(
                    modifier = Modifier.size(16.dp)
                )

                Button(
                    enabled = myTask.isNotEmpty(),
                    onClick = {
                        onTaskAdded(myTask)
                        myTask = ""
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Añadir Trea")
                }
            }

        }
    }

}

