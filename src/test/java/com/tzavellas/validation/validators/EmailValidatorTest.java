/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.tzavellas.validation.validators;

import junit.framework.TestCase;

/**                                                       
 * Performs Validation Test for e-mail validations.
 * 
 * <p>This class is copied and modified from the Apache Jakarta
 * <a href="http://jakarta.apache.org/commons/validator/">commons-validator</a> project.</p>
 */
public class EmailValidatorTest extends TestCase {           

   private EmailValidator validator = new EmailValidator();
   
   private void assertValid(String... emails) {
	   for (String email : emails)
		   assertTrue("'" + email + "' is a valid email address",
				   	validator.isValid(email));
   }
   
   private void assertInvalid(String... emails) {
	   for (String email : emails)
		   assertFalse("'" + email + "' is not a valid email address",
				   validator.isValid(email));
   }

   /**
    * Tests the e-mail validation.
    */
   public void testEmail() {
	   assertValid("jsmith@apache.org");
   }
    
   /**
    * Tests the email validation with numeric domains.
    */
    public void testEmailWithNumericAddress() {
        assertValid("someone@[216.109.118.76]",
        			"someone@yahoo.com");
    }

    /**
     * Tests the e-mail validation.
     */
    public void testEmailExtension() {
        assertValid("jsmith@apache.org",
        			"jsmith@apache.com", 
        			"jsmith@apache.net",
        			"jsmith@apache.info");
        
        assertInvalid("jsmith@apache.",
        				"jsmith@apache.c",
        				//TODO see if this is valid "someone@yahoo.museum",
        				"someone@yahoo.mu-seum");
    }

   /**
    * <p>Tests the e-mail validation with a dash in 
    * the address.</p>
    */
   public void testEmailWithDash() {
      assertValid("andy.noble@data-workshop.com");
      
      assertInvalid("andy-noble@data-workshop.-com",
    		  		"andy-noble@data-workshop.c-om",
    		  		"andy-noble@data-workshop.co-m");
   }

   /**
    * Tests the e-mail validation with a dot at the end of 
    * the address.
    */
   public void testEmailWithDotEnd() {
	   assertInvalid("andy.noble@data-workshop.com.");
   }

    /**
     * Tests the e-mail validation with an RCS-noncompliant character in
     * the address.
     */
    public void testEmailWithBogusCharacter(){
        
        assertInvalid("andy.noble@\u008fdata-workshop.com");
    
        // The ' character is valid in an email username.
        assertValid("andy.o'reilly@data-workshop.com");
        
        // But not in the domain name.
        assertInvalid("andy@o'reilly.data-workshop.com");

        assertValid("foo+bar@i.am.not.in.us.example.com");
    }
   
   /**
    * Tests the email validation with commas.
    */
    public void testEmailWithCommas() {
    	assertInvalid("joeblow@apa,che.org",
        				"joeblow@apache.o,rg",
        				"joeblow@apache,org");
    }
   
   /**
    * Tests the email validation with spaces.
    */
    public void testEmailWithSpaces() {
        assertValid(" joeblow@apache.org", "joeblow@apache.org ");
        
        assertInvalid("joeblow @apache.org",
        			"joeblow@ apache.org",
        			"joe blow@apache.org ",
        			"joeblow@apa che.org ");
    }

   /**
    * Tests the email validation with ascii control characters.
    * (i.e. Ascii chars 0 - 31 and 127)
    */
    //TODO make this test pass
    public void _testEmailWithControlChars() {
        EmailValidator validator = new EmailValidator();
        for (char c = 0; c < 32; c++) {
            assertFalse("Test control char " + ((int)c), validator.isValid("foo" + c + "bar@domain.com"));
        }
        assertFalse("Test control char 127", validator.isValid("foo" + ((char)127) + "bar@domain.com"));
    }

    /**
     * Write this test according to parts of RFC, as opposed to the type of character
     * that is being tested.
     *
     * <p><b>FIXME</b>: This test fails so disable it with a leading _ for 1.1.4 release.
     * The real solution is to fix the email parsing.
     *
     * @throws ValidatorException
     */
//    public void _testEmailUserName() throws ValidatorException {
//        ValueBean info = new ValueBean();
//        info.setValue("joe1blow@apache.org");
//        valueTest(info, true);
//        info.setValue("joe$blow@apache.org");
//        valueTest(info, true);
//        info.setValue("joe-@apache.org");
//        valueTest(info, true);
//        info.setValue("joe_@apache.org");
//        valueTest(info, true);
//
//        //UnQuoted Special characters are invalid
//
//        info.setValue("joe.@apache.org");
//        valueTest(info, false);
//        info.setValue("joe+@apache.org");
//        valueTest(info, false);
//        info.setValue("joe!@apache.org");
//        valueTest(info, false);
//        info.setValue("joe*@apache.org");
//        valueTest(info, false);
//        info.setValue("joe'@apache.org");
//        valueTest(info, false);
//        info.setValue("joe(@apache.org");
//        valueTest(info, false);
//        info.setValue("joe)@apache.org");
//        valueTest(info, false);
//        info.setValue("joe,@apache.org");
//        valueTest(info, false);
//        info.setValue("joe%45@apache.org");
//        valueTest(info, false);
//        info.setValue("joe;@apache.org");
//        valueTest(info, false);
//        info.setValue("joe?@apache.org");
//        valueTest(info, false);
//        info.setValue("joe&@apache.org");
//        valueTest(info, false);
//        info.setValue("joe=@apache.org");
//        valueTest(info, false);
//
//        //Quoted Special characters are valid
//        info.setValue("\"joe.\"@apache.org");
//        valueTest(info, true);
//        info.setValue("\"joe+\"@apache.org");
//        valueTest(info, true);
//        info.setValue("\"joe!\"@apache.org");
//        valueTest(info, true);
//        info.setValue("\"joe*\"@apache.org");
//        valueTest(info, true);
//        info.setValue("\"joe'\"@apache.org");
//        valueTest(info, true);
//        info.setValue("\"joe(\"@apache.org");
//        valueTest(info, true);
//        info.setValue("\"joe)\"@apache.org");
//        valueTest(info, true);
//        info.setValue("\"joe,\"@apache.org");
//        valueTest(info, true);
//        info.setValue("\"joe%45\"@apache.org");
//        valueTest(info, true);
//        info.setValue("\"joe;\"@apache.org");
//        valueTest(info, true);
//        info.setValue("\"joe?\"@apache.org");
//        valueTest(info, true);
//        info.setValue("\"joe&\"@apache.org");
//        valueTest(info, true);
//        info.setValue("\"joe=\"@apache.org");
//        valueTest(info, true);
//
//    }

    /**
     * These test values derive directly from RFC 822 &
     * Mail::RFC822::Address & RFC::RFC822::Address perl test.pl
     * For traceability don't combine these test values with other tests.
     */
    TestPair[] testEmailFromPerl = {
        new TestPair("abigail@example.com", true),
        new TestPair("abigail@example.com ", true),
        new TestPair(" abigail@example.com", true),
        new TestPair("abigail @example.com ", true),
        new TestPair("*@example.net", true),
        new TestPair("\"\\\"\"@foo.bar", true),
        new TestPair("fred&barny@example.com", true),
        new TestPair("---@example.com", true),
        new TestPair("foo-bar@example.net", true),
        new TestPair("\"127.0.0.1\"@[127.0.0.1]", true),
        new TestPair("Abigail <abigail@example.com>", true),
        new TestPair("Abigail<abigail@example.com>", true),
        new TestPair("Abigail<@a,@b,@c:abigail@example.com>", true),
        new TestPair("\"This is a phrase\"<abigail@example.com>", true),
        new TestPair("\"Abigail \"<abigail@example.com>", true),
        new TestPair("\"Joe & J. Harvey\" <example @Org>", true),
        new TestPair("Abigail <abigail @ example.com>", true),
        new TestPair("Abigail made this <  abigail   @   example  .    com    >", true),
        new TestPair("Abigail(the bitch)@example.com", true),
        new TestPair("Abigail <abigail @ example . (bar) com >", true),
        new TestPair("Abigail < (one)  abigail (two) @(three)example . (bar) com (quz) >", true),
        new TestPair("Abigail (foo) (((baz)(nested) (comment)) ! ) < (one)  abigail (two) @(three)example . (bar) com (quz) >", true),
        new TestPair("Abigail <abigail(fo\\(o)@example.com>", true),
        new TestPair("Abigail <abigail(fo\\)o)@example.com> ", true),
        new TestPair("(foo) abigail@example.com", true),
        new TestPair("abigail@example.com (foo)", true),
        new TestPair("\"Abi\\\"gail\" <abigail@example.com>", true),
        new TestPair("abigail@[example.com]", true),
        new TestPair("abigail@[exa\\[ple.com]", true),
        new TestPair("abigail@[exa\\]ple.com]", true),
        new TestPair("\":sysmail\"@  Some-Group. Some-Org", true),
        new TestPair("Muhammed.(I am  the greatest) Ali @(the)Vegas.WBA", true),
        new TestPair("mailbox.sub1.sub2@this-domain", true),
        new TestPair("sub-net.mailbox@sub-domain.domain", true),
        new TestPair("name:;", true),
        new TestPair("':;", true),
        new TestPair("name:   ;", true),
        new TestPair("Alfred Neuman <Neuman@BBN-TENEXA>", true),
        new TestPair("Neuman@BBN-TENEXA", true),
        new TestPair("\"George, Ted\" <Shared@Group.Arpanet>", true),
        new TestPair("Wilt . (the  Stilt) Chamberlain@NBA.US", true),
        new TestPair("Cruisers:  Port@Portugal, Jones@SEA;", true),
        new TestPair("$@[]", true),
        new TestPair("*()@[]", true),
        new TestPair("\"quoted ( brackets\" ( a comment )@example.com", true),
        new TestPair("\"Joe & J. Harvey\"\\x0D\\x0A     <ddd\\@ Org>", true),
        new TestPair("\"Joe &\\x0D\\x0A J. Harvey\" <ddd \\@ Org>", true),
        new TestPair("Gourmets:  Pompous Person <WhoZiWhatZit\\@Cordon-Bleu>,\\x0D\\x0A" +
            "        Childs\\@WGBH.Boston, \"Galloping Gourmet\"\\@\\x0D\\x0A" +
            "        ANT.Down-Under (Australian National Television),\\x0D\\x0A" +
            "        Cheapie\\@Discount-Liquors;", true),
        new TestPair("   Just a string", false),
        new TestPair("string", false),
        new TestPair("(comment)", false),
        new TestPair("()@example.com", false),
        new TestPair("fred(&)barny@example.com", false),
        new TestPair("fred\\ barny@example.com", false),
        new TestPair("Abigail <abi gail @ example.com>", false),
        new TestPair("Abigail <abigail(fo(o)@example.com>", false),
        new TestPair("Abigail <abigail(fo)o)@example.com>", false),
        new TestPair("\"Abi\"gail\" <abigail@example.com>", false),
        new TestPair("abigail@[exa]ple.com]", false),
        new TestPair("abigail@[exa[ple.com]", false),
        new TestPair("abigail@[exaple].com]", false),
        new TestPair("abigail@", false),
        new TestPair("@example.com", false),
        new TestPair("phrase: abigail@example.com abigail@example.com ;", false),
        new TestPair("invalid�char@example.com", false)
    };

    /**
     * Write this test based on perl Mail::RFC822::Address
     * which takes its example email address directly from RFC822
     * 
     * @throws ValidatorException
     * 
     * FIXME This test fails so disable it with a leading _ for 1.1.4 release.
     * The real solution is to fix the email parsing.
     */
    public void _testEmailFromPerl() {
        for (int index = 0; index < testEmailFromPerl.length; index++) {
        	assertEquals(testEmailFromPerl[index].valid,
        			validator.isValid(testEmailFromPerl[index].item));
        }
    }
}                                                     
