#!/usr/bin/env bash
#编译+部署order站点

#需要配置如下参数
# 项目路径, 在Execute Shell中配置项目路径, pwd 就可以获得该项目路径
# export PROJ_PATH=这个jenkins任务在部署机器上的路径

# 输入你的环境上tomcat的全路径
# export TOMCAT_APP_PATH=tomcat在部署机器上的路径

### base 函数
killTomcat()
{
    pid=`ps -ef|grep arcsoft-face|grep java|awk '{print $2}'`
    echo "arcsoft-face Id list :$pid"
    if [ "$pid" = "" ]
    then
      echo "no arcsoft-face pid alive"
    else
      kill -9 $pid
    fi
}
cd $PROJ_PATH/arcsoftface/ArcSoftFaceDemo-master
mvn clean install

# 停tomcat
# killTomcat

# 删除原有工程
# rm -rf $TOMCAT_APP_PATH/webapps/ROOT
# rm -f $TOMCAT_APP_PATH/webapps/ROOT.war
# rm -f $TOMCAT_APP_PATH/webapps/arcsoft-face-1.0.0-SNAPSHOT.war
rm -f /testjar/arcsoft-face-1.0.0-SNAPSHOT.jar

# 复制新的工程
# cp $PROJ_PATH/arcsoftface/ArcSoftFaceDemo-master/target/arcsoft-face-1.0.0-SNAPSHOT.jar $TOMCAT_APP_PATH/webapps/
cp $PROJ_PATH/arcsoftface/ArcSoftFaceDemo-master/target/arcsoft-face-1.0.0-SNAPSHOT.jar /testjar/
# cd $TOMCAT_APP_PATH/webapps/
# mv arcsoft-face-1.0.0-SNAPSHOT.war ROOT.war

# 启动Tomcat
cd /testjar
sudo java -jar arcsoft-face-1.0.0-SNAPSHOT.jar &
