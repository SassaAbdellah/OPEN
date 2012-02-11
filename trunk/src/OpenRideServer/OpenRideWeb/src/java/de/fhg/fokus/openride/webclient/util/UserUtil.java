/*
    OpenRide -- Car Sharing 2.0
    Copyright (C) 2010  Fraunhofer Institute for Open Communication Systems (FOKUS)

    Fraunhofer FOKUS
    Kaiserin-Augusta-Allee 31
    10589 Berlin
    Tel: +49 30 3463-7000
    info@fokus.fraunhofer.de

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU Affero General Public License Version 3 as
    published by the Free Software Foundation.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU Affero General Public License for more details.

    You should have received a copy of the GNU Affero General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package de.fhg.fokus.openride.webclient.util;

import de.fhg.fokus.openride.customerprofile.CustomerEntity;
import de.fhg.fokus.openride.webclient.UserBean;

public class UserUtil {

    UserUtil() {
    }

    public static void copyUserProperties(CustomerEntity userFrom, UserBean userTo) {
        
        if (userFrom.getIsLoggedIn() != null)
            userTo.setIsLoggedIn(userFrom.getIsLoggedIn());

        userTo.setId(userFrom.getCustId());

        userTo.setUsername(userFrom.getCustNickname());
        userTo.setPassword(userFrom.getCustPasswd());        
        userTo.setFirstName(userFrom.getCustFirstname());
        userTo.setLastName(userFrom.getCustLastname());
        if (userFrom.getCustGender() != null)
            userTo.setGender(userFrom.getCustGender());
        if (userFrom.getCustDateofbirth() != null)
            userTo.setDateOfBirth(userFrom.getCustDateofbirth());
        userTo.setEmail(userFrom.getCustEmail());
        if (userFrom.getCustMobilephoneno() != null)
            userTo.setMobilePhoneNumber(userFrom.getCustMobilephoneno());
        if (userFrom.getCustFixedphoneno() != null)
            userTo.setFixedPhoneNumber(userFrom.getCustFixedphoneno());
        if (userFrom.getCustAddrStreet() != null)
            userTo.setStreetAddress(userFrom.getCustAddrStreet());
        if (userFrom.getCustAddrZipcode() != null)
            userTo.setZipCode(userFrom.getCustAddrZipcode());
        if (userFrom.getCustAddrCity() != null)
            userTo.setCity(userFrom.getCustAddrCity());
        if (userFrom.getCustIssmoker() != null)
            userTo.setIsSmoker(userFrom.getCustIssmoker());
        if (userFrom.getCustLicensedate() != null)
            userTo.setLicenseDate(userFrom.getCustLicensedate());

        if (userFrom.getCustDriverprefAge() != null)
            userTo.setDriverPrefAge(userFrom.getCustDriverprefAge());
        if (userFrom.getCustDriverprefGender() != null)
            userTo.setDriverPrefGender(userFrom.getCustDriverprefGender());
        if (userFrom.getCustDriverprefIssmoker() != null)
            userTo.setDriverPrefIsSmoker(userFrom.getCustDriverprefIssmoker());

        if (userFrom.getCustRiderprefAge() != null)
            userTo.setRiderPrefAge(userFrom.getCustRiderprefAge());
        if (userFrom.getCustRiderprefGender() != null)
            userTo.setRiderPrefGender(userFrom.getCustRiderprefGender());
        if (userFrom.getCustRiderprefIssmoker() != null)
            userTo.setRiderPrefIsSmoker(userFrom.getCustRiderprefIssmoker());

    }

    /**
     * This method is called upon User logout to clear the user info stored in session.
     * @param user
     */
    public void clearUserProperties(UserBean user) {
        //most important:
        user.setIsLoggedIn(false);
        //and then:
        user.setId(-1);
        user.setUsername("");
        user.setPassword("");
        user.setFirstName("");
        user.setLastName("");
        user.setEmail("");
        user.setGender(" ".charAt(0));
        user.setIsLoggedIn(false);
    }
}
