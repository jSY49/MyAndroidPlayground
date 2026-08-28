package com.example.apod.network

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor { // Interceptor : 요청이 서버로 나가기 전 (또는 응답이 돌아온 후) 가래채서 가공하는 훅
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()  //chain : 지금 진행 중인 요청/응답의 흐름을 나타내는 객체 => chain.request : 현재 까지 만들어진 request 객체를 가져옴 -> 요청 api url에서 api키는 안붙은채로 넘어옴
        val newUrl = original.url.newBuilder()
            .addQueryParameter("api_key", apiKey)
            .build()    // url에 apikey 붙여줌
        val newRequest = original.newBuilder()
            .url(newUrl)
            .build()
        return chain.proceed(newRequest)
    }
}
