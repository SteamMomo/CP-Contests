package com.example.kotlinbasics

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiInteface {

    @GET("/api/v1/all")
    fun getAllContests() : Call<List<AllContestModel>>

    @GET("/api/v1/codeforces")
    fun getCodeforcesContests() : Call<List<AllContestModel>>

    @GET("/api/v1/code_chef")
    fun getCodechefContests() : Call<List<AllContestModel>>

    @GET("/api/v1/leet_code")
    fun getLeetcodeContests() : Call<List<AllContestModel>>

    @GET("/api/v1/hacker_rank")
    fun getHackerrankContests() : Call<List<AllContestModel>>

    @GET("/api/v1/top_coder")
    fun getTopCoderContests() : Call<List<AllContestModel>>

    @GET("/api/v1/at_coder")
    fun getAtcoderContests() : Call<List<AllContestModel>>

    @GET("/api/v1/kick_start")
    fun getKickStartContests() : Call<List<AllContestModel>>

    @GET("/api/v1/hacker_earth")
    fun getHackerEarthContests() : Call<List<AllContestModel>>
}