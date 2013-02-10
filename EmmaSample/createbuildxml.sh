#!/bin/sh

android.bat update project --path .
dc=`pwd`
dd=`cygpath -w ${dc}`
android update test-project -m ${dd} -p tests/

