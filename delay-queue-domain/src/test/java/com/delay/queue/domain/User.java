package com.delay.queue.domain;

import lombok.*;

/**
 * @description: TODO
 * @author: 贾诩
 * @date: 2021/3/27 18:55
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private String password;

    public static void main(String[] args) {
        User u=new User();
        u.setId(1L);
        u.setUsername("123");
        u.setPassword("123456");
        User.builder().id(1L).username("123").password("123456").build();
    }
}