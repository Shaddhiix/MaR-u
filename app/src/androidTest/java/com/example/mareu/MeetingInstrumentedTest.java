package com.example.mareu;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.doubleClick;
import static androidx.test.espresso.action.ViewActions.pressImeActionButton;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.PickerActions.setTime;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.example.mareu.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import android.widget.DatePicker;
import android.widget.TimePicker;

import androidx.test.espresso.contrib.PickerActions;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.RootMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.example.mareu.activity.MeetingListActivity;
import com.example.mareu.di.DI;
import com.example.mareu.services.MeetingApiService;
import com.example.mareu.utils.DeleteViewAction;

import org.hamcrest.Matchers;
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

    private void addReunion(){
        onView(withId(R.id.floatbtn))
                .perform(click());
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

    @Test
    public void AddaMeeting() {
        onView(withId(R.id.recyclerView))
                .check(withItemCount(ITEMS_COUNT));
       addReunion ();
        onView(withId(R.id.tv_name_meeting))
                .perform(typeText("App"), closeSoftKeyboard());
        onView(withId(R.id.spinnerRoomMeeting))
                .perform(scrollTo(), click());
        onData(allOf(is(instanceOf(String.class))))
                .atPosition(4)
                .perform(click());
        onView(allOf(withId(R.id.tv_date_meeting)))
                .perform(doubleClick());
        onView(withClassName( Matchers.equalTo( DatePicker.class.getName())))
                .perform( PickerActions.setDate(2022, 7, 21));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.tv_time_meeting))
                .perform(scrollTo(), doubleClick());
        onView(withClassName(Matchers.equalTo( TimePicker.class.getName())))
                .perform(setTime(13, 45));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.tv_person_name))
                .perform(typeText ( "alex@lamzone.com" ))
                .perform(pressImeActionButton());
        onView(withId(R.id.btn_create_meeting))
                .perform(scrollTo(), click());
        onView(withId(R.id.recyclerView))
                .check(withItemCount(ITEMS_COUNT +1));
    }

    @Test
    public void CheckFilterByDate_isDisplayed() {
        onView(withId(R.id.filter_btn))
                .perform(click());
        onView(withText("Par Date"))
                .perform(click());
        onView(withClassName(Matchers.equalTo(DatePicker.class.getName())))
                .perform(PickerActions.setDate(2022, 7, 27));
        onView(withId(android.R.id.button1))
                .perform(click());
        onView(withId(R.id.recyclerView))
                .check(withItemCount(1));
    }
    @Test
    public void CheckFilterByRoom_isDisplayed() {
        onView(withId(R.id.filter_btn))
                .perform(click());
        onView(withText("Par Salle"))
                .perform(click());
        onView(withId(R.id.dialogRoomSpinner))
                .perform(click());
        onData(allOf(is(instanceOf(String.class))))
                .atPosition(4)
                .inRoot( RootMatchers.isPlatformPopup())
                .perform(click());
        onView(withId(R.id.okButton))
                .perform(click());
        onView(withId(R.id.recyclerView))
                .check(withItemCount(1));
    }
}