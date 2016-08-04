basepath=$(cd `dirname $0`; pwd)
cd $basepath
rm -r bin
ant release