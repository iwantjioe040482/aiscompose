package com.arcadia.aiscompose.View

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Modifier
import com.arcadia.aiscompose.Model.TransactionView
import androidx.compose.ui.text.style.TextAlign

@Composable
fun TransactionItem(e: TransactionView) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(6.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = e.gl_date,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Start
                )
                Text(
                    text =  e.coa_name,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(2f),
                    textAlign = TextAlign.Start
                )
                Text(
                    text = "Rp ${"%,.0f".format(e.total)}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.End
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            //e.coa_name?.let {
            //    if (it.isNotBlank()) {
            //        Spacer(modifier = Modifier.height(2.dp))
            //        Text(
            //            text = it,
            //            style = MaterialTheme.typography.bodySmall,
            //            color = MaterialTheme.colorScheme.onSurfaceVariant
            //        )
            //    }
            //}
        }
    }
}