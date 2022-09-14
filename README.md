![输入图片说明](https://foruda.gitee.com/images/1663006749965065038/fca433db_7960656.png "屏幕截图")

第一个阶段使用 Nacos、Loadbalancer 和 OpenFeign 实现了跨服务的调用；
第二阶段使用 Sentinel、Nacos Config 和 Sleuth 实现了服务容错、配置管理和分布式链路追踪；
第三阶段使用 Gateway、Stream 和 Seata 实现了微服务网关、消息事件驱动和分布式事务。