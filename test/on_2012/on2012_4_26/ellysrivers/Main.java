package on_2012.on2012_4_26.ellysrivers;

import net.egork.chelper.tester.TopCoderTester;
import org.junit.Assert;
import org.junit.Test;

public class Main {
    @Test
    public void test() throws Exception {
        if (!TopCoderTester.test("double getMin(int length, int walk, int[] width, int[] speed)",
                "on_2012.on2012_4_26.ellysrivers.EllysRivers",
                "10;;3;;5,2,3;;5,2,7;;3.231651964071508;;true::10000;;211;;911;;207;;48.24623664712219;;true::1337;;2;;100,200,300,400;;11,12,13,14;;128.57830549575695;;true::77;;119;;11,12,13,14;;100,200,300,400;;0.3842077071089629;;true::7134;;1525;;11567,19763,11026,10444,24588,22263,17709,11181,15292,28895,15039,18744,19985,13795,26697,18812,25655,13620,28926,12393;;1620,1477,2837,2590,1692,2270,1655,1078,2683,1475,1383,1153,1862,1770,1671,2318,2197,1768,1979,1057;;214.6509731258811;;true::100000;;1;;1000000;;1000000;;1.004987562112089;;true")) {
            Assert.fail();
        }
    }
}
