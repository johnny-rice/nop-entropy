package io.nop.ofbiz.transform;

import io.nop.api.core.annotations.autotest.NopTestConfig;
import io.nop.autotest.junit.JunitBaseTestCase;
import io.nop.ofbiz.migration.transform.EntityDefDirTransformer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.File;

@Disabled
@NopTestConfig(localDb = true)
public class TestEntityDefDirTransformer extends JunitBaseTestCase {


    @Test
    public void testTransform() throws Exception {
        File dir = getTestResourcesDir();
        File dataDir = new File(dir, "ofbiz/entitydef").getCanonicalFile();
        System.out.println(dataDir);

        new EntityDefDirTransformer().transformDir(dataDir, getTargetFile("nop-ofbiz"));
    }
}
