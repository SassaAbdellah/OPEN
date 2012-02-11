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

package de.fhg.fokus.openride.webclient.backing;

import de.fhg.fokus.openride.customerprofile.RegistrationPassControllerLocal;
import de.fhg.fokus.openride.customerprofile.RegistrationPassEntity;
import de.fhg.fokus.openride.webclient.util.RegistrationPassGenerator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 * Backing class for registration_passes view offering methods used to generate pass code samples and to persist new codes through RegistrationPassControllerBean.
 *
 * @author Tilo Kussatz <tilo at kussatz.com>
 */
public class RegistrationPasses {

    private int passCount;
    private int passLength;
    private String passPrefix;

    private ArrayList<String> passes;
    private ArrayList<String> persistedPasses;

    private List<RegistrationPassEntity> invalidPasses;

    @EJB
    private RegistrationPassControllerLocal registrationPassControllerBean;

    private Logger logger = Logger.getLogger(this.getClass().toString());

    @PostConstruct
    public void initialize() {

        logger.info("RegistratinPasses_Backing.initialize");

        invalidPasses = registrationPassControllerBean.getInvalidPassCodes();
        Comparator<RegistrationPassEntity> comp = new PassesComparator();
        Collections.sort(invalidPasses, comp);

    }

    /**
     * Takes the values for passCount, passLength, passPrefix entered by the user and passes them on to a RegistrationPassGenerator instance to produce sample codes.
     *
     * (ArrayList "passes" is displayed in the registration_passes view as a datagrid.)
     */
    public void generate() {
        RegistrationPassGenerator rpg = new RegistrationPassGenerator();
        passes = rpg.generatePasses(passCount, passLength, passPrefix);
    }

    /**
     * Persists passcodes as RegistrationPass objects. Checks for duplicates that could not be added and replaces them with new codes, if needed.
     *
     * (ArrayList "persistedPasses" is displayed in the registration_passes view as a datagrid.)
     */
    public void persist() {
        RegistrationPassGenerator rpg = new RegistrationPassGenerator();
        passes = new ArrayList<String>();
        passes = rpg.generatePasses(passCount, passLength, passPrefix);
        persistedPasses = new ArrayList<String>();
        int duplicates = 0;
        int passId;
        for (int i=0; i<passCount; i++) {
            passId = registrationPassControllerBean.addRegistrationPass(passes.get(i));
            if (passId != -1)
                persistedPasses.add(passes.get(i));
            else
                duplicates++;
        }
        while (duplicates > 0) {
            passId = registrationPassControllerBean.addRegistrationPass(rpg.generatePasses(1, passLength, passPrefix).get(0));
            if (passId != -1) {
                persistedPasses.add(registrationPassControllerBean.getRegistrationPass(passId).getPasscode());
                duplicates--;
            }
                
        }
        passes.clear();
    }

    public int getPassCount() {
        return passCount;
    }

    public void setPassCount(int passCount) {
        this.passCount = passCount;
    }

    public int getPassLength() {
        return passLength;
    }

    public void setPassLength(int passLength) {
        this.passLength = passLength;
    }

    public String getPassPrefix() {
        return passPrefix;
    }

    public void setPassPrefix(String passPrefix) {
        this.passPrefix = passPrefix;
    }

    public ArrayList<String> getPasses() {
        return passes;
    }

    public void setPasses(ArrayList<String> passes) {
        this.passes = passes;
    }

    public ArrayList<String> getPersistedPasses() {
        return persistedPasses;
    }

    public void setPersistedPasses(ArrayList<String> persistedPasses) {
        this.persistedPasses = persistedPasses;
    }

    public List<RegistrationPassEntity> getInvalidPasses() {
        return invalidPasses;
    }

    public void setInvalidPasses(List<RegistrationPassEntity> invalidPasses) {
        this.invalidPasses = invalidPasses;
    }

    private class PassesComparator implements Comparator<RegistrationPassEntity>{

        @Override
        public int compare(RegistrationPassEntity o1, RegistrationPassEntity o2) {
            if (o1.getUsageDate().after(o2.getUsageDate())) {
                return -1;
            } else if (o1.getUsageDate().before(o2.getUsageDate())) {
                return 1;
            } else {
                return 0;
            }
        }

    }

}
