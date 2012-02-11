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

import java.io.IOException;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Tilo Kussatz <tilo at kussatz.com>
 */
public class Welcome {

    private Logger logger = Logger.getLogger(this.getClass().toString());

    /**
     *
     * @return
     * @throws IOException
     */
    public String logout() throws IOException {

        logger.info("Welcome_Backing.logout");

        // Destroy active session
        ExternalContext ectx = FacesContext.getCurrentInstance().getExternalContext();
        HttpSession session = (HttpSession) ectx.getSession(false);
        session.invalidate();

        return "loggedOut";

    }

}
