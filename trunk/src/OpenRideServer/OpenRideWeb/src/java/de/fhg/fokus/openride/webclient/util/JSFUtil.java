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

import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

/**
 * Utility class with useful methods for JSF/Web apps
 */
public class JSFUtil {

    public static ValueBinding getValueBinding(String expression) {
        FacesContext context = FacesContext.getCurrentInstance();
        return context.getApplication().createValueBinding(expression);
    }

    public static String getValueBindingString(String expression) {
        FacesContext context = FacesContext.getCurrentInstance();
        ValueBinding currentBinding = context.getApplication().createValueBinding(expression);
        return (String) currentBinding.getValue(context);

    }

    public static Object getManagedObject(String objectName) {
        FacesContext context = FacesContext.getCurrentInstance();
        Object requestedObject = context.getApplication().getVariableResolver().resolveVariable(context, objectName);
        return requestedObject;
    }

    public static void storeOnSession(FacesContext ctx, String key, Object object) {
        Map sessionState = ctx.getExternalContext().getSessionMap();
        sessionState.put(key, object);
    }

}
