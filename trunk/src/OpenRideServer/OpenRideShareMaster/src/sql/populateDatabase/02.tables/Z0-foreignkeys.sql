
--
-- Name: drive_route_point_fk; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY drive_route_point
    ADD CONSTRAINT drive_route_point_fk FOREIGN KEY (drive_id) REFERENCES driverundertakesride(ride_id) DEFERRABLE INITIALLY DEFERRED;


--
-- Name: fk_accounthistory_cust_id; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY accounthistory
    ADD CONSTRAINT fk_accounthistory_cust_id FOREIGN KEY (cust_id) REFERENCES customer(cust_id);


--
-- Name: fk_cardetails_cust_id; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY cardetails
    ADD CONSTRAINT fk_cardetails_cust_id FOREIGN KEY (cust_id) REFERENCES customer(cust_id) ON DELETE RESTRICT;


--
-- Name: fk_driverundertakesride_cust_id; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY driverundertakesride
    ADD CONSTRAINT fk_driverundertakesride_cust_id FOREIGN KEY (cust_id) REFERENCES customer(cust_id);


--
-- Name: fk_favoritepoints_cust_id; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY favoritepoint
    ADD CONSTRAINT fk_favoritepoints_cust_id FOREIGN KEY (cust_id) REFERENCES customer(cust_id);


--
-- Name: fk_riderundertakesride_cust_id; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY riderundertakesride
    ADD CONSTRAINT fk_riderundertakesride_cust_id FOREIGN KEY (cust_id, account_timestamp) REFERENCES accounthistory(cust_id, account_timestamp);


--
-- Name: fk_riderundertakesride_ride_id; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY riderundertakesride
    ADD CONSTRAINT fk_riderundertakesride_ride_id FOREIGN KEY (ride_id) REFERENCES driverundertakesride(ride_id);


--
-- Name: message_recipient_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY message
    ADD CONSTRAINT message_recipient_id_fkey FOREIGN KEY (recipient_id) REFERENCES customer(cust_id) ON DELETE SET NULL;


--
-- Name: message_sender_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY message
    ADD CONSTRAINT message_sender_id_fkey FOREIGN KEY (sender_id) REFERENCES customer(cust_id) ON DELETE SET NULL;


--
-- Name: registration_pass_custid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY registration_pass
    ADD CONSTRAINT registration_pass_custid_fkey FOREIGN KEY (cust_id) REFERENCES customer(cust_id);


--
-- Name: rideid; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY match
    ADD CONSTRAINT rideid FOREIGN KEY (ride_id) REFERENCES driverundertakesride(ride_id);


--
-- Name: riderrouteid; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY match
    ADD CONSTRAINT riderrouteid FOREIGN KEY (riderroute_id) REFERENCES riderundertakesride(riderroute_id);


--
-- Name: route_point_ride_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY route_point
    ADD CONSTRAINT route_point_ride_id_fkey FOREIGN KEY (ride_id) REFERENCES driverundertakesride(ride_id);


--
-- Name: waypoint_ride_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: openride
--

ALTER TABLE ONLY waypoint
    ADD CONSTRAINT waypoint_ride_id_fkey FOREIGN KEY (ride_id) REFERENCES driverundertakesride(ride_id);

