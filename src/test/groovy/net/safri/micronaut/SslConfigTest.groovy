package net.safri.micronaut

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.client.DefaultHttpClient
import io.micronaut.http.client.HttpClientConfiguration
import io.micronaut.http.client.RxHttpClient
import io.micronaut.inject.qualifiers.Qualifiers
import io.micronaut.runtime.server.EmbeddedServer
import io.micronaut.test.annotation.MicronautTest
import spock.lang.Specification

import javax.inject.Inject

@MicronautTest
class SslConfigTest extends Specification {

    public final static String DN = "CN=client1.test.example.com, OU=IT, O=Whatever, L=Munich, ST=Bavaria, C=DE, EMAILADDRESS=info@example.com"

    @Inject
    ApplicationContext ctx

    @Inject
    EmbeddedServer embeddedServer

    @Inject
    Client1 client1

    // works
    def "instance"() {
        when:
        def c = new DefaultHttpClient(embeddedServer.getURL(), ctx.getBean(HttpClientConfiguration, Qualifiers.byName("client1")))

        then:
        c.toBlocking().retrieve(HttpRequest.GET("/"), String) == DN
    }

    // fails
    def "bean"() {
        when:
        def c = ctx.getBean(RxHttpClient, Qualifiers.byName("client1"))

        then:
        c.toBlocking().retrieve(HttpRequest.GET("/"), String) == DN
    }

    // fails
    def "declarative"() {
        expect:
        client1.test().body() == "CN=client1.test.example.com, OU=IT, O=Whatever, L=Munich, ST=Bavaria, C=DE, EMAILADDRESS=info@example.com"
    }
}
