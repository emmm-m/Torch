package com.bucai.torch.util

/**
 * Created by zia on 2018/6/26.
 */
public fun getStar(star: Int): String {
    return when (star) {
        0 -> ""
        1 -> "⭐️"
        2 -> "⭐⭐"
        3 -> "⭐⭐⭐"
        4 -> "⭐⭐⭐⭐"
        else -> "⭐⭐⭐⭐⭐"
    }
}