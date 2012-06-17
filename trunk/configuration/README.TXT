############################################
###### CREATING A JORIDE INSTALLATION ######
############################################

 installing jORide from scratch can be done automatically
 using a number of scripts.
 

 1) OSLEVEL: 

   



    Run script oslevel/01.createusers.sh 

    To create users
    "postgres" with home directory "/opt/postgres"
    "openride" with home directory "/opt/openride"
   
 2) Postgres

    Install  readline-devel, geos-devel , proj-devel (Fedora)



 
     copy contents of svn/trunc/configuration/postgres
     recursively to /opt/postgres

     * source /opt/postgres/openride.conf

     to get environment right



 3) Glassfish
 

