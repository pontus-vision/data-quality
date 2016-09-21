// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.email;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.talend.dataquality.email.api.CheckerParams;
import org.talend.dataquality.email.api.EmailVerify;
import org.talend.dataquality.email.api.EmailVerifyResult;
import org.talend.dataquality.email.exception.TalendSMTPRuntimeException;

/**
 * created by qiongli on 2014年12月26日 Detailled comment
 *
 */
public class EmailVerifyTest {

    String email = null;

    String regularPattern = "aw"; //$NON-NLS-1$

    EmailVerify emailVerify;

    @Before
    public void setUp() throws Exception {
        emailVerify = new EmailVerify();
    }

    /**
     * Test method for {@link org.talend.dataquality.email.api.EmailVerify#verify(java.lang.String)}.
     */
    @Test
    public void testVerify_regular() {
        email = "ab_2c@sina.com"; //$NON-NLS-1$
        emailVerify = emailVerify.addRegularRegexChecker(true, "");
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail(email));
    }

    @Test
    public void testVerify_regular_invalid() {
        email = "ab_2c@sina.com."; //$NON-NLS-1$
        emailVerify = emailVerify.addRegularRegexChecker(true, null);
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(email));
    }

    @Test
    public void testVerify_regular_invalid_2() {
        email = "ab_2c"; //$NON-NLS-1$
        emailVerify = emailVerify.addRegularRegexChecker(true, "");
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(email));
    }

    /**
     * Test method for {@link org.talend.dataquality.email.api.EmailVerify#verify(java.lang.String)}.
     */
    @Test
    public void testVerify_localPart() {
        email = "abc@sina.com"; //$NON-NLS-1$
        emailVerify = emailVerify.addRegularRegexChecker(true, " ").addLocalPartRegexChecker(regularPattern, false, true);
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail(email));
        email = "ab2@sina.com";
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(email));
    }

    /**
     * Test method for {@link org.talend.dataquality.email.api.EmailVerify#verify(java.lang.String)}.
     */
    @Test
    public void testVerify_localPart_2() {
        email = "abc-sina.com"; //$NON-NLS-1$
        emailVerify = emailVerify.addRegularRegexChecker(true, null).addLocalPartRegexChecker(regularPattern, false, true);
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(email));
    }

    @Test
    public void testVerify_localPart_3() {
        emailVerify = emailVerify.addRegularRegexChecker(true, null).addLocalPartRegexChecker(regularPattern, false, true);
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(null));
    }

    @Test
    public void testVerify_RegularAndlocalPart_9985() {
        email = "2@qq.com"; //$NON-NLS-1$
        emailVerify = emailVerify.addRegularRegexChecker(true, "^\\w+([-+.]\\w+)@\\w([-.]\\w+)\\.\\w+([-.]\\w+)$")
                .addLocalPartRegexChecker("^\\d$", false, false);
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(email));
    }

    /**
     * Test method for {@link org.talend.dataquality.email.api.EmailVerify#verify(java.lang.String)}.
     */
    @Test
    public void testVerify_TLD() {
        email = "ab_2c@sina.com.xyz"; //$NON-NLS-1$
        List<String> tldLs = new ArrayList<String>();
        tldLs.add("xyz".toUpperCase()); //$NON-NLS-1$
        emailVerify = emailVerify.addRegularRegexChecker(false, null).addTLDsChecker(true, tldLs, true);
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail(email));
        email = "ab_2c@sina.com.xy"; //$NON-NLS-1$
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(email));
    }

    @Test
    public void testVerify_TLD_2() {
        email = "ab_2c-sina.com.xy"; //$NON-NLS-1$
        List<String> tldLs = new ArrayList<String>();
        tldLs.add("xyz".toUpperCase()); //$NON-NLS-1$
        emailVerify = emailVerify.addRegularRegexChecker(false, "").addTLDsChecker(true, tldLs, true);
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(email));
    }

    @Test
    public void testVerify_TLD_3() {
        List<String> tldLs = new ArrayList<String>();
        tldLs.add("xyz".toUpperCase()); //$NON-NLS-1$
        emailVerify = emailVerify.addRegularRegexChecker(false, "").addTLDsChecker(true, tldLs, true);
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(null));
    }

    @Test
    public void testVerify_TLD_4() {
        List<String> tldLs = new ArrayList<String>();
        tldLs.add("xyz".toUpperCase()); //$NON-NLS-1$
        emailVerify = emailVerify.addRegularRegexChecker(false, "").addTLDsChecker(true, tldLs, true);
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail("abc"));
    }

    /**
     * Test method for {@link org.talend.dataquality.email.api.EmailVerify#verify(java.lang.String)}.
     */
    @Test
    public void testVerify_domain_white() {
        email = "ab@abx.com"; //$NON-NLS-1$
        List<String> ls = new ArrayList<String>();
        ls.add("abx.com"); //$NON-NLS-1$
        emailVerify = emailVerify.addListDomainsChecker(false, ls);
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail(email));
    }

    @Test
    public void testVerify_domain_white_2() {
        email = "ab-abx.com"; //$NON-NLS-1$
        List<String> ls = new ArrayList<String>();
        ls.add("abx.com"); //$NON-NLS-1$
        emailVerify = emailVerify.addListDomainsChecker(false, ls);
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(email));
    }

    @Test
    public void testVerify_domain_white_3() {
        List<String> ls = new ArrayList<String>();
        ls.add("abx.com"); //$NON-NLS-1$
        emailVerify = emailVerify.addListDomainsChecker(false, ls);
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(null));
    }

    @Test
    public void testVerify_domain_white_empty() {
        email = "ab@abx.com"; //$NON-NLS-1$
        List<String> ls = new ArrayList<String>();
        emailVerify = emailVerify.addListDomainsChecker(false, ls);
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(email));
    }

    /**
     * Test method for {@link org.talend.dataquality.email.api.EmailVerify#verify(java.lang.String)}.
     */
    @Test
    public void testVerify_domain_white_multiEmails() {
        List<String> emailLs = new ArrayList<String>();
        emailLs.add("ab@abx.com"); //$NON-NLS-1$
        emailLs.add("ab@;cd12.com"); //$NON-NLS-1$

        List<String> whiteLs = new ArrayList<String>();
        whiteLs.add("abx.com"); //$NON-NLS-1$
        emailVerify = emailVerify.addListDomainsChecker(false, whiteLs);
        for (int i = 0; i < emailLs.size(); i++) {
            String em = emailLs.get(i);
            if (i == 0) {
                assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail(em));
            } else {
                assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(em));
            }
        }

    }

    /**
     * Test method for {@link org.talend.dataquality.email.api.EmailVerify#verify(java.lang.String)}.
     */
    @Test
    public void testVerify_domain_black() {
        email = "ab@abx.com"; //$NON-NLS-1$
        List<String> ls = new ArrayList<String>();
        ls.add("abx.com"); //$NON-NLS-1$
        emailVerify = emailVerify.addListDomainsChecker(true, ls);
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(email));
        email = "ab@cd.com"; //$NON-NLS-1$
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail(email));
    }

    @Test
    public void testVerify_domain_black_9985() {
        String[] emails = { "luck@qq.com", "luck@aa.bbzz11", "luck@bb.zbzz12", "luck@as.cn", "luck@cc.zbzz13" }; //$NON-NLS-1$
        List<String> ls = new ArrayList<String>();
        ls.add("cc.zbzz13"); //$NON-NLS-1$
        emailVerify = emailVerify.addListDomainsChecker(true, ls);
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(email));
        email = "ab@cd.com"; //$NON-NLS-1$

        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail(emails[0]));
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail(emails[1]));
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail(emails[2]));
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail(emails[3]));
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(emails[4]));
    }

    @Test
    @Ignore
    public void testCallback_valid1() {
        emailVerify = emailVerify.addRegularRegexChecker(true, "").addCallbackMailServerChecker(true);
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail("237283696@qq.com"));
    }

    @Test
    @Ignore
    public void testCallback_valid_2() {
        emailVerify = emailVerify.addCallbackMailServerChecker(true).addRegularRegexChecker(true, null);
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail("237283696@qq.com")); //$NON-NLS-1$
    }

    @Test
    @Ignore
    public void testCallback_valid2() {
        emailVerify = emailVerify.addRegularRegexChecker(true, null).addCallbackMailServerChecker(true);
        try {
            assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail("qiongli1@163.com"));
        } catch (TalendSMTPRuntimeException e) {
        }
    }

    @Test
    @Ignore
    public void testCallback_invalid_2() {
        emailVerify = emailVerify.addRegularRegexChecker(true, "").addCallbackMailServerChecker(true);
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail("qiongli-163.com"));
    }

    @Test
    @Ignore
    public void testCallback_valid_3() {
        emailVerify = emailVerify.addRegularRegexChecker(true, "").addCallbackMailServerChecker(true);
        try {
            assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail("237283696@qq.com"));
        } catch (TalendSMTPRuntimeException e) {
        }
    }

    /**
     * Test method for {@link org.talend.dataquality.email.api.EmailVerify#verify(java.lang.String)}.
     */
    @Test
    public void testVerify_All() {

        List<String> domainLs = new ArrayList<String>();
        domainLs.add("abx.com.cn"); //$NON-NLS-1$
        List<String> tldLs = new ArrayList<String>();
        tldLs.add("hk".toUpperCase()); //$NON-NLS-1$
        tldLs.add("cn".toUpperCase()); //$NON-NLS-1$
        email = "ab@abx.com.cn"; //$NON-NLS-1$
        emailVerify = emailVerify.addRegularRegexChecker(true, "").addLocalPartRegexChecker(regularPattern, true, true)
                .addListDomainsChecker(false, domainLs).addTLDsChecker(false, tldLs, false);
        // .addCallbackMailServerChecker(true, "qiongli@talend.com");
        // EmailCheckerFactory.createEmailChecker(true, regularPattern, true, true, true, false, false, tldLs,
        // domainLs);
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail(email));
    }

    @Test
    public void testVerify_All_2() {

        List<String> domainLs = new ArrayList<String>();
        domainLs.add("abx.com.cn"); //$NON-NLS-1$
        List<String> tldLs = new ArrayList<String>();
        tldLs.add("hk".toUpperCase()); //$NON-NLS-1$
        tldLs.add("cn".toUpperCase()); //$NON-NLS-1$
        email = "ab@abx.com.cn"; //$NON-NLS-1$
        emailVerify = emailVerify.addRegularRegexChecker(true, "").addLocalPartRegexChecker("^\\\\d$", true, true)
                .addListDomainsChecker(true, domainLs).addTLDsChecker(true, tldLs, true);
        // it fail to balck domain list
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(email));
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(null));
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail("abc"));
    }

    /**
     * Test method for {@link org.talend.dataquality.email.api.EmailVerify#verify(java.lang.String)}.
     */
    @Test
    public void testVerify_email_null() {
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(null));
    }

    /**
     * Test for :use column content, in local part
     */
    @Test
    public void testUseColumnContent_1() {
        // use first 2 of the firstname, use all of the last name
        emailVerify = emailVerify.addLocalPartColumnContentChecker(true, false, "L", "2", "0", "0", "10", "-");

        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail("ab-full@email.com", new CheckerParams("abaa", "full")));
        // first n<2
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("a-full@email.com", new CheckerParams("abaa", "full")));
        assertEquals("ab-full@email.com", emailVerify.getSuggestedEmail());

        // first n>2
        assertEquals(EmailVerifyResult.CORRECTED,
                emailVerify.checkEmail("aba-full@email.com", new CheckerParams("abaa", "full")));
        assertEquals("ab-full@email.com", emailVerify.getSuggestedEmail());

        // last n<all
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("ab-ful@email.com", new CheckerParams("abaa", "full")));
        assertEquals("ab-full@email.com", emailVerify.getSuggestedEmail());

        // last n>all
        assertEquals(EmailVerifyResult.CORRECTED,
                emailVerify.checkEmail("ab-fullyy@email.com", new CheckerParams("abaa", "full")));
        assertEquals("ab-full@email.com", emailVerify.getSuggestedEmail());

        // both less
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("a-ful@email.com", new CheckerParams("abaa", "full")));
        assertEquals("ab-full@email.com", emailVerify.getSuggestedEmail());

        // both more
        assertEquals(EmailVerifyResult.CORRECTED,
                emailVerify.checkEmail("ababb-fullyy@email.com", new CheckerParams("abaa", "full")));
        assertEquals("ab-full@email.com", emailVerify.getSuggestedEmail());

        // only first
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("ab-@email.com", new CheckerParams("abaa", "full")));
        assertEquals("ab-full@email.com", emailVerify.getSuggestedEmail());

        // only last
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("-full@email.com", new CheckerParams("abaa", "full")));
        assertEquals("ab-full@email.com", emailVerify.getSuggestedEmail());
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("full@email.com", new CheckerParams("abaa", "full")));
        assertEquals("ab-full@email.com", emailVerify.getSuggestedEmail());

        // not contain '@'
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail("ccc", new CheckerParams("abaa", "full")));
        // not contain localpart
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail("@ccc", new CheckerParams("abaa", "full")));

        // first name empty
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail("ccc", new CheckerParams("", "full")));
        // first empty, only has last
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("ful@email.com", new CheckerParams("", "full")));
        assertEquals("full@email.com", emailVerify.getSuggestedEmail());
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail("full@email.com", new CheckerParams("", "full")));

        // last name empty
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail("ccc", new CheckerParams("abc", "")));
        // last empty, only has first
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("a@email.com", new CheckerParams("abaa", "")));
        assertEquals("ab@email.com", emailVerify.getSuggestedEmail());
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail("ab@email.com", new CheckerParams("abaa", "")));

        // empty email -- invalid
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail("", new CheckerParams("abc", "full")));

        // email = null
        assertEquals(EmailVerifyResult.INVALID, emailVerify.checkEmail(null, new CheckerParams("abc", "full")));
    }

    @Test
    public void testUseColumnContent_2() {
        // use all of the firstname, use 2 first of the last name
        emailVerify = emailVerify.addLocalPartColumnContentChecker(true, false, "L", "20", "0", "2", "0", "-");

        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail("full-aa@email.com", new CheckerParams("full", "aaaa")));
        // first n<all
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("fu-aa@email.com", new CheckerParams("full", "aaaa")));
        assertEquals("full-aa@email.com", emailVerify.getSuggestedEmail());

        // first n>all
        assertEquals(EmailVerifyResult.CORRECTED,
                emailVerify.checkEmail("fullyy-aa@email.com", new CheckerParams("full", "aaaa")));
        assertEquals("full-aa@email.com", emailVerify.getSuggestedEmail());

        // last n<2
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("full-b@email.com", new CheckerParams("full", "aaaa")));
        assertEquals("full-aa@email.com", emailVerify.getSuggestedEmail());

        // last n>2
        assertEquals(EmailVerifyResult.CORRECTED,
                emailVerify.checkEmail("full-aba@email.com", new CheckerParams("full", "aaaa")));
        assertEquals("full-aa@email.com", emailVerify.getSuggestedEmail());

        // both less
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("ful-b@email.com", new CheckerParams("full", "aaaa")));
        assertEquals("full-aa@email.com", emailVerify.getSuggestedEmail());

        // both more
        assertEquals(EmailVerifyResult.CORRECTED,
                emailVerify.checkEmail("ababb-fullyy@email.com", new CheckerParams("full", "aaaa")));
        assertEquals("full-aa@email.com", emailVerify.getSuggestedEmail());
    }

    @Test
    public void testUseColumnContent_4() {
        // use 2+1 of the firstname, use 4+3 first of the last name
        emailVerify = emailVerify.addLocalPartColumnContentChecker(true, false, "L", "2", "1", "4", "3", "-");

        assertEquals(EmailVerifyResult.VALID,
                emailVerify.checkEmail("124-1234678@email.com", new CheckerParams("1234", "12345678")));
        // first n<all
        assertEquals(EmailVerifyResult.CORRECTED,
                emailVerify.checkEmail("1-1234678@email.com", new CheckerParams("1234", "12345678")));
        assertEquals("124-1234678@email.com", emailVerify.getSuggestedEmail());

        // first n>all
        assertEquals(EmailVerifyResult.CORRECTED,
                emailVerify.checkEmail("1234-1234678@email.com", new CheckerParams("1234", "12345678")));
        assertEquals("124-1234678@email.com", emailVerify.getSuggestedEmail());

        // last n<2
        assertEquals(EmailVerifyResult.CORRECTED,
                emailVerify.checkEmail("124-12@email.com", new CheckerParams("1234", "12345678")));
        assertEquals("124-1234678@email.com", emailVerify.getSuggestedEmail());

        // last n>2
        assertEquals(EmailVerifyResult.CORRECTED,
                emailVerify.checkEmail("124-123456789@email.com", new CheckerParams("1234", "12345678")));
        assertEquals("124-1234678@email.com", emailVerify.getSuggestedEmail());

        // both less
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("1-1@email.com", new CheckerParams("1234", "12345678")));
        assertEquals("124-1234678@email.com", emailVerify.getSuggestedEmail());

        // both more
        assertEquals(EmailVerifyResult.CORRECTED,
                emailVerify.checkEmail("11111-222222222@email.com", new CheckerParams("1234", "12345678")));
        assertEquals("124-1234678@email.com", emailVerify.getSuggestedEmail());
    }

    // use invalid parameters of each string
    @Test
    public void testUseColumnContent_3() {
        emailVerify = emailVerify.addLocalPartColumnContentChecker(true, false, "L", "ab", "cc", "x", "z", "-");
        assertEquals(EmailVerifyResult.CORRECTED,
                emailVerify.checkEmail("124-1234678@email.com", new CheckerParams("1234", "12345678")));

        // use 0 of the firstname, use all of the last name
        emailVerify = emailVerify.addLocalPartColumnContentChecker(true, false, "L", "0", "0", "10", "0", "-");
        assertEquals(EmailVerifyResult.VALID,
                emailVerify.checkEmail("12345678@email.com", new CheckerParams("1234", "12345678")));

        assertEquals(EmailVerifyResult.CORRECTED,
                emailVerify.checkEmail("1-12345678@email.com", new CheckerParams("1234", "12345678")));

    }

    // test case sensitive
    @Test
    public void testUseColumnContent_5() {
        emailVerify = emailVerify.addLocalPartColumnContentChecker(true, true, "K", "0", "2", "0", "2", "-");
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail("aA-bB@email.com", new CheckerParams("AaA", "BbB")));

        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("aa-BB@email.com", new CheckerParams("AaA", "BbB")));
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("aa-bb@email.com", new CheckerParams("AaA", "BbB")));

        emailVerify = emailVerify.addLocalPartColumnContentChecker(true, true, "L", "0", "2", "0", "2", "-");
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail("aa-bb@email.com", new CheckerParams("AaA", "BbB")));

        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("aa-BB@email.com", new CheckerParams("AaA", "BbB")));
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("Aa-Bb@email.com", new CheckerParams("AaA", "BbB")));

        emailVerify = emailVerify.addLocalPartColumnContentChecker(true, true, "U", "0", "2", "0", "2", "-");
        assertEquals(EmailVerifyResult.VALID, emailVerify.checkEmail("AA-BB@email.com", new CheckerParams("AaA", "BbB")));

        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("aa-BB@email.com", new CheckerParams("AaA", "BbB")));
        assertEquals(EmailVerifyResult.CORRECTED, emailVerify.checkEmail("aa-bb@email.com", new CheckerParams("AaA", "BbB")));

    }
}