# RabbitMq
RabbitMq publisher and consumer using spring boot
Installing rabbitmq
1.	Install Erlang with administrative rights https://www.erlang.org/downloads
2.	Install RabbitMq with administrative rights https://www.rabbitmq.com/install-windows.html
3.	Ensure Erlang and RabbitMq versions are compatible with each other
4.	Open command prompt and go to C:\Program Files\RabbitMQ Server\rabbitmq_server-3.9.12\sbin folder
5.	Run below command –
rabbitmq-plugins enable rabbitmq_management
6.	Login to RabbitMq dashboard http://localhost:15672/
Username and password - guest/guest

Authentication and Authorization –
1.	In dashboard, goto admin tab and create a new user. Guest user works only with localhost
2.	Click on the user and provide the required permissions

