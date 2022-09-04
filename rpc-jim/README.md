# 工程简介
bff模板代码

# 延伸阅读
1、运行命令 mvn archetype:create-from-project -Darchetype.properties=archetype.properties
2.然后进入target/generated-sources/archetype/
3.pom.xml文件加上仓库信息
<distributionManagement>
<repository>
<id>lt-releases</id>
<name>nexus Repository RELEASES</name>
<url>http://47.97.230.127:18081/repository/maven-releases/</url>
</repository>
<snapshotRepository>
<id>lt-snapshots</id>
<name>nexus Repository SNAPSHOTS</name>
<url>http://47.97.230.127:18081/repository/maven-snapshots/</url>
</snapshotRepository>
</distributionManagement>

4.修改target/generated-sources/archetype/META-INF/maven/archetype-metadata.xml 对应module改为如下
<module id="${rootArtifactId}-sdk" dir="__rootArtifactId__-sdk" name="${rootArtifactId}-sdk">
<module id="${rootArtifactId}-server" dir="__rootArtifactId__-server" name="${rootArtifactId}-server">
5.修改__rootArtifactId__-server pom.xml 自身的artifactId
<artifactId>${rootArtifactId}-server</artifactId>
添加依赖
<dependency>
<groupId>${groupId}</groupId>
<artifactId>${rootArtifactId}-sdk</artifactId>
<version>${version}</version>
</dependency>
6.修改__rootArtifactId__-sdk pom.xml 自身的artifactId
<artifactId>${rootArtifactId}-sdk</artifactId>
7.运行命令 mvn deploy 上传到私服
8.在idea上创建项目的时候，选择从原型中创建，添加原型。添加完成后，选择对应的原型。
