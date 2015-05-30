


--
-- update drive route points to new srid
--
--
update drive_route_point set coordinate_c=st_transform(st_setSrid(st_makepoint(coordinate[0], coordinate[1]), 4326), orsGetSrid());
--
--
--
--
update riderundertakesride set startpt_c = st_transform(st_setSrid(st_makepoint(startpt[0], startpt[1]), 4326), orsGetSrid());
update riderundertakesride set endpt_c   = st_transform(st_setSrid(st_makepoint(endpt[0]  , endpt[1]  ), 4326), orsGetSrid());
