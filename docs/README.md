## 欢迎使用Snail Log！

### 导言

在每个中、大型的应用程序中都会包含日志记录功能，虽然目前市面上已有很多开源的日志API，可以免费使用而且非常的成熟。也许是因为对这些日志工具的了解不够深入，也许是这些API的的功能确实有所欠缺，他们并不能很好的满足我的需求，所以我决定编写一个自己的日志工具。Snail Log第一版本正式发布于2020年8月，Slog将以轻量级和灵活性为核心，尽量减少资源占用，同时方便扩展。

第一个开源工具，希望不烂尾。

- [认识Snail Log](know-slog.md)

- [javadoc](apidocs/index.html)

- 使用API
  - [使用Slog生成第一条日志](slog-build-api.md)
  - [使用Filter](filter-logger.md)
  - [使用Appender](appender-logger.md)
  - [使用Layout](layout-logger.md)

- 配置
  -[properties配置](properties-config.md)
  -[javaApi配置](javaapi-config.md)
  -[xml配置](xml-config.md)