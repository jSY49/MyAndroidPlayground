package com.example.apod.ui.Screen

import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.apod.R
import com.example.apod.Util.toKoreanDateString
import com.example.apod.ViewModel.ApodUiState
import com.example.apod.ViewModel.ApodViewModel
import com.example.apod.ui.Components.ApodActionButtons
import com.example.apod.ui.Components.MediaTypeChips
import com.example.apod.ui.theme.bg
import com.example.apod.ui.theme.grayborder
import com.example.apod.ui.theme.mainColor
import com.example.apod.ui.theme.sub_title_color
import com.example.apod.ui.theme.title_color

@Composable
fun ApodScreen(modifier: Modifier = Modifier, viewModel: ApodViewModel = viewModel()) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Surface(
        modifier = modifier.fillMaxSize(),
        color = bg
    ) {

        Column(
            modifier = modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        ) {

            Text(
                modifier = modifier.padding(0.dp, 16.dp, 0.dp, 4.dp),
                text = stringResource(R.string.nasaApod),
                color = sub_title_color,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                modifier = modifier.padding(0.dp, 0.dp, 0.dp, 4.dp),
                text = stringResource(R.string.todayApod),
                color = title_color,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold
            )

            val state = uiState
            val items = (state as? ApodUiState.Success)?.data ?: emptyList()
            val pagerState = rememberPagerState(pageCount = { items.size })
            val currentItem = items.getOrNull(pagerState.currentPage)
            var isOverflowing by remember { mutableStateOf(false) }

            Row(
                modifier = modifier.padding(
                    0.dp,
                    14.dp,
                    0.dp,
                    4.dp
                ),   // Row는 원본 modifier 사용 (맞음, 바깥에서 받은 거니까)
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(48.dp)
                        .border(
                            0.5.dp, grayborder,
                            RoundedCornerShape(50)
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronLeft,
                        modifier = Modifier.size(32.dp),
                        tint = Color.White,
                        contentDescription = "Prev"
                    )
                }

                Text(
                    modifier = Modifier
                        .padding(0.dp, 0.dp, 0.dp, 4.dp)
                        .weight(1f),
                    text = currentItem?.date?.toKoreanDateString() ?: "",
                    color = title_color,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )

                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .size(48.dp)
                        .border(
                            0.5.dp, grayborder,
                            RoundedCornerShape(50)
                        ),
                ) {
                    Icon(
                        imageVector = Icons.Default.ChevronRight,
                        modifier = Modifier.size(32.dp),
                        tint = Color.White,
                        contentDescription = "Next"
                    )
                }
            }

            //데이터 파싱 확인 용
            when (state) {
                is ApodUiState.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(color = mainColor)
                    }
                }

                is ApodUiState.Error -> {
                    Text(
                        text = "에러: ${state.message}",
                        color = title_color,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                }

                is ApodUiState.Success -> {
                    HorizontalPager(
                        state = pagerState,
                        pageSpacing = 12.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(240.dp)
                    ) { page ->
                        AsyncImage(
                            model = items[page].url,
                            contentDescription = items[page].title,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(RoundedCornerShape(12.dp)),
                            error = painterResource(id = R.drawable.apod_placeholder),
                            placeholder = painterResource(id = R.drawable.apod_placeholder)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(items.size) { index ->
                            val selected = pagerState.currentPage == index
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(if (selected) 8.dp else 6.dp)
                                    .clip(CircleShape)
                                    .background(if (selected) title_color else grayborder)
                            )
                        }
                    }

                    currentItem?.mediaType?.let { mediaType ->
                        MediaTypeChips(mediaType)
                    }

                    Text(
                        text = currentItem?.title ?: "",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = title_color,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .basicMarquee()
                    )

                    Text(
                        text = currentItem?.explanation ?: "",
                        maxLines = 4,
                        overflow = TextOverflow.Ellipsis,
                        color = sub_title_color,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(top = 8.dp),
                        onTextLayout = { res ->
                            isOverflowing = res.hasVisualOverflow
                        }
                    )

                    if (isOverflowing) {
                        Text(
                            text = stringResource(R.string.more),
                            color = mainColor,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }

                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 0.dp, vertical = 8.dp),
                        thickness = 1.dp,
                        color = Color.Gray
                    )

                    Row(
                        modifier = Modifier.padding(vertical = 16.dp)
                    ) {

                        ApodActionButtons(
                            modifier = Modifier.weight(1f),
                            label = stringResource(R.string.save),
                            onClick = {}
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        ApodActionButtons(
                            modifier = Modifier.weight(1f),
                            label = stringResource(R.string.share),
                            onClick = {}
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
fun ApodScreenPreview() {
    ApodScreen()
}
