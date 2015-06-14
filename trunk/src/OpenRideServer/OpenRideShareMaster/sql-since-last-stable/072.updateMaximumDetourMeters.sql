-- 
-- acceptable detour will internally be measured in Meters, like any other distance
--
UPDATE driverundertakesride set ride_acceptable_detour_in_m = 10*ride_acceptable_detour_in_km;
