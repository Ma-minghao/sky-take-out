package com.sky.controller.user;


import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags = "店铺相关接口")
public class ShopController {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * @param status
     * @return
     */
    @PutMapping("/{status}")
    @ApiOperation("修改店铺营业状态")
    public Result setStatus(@PathVariable Integer status) {
        log.info("修改店铺营业状态:{}", status == 1 ? "营业中" : "打烊中");
        redisTemplate.opsForValue().set("SHOP_STATUS", status);

        return Result.success();

    }

    /**
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取店铺营业状态")
    public Result getStatus() {
        Integer status = (Integer) redisTemplate.opsForValue().get("SHOP_STATUS");
        log.info("获取店铺营业状态为:{}", status == 1 ? "营业中" : "打烊中");

        return Result.success(status);

    }
}