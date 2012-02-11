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

package de.fhg.fokus.openride.webclient;

import java.util.Date;

/**
 * Used to store user profile info in session - accessed throughout the application.
 * 
 * @author Tilo Kussatz <tilo at kussatz.com>
 */
public class UserBean {

    private boolean isLoggedIn;

    private int id;
    private String username;
    private String password;    
    private String firstName;
    private String lastName;
    private char gender;
    private Date dateOfBirth;
    private String email;
    private String mobilePhoneNumber;
    private String fixedPhoneNumber;
    private String streetAddress;
    private int zipCode;
    private String city;
    private Boolean isSmoker;
    private Date licenseDate;

    // Driver prefs
    private int driverPrefAge;
    private char driverPrefGender;
    private char driverPrefIsSmoker;
    // Rider prefs
    private int riderPrefAge;
    private char riderPrefGender;
    private char riderPrefIsSmoker;

    public UserBean() {
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public boolean isIsLoggedIn() {
        return isLoggedIn;
    }

    public void setIsLoggedIn(boolean isLoggedIn) {
        this.isLoggedIn = isLoggedIn;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getFixedPhoneNumber() {
        return fixedPhoneNumber;
    }

    public void setFixedPhoneNumber(String fixedPhoneNumber) {
        this.fixedPhoneNumber = fixedPhoneNumber;
    }

    public Boolean isIsSmoker() {
        return isSmoker;
    }

    public void setIsSmoker(Boolean isSmoker) {
        this.isSmoker = isSmoker;
    }

    public Date getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Date licenseDate) {
        this.licenseDate = licenseDate;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getDriverPrefAge() {
        return driverPrefAge;
    }

    public void setDriverPrefAge(int driverPrefAge) {
        this.driverPrefAge = driverPrefAge;
    }

    public char getDriverPrefGender() {
        return driverPrefGender;
    }

    public void setDriverPrefGender(char driverPrefGender) {
        this.driverPrefGender = driverPrefGender;
    }

    public char isDriverPrefIsSmoker() {
        return driverPrefIsSmoker;
    }

    public void setDriverPrefIsSmoker(char driverPrefIsSmoker) {
        this.driverPrefIsSmoker = driverPrefIsSmoker;
    }

    public int getRiderPrefAge() {
        return riderPrefAge;
    }

    public void setRiderPrefAge(int riderPrefAge) {
        this.riderPrefAge = riderPrefAge;
    }

    public char getRiderPrefGender() {
        return riderPrefGender;
    }

    public void setRiderPrefGender(char riderPrefGender) {
        this.riderPrefGender = riderPrefGender;
    }

    public char isRiderPrefIsSmoker() {
        return riderPrefIsSmoker;
    }

    public void setRiderPrefIsSmoker(char riderPrefIsSmoker) {
        this.riderPrefIsSmoker = riderPrefIsSmoker;
    }

    
}
