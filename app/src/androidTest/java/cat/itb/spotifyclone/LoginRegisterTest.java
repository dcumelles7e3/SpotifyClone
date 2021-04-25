package cat.itb.spotifyclone;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.schibsted.spain.barista.assertion.BaristaVisibilityAssertions;
import com.schibsted.spain.barista.interaction.BaristaClickInteractions;
import com.schibsted.spain.barista.interaction.BaristaEditTextInteractions;
import com.schibsted.spain.barista.rule.BaristaRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class LoginRegisterTest{
    @Rule
    public BaristaRule<LoginActivity> baristaRule = BaristaRule.create(LoginActivity.class);
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

    //Ex2
    @Test
    public void register() {
        BaristaClickInteractions.clickOn(R.id.registerText);
        BaristaVisibilityAssertions.assertDisplayed(R.id.registeractivity);
        BaristaEditTextInteractions.writeTo(R.id.etlSearch, "hola@que.tal");
        BaristaEditTextInteractions.writeTo(R.id.etpass, "holaquetal");
        BaristaEditTextInteractions.writeTo(R.id.etrepeatpass, "holaquetal");
        BaristaEditTextInteractions.writeTo(R.id.etusername, "hola");
        BaristaClickInteractions.clickOn(R.id.signupButton);
        //BaristaVisibilityAssertions.assertDisplayed(R.id.container);
    }
    @Test
    public void login() {
        BaristaVisibilityAssertions.assertDisplayed(R.id.loginContent);
        BaristaEditTextInteractions.writeTo(R.id.etlSearch, "hola@que.tal");
        BaristaEditTextInteractions.writeTo(R.id.etpass, "holaquetal");
        BaristaClickInteractions.clickOn(R.id.loginbutton);
        //BaristaVisibilityAssertions.assertDisplayed(R.id.container);
    }


}