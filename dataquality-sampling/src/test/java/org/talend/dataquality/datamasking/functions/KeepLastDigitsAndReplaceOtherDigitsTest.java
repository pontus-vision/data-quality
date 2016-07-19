package org.talend.dataquality.datamasking.functions;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.talend.dataquality.duplicating.RandomWrapper;

public class KeepLastDigitsAndReplaceOtherDigitsTest {

    private String output;

    private String input = "a1b2c3d456"; //$NON-NLS-1$

    private KeepLastDigitsAndReplaceOtherDigits kfag = new KeepLastDigitsAndReplaceOtherDigits();

    @Test
    public void testGood() {
        kfag.parse("3", false, new RandomWrapper(42));
        output = kfag.generateMaskedRow(input);
        assertEquals("a8b3c0d456", output); //$NON-NLS-1$
    }

    @Test
    public void testDummyGood() {
        kfag.parse("15", false, new RandomWrapper(542));
        output = kfag.generateMaskedRow(input);
        assertEquals(input, output);
    }
}
