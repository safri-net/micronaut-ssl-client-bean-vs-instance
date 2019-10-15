package net.safri.micronaut


import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.server.netty.NettyHttpRequest
import io.netty.handler.ssl.SslHandler

import java.security.cert.X509Certificate

@Controller
class SslController {

    @Get
    HttpResponse<String> test(HttpRequest request) {
        def pipeline = (request as NettyHttpRequest).getChannelHandlerContext().pipeline()
        def sslHandler = pipeline.get(SslHandler)
        def certs = sslHandler.engine().getSession().getPeerCertificates()

        def cert = certs.first() as X509Certificate

        return HttpResponse.ok(cert.subjectDN.name)
    }

}
