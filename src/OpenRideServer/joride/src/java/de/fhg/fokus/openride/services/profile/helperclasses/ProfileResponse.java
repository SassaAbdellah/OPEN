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

package de.fhg.fokus.openride.services.profile.helperclasses;

/**
 *
 * @author tku
 */
public class ProfileResponse {

    private String firstName;
    private String lastName;
    private Character gender;
    private Long dateOfBirth;
    private String email;
    private String mobilePhoneNumber;
    private String fixedPhoneNumber;
    private String streetAddress;
    private Integer zipCode;
    private String city;
    private Character isSmoker;
    private Short licenseDate;
    private String carColour;
    private String carBrand;    
    private Short carBuildYear;
    private String carPlateNo;


    public ProfileResponse() {
    }

    public ProfileResponse(String firstName, String lastName, Character gender, Long dateOfBirth, String email, String mobilePhoneNumber, String fixedPhoneNumber, String streetAddress, Integer zipCode, String city, Character isSmoker, Short licenseDate, String carColour, String carBrand, Short carBuildYear, String carPlateNo) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.fixedPhoneNumber = fixedPhoneNumber;
        this.streetAddress = streetAddress;
        this.zipCode = zipCode;
        this.city = city;
        this.isSmoker = isSmoker;
        this.licenseDate = licenseDate;
        this.carColour = carColour;
        this.carBrand = carBrand;
        this.carBuildYear = carBuildYear;
        this.carPlateNo = carPlateNo;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Long getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Long dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getFixedPhoneNumber() {
        return fixedPhoneNumber;
    }

    public void setFixedPhoneNumber(String fixedPhoneNumber) {
        this.fixedPhoneNumber = fixedPhoneNumber;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public Character getIsSmoker() {
        return isSmoker;
    }

    public void setIsSmoker(Character isSmoker) {
        this.isSmoker = isSmoker;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Short getLicenseDate() {
        return licenseDate;
    }

    public void setLicenseDate(Short licenseDate) {
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

    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public Short getCarBuildYear() {
        return carBuildYear;
    }

    public void setCarBuildYear(Short carBuildYear) {
        this.carBuildYear = carBuildYear;
    }

    public String getCarColour() {
        return carColour;
    }

    public void setCarColour(String carColour) {
        this.carColour = carColour;
    }

    public String getCarPlateNo() {
        return carPlateNo;
    }

    public void setCarPlateNo(String carPlateNo) {
        this.carPlateNo = carPlateNo;
    }



}
