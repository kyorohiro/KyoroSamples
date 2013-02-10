#!/bin/sh

adb uninstall info.kyorohiro.samples.android.emma.test
adb uninstall info.kyorohiro.samples.android.emma
ant emma debug install
cd tests
ant emma debug install test

