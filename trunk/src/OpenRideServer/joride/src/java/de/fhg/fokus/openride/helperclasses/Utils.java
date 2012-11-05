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

package de.fhg.fokus.openride.helperclasses;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;
import java.util.List;

/**
 *
 * @author pab
 */
public class Utils {
    /**
     * This method creates an object of type XStream for JSON, and adds a given list of mappings to the context.
     * @return
     */
    public static XStream getJasonXStreamer(List typesToMap){
        XStream x = new XStream(new JettisonMappedXmlDriver());
        x.setMode(XStream.XPATH_RELATIVE_REFERENCES);
        String simpleName;
        if(typesToMap != null){
            // the list is filled
            for(Object o: typesToMap){
                simpleName = o.getClass().getSimpleName();
                x.alias(""+simpleName, o.getClass());
                System.out.println("Util->simpleName: "+simpleName);
            }
            return x;
        }else{
            // the list is not filled
            return null;
        }
    }
}
