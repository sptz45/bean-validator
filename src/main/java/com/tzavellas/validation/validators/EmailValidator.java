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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Perform email validations.
 * 
 * <p>Based on a script by <a href="mailto:stamhankar@hotmail.com">Sandeep V. Tamhankar</a>
 * http://javascript.internet.com</p>
 * 
 * <p>This implementation is not guaranteed to catch all possible errors in an email address.
 * For example, an address like nobody@noplace.somedog will pass validator, even though there
 * is no TLD "somedog".</p>
 * 
 * <p>This class is copied and modified from the Apache Jakarta
 * <a href="http://jakarta.apache.org/commons/validator/">commons-validator</a> project.</p>
 */
public class EmailValidator implements Validator<String> {

    private static final String SPECIAL_CHARS = "[\\000-\\037]\\(\\)<>@,;:'\\\\\\\"\\.\\[\\]\\0177";
    private static final String VALID_CHARS = "[^\\s" + SPECIAL_CHARS + "]";
    private static final String QUOTED_USER = "(\"[^\"]*\")";
    private static final String ATOM = VALID_CHARS + '+';
    private static final String WORD = "((" + VALID_CHARS + "|')+|" + QUOTED_USER + ")";

    private static final Pattern LEGAL_ASCII_PATTERN = Pattern.compile("^[\\0000-\\0177]+$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^(.+)@(.+)[^\\.]*$");
    private static final Pattern IP_DOMAIN_PATTERN = Pattern.compile(
            "^\\[(\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})[.](\\d{1,3})\\]$");
    
    private static final Pattern TLD_PATTERN = Pattern.compile("^\\p{Alpha}\\p{Alnum}{1,3}");
            
    private static final Pattern USER_PATTERN = Pattern.compile("^\\s*" + WORD + "(\\." + WORD + ")*$");
    private static final Pattern DOMAIN_PATTERN = Pattern.compile("^" + ATOM + "(\\." + ATOM + ")*\\s*$");


    /**
     * Checks if a field has a valid e-mail address.
     *
     * @param email The value validation is being performed on.  A <code>null</code>
     * value is considered invalid.
     * 
     * @return true if the email address is valid.
     */
    public boolean isValid(String email) {
        if (email == null) {
            return false;
        }

        if (!LEGAL_ASCII_PATTERN.matcher(email).matches()) {
            return false;
        }

        return isValidInternal(stripComments(email));
    }
    
    
    private boolean isValidInternal(String email) {
    	//Check the whole email address structure
        Matcher emailMatcher = EMAIL_PATTERN.matcher(email); 
        if (!emailMatcher.matches()) {
            return false;
        }

        if (!isValidUser(emailMatcher.group(1))) {
            return false;
        }

        if (!isValidDomain(emailMatcher.group(2))) {
            return false;
        }

        return true;
    }

    /**
     * Returns true if the domain component of an email address is valid.
     * 
     * @param domain being validatied.
     * 
     * @return true if the email address's domain is valid.
     */
    protected boolean isValidDomain(String domain) {
        boolean symbolic = false;
        Matcher ipAddressMatcher = IP_DOMAIN_PATTERN.matcher(domain);

        if (ipAddressMatcher.matches()) {
            if (!isValidIpAddress(ipAddressMatcher)) {
                return false;
            } else {
                return true;
            }
        } else {
            // Domain is symbolic name
            symbolic = DOMAIN_PATTERN.matcher(domain).matches();
        }

        if (symbolic) {
            if (!isValidSymbolicDomain(domain)) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }

    /**
     * Returns true if the user component of an email address is valid.
     * 
     * @param user being validated
     * 
     * @return true if the user name is valid.
     */
    protected boolean isValidUser(String user) {
        return USER_PATTERN.matcher(user).matches();
    }

    /**
     * Validates an IP address. Returns true if valid.
     * 
     * @param ipAddressMatcher Pattren matcher
     * 
     * @return true if the ip address is valid.
     */
    protected boolean isValidIpAddress(Matcher ipAddressMatcher) {
        for (int i = 1; i <= 4; i++) {
            String ipSegment = ipAddressMatcher.group(i);
            if (ipSegment == null || ipSegment.length() <= 0) {
                return false;
            }

            int iIpSegment = 0;
            
            try {
                iIpSegment = Integer.parseInt(ipSegment);
            } catch(NumberFormatException e) {
                return false;
            }

            if (iIpSegment > 255) {
                return false;
            }

        }
        return true;
    }

    /**
     * Validates a symbolic domain name.  Returns true if it's valid.
     * 
     * @param domain symbolic domain name
     * 
     * @return true if the symbolic domain name is valid.
     */
    protected boolean isValidSymbolicDomain(String domain) {
    	char[] chars2 = domain.toCharArray();
        int segments = 1;
        int topLevelStart = 0;
        for(int i = 0; i < chars2.length; i++) {
            if(chars2[i] == '.') {
                segments++;
                topLevelStart = i;
            }
        }
    	//TODO validate that segments do not contain invalid characters
    	if (segments > 1) {
    		String topLevel = domain.substring(topLevelStart + 1).trim();

    		if (!TLD_PATTERN.matcher(topLevel).matches()) {
    			return false;
    		}
            
    	} else {
    		// Make sure there's a host name preceding the authority.
    		return false; // for compatibility...
    	}

        return true;
    }
    
    /**
     * For now it just returns the emaill address with the comments.
     * 
     * TODO: port to use java.util.regex
     * 
     * Recursively remove comments, and replace with a single space.  The simpler
     * regexps in the Email Addressing FAQ are imperfect - they will miss escaped
     * chars in atoms, for example.
     * 
     * Derived From Mail::RFC822::Address
     * 
     * @param emailStr The email address
     * @return address with comments removed.
    */
    private String stripComments(String emailStr)  {
//     String input = emailStr;
     String result = emailStr;
//     String commentPat = "s/^((?:[^\"\\\\]|\\\\.)*(?:\"(?:[^\"\\\\]|\\\\.)*\"(?:[^\"\\\\]|\111111\\\\.)*)*)\\((?:[^()\\\\]|\\\\.)*\\)/$1 /osx";
//     Perl5Util commentMatcher = new Perl5Util();
//     result = commentMatcher.substitute(commentPat,input);
//     // This really needs to be =~ or Perl5Matcher comparison
//     while (!result.equals(input)) {
//        input = result;
//        result = commentMatcher.substitute(commentPat,input);
//     }
     return result;

    }
}
