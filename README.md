# Capstone 2018 Project
Some files related with the project

## References

* Vagrant - https://www.vagrantup.com/
* Virtual Box - https://www.virtualbox.org/
* Docker configiration for Confluent components - https://docs.confluent.io/current/installation/docker/docs/configuration.html
* Streaming Queries using KSQL (Docker) - https://docs.confluent.io/current/ksql/docs/tutorials/basics-docker.html#ksql-quickstart-docker
* KSQL git repository - https://github.com/confluentinc/ksql
* Spring Boot - https://projects.spring.io/spring-boot/
* Spring Boot and Docker - https://spring.io/guides/gs/spring-boot-docker/

## Vagrant & Virtual Box
Before you get started make sure you have installed Virtual Box and Vagrant (see refernces). 

> Create a Vagrant cloud account to be able to download the available boxes.

### vagrant login
Before running `vagrant up` for the first time you will need to login with your account to be able to download the box. 

    $ vagrant login
    In a moment we will ask for your username and password to HashiCorp's
    Vagrant Cloud. After authenticating, we will store an access token locally on
    disk. Your login details will be transmitted over a secure connection, and
    are never stored on disk locally.

    If you do not have an Vagrant Cloud account, sign up at
    https://www.vagrantcloud.com

    Vagrant Cloud username or email: [USER NAME]
    Password (will be hidden): [PASSWORD]
    Token description (Defaults to "Vagrant login from ********"):
    You are now logged in.

### vagrant up
Run vagrant up in the folder that contains the file `Vagrantfile` to start the virtual box. 

    GVilleda@LT-00008855 MINGW64 /c/src/capstone-2018/vagrant/ubuntu
    $ vagrant up
    Bringing machine 'default' up with 'virtualbox' provider...
    ==> default: Box 'ubuntu/trusty64' could not be found. Attempting to find and install...
        default: Box Provider: virtualbox
        default: Box Version: >= 0
    ==> default: Loading metadata for box 'ubuntu/trusty64'
        default: URL: https://vagrantcloud.com/ubuntu/trusty64
    ==> default: Adding box 'ubuntu/trusty64' (v20180430.0.0) for provider: virtualbox
        default: Downloading: https://vagrantcloud.com/ubuntu/boxes/trusty64/versions/20180430.0.0/providers/virtualbox.box
        default: Download redirected to host: cloud-images.ubuntu.com
        default:

### vagrant box add --insecure
Use this option if you get an error while downloading the Vagrant box

    An error occurred while downloading the remote file. The error
    message, if any, is reproduced below. Please fix this error and try
    again.

    SSL certificate problem: unable to get local issuer certificate

Run the following:

    $ vagrant box add --insecure williamyeh/ubuntu-trusty64-docker

You should see:

    ==> box: Loading metadata for box 'williamyeh/ubuntu-trusty64-docker'
        box: URL: https://vagrantcloud.com/williamyeh/ubuntu-trusty64-docker
    This box can work with multiple providers! The providers that it
    can work with are listed below. Please review the list and choose
    the provider you will be working with.

    1) virtualbox
    2) vmware_desktop

    Enter your choice: 1
    ==> box: Adding box 'williamyeh/ubuntu-trusty64-docker' (v1.12.1.20160830) for provider: virtualbox
        box: Downloading: https://vagrantcloud.com/williamyeh/boxes/ubuntu-trusty64-docker/versions/1.12.1.20160830/providers/virtualbox.box
        box: Progress: 26% (Rate: 3920k/s, Estimated time remaining: 0:02:29)

Run vagrant up again:

    $ vagrant up

## vagrant ssh
Run the `vagrant ssh` command to logon to the virtual box. You will see the new ubuntu prompt similar to the following:

    $ vagrant ssh
    Welcome to Ubuntu 14.04.5 LTS (GNU/Linux 4.2.0-42-generic x86_64)

    * Documentation:  https://help.ubuntu.com/
    vagrant@localhost ~ $

## capstone folder
The virtual box has a mapped directory that points to the root of this git repository. 

    $ cd capstone

## docker-compose.yml
Inside the capstone folder there is a `docker-compose.yml` file that starts Kafka and KSQL required components. 
This docker compose file uses the host's network to start the docker containers. 
This is the method we use at SEI as it enables smooth connectivity with the containers from code running on the host comptuter (e.g. IDE)

    docker-compose up -d

## Start KSQL
Verify that KSQL is operational by running the following command. 

    docker-compose exec ksql-cli ksql http://localhost:8088

You should see: 

    vagrant@localhost ~/capstone $ docker-compose exec ksql-cli ksql http://localhost:8088

                    ===========================================
                    =        _  __ _____  ____  _             =
                    =       | |/ // ____|/ __ \| |            =
                    =       | ' /| (___ | |  | | |            =
                    =       |  <  \___ \| |  | | |            =
                    =       | . \ ____) | |__| | |____        =
                    =       |_|\_\_____/ \___\_\______|       =
                    =                                         =
                    =  Streaming SQL Engine for Apache Kafka® =
                    ===========================================

    Copyright 2017 Confluent Inc.

    CLI v4.1.1-SNAPSHOT, Server v4.1.1-SNAPSHOT located at http://localhost:8088

    Having trouble? Type 'help' (case-insensitive) for a rundown of how things work!

    ksql>

## Create a Stream 
Create a KSQL stream

    CREATE STREAM pageviews_original (viewtime bigint, userid varchar, pageid varchar) WITH (kafka_topic='pageviews', value_format='DELIMITED');

You should see:

    Message
    ----------------
    Stream created
    ----------------

## Browse Kafka
The docker-compose file includes a tool (kafdrop) used to browse the kafka cluster. 
Open your browser and navigate to http://localhost:9100. You can view and explore the content of the topics (including newly created streams).



## docker-compose-base.yml
This is the original docker-compose file with the addition of kafdrop (a kafka browser UI) and the capstone producer demo.

> NOTE: The producer will continue to send messages until stopped!

## kafka-producer
This sample project illustrates the use of SpringBoot to connect to Kafka.
The project has a component that publishes a heartbeat message every n (10) seconds. It is only to illustrate a possible way to publish messages.

### Build
Build using the following maven command (will also build a docker image)

    cd /home/vagrant/capstone/kafka-producer
    mvn clean package dockerfile:build

## Useful commands
Here are some useful commands (you be the judge)

    # Start the docker-compose group
    docker-compose up -d

    # Show running docker instances
    docker ps -a

    # Log (follow)
    docker logs -f [image name]

    # Stop it all
    docker-compose down

    # Stop individual instance
    docker stop [image name]

    # Remove individual instance
    docker rm [image name]

    # List docker images
    docker images

    # Start KSQL
    docker-compose exec ksql-cli ksql http://localhost:8088

    # Build the producer code (docker image will build if tools are available)
    cd /home/vagrant/capstone/kafka-producer
    mvn package

    # Run the producer code
    java -jar target/capstone-kafka-producer-0.1.0.jar 

