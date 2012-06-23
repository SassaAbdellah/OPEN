############################################
###### CREATING A JORIDE INSTALLATION ######
############################################

 installing jORide from scratch can be done automatically
 using a number of scripts.
 

 1) OSLEVEL:

  
    Make sure, that the value that the $(hostname) command
    returns corresponds to an existing interface.

    I.e the command 

            ping $(hostname) 
  
    should work. Setting the hostname to "localhost"
    is probably a good Idea on a development machine.

 


    Run script oslevel/01.createusers.sh 

    To create users
    "postgres" with home directory "/opt/postgres"
    "openride" with home directory "/opt/openride"
   
 2) Postgres


 2.1) Preliminaries

    Install  readline-devel, geos-devel , proj-devel, zlib-devel (Fedora)


     copy contents of svn/trunc/configuration/postgres
     recursively to /opt/postgres
 

 2.2) Build Postgres

     * source /opt/postgres/openride.conf

     to get environment right

     run script /opt/postgres/postgres-build/01.build.postgres.sh
     to build postgres.
     This will build postgres and install it to 

      /opt/postgres/postgres-installation/<version>

     where <version>  is the version of postgres to be installed.

 2.3) Build Postgis


     * source /opt/postgres/openride.conf

     to get environment right

     run script /opt/postgres/postgis-build/01.build.postgis.sh

     This will build postgis and install it to aforementioned 
     postgres installation directory:

      /opt/postgres/postgis-installation/<version>

 

 2.4) Init Database

  Once again after sourcing  /opt/postgres/opernride.conf

  as user postgres run the script 

  "/opt/postgres/installationscripts/01initdb"
  
  this will initialize directory /opt/postgres/data   
  to contain the postgres databases.


2.5) Start postgres (postmaster)

  Once again after sourcing  /opt/postgres/opernride.conf
  as user postgres, run script
 
  /opt/postgres/installationScripts/02.startPostgres

  This will start the postgres daemon 
  (the daemon formerly known as postmaster)
  to run on localhost:5432.

  alternatively You can also start the       
  
  /opt/postgres/installationScripts/02.startPostgresDebug

  script, which is more verbose and useful for troubleshooting.

  The postres demon will report to the console like so:

   -bash-4.2$ ./02.startPostgres
   LOG:  database system was shut down at 2012-06-18 18:42:18 CEST
   LOG:  database system is ready to accept connections
   LOG:  autovacuum launcher started
    :

  
  The 02startPostgres and 02.startPostgresDebug scripts can also be used
  on development systems. For productive Systems, the postmaster should 
  be run without console and less verbose.
  This will be dealt with elsewhere...

 2.6) Create user "openride"

  While the postmaster is still running, 
      as user "postgres" open another terminal, 


  and after sourcing  /opt/postgres/opernride.conf

  as user postgres run the script

  "/opt/postgres/installationscripts/03createUser"

  to create a postgres user named "openride" run  
  
  "/opt/postgres/installationscripts/03createUser"

 When running for the first time, You may expect the following output:
   
 dropuser: removal of role "openride" failed: ERROR:  role "openride" does not exist
 
> The Error message is no reason to be alarmed, it just says that a role named
> "openride" has not been created yet.

 Next, You will be prompted for the password for openride.

> Enter password for new role: 

 To follow this setup, enter "openride" here. As this is the password that other 
 components will use to access the demo setup. 

>  Enter it again: 
 
 Confirm the password "openride" . You may change it anytime later.

 2.7) Create Database "openride"

 Keep beeing logged in as user "postgres" , keep the 
 postmaster still running.
 Source configuration

  . /opt/postgres/etc/openride.config

 To create a database named "openride" 
 run script 

  /opt/postgres/installationScripts/04.createDatabase

 This will create an empty database for openride silently.
 It will also try to delete an already existing db named 
 "openride". Any error messages about this database 
 not existing ....

>  ERROR:  database "openride" does not exist

 ... may therefore happyly ignored.
 

 2.8) Enable PostGIS extensions in openride db

 While the postmaster is still running,
 source configuration

  . /opt/postgres/etc/openride.config

 To enable PostGIS extensions in openride, run script:

  /opt/postgres/installationScripts/05.enablePostgis


2.9 Populate Database with initial data

While the postmaster is still running,
source configuration

  . /opt/postgres/etc/openride.config

Run script
 
  /opt/postgres/installationScripts/06.populateDatabase 

twice

06.populate Database runs the content of the original openride
database into our local db.
This will create about 60 Errors on the first run,
and still about 14  errors on the second run:

==== snip ====
 pg_restore: [archiver (db)] could not execute query: ERROR:  function public.st_box2d_in(cstring) does not exist
    Command was: DROP FUNCTION public.st_box2d_in(cstring);
 WARNING: errors ignored on restore: 14
=== snap ==== 

 Note: unfortunately, openride originally came with a binary dump.
 Thus, the errors could not have been totally liquidated.
 However, we found that we finally got a usable db following 
 the steps above!

 TODO: someone should set out and do a ASCII dump, so we can start over
 freshly. 



 3) Glassfish

 Unzip the glassfish-3.0.1.zip  archive into the directory
   
    /opt/openride/glassfish/glassfish-3.0.1/

 after that, something the directory 

    /opt/openride/glassfish/glassfish-3.0.1/

 should contain  the directory  

  glassfishv3


 next, log in as user postgres, and start one of the  scriptss

  /opt/postgres/installscripts


  source the glassfish configuration:

  .  /opt/openride/etc/openride.glassfish.conf 



  then run script 


  "/opt/openride/scripts/01.create_jdbc_resource.sh"

 to create a jdbc resourcre. 

 The output should be something like:

> $ ./01.create_jdbc_resource.sh 
`/opt/openride/archives/postgresql-8.4-701.jdbc4.jar' -> `/opt/openride/glassfish/glassfish-3.0.1/glassfishv3/glassfish//lib/postgresql-8.4-701.jdbc4.jar'
>Waiting for DAS to start ..............
>Started domain: domain1
>Domain location: /opt/openride/glassfish/glassfish-3.0.1/glassfishv3/glassfish/domains/domain1
>Log file: /opt/openride/glassfish/glassfish-3.0.1/glassfishv3/glassfish/domains/domain1/logs/server.log
>Admin port for the domain: 4848
>Command start-domain executed successfully.
>
>Command create-jdbc-connection-pool executed successfully.
>

watch out for the line: 

> Command create-jdbc-connection-pool executed successfully.

since it indicates success


next create a security realm for jORide to run in:
while the glassfish server is still running, run script 

/opt/openride/scripts/02.create_jdbc_realm:


> -bash-4.2$ ./02.create_jdbc_realm 
> com.sun.enterprise.admin.cli.CommandException: remote failure: Authrealm named jdbc_realm not found.
>
>
> Command delete-auth-realm failed.
> com.sun.enterprise.admin.cli.CommandException: remote failure: A JDBC resource named jdbc/openride does not exist.
>
>
> Command delete-jdbc-resource failed.
>
> Command create-jdbc-resource executed successfully.
>
> Command create-auth-realm executed successfully.
> configs.config.server-config.security-service.auth-realm.jdbc_realm.property.digest-algorithm=MD5
>
> Command set executed successfully.
>

 Watch out for the line

> Command set executed successfully.

 to appear, since it indicates successful operation
 the messages about previously failed delete operations may be safely ignored. 



















