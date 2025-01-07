package com.openclassrooms.magicgithub

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.openclassrooms.magicgithub.di.Injection.getRepository
import com.openclassrooms.magicgithub.ui.user_list.ListUserActivity
import com.openclassrooms.magicgithub.utils.RecyclerViewUtils.ItemCount
import com.openclassrooms.magicgithub.utils.RecyclerViewUtils.clickChildView
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


/**
 * Instrumented test, which will execute on an Android device.
 * Testing ListUserActivity screen.
 */
@RunWith(AndroidJUnit4::class)
@LargeTest
class UserListInstrumentedTest {
    @Rule
    @JvmField
    val mActivityRule = ActivityTestRule(ListUserActivity::class.java)

    private var currentUsersSize = -1

    @Before
    fun setup() {
        currentUsersSize = getRepository().getUsers().size
    }

    @Test
    fun checkIfRecyclerViewIsNotEmpty() {
        Espresso.onView(ViewMatchers.withId(R.id.activity_list_user_rv))
            .check(ItemCount(currentUsersSize))
    }

    @Test
    fun checkIfAddingRandomUserIsWorking() {
        Espresso.onView(ViewMatchers.withId(R.id.activity_list_user_fab))
            .perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.activity_list_user_rv))
            .check(ItemCount(currentUsersSize + 1))
    }

    @Test
    fun checkIfRemovingUserIsWorking() {
        Espresso.onView(ViewMatchers.withId(R.id.activity_list_user_rv))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                    0,
                    clickChildView(R.id.item_list_user_delete_button)
                )
            )
        Espresso.onView(ViewMatchers.withId(R.id.activity_list_user_rv))
            .check(ItemCount(currentUsersSize - 1))
    }

    @Test
    fun checkIfSwipeIsWorking() {
        // Effectue un swipe sur le premier élément
        Espresso.onView(ViewMatchers.withId(R.id.activity_list_user_rv))
            .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, ViewActions.swipeRight()))
        val user = getRepository().getUsers()[0]
        assert(!user.active)
    }
}