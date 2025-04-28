package com.example.showcase

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.showcase.base.Async
import com.example.showcase.features.mainlist.ui.MainListScreen
import com.example.showcase.features.mainlist.ui.MainListState
import com.example.showcase.features.mainlist.ui.MediaPreview
import com.example.showcase.ui.PreviewTransitionAnimation
import io.mockk.spyk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalSharedTransitionApi::class)
class MainListScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun listOfItemsShouldBeVisible() {
        composeTestRule.setContent {
            PreviewTransitionAnimation { sharedTransitionScope, animatedContentScope ->
                MainListScreen(
                    state = MainListState(
                        items = Async.Success(dummyMediaPreviews)
                    ),
                    onRefresh = {},
                    onItemDeletion = {},
                    onItemClick = {},
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope
                )
            }
        }

        composeTestRule.onNodeWithText("Title 1").assertIsDisplayed()
        composeTestRule.onNodeWithText("11/11/1111").assertIsDisplayed()
        composeTestRule.onNodeWithText("Title 2").assertIsDisplayed()
        composeTestRule.onNodeWithText("22/22/2222").assertIsDisplayed()
    }

    @Test
    fun itemsShouldBeClickable() {
        val mockedLambda: (Int) -> Unit = spyk({})
        composeTestRule.setContent {
            PreviewTransitionAnimation { sharedTransitionScope, animatedContentScope ->
                MainListScreen(
                    state = MainListState(
                        items = Async.Success(dummyMediaPreviews)
                    ),
                    onRefresh = {},
                    onItemDeletion = {},
                    onItemClick = mockedLambda,
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope
                )
            }
        }

        composeTestRule.onNodeWithText("Title 1").performClick()
        verify(exactly = 1) { mockedLambda(1) }
    }

    @Test
    fun pullingDownTheListShouldCallRefresh() {
        val mockedLambda: () -> Unit = spyk({})
        composeTestRule.setContent {
            PreviewTransitionAnimation { sharedTransitionScope, animatedContentScope ->
                MainListScreen(
                    state = MainListState(
                        items = Async.Success(dummyMediaPreviews)
                    ),
                    onRefresh = mockedLambda,
                    onItemDeletion = {},
                    onItemClick = {},
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope
                )
            }
        }

        composeTestRule.onNodeWithText("Title 1").performTouchInput {
            down(center)
            moveBy(Offset(0f, y = 500.dp.toPx()), 0)
            up()
        }
        composeTestRule.waitForIdle()

        verify(exactly = 1) { mockedLambda() }
    }

    @Test
    fun swipingLeftOverAnItemShouldCallDeletion() {
        val mockedLambda: (Int) -> Unit = spyk({})
        composeTestRule.setContent {
            PreviewTransitionAnimation { sharedTransitionScope, animatedContentScope ->
                MainListScreen(
                    state = MainListState(
                        items = Async.Success(dummyMediaPreviews)
                    ),
                    onRefresh = {},
                    onItemDeletion = mockedLambda,
                    onItemClick = {},
                    sharedTransitionScope = sharedTransitionScope,
                    animatedContentScope = animatedContentScope
                )
            }
        }

        composeTestRule.onNodeWithText("Title 1").performTouchInput {
            down(center)
            moveBy(Offset(x = -100.dp.toPx(), y = 0f), 0)
            up()
        }
        composeTestRule.waitForIdle()

        verify(exactly = 1) { mockedLambda(1) }
    }

    companion object {
        private val dummyMediaPreviews = listOf(
            MediaPreview(
                id = 1,
                title = "Title 1",
                dateTime = "11/11/1111"
            ),
            MediaPreview(
                id = 2,
                title = "Title 2",
                dateTime = "22/22/2222"
            )
        )
    }
}