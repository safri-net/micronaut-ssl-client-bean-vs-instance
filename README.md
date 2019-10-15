# Micronaut HTTP SSL Client bean fails  

A declarative micronaut client 

```groovy
@Client("client1")
interface Client1 {
    @Get
    HttpResponse<String> test()
}
```

or a bean  

```groovy
ctx.getBean(RxHttpClient, Qualifiers.byName("client1"))
```

with following configuration:   

```yaml
micronaut:    
  ssl:
    enabled: true
  server:
    ssl:
      client-authentication: NEED
      key-store:
        path: classpath:test/certs/server.p12
        password: secret
      trust-store:
        path: classpath:test/certs/truststore
        password: secret
  http:
    services:
      client1:
        urls:
          - https://localhost:8443
        ssl:
          enabled: true
          client-authentication: NEED
          key-store:
            path: classpath:test/certs/client1.p12
            password: secret
```

Causes following error:
```
io.netty.handler.codec.DecoderException: javax.net.ssl.SSLHandshakeException: null cert chain
	at io.netty.handler.codec.ByteToMessageDecoder.callDecode(ByteToMessageDecoder.java:475)
	at io.netty.handler.codec.ByteToMessageDecoder.channelRead(ByteToMessageDecoder.java:283)
```

whereas a client instantiated
```groovy
new DefaultHttpClient(embeddedServer.getURL(), ctx.getBean(HttpClientConfiguration, Qualifiers.byName("client1")))
```

works.

