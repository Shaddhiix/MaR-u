package com.example.mareu;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.activity.MeetingListActivity;
import com.example.mareu.di.DI;
import com.example.mareu.services.MeetingApiService;
import com.example.mareu.utils.DeleteViewAction;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Instrumented test
 **/

@RunWith(AndroidJUnit4.class)
public class MeetingInstrumentedTest {

    private static int ITEMS_COUNT = 2;
    private int position = 1;

    private MeetingListActivity listActivity;
    private MeetingApiService apiService;

    @Rule
    public ActivityTestRule<MeetingListActivity> activityTestRule
            = new ActivityTestRule<> (MeetingListActivity.class);

    @Before
    public void setUp() {
        listActivity = activityTestRule.getActivity();
        assertThat (listActivity, notNullValue());
        apiService = DI.getMeetingApiService();
    }

    /**
     * We ensure that our recyclerview is displaying at least on item
     */
    @Test
    public void theMeetingList_ShouldNotBeEmpty() {
        onView( ViewMatchers.withId(R.id.recyclerView))
                .check (matches(hasMinimumChildCount(1)));
    }

    /**
     * When we delete an item, the item is no more shown
     */
    @Test
    public void theMeetingList_deleteAction_shouldRemoveItem() {
        onView ( withId ( R.id.recyclerView ) )
                .check ( withItemCount ( ITEMS_COUNT ) )
                .perform ( RecyclerViewActions.actionOnItemAtPosition ( 1, new DeleteViewAction () ) )
                .check ( withItemCount ( ITEMS_COUNT - 1 ) );
    }

}