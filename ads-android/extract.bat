cd /d %~dp0
cd bin
apktool d main-release.apk apk-apktool &mkdir apk-extract & unzip -o -d apk-extract main-release.apk & dex2jar apk-extract\classes.dex 