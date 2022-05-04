package com.tdr.app.kimikoscanvas.fragments

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.tdr.app.kimikoscanvas.R
import com.tdr.app.kimikoscanvas.data.Canvas
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
@MediumTest
class ListFragmentTest {

    @Test
    fun startFragment_checkData() = runTest {

        val scenario = launchFragmentInContainer<ListFragment>(Bundle(), R.style.Theme_KimikosCanvas)

        // Mockito mocks NavController class
        val navController = mock(NavController::class.java)

        // Attaches mocked navController to fragment
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }

        sleep(3)

        val canvas = Canvas(
            "Clear Creek Natural Heritage Center",
        "https://firebasestorage.googleapis.com/v0/b/kimikos-canvas-a39b2.appspot.com/o/images%2Fkimikos_canvas%2FClear%20Creek%20Natural%20Heritage%20Center%20Pic%201.jpg?alt=media&token=09c540f9-6432-4b43-8d2d-a1c3d12966d5",
            33.26025,
            -97.0641,
        "96051781-16b8-4895-9aeb-4f5bec32ddfd")

        Espresso.onView(withId(R.id.recycler_view))
            .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(0))
            .check(ViewAssertions.matches(ViewMatchers.hasDescendant(ViewMatchers.withText(canvas.name))))

    }

    fun sleep(time: Int){
        val seconds : Long = (time * 1000).toLong()
        Thread.sleep(seconds)
    }


    @Test
    fun startFragment_navigate() {

    }
}