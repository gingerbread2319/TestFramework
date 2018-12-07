package test.TestSuite;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import test.TestScripts.KryptonGuruScripts.TC02_KryptonGuruTest;
import test.TestScripts.TC01_SearchGoogle;
@RunWith(Suite.class)

@Suite.SuiteClasses({
        TC01_SearchGoogle.class,
        TC02_KryptonGuruTest.class
})
public class SampleTestSuite {
}
