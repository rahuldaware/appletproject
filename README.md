## Applet Project

### How to setup local environment

1. Got to terminal and type 
```
git clone git@github.com:rahuldaware/appletproject.git
```
2. Use maven to build the project. Run
```
cd appletproject && mvn clean package
```
3. Download IntelliJ IDEA Community Edition

4. After starting IntelliJ, Click **Open Project** -> **Browse appletproject from file explorer** -> **Click Open**. IntelliJ is will open the project and automatically download maven dependencies.

5. To run the applet, go to src/main/java/Main.java file and click run button

### How to setup Maria DB (Works on Ubuntu)
Run following commands:

1. First update your ubuntu : 
```
sudo apt update
``` 
2. Install MariaDB Server
```
sudo apt install mariadb-server
``` 
3. Check status of server. If the status says active, then mariaDB has successfully started
```
sudo systemctl status mariadb
```

### How to create a new user to connect using JDBC client
Run following commands:

1. Login to mysql client using root privileges
```
sudo mysql
```
2. On the mysql CLI, create a new user
```
CREATE USER 'rdaware'@'localhost' IDENTIFIED BY 'password';
```
3. Grant all privileges to this user
```
GRANT ALL PRIVILEGES ON *.* TO 'rdaware'@'localhost' WITH GRANT OPTION;
```
4. Flush privileges so that new user is updated in MariaDB. 
```
FLUSH PRIVILEGES;
```
5. New user is now created. Try exiting the CLI and logging back in with
```
mysql -u rdaware -p
On next prompt, enter password
```
6. Create table
```
CREATE TABLE users(username VARCHAR(20), password VARCHAR(20), signup_date DATE, PRIMARY KEY(username));
```
