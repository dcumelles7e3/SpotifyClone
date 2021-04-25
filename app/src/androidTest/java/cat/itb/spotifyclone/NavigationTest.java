package cat.itb.spotifyclone;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;


import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions;
import com.schibsted.spain.barista.interaction.BaristaClickInteractions;
import com.schibsted.spain.barista.interaction.BaristaKeyboardInteractions;
import com.schibsted.spain.barista.interaction.BaristaListInteractions;
import com.schibsted.spain.barista.rule.BaristaRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class NavigationTest {
    @Rule
    public BaristaRule<MainActivity> baristaRule = BaristaRule.create(MainActivity.class);
    public @Rule
    TestName name = new TestName();

    @Before
    public void setUp(){
        Log.i("Info","[START] - Launch Test: " + name.getMethodName());
        baristaRule.launchActivity();
    }
    @After
    public void tearDown(){
        Log.i("Info", "[FINISH] - Test: " + name.getMethodName());
    }

    //Ex3
    @Test
    public void homefragment_to_searchfragment() {
        BaristaClickInteractions.clickOn(R.id.navigation_search);
        BaristaVisibilityAssertions.assertDisplayed(R.id.fragmentsearch);
    }

    @Test
    public void homefragment_to_options() {
        BaristaClickInteractions.clickOn(R.id.settingsButton);
        BaristaVisibilityAssertions.assertDisplayed(R.id.settingsFragment);
    }

    @Test
    public void search_to_searching(){
        homefragment_to_searchfragment();
        BaristaClickInteractions.clickOn(R.id.etlSearch);
        BaristaVisibilityAssertions.assertDisplayed(R.id.searchingFragment);
        BaristaKeyboardInteractions.closeKeyboard();
    }

    //Ex4
    @Test
    public void recycler_to_item(){
        BaristaListInteractions.clickListItem(R.id.recyclerView1, 1);
        BaristaVisibilityAssertions.assertDisplayed(R.id.fragmentAlbumList);
        BaristaVisibilityAssertions.assertDisplayed("Iron Maiden");
    }
}