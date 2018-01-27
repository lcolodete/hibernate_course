SET CLASSPATH=..\bin
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\hibernate3.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\ant-1.6.5.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\ant-antlr-1.6.5.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\ant-junit-1.6.5.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\ant-launcher-1.6.5.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\antlr-2.7.6.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\ant-swing-1.6.5.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\asm.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\asm-attrs.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\c3p0-0.9.0.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\cglib-2.1.3.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\checkstyle-all.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\cleanimports.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\commons-collections-2.1.1.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\commons-logging-1.0.4.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\concurrent-1.3.2.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\connector.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\dom4j-1.6.1.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\ehcache-1.2.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\jaas.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\jacc-1_0-fr.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\javassist.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\jaxen-1.1-beta-7.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\jboss-cache.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\jboss-common.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\jboss-jmx.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\jboss-system.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\jdbc2_0-stdext.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\jgroups-2.2.8.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\jta.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\junit-3.8.1.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\log4j-1.2.11.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\oscache-2.1.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\proxool-0.8.3.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\swarmcache-1.0rc2.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\syndiag2.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\versioncheck.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\xerces-2.6.2.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\xml-apis.jar
SET CLASSPATH=%CLASSPATH%;c:\jdk1.5.0_02\classes\oracle\jdbc\classes12.jar

java org.hibernate.tool.hbm2ddl.SchemaExport --quiet --text --output=schema_ex40.rtf --config=hibernate.cfg.xml --delimiter=; --format

pause

