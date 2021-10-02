package com.sungan.postApi.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("main")
class MainController {

    @GetMapping("")
    fun getMain(userId: Long, ) {

    }
}