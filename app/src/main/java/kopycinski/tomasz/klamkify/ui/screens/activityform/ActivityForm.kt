package kopycinski.tomasz.klamkify.ui.screens.activityform

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kopycinski.tomasz.klamkify.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityForm(
    navigateBack: () -> Unit,
    onAddCategory: () -> Unit,
    viewModel: ActivityFormViewModel = hiltViewModel()
) {
    val uiState = viewModel.uiState.value

    var isDropdownExpanded by remember { mutableStateOf(false) }

    Scaffold(topBar = {
        TopAppBar(title = { Text(text = stringResource(id = R.string.add_activity)) })
    }) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                value = uiState.activityName,
                onValueChange = { viewModel.setActivityName(it) },
                label = { Text(text = stringResource(id = R.string.name)) })
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = isDropdownExpanded,
                    onExpandedChange = { isDropdownExpanded = !isDropdownExpanded },
                    modifier = Modifier.weight(1f)
                ) {
                    OutlinedTextField(
                        readOnly = true,
                        value = uiState.chosenCategoryName,
                        onValueChange = {},
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isDropdownExpanded)
                        },
                        label = { Text(text = stringResource(id = R.string.category)) },
                        modifier = Modifier.menuAnchor().fillMaxWidth()
                    )
                    ExposedDropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false }
                    ) {
                        uiState.categoryList.forEach {
                            if (it.categoryId == uiState.chosenCategoryId) {
                                viewModel.setCurrentCategoryName(it.name)
                            }
                            DropdownMenuItem(
                                onClick = {
                                    viewModel.setCurrentCategoryId(it.categoryId)
                                    isDropdownExpanded = false
                                },
                                text = { Text(text = it.name) }
                            )
                        }
                    }
                }
                IconButton(onClick = onAddCategory) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(id = R.string.add_category)
                    )
                }
            }
            Button(
                modifier = Modifier.padding(8.dp),
                onClick = { viewModel.saveActivity().also { navigateBack() } }
            ) {
                Text(text = stringResource(id = R.string.save))
            }
        }
    }
}