package cat.itb.spotifyclone;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.schibsted.spain.barista.assertion.BaristaImageViewAssertions;
import com.schibsted.spain.barista.assertion.BaristaRecyclerViewAssertions;
import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions;
import com.schibsted.spain.barista.interaction.BaristaClickInteractions;
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions;
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
public class UseCaseTest {
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

    //Ex5
    @Test
    public void search_song(){
        BaristaClickInteractions.clickOn(R.id.navigation_search);
        BaristaVisibilityAssertions.assertDisplayed(R.id.fragmentsearch);
        BaristaClickInteractions.clickOn(R.id.etlSearch);
        BaristaVisibilityAssertions.assertDisplayed(R.id.searchingFragment);
        BaristaEditTextInteractions.writeTo(R.id.et_searching, "dire straits");
        BaristaKeyboardInteractions.pressImeActionButton();
        BaristaRecyclerViewAssertions.assertRecyclerViewItemCount(R.id.search_rv, 12);
    }

    @Test
    public void play_song(){
        search_song();
        BaristaListInteractions.clickListItem(R.id.search_rv, 1);
        BaristaVisibilityAssertions.assertDisplayed(R.id.playerActivity);
        BaristaVisibilityAssertions.assertDisplayed("Dire Straits");
        BaristaImageViewAssertions.assertHasDrawable(R.id.b_play, R.drawable.ic_pause);
    }

    @Test
    public void pause_song(){
        play_song();
        BaristaClickInteractions.clickOn(R.id.b_play);
        BaristaImageViewAssertions.assertHasDrawable(R.id.b_play, R.drawable.ic_play);
    }

    @Test
    public void next_song(){
        play_song();
        BaristaClickInteractions.clickOn(R.id.forwards);
        BaristaImageViewAssertions.assertHasDrawable(R.id.b_play, R.drawable.ic_pause);
    }

    @Test
    public void save_favorite(){
        play_song();
        BaristaClickInteractions.clickOn(R.id.imageViewCorazon);
        BaristaImageViewAssertions.assertHasDrawable(R.id.imageViewCorazon, R.drawable.ic_action_favorite);
    }
}