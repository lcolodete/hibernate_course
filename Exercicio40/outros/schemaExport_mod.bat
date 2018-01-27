SET CLASSPATH=..\bin
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\hibernate3.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\commons-logging-1.0.4.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\dom4j-1.6.1.jar
SET CLASSPATH=%CLASSPATH%;c:\eclipse\_Curso de Hibernate\Hibernate32\lib\commons-collections-2.1.1.jar

java org.hibernate.tool.hbm2ddl.SchemaExport --quiet --text --output=schema_ex40.rtf --config=hibernate.cfg.xml --delimiter=; --format

pause

