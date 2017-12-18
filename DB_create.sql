CREATE TABLE plate_areas(id       integer primary key AUTOINCREMENT,
                         plate1   char(2) not null,
                         plate2   char(2) not null,
                         position varchar(25),
                         minlon   float,
                         maxlon   float,
                         minlat   float,
                         maxlat   float,
                         constraint plate_areas_ck1
                              check(plate1 <= plate2),
                         constraint plate_areas_uq
                              unique(minlon,maxlon,minlat,maxlat),
                         constraint plate_areas_fk1
                              foreign key(plate1) references plates(code),
                         constraint plate_areas_fk2
                              foreign key(plate2) references plates(code)
                       );
CREATE TABLE plates(code char(2) not null primary key,
                    name varchar(30) not null unique,
                    info varchar(100));
CREATE TABLE quakes(id int not null primary key,  UTC_date   datetime not null,  latitude   float not null  check (latitude between -90 and 90),  longitude  float not null  check (longitude between -180 and 180),  depth      int not null,  magnitude  float not null,  region     varchar(100), area_id int references plate_areas(id),  constraint quakes_uq    unique(UTC_date,latitude,longitude));

CREATE TRIGGER quakes_trg
after insert on quakes
for each row
begin
  update quakes
  set area_id =
 (select id
  from (select pa.id, 1 as pref    -- Places in a plate boundary
        from (select new.latitude,
                    new.longitude,
                    id,
                    plate1,
                    plate2,
                    (new.latitude - (maxlat + minlat)/2)
                    *(new.latitude - (maxlat + minlat)/2)
                    +(new.longitude - (maxlon + minlon)/2)
                    *(new.longitude - (maxlon + minlon)/2) dist
             from plate_areas
             where new.latitude between minlat and maxlat
               and new.longitude between minlon and maxlon
               and plate1<>plate2) pa
            join (select latitude, longitude, min(dist) as dist
                  from (select new.latitude, new.longitude, id,
                               plate1, plate2,
                               (new.latitude - (maxlat + minlat)/2)
                               *(new.latitude - (maxlat + minlat)/2)
                               +(new.longitude - (maxlon + minlon)/2)
                               *(new.longitude - (maxlon + minlon)/2) dist
                        from plate_areas
                        where new.latitude between minlat and maxlat
                          and new.longitude between minlon and maxlon
                          and plate1<>plate2) pab
                  group by latitude, longitude) px
               on px.latitude = pa.latitude
              and px.longitude = pa.longitude
              and px.dist = pa.dist -- Boundary we are closest to the middle of
       union all
       select pa2.id, 2   -- Places outside plate known plate boundaries
       from (select p.code
             from (select group_concat(x.area) areas
                   -- Put together boundaries found to N, E, S and W
                   from (select area
                         from (select pa.plate1||','||pa.plate2 area
                               from plate_areas pa
                               where pa.minlon <= new.longitude
                                 and new.latitude between pa.minlat
                                                      and pa.maxlat
                                 and new.longitude - pa.minlon =
                                 (select min(new.longitude - p.minlon)
                                  from plate_areas p
                                  where p.minlon <= new.longitude
                                    and new.latitude between p.minlat
                                                         and p.maxlat)) minlon
                         union all
                         select area
                         from (select pa.plate1||','||pa.plate2 area
                               from plate_areas pa
                               where pa.maxlon >= new.longitude
                                 and new.latitude between pa.minlat
                                                      and pa.maxlat
                                 and pa.maxlon - new.longitude =
                                 (select min(p.maxlon - new.longitude)
                                  from plate_areas p
                                  where p.maxlon >= new.longitude
                                    and new.latitude between p.minlat
                                                         and p.maxlat)) maxlon
                         union all
                         select area
                         from (select pa.plate1||','||pa.plate2 area
                               from plate_areas pa
                               where pa.minlat <= new.latitude
                                 and new.longitude between pa.minlon
                                                       and pa.maxlon
                                 and new.latitude - pa.minlat =
                                 (select min(new.latitude - p.minlat)
                                  from plate_areas p
                                  where p.minlat <= new.latitude
                                    and new.longitude between p.minlon
                                                          and p.maxlon)) minlat
                         union all
                         select area
                         from (select pa.plate1||','||pa.plate2 area
                               from plate_areas pa
                               where pa.maxlat >= new.latitude
                                 and new.longitude between pa.minlon
                                                       and pa.maxlon
                                 and pa.maxlat - new.latitude =
                                 (select min(p.maxlat - new.latitude)
                                  from plate_areas p
                                  where p.maxlat >= new.latitude
                                    and new.longitude between p.minlon
                                                          and p.maxlon)) maxlat
                   ) x) y
                cross join
                  (select pl.code
                   from plates pl
                       join plate_areas pp
                        on pp.plate1 = pl.code
                       and pp.plate1 = pp.plate2
                       and ((pp.minlon < pp.maxlon
                            and new.longitude between pp.minlon
                                                  and pp.maxlon)
                          or (pp.minlon > pp.maxlon
                              and (new.longitude between pp.minlon
                                                     and 180
                               or new.longitude  between -180
                                                     and pp.maxlon)))
                       and (new.latitude between pp.minlat
                                             and pp.maxlat)) p
             order by length(replace(y.areas,p.code,'')) 
             limit 1) z  -- Plate code found most often "around" the place
          join plate_areas pa2
            on pa2.plate1 = z.code
           and pa2.plate2 = z.code
      order by pref
      limit 1))
 where id = new.id;
end
