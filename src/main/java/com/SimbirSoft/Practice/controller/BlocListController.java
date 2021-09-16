package com.SimbirSoft.Practice.controller;

import com.SimbirSoft.Practice.domain.util.BlockList;
import com.SimbirSoft.Practice.service.BlocListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/bloc")
public class BlocListController {

    @Autowired
    private BlocListService blocListService;

/*    @PostMapping
    public ResponseEntity addUser(@RequestBody BlockList blockList,
                                  @RequestParam Long userId) {
        try {

            return ResponseEntity.ok(blocListService.add(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }*/

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(blocListService.delete(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
