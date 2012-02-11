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

import java.security.SecureRandom;
import java.util.ArrayList;

/**
 *
 * @author Tilo Kussatz <tilo at kussatz.com>
 */
public class RegistrationPassGenerator {

    private SecureRandom random = new SecureRandom();

    /**
     *
     * @param passLength The desired number of characters of the generated pass.
     * @return A random string of the given length, generated using a cryptographically strong random number generator.
     */
    private String nextRegistrationPass(int passLength) {
        StringBuffer sb = new StringBuffer(passLength);
        int c = 'A';
        int r1 = 0;

        for (int i = 0; i < passLength; i++) {
            r1 = (int) (random.nextFloat() * 3);
            switch (r1) {
                case 0:
                    c = '0' + (int) (random.nextFloat() * 10);
                    break;
                case 1:
                    c = 'a' + (int) (random.nextFloat() * 26);
                    break;
                case 2:
                    c = 'A' + (int) (random.nextFloat() * 26);
                    break;
            }
            sb.append((char) c);
        }

        return sb.toString();
    }

    /**
     *
     * @param passCount The number of registration passes to be generated.
     * @param passLength The desired number of characters per pass (excluding prefix).
     * @param passPrefix A string to be prepended to the passes.
     * @return An ArrayList containing the generated registration passes.
     */
    public ArrayList<String> generatePasses(int passCount, int passLength, String passPrefix) {
        ArrayList<String> passes = new ArrayList<String>();
        for (int i = passCount; i > 0; i--) {
            passes.add(passPrefix + nextRegistrationPass(passLength));
        }
        return passes;
    }
}
