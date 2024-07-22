//package com.example.controller;
//import com.example.common.Result;
//import com.example.entity.Type;
//import com.example.entity.Params;
//import com.example.exception.CustomException;
//import com.example.service.TypeService;
//import com.github.pagehelper.PageInfo;
//import jakarta.annotation.Resource;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//
//@CrossOrigin
//@RestController
//@RequestMapping("/type")
//public class TypeController {
//
//    @Resource
//    private TypeService typeService;
//
//    @GetMapping("/search")
//    public Result findBySearch(Params params) {
//        PageInfo<Type> info = typeService.findBySearch(params);
//        return Result.success(info);
//    }
//
//    @PostMapping
//    public Result save(@RequestBody Type type) {
//
//
//        try {
//            if (type.getId() == null) {
//                typeService.add(type);
//            } else {
//                typeService.update(type);
//            }
//            return Result.success();
//        } catch (CustomException e) {
//            return Result.error(e.getMsg());
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public Result delete(@PathVariable Integer id) {
//        typeService.delete(id);
//        return Result.success();
//    }
//
//    @PutMapping("/delBatch")
//    public Result delBatch(@RequestBody List<Type> list) {
//        for (Type type : list) {
//            typeService.delete(type.getId());
//        }
//        return Result.success();
//    }
//
//}

package com.example.controller;

import com.example.common.Result;
import com.example.entity.Params;
import com.example.entity.Type;
import com.example.service.TypeService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/type")
public class TypeController {

    @Resource
    private TypeService typeService;

    @PostMapping
    public Result save(@RequestBody Type type) {
        if (type.getId() == null) {
            typeService.add(type);
        } else {
            typeService.update(type);
        }
        return Result.success();
    }

    @GetMapping("/search")
    public Result findBySearch(Params params) {
        PageInfo<Type> info = typeService.findBySearch(params);
        return Result.success(info);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        typeService.delete(id);
        return Result.success();
    }

    @PutMapping("/delBatch")
    public Result delBatch(@RequestBody List<Type> list) {
        for (Type type : list) {
            typeService.delete(type.getId());
        }
        return Result.success();
    }

}


