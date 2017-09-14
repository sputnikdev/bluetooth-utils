[![Build Status](https://travis-ci.org/sputnikdev/bluetooth-utils.svg?branch=master)](https://travis-ci.org/sputnikdev/bluetooth-utils)
[![Coverage Status](https://coveralls.io/repos/github/sputnikdev/bluetooth-utils/badge.svg?branch=master)](https://coveralls.io/github/sputnikdev/bluetooth-utils?branch=master)
[![Join the chat at https://gitter.im/sputnikdev/bluetooth-utils](https://badges.gitter.im/sputnikdev/bluetooth-utils.svg)](https://gitter.im/sputnikdev/bluetooth-utils?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
# bluetooth-utils

A small library of utility classes to work with Bluetooth Smart objects.

**Features:**

_Bluetooth Uniform Resource Locator (URL)._ A class which represents bluetooth resources, such as: bluetooth adapters, bluetooth devices, GATT services, GATT characteristics or GATT fields. For example:

```java
URL url = new URL("/54:60:09:95:86:01/11:22:33:44:55:66/0000180f-0000-1000-8000-00805f9b34fb/00002a19-0000-1000-8000-00805f9b34fb/Level");
```
