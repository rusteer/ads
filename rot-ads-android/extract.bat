cd /d %~dp0
cd bin
mkdir apk-extract & unzip -o -d apk-extract main-release.apk & dex2jar apk-extract\classes.dex 