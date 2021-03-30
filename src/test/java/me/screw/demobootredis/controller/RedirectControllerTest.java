package me.screw.demobootredis.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RedirectControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void getRedirectToken() throws Exception {
        mockMvc.perform(get("/redirect/token"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/token"))
                ;
    }

    @Test
    public void getRedirectForm() throws Exception{
        mockMvc.perform(get("/redirect/form"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/form"))
        ;
    }
}