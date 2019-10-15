package net.safri.micronaut

import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Get
import io.micronaut.http.client.annotation.Client

@Client("client1")
interface Client1 {
    @Get
    HttpResponse<String> test()
}