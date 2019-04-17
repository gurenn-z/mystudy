# mystudy

# 20190417 -- RabbitMQ
rabbitmq部署在阿里云轻量级服务器上，centos7，用户权限root

下面是配置时遇到的的一些坑：
```
# 关闭防火墙，不然外网访问不了
systemctl stop firewalld

添加rabbitmq登录用户：admin

#修改配置文件：rabbit.app
（笔者路径为 /usr/lib/rabbitmq/lib/rabbitmq_server-3.7.3/ebin/rabbit.app ---->  ）loopback_users  中的[<<"guest">>] 改为 []

#修改配置文件rabbitmq.config（笔者路径为 /etc/rabbitmq/rabbitmq.config  ）
#保存以下内容
[
{rabbit, [{tcp_listeners, [5672]}, {loopback_users, []}]}
].

#启动服务
systemctl start rabbitmq-server

#查看状态
systemctl status rabbitmq-server

#设置为开机启动
systemctl enable rabbitmq-server

# 以 admin 身份登入 rabbitmq 后台管理界面，可以手动添加一个exchange和一个queue
```
