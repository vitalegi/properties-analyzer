# README

Aim of this project is to compare different properties file, searching for properties that don't match.

## Compile

```bash
set PATH=C:\a\software\apache-maven-3.5.0\bin;C:\Program Files\Java\jdk1.8.0_131\bin;%PATH%
set JAVA_HOME=C:\Program Files\Java\jdk1.8.0_131
mvn clean package
```

## Run

```bash
set PATH=C:\Program Files\Java\jdk1.8.0_131\bin;%PATH%
java -jar .\target\properties-analyzer-0.0.1.jar <OPTIONS>
```

### OPTIONS

`--a=alias1 --f=path\to\file1.properties --a=alias2 --f=path\to\file2.properties --a=alias3 --f=path\to\file3.properties --o=path\to\output\file`

`--a` _alias_

Alias of the next properties file declared.


`--f` _filepath_

Path where properties file is located.

`--o` _filepath_

Path where to store the resulting analysis

#### Example usage

`--a=test --f=test-application.properties --a=uat --f=uat-application.properties --a=preprod --f=preprod-application.properties --a=prod --f=prod-application.properties --o=diffs.html`

Will analyze the files _test-application.properties_ (test), _uat-application.properties_ (uat), _preprod-application.properties_ (preprod), _prod-application.properties_ (prod), printing the results to file _diffs.html_.