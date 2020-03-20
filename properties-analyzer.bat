
set PATH=C:\Program Files\Java\jdk1.8.0_131\bin;%PATH%
java -jar .\target\properties-analyzer-0.0.1.jar ^
    --a=test --f=application-test.properties ^
    --a=uat --f=application-uat.properties ^
    --a=preprod --f=application-preprod.properties ^
    --a=prod --f=application-prod.properties ^
    --o=diffs.html
