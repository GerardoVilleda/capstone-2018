#!/usr/bin/env bash

echo Started box customizations...

# Some cleanup stuff
docker stop cadvisor
docker rm cadvisor

sudo apt-get install software-properties-common
sudo apt-get update

# Oracle JDK
# https://launchpad.net/~webupd8team/+archive/ubuntu/java
sudo cp -f /home/vagrant/capstone/vagrant/webupd8team.list /etc/apt/sources.list.d/webupd8team.list

sudo apt-get update
# https://askubuntu.com/questions/190582/installing-java-automatically-with-silent-option
sudo echo debconf shared/accepted-oracle-license-v1-1 select true | \
     sudo debconf-set-selections
sudo echo debconf shared/accepted-oracle-license-v1-1 seen true | \
     sudo debconf-set-selections
sudo apt-get --assume-yes --force-yes install oracle-java8-installer
sudo apt-get update

sudo apt-get --assume-yes install maven
sudo apt-get update
#sudo apt-get --assume-yes install default-jdk

echo Finished bootstrap.sh
