package com.guagua.paboo

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.guagua.paboo.data.model.Category
import com.guagua.paboo.data.repository.PabooRepository
import com.guagua.paboo.data.util.MockUtil
import com.guagua.paboo.domain.GetTopHeadlinesUseCase
import com.guagua.paboo.presentation.category.CategoryEvent
import com.guagua.paboo.presentation.category.CategoryIntent
import com.guagua.paboo.presentation.category.CategoryState
import com.guagua.paboo.presentation.category.CategoryViewModel
import com.guagua.paboo.presentation.navigation.Screen
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(JUnit4::class)
class CategoryViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: CategoryViewModel

    @MockK
    private lateinit var repository: PabooRepository

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxed = true)
        viewModel = CategoryViewModel(GetTopHeadlinesUseCase(repository))
    }

    @Test
    fun `test get paging flow when category changed`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        val intentFlow = flowOf(CategoryIntent.CategoryChanged(Category.BUSINESS))

        viewModel.handleIntent(intentFlow)

        val state = viewModel.state.drop(1).first()
        assert(state.categoryFlowMap[Category.BUSINESS] != null)
        assert(state.categoryFlowMap.size == 1)
    }

    @Test
    fun `test click article`() = runTest {
        Dispatchers.setMain(StandardTestDispatcher(testScheduler))
        val article = MockUtil.getArticle(1)
        val intentFlow = flowOf(CategoryIntent.ClickArticle(article))

        viewModel.handleIntent(intentFlow)

        val state = viewModel.state.drop(1).first()
        assertEquals(state.event, CategoryEvent.Navigation(Screen.NewsDetail(article.url)))
    }
}