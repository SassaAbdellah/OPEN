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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.fhg.fokus.openride.webclient.backing;

import de.fhg.fokus.openride.monitoring.MonitoringControllerBeanLocal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;

/**
 *
 * @author bdi
 */
public class Monitoring {
    private long users;
    private long registrationsToday;
    private Set<String> activeUsers = new HashSet<String>();
    private long bookingsAll;
    private long bookingsToday;
    private long offersAll;
    private long offersToday;
    private long searchesAll;
    private long searchesToday;
    private long matchesAll;
    private long matchesToday;

    private final int YEAR_2010 = 2010;
    private final int AUGUST = 7;
    private final int DAY_31 = 31;
    private final int DAY_6 = 6;
    private final int SEPTEMBER = 8;

    @EJB
    MonitoringControllerBeanLocal monitoringControllerBean;

    @PostConstruct
    public void init() {
        Date today = new Date();
        Calendar cal = Calendar.getInstance();

        cal.set(YEAR_2010, AUGUST, DAY_31, 0, 0);
        users = monitoringControllerBean.getCountForTotalCustomers(cal.getTime());

        cal.set(YEAR_2010, SEPTEMBER, DAY_6, 0, 0);
        offersAll = monitoringControllerBean.getTotalDrivesCount(cal.getTime());
        offersToday = monitoringControllerBean.getDrivesCountBetweenDates(getTodaysStartingTime(), getTodaysEndingTime());
        searchesAll = monitoringControllerBean.getTotalRideSearchCount(cal.getTime());
        searchesToday = monitoringControllerBean.getRideSearchCountBetweenDates(getTodaysStartingTime(), getTodaysEndingTime());
        bookingsAll = monitoringControllerBean.getTotalBookingsCount(cal.getTime());
        bookingsToday = monitoringControllerBean.getBookingsCountBetweenDates(getTodaysStartingTime(), getTodaysEndingTime());
        registrationsToday = monitoringControllerBean.getRegistratedUsersCountByDate(today);
        matchesAll = monitoringControllerBean.getTotalMatchesCountAfterDate(cal.getTime());
        matchesToday = monitoringControllerBean.getMatchesCountBetweenDates(getTodaysStartingTime(), getTodaysEndingTime());
    }

    private Date getTodaysStartingTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        Calendar calCut = Calendar.getInstance();
        calCut.set(year, month, day, 0, 0);

        return calCut.getTime();
    }

    private Date getTodaysEndingTime() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DATE);

        Calendar calCut = Calendar.getInstance();
        calCut.set(year, month, day + 1, 0, 0);

        return calCut.getTime();
    }

    public long getMatchesToday() {
        return matchesToday;
    }

    public void setMatchesToday(long matchesToday) {
        this.matchesToday = matchesToday;
    }

    public long getBookingsAll() {
        return bookingsAll;
    }

    public void setBookingsAll(long bookingsAll) {
        this.bookingsAll = bookingsAll;
    }

    public long getBookingsToday() {
        return bookingsToday;
    }

    public void setBookingsToday(long bookingsToday) {
        this.bookingsToday = bookingsToday;
    }

    public long getRegistrationsToday() {
        return registrationsToday;
    }

    public void setRegistrationsToday(long registrationsToday) {
        this.registrationsToday = registrationsToday;
    }

    public Set<String> getActiveUsers() {
        return activeUsers;
    }

    public void setActiveUsers(Set<String> activeUsers) {
        this.activeUsers = activeUsers;
    }

    public long getMatchesAll() {
        return matchesAll;
    }

    public void setMatchesAll(long matchesAll) {
        this.matchesAll = matchesAll;
    }

    public long getOffersAll() {
        return offersAll;
    }

    public void setOffersAll(long offersAll) {
        this.offersAll = offersAll;
    }

    public long getOffersToday() {
        return offersToday;
    }

    public void setOffersToday(long offersToday) {
        this.offersToday = offersToday;
    }

    public long getSearchesAll() {
        return searchesAll;
    }

    public void setSearchesAll(long searchesAll) {
        this.searchesAll = searchesAll;
    }

    public long getSearchesToday() {
        return searchesToday;
    }

    public void setSearchesToday(long searchesToday) {
        this.searchesToday = searchesToday;
    }

    public long getUsers() {
        return users;
    }

    public void setUsers(long users) {
        this.users = users;
    }

}
