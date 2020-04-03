
# 基于CQHTTP镜像
FROM richardchien/cqhttp:latest

LABEL maintainer="vwmin"

EXPOSE 5700 9000

# 添加java环境
ADD docker-properties/jdk-13.0.1_linux-x64_bin.tar.gz /usr/local/
ENV JAVA_HOME=/usr/local/jdk-13.0.1
ENV CLASSPATH=$JAVA_HOME/lib/dt.jar:$JAVA_HOME/lib/tools.jar
ENV PATH=$PATH:$JAVA_HOME/bin

ENV COOLQ_ACCOUNT=212746897
# 备用账号
# ENV COOLQ_ACCOUNT=926045191 

# 事件上报地址
ENV CQHTTP_POST_URL=http://localhost:5699

# 允许通过api接口访问数据
ENV CQHTTP_SERVE_DATA_FILES=yes

# 访问VNC的密码
ENV VNC_PASSWD=justmiss.coolq

# coolq pro
ENV COOLQ_URL=https://dlsec.cqp.me/cqp-tuling

# 设置事件过滤器
ENV CQHTTP_EVENT_FILTER=filter.json

# 设置access_token
#ENV CQHTTP_ACCESS_TOKEN=justmiss.coolq

# 写入事件过滤配置
COPY docker-properties/filter.json /home/user/coolq/app/io.github.richardchien.coolqhttpapi/

# 切换工作目录
WORKDIR /home/user/

# 为启动jar包做准备
RUN mkdir bot
COPY target/coolq.jar app.jar
COPY docker-properties/kill.sh bot
COPY docker-properties/start.sh bot

ENTRYPOINT ["sh", "bot/start.sh"]





