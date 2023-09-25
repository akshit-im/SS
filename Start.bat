echo %cd%
set PATH=D:\Tools\jdk-17.0.6\bin;"%path%
start /max "zipkin-server" java -jar D:\Tools\zipkin-server-2.24.0-exec.jar
pause
start /max "discovery-server" java -jar "%cd%\discovery-server\target\discovery-server-1.0.0.jar"
pause
start /max "config-server" java -jar "%cd%\config-server\target\config-server-1.0.0.jar"
pause
start /max "api-gateway" java -jar "%cd%\api-gateway\target\api-gateway-1.0.0.jar"
start /max "user-service" java -jar "%cd%\user-service\target\user-service-1.0.0.jar"
start /max "auth-service" java -jar "%cd%\auth-service\target\auth-service-1.0.0.jar"
#start /max "enquiry-service" java -jar "%cd%\enquiry-service\target\enquiry-service-1.0.0.jar"
start /max "product-service" java -jar "%cd%\product-service\target\product-service-1.0.0.jar"
#start /max "inventory-service" java -jar "%cd%\inventory-service\target\inventory-service-1.0.0.jar"
#start /max "sales-service" java -jar "%cd%\sales-service\target\sales-service-1.0.0.jar"
#start /max "order-service" java -jar "%cd%\order-service\target\order-service-1.0.0.jar"
#start /max "report-service" java -jar "%cd%\report-service\target\report-service-1.0.0.jar"