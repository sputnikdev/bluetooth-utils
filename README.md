[![Maven Central](https://img.shields.io/maven-central/v/org.sputnikdev/bluetooth-utils.svg)](https://mvnrepository.com/artifact/org.sputnikdev/bluetooth-utils)
[![Build Status](https://travis-ci.org/sputnikdev/bluetooth-utils.svg?branch=master)](https://travis-ci.org/sputnikdev/bluetooth-utils)
[![Coverage Status](https://coveralls.io/repos/github/sputnikdev/bluetooth-utils/badge.svg?branch=master)](https://coveralls.io/github/sputnikdev/bluetooth-utils?branch=master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/42d4968bc4ae4474b2cda4d01f8e4d56)](https://www.codacy.com/app/vkolotov/bluetooth-utils?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=sputnikdev/bluetooth-utils&amp;utm_campaign=Badge_Grade)
[![Join the chat at https://gitter.im/sputnikdev/bluetooth-utils](https://badges.gitter.im/sputnikdev/bluetooth-utils.svg)](https://gitter.im/sputnikdev/bluetooth-utils?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
# bluetooth-utils

A small library of utility classes to work with Bluetooth Smart objects.

**Features:**

_Bluetooth Uniform Resource Locator (URL)._ 

![Bluetooth URL diagram](url.png?raw=true "URL component diagram")

A utility class that represents a Uniform Resource Locator for bluetooth resources, 
e.g. bluetooth adapters, bluetooth devices, GATT services, GATT characteristics and GATT fields.

For example, if you have an adapter with MAC address B8:27:EB:60:0C:43, a device with MAC address 54:60:09:95:86:01 
which is connected to the adapter, the device has a GATT service with UUID 0000180f-0000-1000-8000-00805f9b34fb, 
the service has a characteristic with UUID 00002a19-0000-1000-8000-00805f9b34fb and the characteristic has a field 
called "Level", then a URL for the field can be:
```
/B8:27:EB:60:0C:43/54:60:09:95:86:01/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level
```
Or a URL for the service can be:
```
/B8:27:EB:60:0C:43/54:60:09:95:86:01/0000180f-0000-1000-8000-00805f9b34fb
```
Similarly, it is easy to define a URL for other components, e.g. adapters, devices and characteristics.

If there are more than one protocol used to access Bluetooth devices (e.g. DBus, serial interface etc), 
then it is possible to define protocol as well:
```
tinyb://B8:27:EB:60:0C:43/54:60:09:95:86:01/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level
```

A simple usage would be:

```java
URL url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level");
```

The class is reasonable documented, check its [javadoc](src/main/java/org/sputnikdev/bluetooth/URL.java).
More examples can be found [here](src/test/java/org/sputnikdev/bluetooth/URLTest.java).

---
## Contribution

To build the project with maven:
```bash
mvn clean install
```

To cut a new release and upload it to the Maven Central Repository:
```bash
mvn release:prepare -B
mvn release:perform
```
Travis CI process will take care of everything, you will find a new artifact in the Maven Central repository when the release process finished successfully.